package com.ah.cloud.permissions.biz.domain.code;

import com.ah.cloud.permissions.biz.infrastructure.util.AppUtils;
import com.ah.cloud.permissions.enums.common.ErrorCodeEnum;
import com.ah.cloud.permissions.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: permissions-center
 * @description: 发送结果
 * @author: YuKai Fan
 * @create: 2022-01-19 15:52
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SendResult<T> {

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
    private ErrorCodeEnum errorCode;

    /**
     * 发送结果
     */
    private T data;

    /**
     * 构建校验结果
     * @param errorCodeEnum
     * @return
     */
    public static <T> SendResult<T> buildResult(ErrorCodeEnum errorCodeEnum) {
        return SendResult.<T>builder()
                .code(errorCodeEnum.getCode())
                .message(errorCodeEnum.getMsg())
                .errorCode(errorCodeEnum)
                .data(null)
                .build();
    }

    /**
     * 构建校验结果
     * @param errorCodeEnum
     * @return
     */
    public static <T> SendResult<T> buildResult(ErrorCodeEnum errorCodeEnum, T data) {
        return SendResult.<T>builder()
                .code(errorCodeEnum.getCode())
                .message(errorCodeEnum.getMsg())
                .errorCode(errorCodeEnum)
                .data(data)
                .build();
    }

    /**
     * 构建校验结果
     * @return
     */
    public static <T> SendResult<T> buildSuccessResult(T data) {
        return buildResult(ErrorCodeEnum.SUCCESS, data);
    }

    /**
     * 构建校验结果
     * @return
     */
    public static <T> SendResult<T> buildSuccessResult() {
        return buildResult(ErrorCodeEnum.SUCCESS);
    }

    /**
     * 构建校验结果
     * @param errorCode
     * @param args
     * @return
     */
    public static <T> SendResult<T> buildResult(ErrorCodeEnum errorCode, String... args) {
        return SendResult.<T>builder()
                .code(errorCode.getCode())
                .message(AppUtils.getErrorMsg(errorCode, args))
                .errorCode(errorCode)
                .data(null)
                .build();
    }
}
