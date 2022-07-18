package com.ah.cloud.permissions.domain.common;

import com.ah.cloud.permissions.enums.common.ErrorCodeEnum;
import lombok.Data;

/**
 * @program: permissions-center
 * @description: 通用的返回对象
 * @author: yin.jinbiao
 * @create: 2021/12/6 09:17
 **/
@Data
public class ResponseResult<T> {

    /**
     * 状态码
     */
    private int code;

    /**
     * 消息
     */
    private String msg;

    /**
     * 返回对象
     */
    private T data;

    public static <T> ResponseResult<T> ok(){
        return newResponse(ErrorCodeEnum.SUCCESS, null);
    }

    public static <T> ResponseResult<T> ok(T data){
        return newResponse(ErrorCodeEnum.SUCCESS, data);
    }

    public static <T> ResponseResult<T> newResponse(int code, String msg,T data) {
        ResponseResult<T> result = new ResponseResult<T>();
        result.setCode(code);
        result.setMsg(msg);
        if (data != null && data instanceof String && "".equals(data)) {
            result.setData(null);
        } else {
            result.setData(data);
        }
        return result;
    }


    public static <T> ResponseResult<T> newResponse(ErrorCodeEnum errorCodeEnum,T data) {
        ResponseResult<T> result = new ResponseResult<T>();
        result.setCode(errorCodeEnum.getCode());
        result.setMsg(errorCodeEnum.getMsg());
        if (data != null && data instanceof String && "".equals(data)) {
            result.setData(null);
        } else {
            result.setData(data);
        }
        return result;
    }

    public static <T> ResponseResult<T> newResponse(ErrorCodeEnum errorCodeEnum) {
        ResponseResult<T> result = new ResponseResult<T>();
        result.setCode(errorCodeEnum.getCode());
        result.setMsg(errorCodeEnum.getMsg());
        return result;
    }

    public static <T> ResponseResult<T> newResponseArgs(ErrorCodeEnum errorCodeEnum, String... args) {
        ResponseResult<T> result = new ResponseResult<T>();
        result.setCode(errorCodeEnum.getCode());
        result.setMsg(String.format(errorCodeEnum.getMsg(), args));
        return result;
    }

}
