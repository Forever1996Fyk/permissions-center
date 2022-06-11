package com.ah.cloud.permissions.netty.infrastructure.event.message.impl;

import com.ah.cloud.permissions.biz.infrastructure.util.JsonUtils;
import com.ah.cloud.permissions.enums.netty.MsgTypeEnum;
import com.ah.cloud.permissions.netty.application.manager.SessionManager;
import com.ah.cloud.permissions.netty.domain.message.body.MessageBody;
import com.ah.cloud.permissions.netty.domain.message.OfflineMessage;
import com.ah.cloud.permissions.netty.domain.session.key.SingleSessionKey;
import com.ah.cloud.permissions.netty.domain.session.SingleSession;
import com.ah.cloud.permissions.netty.infrastructure.event.message.AbstractMessageHandler;
import com.ah.cloud.permissions.netty.infrastructure.event.message.MessageHandler;
import com.ah.cloud.permissions.netty.infrastructure.service.client.SingleSendClientService;
import com.ah.cloud.permissions.netty.infrastructure.service.session.SessionService;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-05-19 16:03
 **/
@Slf4j
@Component
public class OfflineMessageHandler extends AbstractMessageHandler<OfflineMessage> {
    private final static String LOG_MARK = "OfflineMessageHandler";
    @Resource
    private SingleSendClientService singleClientService;

    @Override
    protected OfflineMessage convert(String message) {
        return JsonUtils.stringToBean(message, OfflineMessage.class);
    }

    @Override
    protected void doHandle(ChannelHandlerContext context, MessageBody<OfflineMessage> body) {
        OfflineMessage offlineMessage = body.getData();
        SessionService<SingleSession, SingleSessionKey> singleSessionService = SessionManager.getSingleSessionService();
        SingleSessionKey toSingleSessionKey = SingleSessionKey.builder().msgSourceEnum(body.getMsgSourceEnum()).sessionId(offlineMessage.getToUserId()).build();
        SingleSessionKey fromSingleSessionKey = SingleSessionKey.builder().msgSourceEnum(body.getMsgSourceEnum()).sessionId(body.getFromId()).build();
        ImmutablePair<SingleSession, SingleSession> pairSession = singleSessionService.getPairSession(ImmutablePair.of(toSingleSessionKey, fromSingleSessionKey));

        singleClientService.sendAndAck(ImmutableTriple.of(toSingleSessionKey, pairSession.getLeft(), pairSession.getRight().getChannel()), body, message -> {
            singleSessionService.remove(toSingleSessionKey);
        });
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
        return MsgTypeEnum.OFFLINE;
    }
}
