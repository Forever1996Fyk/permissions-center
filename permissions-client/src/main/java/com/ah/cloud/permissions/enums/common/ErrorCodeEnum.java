package com.ah.cloud.permissions.enums.common;

import com.ah.cloud.permissions.exception.ErrorCode;
import lombok.Getter;

/**
 * @Description 错误码
 * @Author yin.jinbiao
 * @Date 2021/9/27 14:31
 * @Version 1.0
 */
@Getter
public enum ErrorCodeEnum implements ErrorCode {

    /**
     * 接口调用成功
     */
    SUCCESS(10000,"success."),
    /**
     * 服务不可用
     */
    UNKNOWN_ERROR(20000,"unknown error."),
    /**
     * 授权权限不足
     */
    UNKNOWN_PERMISSION(20001,"unknown permission."),
    /**
     * 缺少必选参数
     */
    PARAM_MISS(40001,"missing param."),
    /**
     * 非法的参数
     */
    PARAM_ILLEGAL(40002,"illegal param."),
    /**
     * 业务处理失败
     */
    BUSINESS_FAIL(40004,"business fail."),
    /**
     * 权限不足
     */
    PERMISSION_DENY(40006,"permission denied."),
    /**
     * 调用频次超限
     */
    CALL_LIMITED(40005,"call limited."),

    /**
     * token验证异常
     */
    TOKEN_EXCEPTION(40007, "token exception."),

    /**
     * 账号错误
     */
    ACCOUNT_ERROR(40008, "account error."),

    /**
     * 账号被禁用
     */
    ACCOUNT_DISABLED(40009, "account disabled."),

    /**
     * 账号被注销
     */
    ACCOUNT_LOG_OFF(400010, "account log off."),

    /**
     * api被禁用
     */
    AUTHORITY_API_DISABLED(50001, "api disabled."),

    /**
     * api未公开
     */
    AUTHORITY_API_NOT_OPEN(500002, "api not open."),
    ;

    private int code;
    private String msg;

    ErrorCodeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
