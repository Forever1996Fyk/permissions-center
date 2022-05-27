package com.ah.cloud.permissions.netty.infrastructure.event.message.impl;

import com.ah.cloud.permissions.biz.application.manager.ThreadPoolManager;
import com.ah.cloud.permissions.biz.infrastructure.util.JsonUtils;
import com.ah.cloud.permissions.enums.netty.GroupTypeEnum;
import com.ah.cloud.permissions.enums.netty.MsgTypeEnum;
import com.ah.cloud.permissions.netty.application.manager.MessageStoreManager;
import com.ah.cloud.permissions.netty.application.manager.SessionManager;
import com.ah.cloud.permissions.netty.domain.message.GroupMessage;
import com.ah.cloud.permissions.netty.domain.message.body.MessageBody;
import com.ah.cloud.permissions.netty.domain.session.GroupSession;
import com.ah.cloud.permissions.netty.domain.session.GroupSessionKey;
import com.ah.cloud.permissions.netty.domain.session.SingleSessionKey;
import com.ah.cloud.permissions.netty.domain.session.SingleSession;
import com.ah.cloud.permissions.netty.infrastructure.event.message.AbstractMessageHandler;
import com.ah.cloud.permissions.netty.infrastructure.event.message.MessageHandler;
import com.ah.cloud.permissions.netty.infrastructure.service.client.GroupSendClientService;
import com.ah.cloud.permissions.netty.infrastructure.service.client.SingleSendClientService;
import com.ah.cloud.permissions.netty.infrastructure.service.session.GroupSessionService;
import com.ah.cloud.permissions.netty.infrastructure.service.session.SessionService;
import com.google.common.collect.Lists;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @program: permissions-center
 * @description: 群消息处理器
 * @author: YuKai Fan
 * @create: 2022-05-14 14:13
 **/
@Slf4j
@Component
public class GroupMessageHandler extends AbstractMessageHandler<GroupMessage> {
    private final static String LOG_MARK = "GroupMessageHandler";

    @Resource
    private MessageStoreManager messageStoreManager;
    @Resource
    private GroupSendClientService groupSendClientService;
    @Resource
    private SingleSendClientService singleSendClientService;

    @Override
    protected GroupMessage convert(String message) {
        return JsonUtils.toBean(message, GroupMessage.class);
    }

    @Override
    protected void doHandle(ChannelHandlerContext context, MessageBody<GroupMessage> body) {
        GroupMessage groupMessage = body.getData();
        GroupSessionService<GroupSession, GroupSessionKey> groupSessionService = SessionManager.getGroupSessionService();
        GroupSessionKey groupSessionKey = GroupSessionKey.builder().sessionId(groupMessage.getGroupId()).groupTypeEnum(GroupTypeEnum.GROUP).build();
        GroupSession groupSession = groupSessionService.get(groupSessionKey);

        Set<Long> groupMemberIdList = groupSession.getGroupMemberIdList();

        SessionService<SingleSession, SingleSessionKey> singleSessionService = SessionManager.getSingleSessionService();
        SingleSessionKey fromSingleSessionKey = SingleSessionKey.builder().msgSourceEnum(body.getMsgSourceEnum()).sessionId(groupMessage.getFromUserId()).build();
        SingleSession fromSingleSession = singleSessionService.get(fromSingleSessionKey);
        Set<SingleSessionKey> singleSessionKeySet = groupMemberIdList.stream()
                .map(item -> SingleSessionKey.builder()
                        .msgSourceEnum(body.getMsgSourceEnum())
                        .sessionId(item)
                        .build()).collect(Collectors.toSet());
        Map<SingleSessionKey, SingleSession> sessionMap = singleSessionService.listByKeys(singleSessionKeySet);
        for (Map.Entry<SingleSessionKey, SingleSession> entry : sessionMap.entrySet()) {
            SingleSessionKey singleSessionKey = entry.getKey();
            SingleSession singleSession = entry.getValue();
            groupSendClientService.simpleSend(ImmutablePair.of(singleSessionKey, singleSession), body);
        }
    }

    @Override
    protected String getLogMark() {
        return LOG_MARK;
    }

    @Override
    protected MessageHandler getCurrentMessageHandler() {
        return this;
    }

    @Override
    public MsgTypeEnum getMsgTypeEnum() {
        return MsgTypeEnum.GROUP;
    }
}
