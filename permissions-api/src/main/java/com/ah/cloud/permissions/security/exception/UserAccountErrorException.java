package com.ah.cloud.permissions.security.exception;

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

    private String subCode;

    private String subMsg;

    public UserAccountErrorException(ErrorCodeEnum errorCodeEnum, String subCode, String subMsg) {
        super(errorCodeEnum.getMsg());
        this.errorCodeEnum = errorCodeEnum;
        this.subCode = subCode;
        this.subMsg = subMsg;
    }

    public UserAccountErrorException(String msg) {
        super(msg);
    }

    public UserAccountErrorException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
