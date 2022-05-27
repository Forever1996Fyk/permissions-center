package com.ah.cloud.permissions.netty.infrastructure.event.message.impl;

import com.ah.cloud.permissions.biz.infrastructure.util.JsonUtils;
import com.ah.cloud.permissions.enums.netty.MsgTypeEnum;
import com.ah.cloud.permissions.netty.application.manager.SessionManager;
import com.ah.cloud.permissions.netty.domain.message.AckMessage;
import com.ah.cloud.permissions.netty.domain.message.body.MessageBody;
import com.ah.cloud.permissions.netty.infrastructure.event.message.AbstractMessageHandler;
import com.ah.cloud.permissions.netty.infrastructure.event.message.MessageHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.Timeout;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-05-16 13:49
 **/
@Slf4j
@Component
public class AckMessageHandler extends AbstractMessageHandler<AckMessage> {

    private final static String LOG_MARK = "AckMessageHandler";

    @Override
    protected AckMessage convert(String message) {
        return JsonUtils.toBean(message, AckMessage.class);
    }

    @Override
    protected void doHandle(ChannelHandlerContext context, MessageBody<AckMessage> body) {
        Integer sequenceId = body.getData().getSequenceId();
        Timeout timeout = SessionManager.getAckMsgTimeoutList().remove(sequenceId);
        if (!Objects.isNull(timeout)) {
            timeout.cancel();
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
        return MsgTypeEnum.ACK;
    }
}
