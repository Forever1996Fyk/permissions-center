package com.ah.cloud.permissions.netty.infrastructure.event.message.impl;

import com.ah.cloud.permissions.biz.application.manager.ThreadPoolManager;
import com.ah.cloud.permissions.biz.infrastructure.util.JsonUtils;
import com.ah.cloud.permissions.enums.netty.MsgTypeEnum;
import com.ah.cloud.permissions.netty.application.manager.MessageStoreManager;
import com.ah.cloud.permissions.netty.application.manager.SessionManager;
import com.ah.cloud.permissions.netty.domain.message.body.MessageBody;
import com.ah.cloud.permissions.netty.domain.message.SingleMessage;
import com.ah.cloud.permissions.netty.domain.session.SingleSessionKey;
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
 * @description: 单个消息处理器
 * @author: YuKai Fan
 * @create: 2022-05-13 17:49
 **/
@Slf4j
@Component
public class SingleMessageHandler extends AbstractMessageHandler<SingleMessage> {
    private final static String LOG_MARK = "SingleMessageHandler";
    @Resource
    private SingleSendClientService singleClientService;

    @Resource
    private MessageStoreManager messageStoreManager;

    @Override
    protected SingleMessage convert(String message) {
        return JsonUtils.toBean(message, SingleMessage.class);
    }

    @Override
    protected void doHandle(ChannelHandlerContext context, MessageBody<SingleMessage> body) {
        SingleMessage singleMessage = body.getData();
        SessionService<SingleSession, SingleSessionKey> singleSessionService = SessionManager.getSingleSessionService();
        SingleSessionKey toSingleSessionKey = SingleSessionKey.builder().msgSourceEnum(body.getMsgSourceEnum()).sessionId(singleMessage.getToUserId()).build();
        SingleSessionKey fromSingleSessionKey = SingleSessionKey.builder().msgSourceEnum(body.getMsgSourceEnum()).sessionId(singleMessage.getFromUserId()).build();
        ImmutablePair<SingleSession, SingleSession> pairSession = singleSessionService.getPairSession(ImmutablePair.of(toSingleSessionKey, fromSingleSessionKey));
        singleClientService.sendAndAck(ImmutableTriple.of(toSingleSessionKey, pairSession.getLeft(), pairSession.getRight()), body, message -> {
            ThreadPoolManager.messageStoreThreadPool.execute(() -> messageStoreManager.saveMessageRecord(body));
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
        return MsgTypeEnum.SINGLE;
    }
}
