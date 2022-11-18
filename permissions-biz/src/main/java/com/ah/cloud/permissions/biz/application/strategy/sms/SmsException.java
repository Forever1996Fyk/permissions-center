package com.ah.cloud.permissions.biz.application.strategy.sms;

import com.ah.cloud.permissions.biz.infrastructure.util.AppUtils;
import com.ah.cloud.permissions.exception.ErrorCode;
import lombok.Data;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022/11/17 20:54
 **/
@Data
public class SmsException extends RuntimeException {

    private ErrorCode errorCode;

    private String errorMessage;

    public SmsException(ErrorCode errorCode) {
        super(errorCode.getMsg());
        this.errorCode = errorCode;
    }

    public SmsException(ErrorCode errorCode, String... args) {
        super(AppUtils.getErrorMsg(errorCode, args));
        this.errorMessage = AppUtils.getErrorMsg(errorCode, args);
        this.errorCode = errorCode;
    }
}
