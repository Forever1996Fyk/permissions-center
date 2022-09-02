package com.ah.cloud.permissions.elsticsearch.domain.doc.search;

import com.ah.cloud.permissions.exception.ErrorCode;
import lombok.*;

import java.util.List;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-08-26 09:18
 **/
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class EsPageResult<T> {

    /**
     * 错误码
     */
    private ErrorCode errorCode;

    /**
     * 是否成功
     */
    private boolean success;

    /**
     * 错误码
     */
    private int code;

    /**
     * 错误信息
     */
    private String message;

    /**
     * 结果
     */
    private List<T> resultList;

    /**
     * 分页信息
     */
    private Pager pager;

    public boolean isFailed() {
        return !success;
    }

    /**
     * 成功返回
     * @param data
     * @param <T>
     * @return
     */
    public static <T> EsPageResult<T> success(List<T> data, Pager pager) {
        return EsPageResult.<T>builder()
                .resultList(data)
                .success(true)
                .pager(pager)
                .errorCode(ErrorCode.defaultSuccess())
                .code(ErrorCode.defaultSuccess().getCode())
                .message(ErrorCode.defaultSuccess().getMsg())
                .build();
    }

    public static <T> EsPageResult<T> success(List<T> data, Pager pager, ErrorCode errorCode) {
        return EsPageResult.<T>builder()
                .resultList(data)
                .success(true)
                .pager(pager)
                .errorCode(errorCode)
                .code(errorCode.getCode())
                .message(errorCode.getMsg())
                .build();
    }

    public static <T> EsPageResult<T> success(List<T> data, ErrorCode errorCode, String message) {
        return EsPageResult.<T>builder()
                .resultList(data)
                .success(true)
                .errorCode(errorCode)
                .code(errorCode.getCode())
                .message(message)
                .build();
    }

    public static <T> EsPageResult<T> failed(ErrorCode errorCode, String message) {
        return EsPageResult.<T>builder()
                .success(false)
                .errorCode(errorCode)
                .code(errorCode.getCode())
                .message(message)
                .build();
    }

    public static <T> EsPageResult<T> failed(ErrorCode errorCode) {
        return EsPageResult.<T>builder()
                .success(false)
                .errorCode(errorCode)
                .code(errorCode.getCode())
                .message(errorCode.getMsg())
                .build();
    }

}
