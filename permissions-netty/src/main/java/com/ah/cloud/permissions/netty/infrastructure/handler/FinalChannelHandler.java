package com.ah.cloud.permissions.netty.infrastructure.handler;

import com.ah.cloud.permissions.netty.domain.dto.ReceiveMessageDTO;
import com.ah.cloud.permissions.netty.infrastructure.event.message.MessageHandler;
import com.ah.cloud.permissions.netty.infrastructure.event.factory.MessageHandlerFactory;
import com.google.common.collect.Maps;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-05-11 22:24
 **/
@Slf4j
@ChannelHandler.Sharable
public class FinalChannelHandler extends SimpleChannelInboundHandler<ReceiveMessageDTO> {
    /**
     * 请求通道
     */
    private final static Map<String, Channel> CHANNEL_GROUP = Maps.newConcurrentMap();

    /**
     * 定义单例
     */
    private static class SingletonFinalChannelHandler {
        static final FinalChannelHandler INSTANCE = new FinalChannelHandler();
    }

    /**
     * 获取单例
     */
    public static FinalChannelHandler getInstance() {
        return FinalChannelHandler.SingletonFinalChannelHandler.INSTANCE;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ReceiveMessageDTO message) throws Exception {
        if (Objects.isNull(message)) {
            log.error("FinalChannelHandler[channelRead0] message is empty");
            return;
        }
        // 根据消息类型获取不同的处理器，处理逻辑
        MessageHandler handler = MessageHandlerFactory.getHandler(message.getMsgTypeEnum());
        handler.handle(channelHandlerContext, message);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        CHANNEL_GROUP.put(ctx.channel().id().asShortText(), ctx.channel());
        log.info("[{}] active, time is {}", ctx.channel().id().asLongText(), LocalDateTime.now());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        CHANNEL_GROUP.remove(ctx.channel().id().asShortText());
        log.info("[{}] inactive removed, time is {}", ctx.channel().id().asLongText(), LocalDateTime.now());

        // 在session中移除当前channel todo
    }
}
