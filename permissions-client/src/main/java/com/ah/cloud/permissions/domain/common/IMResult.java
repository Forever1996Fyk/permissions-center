package com.ah.cloud.permissions.domain.common;

import lombok.Data;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-05-12 18:22
 **/
@Data
public class IMResult {
    /**
     * 状态码
     */
    private int code;

    /**
     * 消息
     */
    private String msg;

    /**
     * 是否成功
     */
    private boolean success;

    public boolean isFailed() {
        return !success;
    }
}
