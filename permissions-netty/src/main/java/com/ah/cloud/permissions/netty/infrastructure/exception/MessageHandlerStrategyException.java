package com.ah.cloud.permissions.netty.infrastructure.exception;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-05-12 13:52
 **/
public class MessageHandlerStrategyException extends RuntimeException {

    public MessageHandlerStrategyException(String message) {
        super(message);
    }
}
