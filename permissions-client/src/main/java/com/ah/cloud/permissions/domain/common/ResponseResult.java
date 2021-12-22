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
     * 子编码
     */
    private String subCode;

    /**
     * 子消息
     */
    private String subMsg;

    /**
     * 返回对象
     */
    private T data;

    public static <T> ResponseResult<T> ok(){
        return newResponse(ErrorCodeEnum.SUCCESS.getCode(), ErrorCodeEnum.SUCCESS.getMsg(), null);
    }

    public static <T> ResponseResult<T> ok(T data){
        return newResponse(ErrorCodeEnum.SUCCESS.getCode(), ErrorCodeEnum.SUCCESS.getMsg(), data);
    }

    public static <T> ResponseResult<T> error(ErrorCodeEnum errorCodeEnum, String subCode, String subMsg){
        return newResponse(errorCodeEnum.getCode(), errorCodeEnum.getMsg(), subCode, subMsg);
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

    public static <T> ResponseResult<T> newResponse(int code, String msg, String subCode, String subMsg) {
        ResponseResult<T> result = new ResponseResult<T>();
        result.setCode(code);
        result.setMsg(msg);
        result.setSubCode(subCode);
        result.setSubMsg(subMsg);
        return result;
    }

}
