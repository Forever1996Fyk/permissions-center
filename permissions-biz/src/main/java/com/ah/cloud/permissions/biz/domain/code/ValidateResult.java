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
 * @description:
 * @author: YuKai Fan
 * @create: 2022-01-19 15:32
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ValidateResult<R> {
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
    private ErrorCodeEnum errorCodeEnum;

    /**
     * 返回数据
     */
    private R data;

    /**
     * 构建校验结果
     * @return
     */
    public static <R> ValidateResult<R> buildSuccessResult() {
        return buildResult(ErrorCodeEnum.SUCCESS);
    }

    /**
     * 构建校验结果
     * @param errorCodeEnum
     * @return
     */
    public static <R> ValidateResult<R> buildResult(ErrorCodeEnum errorCodeEnum) {
        return ValidateResult.<R>builder()
                .code(errorCodeEnum.getCode())
                .message(errorCodeEnum.getMsg())
                .errorCodeEnum(errorCodeEnum)
                .build();
    }

    /**
     * 构建校验结果
     * @param errorCodeEnum
     * @param args
     * @return
     */
    public static <R> ValidateResult<R> buildResult(ErrorCodeEnum errorCodeEnum, String... args) {
        return ValidateResult.<R>builder()
                .code(errorCodeEnum.getCode())
                .message(AppUtils.getErrorMsg(errorCodeEnum, args))
                .errorCodeEnum(errorCodeEnum)
                .build();
    }
}
