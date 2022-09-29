package com.ah.cloud.permissions.biz.infrastructure.sequence.enums;

import com.ah.cloud.permissions.exception.ErrorCode;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022/9/16 15:55
 **/
public enum SequenceErrorCodeEnum implements ErrorCode {

    /**
     * 错误码
     */
    SEQUENCE_PRODUCER_NOT_EXISTED(1_10_000_0001, "[%s]序列生产者不存在");
    ;

    private final int code;

    private final String msg;

    SequenceErrorCodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public int getCode() {
        return 0;
    }

    @Override
    public String getMsg() {
        return null;
    }
}
