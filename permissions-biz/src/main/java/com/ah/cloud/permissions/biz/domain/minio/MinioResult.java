package com.ah.cloud.permissions.biz.domain.minio;

import com.ah.cloud.permissions.enums.common.ErrorCodeEnum;
import com.ah.cloud.permissions.exception.ErrorCode;
import lombok.*;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022/10/16 09:52
 **/
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MinioResult<T> {

    /**
     * 错误编码
     */
    private ErrorCode errorCode;

    /**
     * 返回信息
     */
    private String message;

    /**
     * 返回数据
     */
    private T data;

    /**
     * 是否成功
     */
    private boolean success;

    /**
     * 构造成功
     * @return
     */
    public static MinioResult<Void> ofSuccess() {
        return MinioResult.<Void>builder()
                .errorCode(ErrorCodeEnum.SUCCESS)
                .message(ErrorCodeEnum.SUCCESS.getMsg())
                .success(true)
                .build();
    }

    /**
     * 构造成功
     * @return
     */
    public static <T> MinioResult<T> ofSuccess(T data) {
        return MinioResult.<T>builder()
                .errorCode(ErrorCodeEnum.SUCCESS)
                .message(ErrorCodeEnum.SUCCESS.getMsg())
                .data(data)
                .success(true)
                .build();
    }

    /**
     * 构造成功
     * @return
     */
    public static <T> MinioResult<T> ofSuccess(T data, String message) {
        return MinioResult.<T>builder()
                .errorCode(ErrorCodeEnum.SUCCESS)
                .message(message)
                .data(data)
                .success(true)
                .build();
    }

    /**
     * 构造失败
     * @param errorCode
     * @return
     */
    public static MinioResult<Void> ofFailed(ErrorCode errorCode) {
        return MinioResult.<Void>builder()
                .errorCode(errorCode)
                .message(errorCode.getMsg())
                .success(false)
                .build();
    }

    /**
     * 构造失败
     * @param message
     * @return
     */
    public static MinioResult<Void> ofFailed(String message) {
        return MinioResult.<Void>builder()
                .errorCode(ErrorCodeEnum.SYSTEM_ERROR)
                .message(message)
                .success(false)
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
