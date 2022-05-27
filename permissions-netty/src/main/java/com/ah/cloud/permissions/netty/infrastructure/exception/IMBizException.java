package com.ah.cloud.permissions.netty.infrastructure.exception;

import com.ah.cloud.permissions.biz.infrastructure.util.AppUtils;
import com.ah.cloud.permissions.enums.common.IMErrorCodeEnum;
import com.ah.cloud.permissions.exception.ErrorCode;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-05-12 18:32
 **/
public class IMBizException extends RuntimeException{

    /**
     * 错误码
     */
    private ErrorCode errorCode;

    private String errorMessage;

    public IMBizException(IMErrorCodeEnum errorCode) {
        super(errorCode.getMsg());
        this.errorCode = errorCode;
    }

    public IMBizException(IMErrorCodeEnum errorCode, String...args) {
        super(AppUtils.getErrorMsg(errorCode, args));
        this.errorMessage = AppUtils.getErrorMsg(errorCode, args);
        this.errorCode = errorCode;
    }
}
