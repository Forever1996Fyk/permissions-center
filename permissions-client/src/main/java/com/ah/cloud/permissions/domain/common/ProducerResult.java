package com.ah.cloud.permissions.domain.common;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-05-11 09:36
 **/
public class ProducerResult<R> {
    /**
     * 是否成功
     */
    private boolean success;

    /**
     * 返回码
     */
    private int code;

    /**
     * 信息
     */
    private String msg;

    /**
     * 数据
     */
    private R data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public R getData() {
        return data;
    }

    public void setData(R data) {
        this.data = data;
    }

    public boolean isFailed() {
        return !success;
    }
}
