package com.ah.cloud.permissions.biz.infrastructure.exception;

import com.ah.cloud.permissions.exception.ErrorCode;
import lombok.Data;

/**
 * @Description 业务异常
 * @Author yin.jinbiao
 * @Date 2021/9/27 14:26
 * @Version 1.0
 */
@Data
public class CustomException extends RuntimeException {

    /**
     * 错误码
     */
    private ErrorCode errorCode;

    /**
     * 明细错误码
     */
    private String subCode;

    /**
     * 明细错误码说明
     */
    private String subMsg;

    public CustomException(ErrorCode errorCode, String subCode, String subMsg) {
        super(errorCode.getMsg());
        this.errorCode = errorCode;
        this.subCode = subCode;
        this.subMsg = subMsg;

    }
}
