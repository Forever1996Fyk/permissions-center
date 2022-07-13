package com.ah.cloud.permissions.edi.infrastructure.exceprion;

import com.ah.cloud.permissions.biz.infrastructure.util.AppUtils;
import com.ah.cloud.permissions.enums.edi.EdiErrorCodeEnum;
import com.ah.cloud.permissions.exception.ErrorCode;
import lombok.Data;

/**
 * @Description 业务异常
 * @Author yin.jinbiao
 * @Date 2021/9/27 14:26
 * @Version 1.0
 */
@Data
public class EdiException extends RuntimeException {

    /**
     * 错误码
     */
    private EdiErrorCodeEnum errorCode;

    private String errorMessage;

    public EdiException(EdiErrorCodeEnum errorCode) {
        super(errorCode.getMsg());
        this.errorCode = errorCode;
    }

    public EdiException(EdiErrorCodeEnum errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;
    }

    public EdiException(EdiErrorCodeEnum errorCode, String... args) {
        super(AppUtils.getErrorMsg(errorCode, args));
        this.errorMessage = AppUtils.getErrorMsg(errorCode, args);
        this.errorCode = errorCode;
    }
}
