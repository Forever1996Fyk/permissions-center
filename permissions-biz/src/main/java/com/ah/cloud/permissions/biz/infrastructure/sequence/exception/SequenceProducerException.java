package com.ah.cloud.permissions.biz.infrastructure.sequence.exception;

import com.ah.cloud.permissions.biz.infrastructure.util.AppUtils;
import com.ah.cloud.permissions.exception.ErrorCode;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022/9/16 15:53
 **/
public class SequenceProducerException extends RuntimeException {

    /**
     * 错误码
     */
    private ErrorCode errorCode;

    private String errorMessage;

    public SequenceProducerException(ErrorCode errorCode) {
        super(errorCode.getMsg());
        this.errorCode = errorCode;
        this.errorMessage = errorCode.getMsg();
    }

    public SequenceProducerException(ErrorCode errorCode, String... args) {
        super(AppUtils.getErrorMsg(errorCode, args));
        this.errorMessage = AppUtils.getErrorMsg(errorCode, args);
        this.errorCode = errorCode;
    }


}
