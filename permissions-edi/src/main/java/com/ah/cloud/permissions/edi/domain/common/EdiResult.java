package com.ah.cloud.permissions.edi.domain.common;

import com.ah.cloud.permissions.enums.edi.EdiErrorCodeEnum;
import lombok.*;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-05 16:00
 **/
@Getter
@Builder
@ToString
@EqualsAndHashCode
public class EdiResult<R> {

    /**
     * 是否成功
     */
    private boolean success;

    /**
     * 返回结果码
     */
    private EdiErrorCodeEnum ediErrorCodeEnum;

    /**
     * 错误信息
     */
    private String errorMsg;

    /**
     * 返回数据
     */
    private R data;

    public static <R> EdiResult<R> ofSuccess(R data) {
        return EdiResult.<R>builder()
                .success(true)
                .ediErrorCodeEnum(EdiErrorCodeEnum.SUCCESS)
                .data(data)
                .build();
    }

    public static <R> EdiResult<R> ofSuccess() {
        return EdiResult.<R>builder()
                .success(true)
                .ediErrorCodeEnum(EdiErrorCodeEnum.SUCCESS)
                .build();
    }

    public static <R> EdiResult<R> ofFail(EdiErrorCodeEnum ediErrorCodeEnum) {
        return EdiResult.<R>builder()
                .success(false)
                .ediErrorCodeEnum(ediErrorCodeEnum)
                .errorMsg(ediErrorCodeEnum.getMsg())
                .build();
    }


    public static <R> EdiResult<R> ofFail(EdiErrorCodeEnum ediErrorCodeEnum, R data) {
        return EdiResult.<R>builder()
                .success(false)
                .ediErrorCodeEnum(ediErrorCodeEnum)
                .errorMsg(ediErrorCodeEnum.getMsg())
                .data(data)
                .build();
    }

    public static <R> EdiResult<R> ofFail(String errorMsg) {
        return EdiResult.<R>builder()
                .success(false)
                .errorMsg(errorMsg)
                .build();
    }

    public static <R> EdiResult<R> ofFail(EdiErrorCodeEnum ediErrorCodeEnum, String errorMsg) {
        return EdiResult.<R>builder()
                .success(false)
                .ediErrorCodeEnum(ediErrorCodeEnum)
                .errorMsg(errorMsg)
                .build();
    }

    public boolean isFailed() {
        return !success;
    }
}
