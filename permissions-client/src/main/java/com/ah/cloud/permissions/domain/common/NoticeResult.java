package com.ah.cloud.permissions.domain.common;

import lombok.Data;

/**
 * @program: permissions-center
 * @description: 通知结果
 * @author: YuKai Fan
 * @create: 2022-06-02 14:28
 **/
@Data
public class NoticeResult<T> {
    /**
     * 状态码
     */
    private int code;

    /**
     * 消息
     */
    private String msg;

    /**
     * 数据
     */
    private T data;

    /**
     * 是否成功
     */
    private boolean success;
}
