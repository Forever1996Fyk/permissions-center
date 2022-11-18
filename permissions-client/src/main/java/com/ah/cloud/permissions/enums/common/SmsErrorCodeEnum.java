package com.ah.cloud.permissions.enums.common;

import com.ah.cloud.permissions.exception.ErrorCode;

/**
 * @program: permissions-center
 * @description: 短信错误码
 * @author: YuKai Fan
 * @create: 2022/11/16 17:26
 **/
public enum SmsErrorCodeEnum implements ErrorCode {

    /**
     * 短信错误码
     */
    TEMPLATE_CODE_IS_NULL(9_10_0_001, "模版编码为空"),
    PHONE_NUMBER_IS_NULL(9_10_0_002, "手机号为空"),
    SIGN_NAME_IS_NULL(9_10_0_003, "短信签名为空"),
    PHONE_NUMBER_SIZE_MAX(9_10_0_004, "批量手机号最多传1000条"),
    PHONE_NUMBER_FORMAT_ERROR(9_10_0_005, "手机号格式错误"),

    /**
     * 第三方发送短信
     */
    SEND_SMS_FAILED(9_11_0_001, "短信发送失败"),

    /**
     * 验证码发送
     */
    SEND_CODE_PHONE_NUMBER_SIZE_MUST_ONE(9_20_0_001, "[验证码发送]手机号只能有一个"),
    SEND_CODE_EXPIRE_TIME_IS_ZERO(9_20_0_002, "[验证码发送]过期时间不能为0"),
    SEND_CODE_EFFECTIVE_TIME_IS_ZERO(9_20_0_003, "[验证码发送]有效时间不能为0"),
    SEND_CODE_IN_PERIOD_OF_VALIDITY(9_20_0_004, "[验证码发送]验证码在有效期内，请勿重复发送"),
    SEND_CODE_SIGN_ERROR(9_20_0_005, "[验证码发送]签名错误"),
    SEND_CODE_SIGN_USED(9_20_0_006, "[验证码发送]签名已使用"),


    ;
    private final int code;

    private final String msg;

    SmsErrorCodeEnum(int code, String msg) {
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
