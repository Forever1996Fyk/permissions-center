package com.ah.cloud.permissions.domain.common;

import com.ah.cloud.permissions.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

/**
 * @program: permissions-center
 * @description: 消息推送result
 * @author: YuKai Fan
 * @create: 2022-05-31 15:26
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MsgPushResult<T> {

    /**
     * 是否成功
     */
    private boolean success;

    /**
     * 消息返回码
     */
    private int code;

    /**
     * 返回内容
     */
    private String msg;

    /**
     * 返回数据
     */
    private T data;

    /**
     * 消息推送错误码
     */
    private ErrorCode errorCode;

    /**
     * 构造成功
     * @return
     */
    public static <T> MsgPushResult<T> ofSuccess() {
        return MsgPushResult.<T>builder()
                .success(true)
                .code(0)
                .msg("成功")
                .build();
    }


    /**
     * 构造成功
     * @return
     */
    public static <T> MsgPushResult<T> ofSuccess(ErrorCode errorCode) {
        return MsgPushResult.<T>builder()
                .success(true)
                .code(errorCode.getCode())
                .msg(errorCode.getMsg())
                .errorCode(errorCode)
                .build();
    }

    /**
     * 构造失败
     * @param errorCode
     * @return
     */
    public static <T> MsgPushResult<T> ofFailed(ErrorCode errorCode, Object... args) {
        String msg = errorCode.getMsg();
        if (Objects.nonNull(args)) {
            msg = String.format(msg, args);
        }
        return MsgPushResult.<T>builder()
                .success(false)
                .code(errorCode.getCode())
                .msg(String.format(msg, args))
                .errorCode(errorCode)
                .build();
    }

    /**
     * 构造失败
     * @param errorCode
     * @return
     */
    public static <T> MsgPushResult<T> ofFailed(ErrorCode errorCode) {
        return MsgPushResult.<T>builder()
                .success(false)
                .code(errorCode.getCode())
                .msg(errorCode.getMsg())
                .errorCode(errorCode)
                .build();
    }

}
