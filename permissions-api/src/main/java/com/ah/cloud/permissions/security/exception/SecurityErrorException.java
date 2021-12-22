package com.ah.cloud.permissions.security.exception;

import com.ah.cloud.permissions.enums.common.ErrorCodeEnum;
import lombok.Data;
import org.springframework.security.core.AuthenticationException;

/**
 * @program: permissions-center
 * @description: 自定义认证权限异常
 * @author: YuKai Fan
 * @create: 2021-12-22 17:21
 **/
@Data
public class SecurityErrorException extends AuthenticationException {

    private ErrorCodeEnum errorCodeEnum;

    private String subCode;

    private String subMsg;

    public SecurityErrorException(ErrorCodeEnum errorCodeEnum, String subCode, String subMsg) {
        super(errorCodeEnum.getMsg());
        this.errorCodeEnum = errorCodeEnum;
        this.subCode = subCode;
        this.subMsg = subMsg;
    }

    public SecurityErrorException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public SecurityErrorException(String msg) {
        super(msg);
    }
}
