package com.ah.cloud.perm.domain.common;

import com.ah.cloud.perm.enums.common.PermRetCodeEnum;

import java.util.Objects;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2021-12-03 10:34
 **/
public class PermResult<R> {

    /**
     * 是否成功
     */
    private boolean success;

    /**
     * 返回结果
     */
    private R data;

    /**
     * 返回码
     */
    private int code;

    /**
     * 返回内容
     */
    private String msg;

    public static <R> PermResult<R> ofSuccess(R data) {
        return new PermResult<R>()
                .setCode(PermRetCodeEnum.SUCCESS.getCode())
                .setSuccess(true)
                .setMsg(PermRetCodeEnum.SUCCESS.getMsg())
                .setData(data);
    }

    public static <R> PermResult<R> ofSuccess() {
        return new PermResult<R>()
                .setCode(PermRetCodeEnum.SUCCESS.getCode())
                .setSuccess(true)
                .setMsg(PermRetCodeEnum.SUCCESS.getMsg());
    }

    public static <R> PermResult<R> ofFail(int code, String msg) {
        PermResult<R> result = new PermResult<>();
        result.setSuccess(false);
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }

    public static <R> PermResult<R> ofFail(PermRetCodeEnum errorCodeEnum) {
        PermResult<R> result = new PermResult<>();
        result.setSuccess(false);
        result.setCode(errorCodeEnum.getCode());
        result.setMsg(errorCodeEnum.getMsg());
        return result;
    }

    public static <R> PermResult<R> ofFail(PermRetCodeEnum errorCodeEnum, R data) {
        PermResult<R> result = new PermResult<>();
        result.setSuccess(false);
        result.setCode(errorCodeEnum.getCode());
        result.setMsg(errorCodeEnum.getMsg());
        result.setData(data);
        return result;
    }

    public static <R> PermResult<R> ofThrowable(int code, Throwable throwable) {
        PermResult<R> result = new PermResult<>();
        result.setSuccess(false);
        result.setCode(code);
        result.setMsg(throwable.getClass().getName() + ", " + throwable.getMessage());
        return result;
    }

    public boolean isSuccess() {
        return success;
    }

    /**
     * 判断result是否失败
     *
     * @return true:失败 false:成功
     */
    public boolean isFail() {
        return !success;
    }

    public PermResult<R> setSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public int getCode() {
        return code;
    }

    public PermResult<R> setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public PermResult<R> setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public R getData() {
        return data;
    }

    public PermResult<R> setData(R data) {
        this.data = data;
        return this;
    }


    public boolean success() {
        return success && Objects.nonNull(data);
    }

    @Override
    public String toString() {
        return "Result{" +
                "success=" + success +
                ", code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }


}
