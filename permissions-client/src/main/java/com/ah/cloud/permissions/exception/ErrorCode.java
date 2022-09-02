package com.ah.cloud.permissions.exception;

/**
 * @Description 异常码接口
 * @Author yin.jinbiao
 * @Date 2021/10/1 20:24
 * @Version 1.0
 */
public interface ErrorCode {

    /**
     * 默认返回码
     */
    Integer DEFAULT_CODE = 0;

    /**
     * 默认返回信息
     */
    String DEFAULT_MSG = "成功";

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

    /**
     * 默认成功返回码
     * @return
     */
    static ErrorCode defaultSuccess() {
        return new ErrorCode() {
            @Override
            public int getCode() {
                return DEFAULT_CODE;
            }

            @Override
            public String getMsg() {
                return DEFAULT_MSG;
            }
        };
    }
}
