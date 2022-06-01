package com.ah.cloud.permissions.netty.infrastructure.event.message.impl;

import com.ah.cloud.permissions.biz.infrastructure.util.JsonUtils;
import com.ah.cloud.permissions.enums.common.IMErrorCodeEnum;
import com.ah.cloud.permissions.enums.netty.GroupTypeEnum;
import com.ah.cloud.permissions.enums.netty.MsgTypeEnum;
import com.ah.cloud.permissions.netty.application.manager.SessionManager;
import com.ah.cloud.permissions.netty.domain.message.ChatRoomMessage;
import com.ah.cloud.permissions.netty.domain.message.body.MessageBody;
import com.ah.cloud.permissions.netty.domain.session.ChatRoomSession;
import com.ah.cloud.permissions.netty.domain.session.key.ChatRoomSessionKey;
import com.ah.cloud.permissions.netty.domain.session.key.GroupSessionKey;
import com.ah.cloud.permissions.netty.domain.session.SingleSession;
import com.ah.cloud.permissions.netty.domain.session.key.SingleSessionKey;
import com.ah.cloud.permissions.netty.infrastructure.event.message.AbstractMessageHandler;
import com.ah.cloud.permissions.netty.infrastructure.event.message.MessageHandler;
import com.ah.cloud.permissions.netty.infrastructure.exception.IMBizException;
import com.ah.cloud.permissions.netty.infrastructure.service.client.ChatRoomSendClientService;
import com.ah.cloud.permissions.netty.infrastructure.service.session.GroupSessionService;
import com.ah.cloud.permissions.netty.infrastructure.service.session.SessionService;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-05-14 14:37
 **/
@Slf4j
@Component
public class ChatRoomMessageHandler extends AbstractMessageHandler<ChatRoomMessage> {
    private final static String LOG_MARK = "ChatRoomMessageHandler";

    @Resource
    private ChatRoomSendClientService chatRoomSendClientService;

    @Override
    protected ChatRoomMessage convert(String message) {
        return JsonUtils.toBean(message, ChatRoomMessage.class);
    }

    @Override
    protected void doHandle(ChannelHandlerContext context, MessageBody<ChatRoomMessage> body) {
        ChatRoomMessage chatRoomMessage = body.getData();
        GroupSessionService<ChatRoomSession, ChatRoomSessionKey> groupSessionService = SessionManager.getChatRoomSessionService();
        ChatRoomSessionKey groupSessionKey = ChatRoomSessionKey.builder().sessionId(chatRoomMessage.getRoomId()).build();
        ChatRoomSession chatRoomSession = groupSessionService.get(groupSessionKey);
        if (Objects.isNull(chatRoomSession)) {
            throw new IMBizException(IMErrorCodeEnum.CHAT_ROOM_NOT_EXISTED, String.valueOf(chatRoomMessage.getRoomId()));
        }

        SessionService<SingleSession, SingleSessionKey> singleSessionService = SessionManager.getSingleSessionService();
        SingleSessionKey singleSessionKey = SingleSessionKey.builder().sessionId(chatRoomMessage.getFromId()).msgSourceEnum(body.getMsgSourceEnum()).build();
        SingleSession singleSession = singleSessionService.get(singleSessionKey);
        if (Objects.isNull(singleSession)) {
            throw new IMBizException(IMErrorCodeEnum.USER_IM_NOT_BIND);
        }
        chatRoomSendClientService.chatRoomSend(ImmutablePair.of(chatRoomSession, singleSession), body);
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
        return MsgTypeEnum.CHAT_ROOM;
    }
}
