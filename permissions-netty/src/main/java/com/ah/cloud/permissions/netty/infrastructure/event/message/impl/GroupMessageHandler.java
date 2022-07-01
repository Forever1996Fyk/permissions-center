package com.ah.cloud.permissions.netty.infrastructure.event.message.impl;

import com.ah.cloud.permissions.biz.infrastructure.util.JsonUtils;
import com.ah.cloud.permissions.enums.netty.MsgTypeEnum;
import com.ah.cloud.permissions.netty.application.manager.SessionManager;
import com.ah.cloud.permissions.netty.domain.message.GroupMessage;
import com.ah.cloud.permissions.netty.domain.message.body.MessageBody;
import com.ah.cloud.permissions.netty.domain.session.GroupSession;
import com.ah.cloud.permissions.netty.domain.session.key.GroupSessionKey;
import com.ah.cloud.permissions.netty.domain.session.key.SingleSessionKey;
import com.ah.cloud.permissions.netty.domain.session.SingleSession;
import com.ah.cloud.permissions.netty.infrastructure.event.message.AbstractMessageHandler;
import com.ah.cloud.permissions.netty.infrastructure.event.message.MessageHandler;
import com.ah.cloud.permissions.netty.infrastructure.service.client.GroupSendClientService;
import com.ah.cloud.permissions.netty.infrastructure.service.session.GroupSessionService;
import com.ah.cloud.permissions.netty.infrastructure.service.session.SessionService;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

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
    private GroupSendClientService groupSendClientService;

    @Override
    protected GroupMessage convert(String message) {
        return JsonUtils.stringToBean(message, GroupMessage.class);
    }

    @Override
    protected void doHandle(ChannelHandlerContext context, MessageBody<GroupMessage> body) {
        GroupMessage groupMessage = body.getData();
        GroupSessionService<GroupSession, GroupSessionKey> groupSessionService = SessionManager.getGroupSessionService();
        GroupSessionKey groupSessionKey = GroupSessionKey.builder().sessionId(groupMessage.getGroupId()).build();
        GroupSession groupSession = groupSessionService.get(groupSessionKey);

        SessionService<SingleSession, SingleSessionKey> singleSessionService = SessionManager.getSingleSessionService();
        SingleSessionKey fromSingleSessionKey = SingleSessionKey.builder().msgSourceEnum(body.getMsgSourceEnum()).sessionId(groupMessage.getFromUserId()).build();
        SingleSession fromSingleSession = singleSessionService.get(fromSingleSessionKey);

        groupSendClientService.sendAndAck(ImmutableTriple.of(groupSessionKey, groupSession, fromSingleSession.getChannel()), body, message -> {});
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
