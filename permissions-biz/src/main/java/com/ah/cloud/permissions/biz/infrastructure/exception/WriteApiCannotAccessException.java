package com.ah.cloud.permissions.biz.infrastructure.exception;

import com.ah.cloud.permissions.enums.common.ErrorCodeEnum;
import lombok.Getter;
import org.springframework.security.core.AuthenticationException;

/**
 * @program: permissions-center
 * @description: 写类型的api无法访问异常
 * @author: YuKai Fan
 * @create: 2022-08-23 14:48
 **/
@Getter
public class WriteApiCannotAccessException extends AuthenticationException {

    private final ErrorCodeEnum errorCodeEnum;

    public WriteApiCannotAccessException(ErrorCodeEnum errorCodeEnum) {
        super(errorCodeEnum.getMsg());
        this.errorCodeEnum = errorCodeEnum;
    }
}
