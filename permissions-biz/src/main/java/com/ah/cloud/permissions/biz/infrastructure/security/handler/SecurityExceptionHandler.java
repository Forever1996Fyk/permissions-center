package com.ah.cloud.permissions.biz.infrastructure.security.handler;

import com.ah.cloud.permissions.biz.infrastructure.exception.ApiAuthorityErrorException;
import com.ah.cloud.permissions.biz.infrastructure.exception.SecurityErrorException;
import com.ah.cloud.permissions.biz.infrastructure.exception.UserAccountErrorException;
import com.ah.cloud.permissions.biz.infrastructure.exception.WriteApiCannotAccessException;
import com.ah.cloud.permissions.enums.common.ErrorCodeEnum;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;

/**
 * @program: permissions-center
 * @description: Security异常类处理
 * @author: YuKai Fan
 * @create: 2022-01-04 15:07
 **/
public class SecurityExceptionHandler {

    public static ErrorCodeEnum extractErrorCodeEnum(AccessDeniedException e) {
        ErrorCodeEnum errorCodeEnum = ErrorCodeEnum.UNKNOWN_PERMISSION;
        if (e instanceof ApiAuthorityErrorException) {
            errorCodeEnum = ((ApiAuthorityErrorException) e).getErrorCodeEnum();
        }
        return errorCodeEnum;
    }

    public static ErrorCodeEnum extractErrorCodeEnum(AuthenticationException e) {
        ErrorCodeEnum errorCodeEnum = ErrorCodeEnum.UNKNOWN_PERMISSION;
        if (e instanceof SecurityErrorException) {
            errorCodeEnum = ((SecurityErrorException) e).getErrorCodeEnum();
        } else if (e instanceof UserAccountErrorException) {
            errorCodeEnum = ((UserAccountErrorException) e).getErrorCodeEnum();
        } else if (e instanceof WriteApiCannotAccessException) {
            errorCodeEnum = ((WriteApiCannotAccessException) e).getErrorCodeEnum();
        }
        return errorCodeEnum;
    }
}
