package com.ah.cloud.permissions.biz.infrastructure.exception;

import com.ah.cloud.permissions.enums.common.ErrorCodeEnum;
import lombok.Getter;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2021-12-24 10:53
 **/
@Getter
public class UserAccountErrorException extends UsernameNotFoundException {

    private ErrorCodeEnum errorCodeEnum;

    public UserAccountErrorException(ErrorCodeEnum errorCodeEnum) {
        super(errorCodeEnum.getMsg());
        this.errorCodeEnum = errorCodeEnum;
    }

    public UserAccountErrorException(String msg) {
        super(msg);
    }

    public UserAccountErrorException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
