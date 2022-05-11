package com.ah.cloud.permissions.enums.common;

import com.ah.cloud.permissions.exception.ErrorCode;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-04-06 21:36
 **/
public enum RedisErrorCodeEnum implements ErrorCode {

    /**
     * 操作Redis的key不能为空
     */
    OPS_KEY_NOT_NULL(1_10_0_001, "操作Redis的key不能为空"),

    /**
     * 操作redis获取值错误
     */
    OPS_GET_VALUE_ERROR(1_10_0_002, "操作redis获取值错误"),

    /**
     * 消息发布错误
     */
    PUBLISH_MESSAGE_ERROR(1_10_0_003, "消息发布错误"),
    ;


    private final int code;
    private final String msg;

    RedisErrorCodeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }
}
