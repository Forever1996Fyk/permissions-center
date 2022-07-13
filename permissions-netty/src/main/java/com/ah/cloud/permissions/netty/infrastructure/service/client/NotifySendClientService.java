package com.ah.cloud.permissions.netty.infrastructure.service.client;

import com.ah.cloud.permissions.biz.application.helper.RedisKeyHelper;
import com.ah.cloud.permissions.biz.application.manager.ThreadPoolManager;
import com.ah.cloud.permissions.biz.application.manager.msgcenter.BackstageMsgHandleManager;
import com.ah.cloud.permissions.biz.application.strategy.cache.impl.RedisCacheHandleStrategy;
import com.ah.cloud.permissions.biz.domain.msg.push.dto.MsgAppPushUserDTO;
import com.ah.cloud.permissions.domain.common.ImResult;
import com.ah.cloud.permissions.enums.PushMsgModeEnum;
import com.ah.cloud.permissions.enums.netty.IMErrorCodeEnum;
import com.ah.cloud.permissions.netty.application.helper.SessionHelper;
import com.ah.cloud.permissions.netty.application.manager.MessageStoreManager;
import com.ah.cloud.permissions.netty.domain.message.body.MessageBody;
import com.ah.cloud.permissions.netty.domain.session.DistributionSession;
import com.ah.cloud.permissions.netty.domain.session.SingleSession;
import com.ah.cloud.permissions.netty.domain.session.key.SingleSessionKey;
import com.google.common.base.Throwables;
import com.google.common.collect.Sets;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @program: permissions-center
 * @description: 消息通知
 * @author: YuKai Fan
 * @create: 2022-05-20 10:54
 **/
@Slf4j
@Component
public class NotifySendClientService extends AbstractSendClientService<SingleSessionKey, SingleSession> {
    private final static String LOG_MARK = "NotifySendClientService";

    @Resource
    private SessionHelper sessionHelper;
    @Resource
    private MessageStoreManager messageStoreManager;
    @Resource
    private RedisCacheHandleStrategy redisCacheHandleStrategy;
    @Resource
    private BackstageMsgHandleManager backstageMsgHandleManager;

    @Override
    protected <T> ImResult<Void> doSend(SingleSession session, MessageBody<T> body) {
        ImResult<Void> result = new ImResult<>();
        Channel channel = session.getChannel();
        if (channel.isWritable()) {
            channel.writeAndFlush(body).addListener(future -> result.setSuccess(future.isSuccess()));
            result.setImErrorCodeEnum(IMErrorCodeEnum.SUCCESS);
            return result;
        } else {
            log.info("SingleClientService[doSend] channel can not writeable, channel outbound buffer full");
            try {
                channel.writeAndFlush(body).sync().addListener(future -> result.setSuccess(future.isSuccess()));
                result.setImErrorCodeEnum(IMErrorCodeEnum.SUCCESS);
                return result;
            } catch (InterruptedException e) {
                log.error("SingleClientService[doSend] message send failed, reason is {}", Throwables.getStackTraceAsString(e));
                result.setSuccess(false);
                result.setCode(IMErrorCodeEnum.MSG_SEND_FAILED.getCode());
                result.setMsg(Throwables.getStackTraceAsString(e));
                return result;
            }
        }
    }

    @Override
    public <T> boolean dispatchNode(SingleSessionKey sessionKey, MessageBody<T> body) {
        boolean result = false;
        try {
            /*
            1. 判断当前用户是否存在其他节点
                1.1 如果存在则通过监听redis 触发其他节点发送消息
                1.2 如果不存在则发送消息推送push, 并且存入离线消息列表
             */
            DistributionSession distributionSession = redisCacheHandleStrategy.getCacheObject(RedisKeyHelper.getImDistributionSessionKey(sessionKey.getSessionId()));

            if (Objects.isNull(distributionSession)) {
                // 第三方推送
                MsgAppPushUserDTO msgAppPushUserDTO = MsgAppPushUserDTO.builder()
                        .userIdList(Sets.newHashSet(sessionKey.getSessionId()))
                        .pushMsgModeEnum(PushMsgModeEnum.SINGLE_PUSH)
                        .msgSourceEnum(body.getMsgSourceEnum())
                        .content("你收到一条消息")
                        .title("及时消息")
                        .build();
                ThreadPoolManager.pushMsgThreadPool.execute(() -> backstageMsgHandleManager.sendAppPushMsg(msgAppPushUserDTO));

                // 记录离线列表
                ThreadPoolManager.offlineMessageStoreThreadPool.execute(() -> messageStoreManager.offlineMessageStore(body));
            } else {
                String imListenerNodeKey = RedisKeyHelper.getImListenerNodeKey(distributionSession.getHost(), distributionSession.getPort());
                redisCacheHandleStrategy.lpush(imListenerNodeKey, sessionHelper.buildMessageNodeDTO(body));
                log.info("{}[send] message send other node is {}", getLogMark(), imListenerNodeKey);
            }
            result = true;
        } catch (Throwable throwable) {
            log.error("SingleSendClientService[dispatchNode] single chat dispatch other node failed, singleSessionKey is {}, reason is {}", sessionKey.toString(), Throwables.getStackTraceAsString(throwable));
        }
        return result;
    }

    @Override
    protected String getLogMark() {
        return LOG_MARK;
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }
}

