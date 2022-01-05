package com.ah.cloud.permissions.biz.infrastructure.exception;

import com.ah.cloud.permissions.enums.common.ErrorCodeEnum;
import lombok.Getter;
import org.springframework.security.access.AccessDeniedException;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2021-12-24 17:04
 **/
@Getter
public class ApiAuthorityErrorException extends AccessDeniedException {

    private ErrorCodeEnum errorCodeEnum;

    public ApiAuthorityErrorException(ErrorCodeEnum errorCodeEnum) {
        super(errorCodeEnum.getMsg());
        this.errorCodeEnum = errorCodeEnum;
    }

    public ApiAuthorityErrorException(String msg) {
        super(msg);
    }

    public ApiAuthorityErrorException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
