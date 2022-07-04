package com.ah.cloud.permissions.enums.common;

import com.ah.cloud.permissions.exception.ErrorCode;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-04 15:29
 **/
public enum JasyptErrorCodeEnum implements ErrorCode {

    /**
     * jasypt 异常码
     */
    JASYPT_ENCRYPT_IS_NULL(6_10_0_001, "加密器为空"),
    JASYPT_DECRYPT_IS_NULL(6_10_0_002, "解密器为空"),

    ;
    private final int code;
    private final String msg;

    JasyptErrorCodeEnum(Integer code, String msg) {
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
