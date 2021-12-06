package com.ah.cloud.permissions.exception;

/**
 * @Description 异常码接口
 * @Author yin.jinbiao
 * @Date 2021/10/1 20:24
 * @Version 1.0
 */
public interface ErrorCode {

    /**
     * 获取错误码
     * @return 错误码
     */
    int getCode();

    /**
     * 获取错误信息
     * @return 错误信息
     */
    String getMsg();
}
