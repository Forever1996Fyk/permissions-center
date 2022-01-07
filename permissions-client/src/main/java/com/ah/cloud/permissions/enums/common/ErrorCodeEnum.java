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
     * 公共响应码
     */
    SUCCESS(0,"成功"),
    UNKNOWN_ERROR(100_0_001,"未知错误"),
    SYSTEM_ERROR(100_0_002,"系统异常"),
    PARAM_MISS(100_1_001,"缺少必要参数"),
    PARAM_ILLEGAL(100_1_002,"参数非法"),
    BUSINESS_FAIL(100_2_001,"业务处理异常"),
    CALL_LIMITED(100_3_001,"调用次数过多"),
    TOKEN_EXCEPTION(100_4_001, "Token验证异常"),

    /**
     * 授权相关响应码
     */
    UNKNOWN_PERMISSION(200_0_001,"未知的权限错误, 请联系管理员"),
    PERMISSION_DENY(200_0_002,"权限不足, 拒绝访问"),
    AUTHORITY_API_DISABLED(200_1_001, "当前接口已被禁用,无法访问"),
    AUTHORITY_API_NOT_OPEN(200_1_002, "当前接口未公开, 无法访问"),
    VALIDATE_CODE_USER_IS_NULL(200_2_001, "短信认证失败, 用户不存在");

    /**
     * 认证相关响应码
     */
    ACCOUNT_ERROR(300_0_001, "账号错误, 请重新输入"),
    ACCOUNT_DISABLED(300_0_002, "[%s]当前账号已被禁用"),
    ACCOUNT_LOG_OFF(300_0_003, "[%s]当前账号已被注销"),

    /**
     * 用户相关响应码
     */
    USER_PHONE_IS_EXISTED(400_0_001, "当前用户手机号[%s]已存在, 无需重复新增"),
    USER_NOT_EXIST(400_0_002, "用户不存在"),
    CURRENT_USER_IS_NOT_EXIST(400_0_003, "当前登录用户不存在"),
    ANONYMOUS_USER_HAS_NO_INFO(400_0_004, "当前匿名用户无具体信息"),
    ;

    private int code;
    private String msg;

    ErrorCodeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
