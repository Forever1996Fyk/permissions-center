package com.ah.cloud.permissions.biz.infrastructure.exception;

import com.ah.cloud.permissions.enums.common.ErrorCodeEnum;
import lombok.Getter;
import org.springframework.security.core.AuthenticationException;

/**
 * @program: permissions-center
 * @description: 自定义认证权限异常
 * @author: YuKai Fan
 * @create: 2021-12-22 17:21
 **/
@Getter
public class SecurityErrorException extends AuthenticationException {

    private ErrorCodeEnum errorCodeEnum;

    public SecurityErrorException(ErrorCodeEnum errorCodeEnum) {
        super(errorCodeEnum.getMsg());
        this.errorCodeEnum = errorCodeEnum;
    }

    public SecurityErrorException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public SecurityErrorException(String msg) {
        super(msg);
    }
}
