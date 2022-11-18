package com.ah.cloud.permissions.biz.domain.sms;

import com.ah.cloud.permissions.biz.infrastructure.util.AppUtils;
import com.ah.cloud.permissions.enums.common.ErrorCodeEnum;
import com.ah.cloud.permissions.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: permissions-center
 * @description: 短信发送结果
 * @author: YuKai Fan
 * @create: 2022-01-19 15:52
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SmsResult<T> {

    /**
     * 结果码
     */
    private Integer code;

    /**
     * 结果信息
     */
    private String message;

    /**
     * 错误码枚举
     */
    private ErrorCode errorCode;

    /**
     * 发送结果
     */
    private T data;

    /**
     * 是否成功
     */
    private boolean success;

    /**
     * 构建校验结果
     *
     * @param errorCodeEnum
     * @return
     */
    public static <T> SmsResult<T> buildResult(ErrorCode errorCodeEnum, boolean success) {
        return SmsResult.<T>builder()
                .code(errorCodeEnum.getCode())
                .message(errorCodeEnum.getMsg())
                .errorCode(errorCodeEnum)
                .data(null)
                .success(success)
                .build();
    }

    /**
     * 构建校验结果
     *
     * @param errorCodeEnum
     * @param data
     * @return
     */
    public static <T> SmsResult<T> buildResult(ErrorCode errorCodeEnum, T data, boolean success) {
        return SmsResult.<T>builder()
                .code(errorCodeEnum.getCode())
                .message(errorCodeEnum.getMsg())
                .errorCode(errorCodeEnum)
                .data(data)
                .success(success)
                .build();
    }

    /**
     * 构建校验结果
     * @return
     */
    public static <T> SmsResult<T> ofSuccess(T data) {
        return buildResult(ErrorCode.defaultSuccess(), data, true);
    }

    /**
     * 构建校验结果
     * @return
     */
    public static <T> SmsResult<T> ofSuccess() {
        return buildResult(ErrorCode.defaultSuccess(), true);
    }

    /**
     * 构建校验结果
     * @param errorCode
     * @param args
     * @return
     */
    public static <T> SmsResult<T> ofFailed(ErrorCode errorCode, String... args) {
        return SmsResult.<T>builder()
                .code(errorCode.getCode())
                .message(AppUtils.getErrorMsg(errorCode, args))
                .errorCode(errorCode)
                .success(false)
                .data(null)
                .build();
    }

    /**
     * 构建校验结果
     * @param errorCode
     * @return
     */
    public static <T> SmsResult<T> ofFailed(ErrorCode errorCode) {
        return SmsResult.<T>builder()
                .code(errorCode.getCode())
                .message(errorCode.getMsg())
                .errorCode(errorCode)
                .success(false)
                .data(null)
                .build();
    }

    /**
     * 是否失败
     * @return
     */
    public boolean isFailed() {
        return !success;
    }
}
