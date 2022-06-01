package com.ah.cloud.permissions.domain.common;

import com.ah.cloud.permissions.enums.common.IMErrorCodeEnum;
import lombok.Data;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-05-12 18:22
 **/
@Data
public class ImResult<T> {
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

    public boolean isFailed() {
        return !success;
    }

    public static <T> ImResult<T> ofSuccess() {
        ImResult<T> result = new ImResult<>();
        result.setCode(IMErrorCodeEnum.SUCCESS.getCode());
        result.setMsg(IMErrorCodeEnum.SUCCESS.getMsg());
        result.setSuccess(true);
        return result;
    }

    public static <T> ImResult<T> ofFailed(IMErrorCodeEnum imErrorCodeEnum, String msg) {
        ImResult<T> result = new ImResult<>();
        result.setSuccess(false);
        result.setCode(imErrorCodeEnum.getCode());
        result.setMsg(msg);
        return result;
    }


    public void setImErrorCodeEnum(IMErrorCodeEnum errorCodeEnum) {
        this.code = errorCodeEnum.getCode();
        this.msg = errorCodeEnum.getMsg();
    }
}
