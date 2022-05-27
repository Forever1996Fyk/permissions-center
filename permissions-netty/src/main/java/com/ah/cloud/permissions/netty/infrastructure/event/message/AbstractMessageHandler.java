package com.ah.cloud.permissions.netty.infrastructure.event.message;

import com.ah.cloud.permissions.biz.infrastructure.util.JsonUtils;
import com.ah.cloud.permissions.netty.domain.dto.ReceiveMessageDTO;
import com.ah.cloud.permissions.netty.domain.message.body.MessageBody;
import com.ah.cloud.permissions.netty.infrastructure.event.factory.MessageHandlerFactory;
import com.google.common.base.Throwables;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-05-12 13:59
 **/
@Slf4j
@Component
public abstract class AbstractMessageHandler<T> implements MessageHandler {

    @Override
    public void handle(ChannelHandlerContext context, ReceiveMessageDTO dto) {
        log.info("{}[handle] start handle message, params is {}", getLogMark(), JsonUtils.toJSONString(dto));
        try {
            MessageBody<T> body = convertToBody(dto);
            doHandle(context, body);
        } catch (Exception e) {
            log.error("{}[handle] handle message failed params is {}, reason is {}", getLogMark(), JsonUtils.toJSONString(dto), Throwables.getStackTraceAsString(e));
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        MessageHandlerFactory.register(getMsgTypeEnum(), getCurrentMessageHandler());
    }

    private MessageBody<T> convertToBody(ReceiveMessageDTO dto) {
        return MessageBody.<T>builder()
                .formatEnum(dto.getFormatEnum())
                .msgId(dto.getMsgId())
                .msgTypeEnum(dto.getMsgTypeEnum())
                .timestamp(dto.getTimestamp())
                .msgSourceEnum(dto.getMsgSourceEnum())
                .data(convert(dto.getContent()))
                .build();
    }

    /**
     * 数据转换
     * @param message
     * @return
     */
    protected abstract T convert(String message);

    /**
     * 逻辑处理
     * @param context
     * @param body
     * @return
     */
    protected abstract void doHandle(ChannelHandlerContext context, MessageBody<T> body);

    /**
     * 日志标识
     * @return
     */
    protected abstract String getLogMark();

    /**
     * 当前消息处理器实现类
     * @return
     */
    protected abstract MessageHandler getCurrentMessageHandler();
}
