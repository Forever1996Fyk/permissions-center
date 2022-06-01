package com.ah.cloud.permissions.netty.infrastructure.service.client;

import com.ah.cloud.permissions.biz.application.helper.RedisKeyHelper;
import com.ah.cloud.permissions.biz.application.manager.ThreadPoolManager;
import com.ah.cloud.permissions.biz.application.manager.msgcenter.BackstageMsgHandleManager;
import com.ah.cloud.permissions.biz.application.strategy.cache.impl.RedisCacheHandleStrategy;
import com.ah.cloud.permissions.biz.domain.msg.push.dto.MsgAppPushUserDTO;
import com.ah.cloud.permissions.biz.infrastructure.util.JsonUtils;
import com.ah.cloud.permissions.domain.common.ImResult;
import com.ah.cloud.permissions.enums.PushMsgModeEnum;
import com.ah.cloud.permissions.enums.common.IMErrorCodeEnum;
import com.ah.cloud.permissions.enums.netty.MsgSourceEnum;
import com.ah.cloud.permissions.netty.application.helper.SessionHelper;
import com.ah.cloud.permissions.netty.application.manager.MessageStoreManager;
import com.ah.cloud.permissions.netty.application.manager.SessionManager;
import com.ah.cloud.permissions.netty.domain.message.body.MessageBody;
import com.ah.cloud.permissions.netty.domain.session.DistributionSession;
import com.ah.cloud.permissions.netty.domain.session.GroupSession;
import com.ah.cloud.permissions.netty.domain.session.SingleSession;
import com.ah.cloud.permissions.netty.domain.session.key.GroupSessionKey;
import com.ah.cloud.permissions.netty.domain.session.key.SingleSessionKey;
import com.ah.cloud.permissions.netty.infrastructure.mq.redis.producer.GroupChatNodeListenerProducer;
import com.ah.cloud.permissions.netty.infrastructure.service.session.GroupSessionService;
import com.ah.cloud.permissions.netty.infrastructure.service.session.SessionService;
import com.google.common.base.Throwables;
import com.google.common.collect.Sets;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-05-13 14:31
 **/
@Slf4j
@Component
public class GroupSendClientService extends AbstractSendClientService<GroupSessionKey, GroupSession> {
    private final static String LOG_MARK = "GroupSendClientService";
    @Resource
    private SessionHelper sessionHelper;
    @Resource
    private MessageStoreManager messageStoreManager;
    @Resource
    private RedisCacheHandleStrategy redisCacheHandleStrategy;
    @Resource
    private BackstageMsgHandleManager backstageMsgHandleManager;
    @Resource
    private GroupChatNodeListenerProducer groupChatNodeListenerProducer;

    @Override
    protected <T> ImResult<Void> doSend(GroupSession session, MessageBody<T> body) {
        GroupSessionService<GroupSession, GroupSessionKey> groupSessionService = SessionManager.getGroupSessionService();
        GroupSessionKey groupSessionKey = GroupSessionKey.builder().sessionId(session.getGroupId()).build();
        Set<Long> groupMemberSet = groupSessionService.getGroupMemberSet(groupSessionKey);
        if (CollectionUtils.isEmpty(groupMemberSet)) {
            return ImResult.ofFailed(IMErrorCodeEnum.GROUP_CHAT_FAILED_MEMBER_IS_EMPTY, String.valueOf(session.getGroupId()));
        }

        Set<SingleSessionKey> singleSessionKeys = groupMemberSet.stream().map(groupMemberId -> buildSingleSessionKey(body.getMsgSourceEnum(), groupMemberId)).collect(Collectors.toSet());
        SessionService<SingleSession, SingleSessionKey> singleSessionService = SessionManager.getSingleSessionService();
        Map<SingleSessionKey, SingleSession> sessionKeySingleSessionMap = singleSessionService.listByKeys(singleSessionKeys);
        for (Map.Entry<SingleSessionKey, SingleSession> entry : sessionKeySingleSessionMap.entrySet()) {
            SingleSession singleSession = entry.getValue();
            SingleSessionKey singleSessionKey = entry.getKey();
            if (Objects.isNull(singleSession)) {
                /*
                1. 判断当前用户是否存在其他节点
                    1.1 如果存在则通过监听redis 触发其他节点发送消息
                    1.2 如果不存在则发送消息推送push, 并且存入离线消息列表
                 */
                DistributionSession distributionSession = redisCacheHandleStrategy.getCacheObject(RedisKeyHelper.getImDistributionSessionKey(singleSessionKey.getSessionId()));
                if (Objects.isNull(distributionSession)) {
                    // 第三方推送
                    MsgAppPushUserDTO msgAppPushUserDTO = MsgAppPushUserDTO.builder()
                            .content(JsonUtils.toJSONString(body.getData()))
                            .userIdList(Sets.newHashSet(singleSessionKey.getSessionId()))
                            .pushMsgModeEnum(PushMsgModeEnum.SINGLE_PUSH)
                            .msgSourceEnum(body.getMsgSourceEnum())
                            .title(groupSessionKey.getGroupName())
                            .build();
                    ThreadPoolManager.pushMsgThreadPool.execute(() -> backstageMsgHandleManager.sendAppPushMsg(msgAppPushUserDTO));

                    // 是否记录离线列表
                    ThreadPoolManager.offlineMessageStoreThreadPool.execute(() -> messageStoreManager.offlineMessageStore(body));
                } else {
                    groupChatNodeListenerProducer.sendMessage(sessionHelper.buildMessageNodeDTO(body));
                }
            } else {
                Channel channel = session.getChannel();
                if (channel.isWritable()) {
                    channel.writeAndFlush(body);
                } else {
                    log.info("SingleClientService[doSend] channel can not writeable, channel outbound buffer full");
                    try {
                        channel.writeAndFlush(body).sync();
                    } catch (InterruptedException e) {
                        log.error("SingleClientService[doSend] message send failed, reason is {}", Throwables.getStackTraceAsString(e));
                    }
                }
            }
        }
        return ImResult.ofSuccess();
    }

    private SingleSessionKey buildSingleSessionKey(MsgSourceEnum msgSourceEnum, Long userId) {
        return SingleSessionKey.builder()
                .msgSourceEnum(msgSourceEnum)
                .sessionId(userId)
                .build();
    }

    @Override
    protected String getLogMark() {
        return LOG_MARK;
    }

    @Override
    public <T> boolean dispatchNode(GroupSessionKey sessionKey, MessageBody<T> body) {
        return false;
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }
}
