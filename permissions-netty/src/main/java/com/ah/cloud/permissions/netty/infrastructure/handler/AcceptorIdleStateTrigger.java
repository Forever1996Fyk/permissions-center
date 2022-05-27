package com.ah.cloud.permissions.netty.infrastructure.handler;

import com.ah.cloud.permissions.biz.infrastructure.util.AppUtils;
import com.ah.cloud.permissions.enums.netty.MsgSourceEnum;
import com.ah.cloud.permissions.enums.netty.MsgTypeEnum;
import com.ah.cloud.permissions.netty.application.manager.SessionManager;
import com.ah.cloud.permissions.netty.domain.dto.ReceiveMessageDTO;
import com.ah.cloud.permissions.netty.domain.message.body.MessageBody;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-05-16 14:45
 **/
@Slf4j
public class AcceptorIdleStateTrigger extends SimpleChannelInboundHandler<ReceiveMessageDTO> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ReceiveMessageDTO dto) throws Exception {
        if (!Objects.equals(dto.getMsgTypeEnum(), MsgTypeEnum.PING)) {
            ctx.fireChannelRead(dto);
        } else {
            // 因为心跳消息, 所以这里不直接用MessageHandler策略处理器, 比较繁琐的流程。
            ctx.pipeline().channel().writeAndFlush(buildPongMessageBody());
        }
    }

    private MessageBody<Object> buildPongMessageBody() {
        return MessageBody.builder()
                .msgId(AppUtils.randomLongId())
                .msgTypeEnum(MsgTypeEnum.PONG)
                .timestamp(System.currentTimeMillis())
                .msgSourceEnum(MsgSourceEnum.SERVER)
                .build();
    }


    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        IdleStateEvent event = (IdleStateEvent) evt;
        if (event.state() == IdleState.READER_IDLE) {
            log.info("AcceptorIdleStateTrigger[userEventTriggered] read timeout");
            SessionManager.removeSession(ctx.pipeline().channel());
        }
    }

    /**
     * 定义单例
     */
    private static class SingletonAcceptorIdleStateTrigger {
        static final AcceptorIdleStateTrigger INSTANCE = new AcceptorIdleStateTrigger();
    }

    /**
     * 获取单例
     */
    public static AcceptorIdleStateTrigger getInstance() {
        return AcceptorIdleStateTrigger.SingletonAcceptorIdleStateTrigger.INSTANCE;
    }

}
