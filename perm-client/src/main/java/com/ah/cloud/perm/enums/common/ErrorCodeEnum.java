package com.ah.cloud.perm.enums.common;

import com.ah.cloud.perm.exception.ErrorCode;
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
    UNKNOW_ERROR(20000,"unknow error."),
    /**
     * 授权权限不足
     */
    UNKNOW_PERMISSION(20001,"unknow permission."),
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
    CALL_LIMTIED(40005,"call limited.")
    ;

    private int code;
    private String msg;

    ErrorCodeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
