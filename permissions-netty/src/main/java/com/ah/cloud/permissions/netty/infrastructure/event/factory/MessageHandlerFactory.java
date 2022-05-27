package com.ah.cloud.permissions.netty.infrastructure.event.factory;

import com.ah.cloud.permissions.enums.netty.MsgTypeEnum;
import com.ah.cloud.permissions.netty.infrastructure.event.message.MessageHandler;
import com.ah.cloud.permissions.netty.infrastructure.exception.MessageHandlerStrategyException;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Objects;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-05-12 11:49
 **/
@Slf4j
public class MessageHandlerFactory {

    /**
     * 消息处理器session
     */
    private final static Map<MsgTypeEnum, MessageHandler> HANDLER_MAP = Maps.newConcurrentMap();

    /**
     * 异常值
     */
    private final static String EXCEPTION_NOT_SUPPORT = "不支持该消息处理器类型策略";
    private final static String EXCEPTION_UNKNOWN = "未知的消息处理器类型策略";
    private final static String EXCEPTION_HANDLER_IS_NULL = "消息处理器为空";
    private final static String EXCEPTION_HANDLER_REPEATED = "该消息处理器已存在";

    /**
     * 注册消息处理器
     * @param msgTypeEnum
     * @param handler
     */
    public static void register(MsgTypeEnum msgTypeEnum, MessageHandler handler) {
        if (msgTypeEnum == null) {
            throw new MessageHandlerStrategyException(EXCEPTION_NOT_SUPPORT);
        }
        if (Objects.equals(msgTypeEnum, MsgTypeEnum.UNKNOWN)) {
            throw new MessageHandlerStrategyException(EXCEPTION_UNKNOWN);
        }
        if (Objects.isNull(handler)) {
            throw new MessageHandlerStrategyException(EXCEPTION_HANDLER_IS_NULL);
        }
        if (HANDLER_MAP.containsKey(msgTypeEnum)) {
            throw new MessageHandlerStrategyException(EXCEPTION_HANDLER_REPEATED);
        }
        log.info("消息处理器类型策略注册, 类型为{},", msgTypeEnum);
        HANDLER_MAP.put(msgTypeEnum, handler);
    }

    /**
     * 获取消息处理器
     * @param msgTypeEnum
     * @return
     */
    public static MessageHandler getHandler(MsgTypeEnum msgTypeEnum) {
        if (msgTypeEnum == null) {
            throw new MessageHandlerStrategyException(EXCEPTION_NOT_SUPPORT);
        }
        MessageHandler handler = HANDLER_MAP.get(msgTypeEnum);
        if (Objects.isNull(handler)) {
            throw new MessageHandlerStrategyException(EXCEPTION_HANDLER_IS_NULL);
        }
        return handler;
    }
}
