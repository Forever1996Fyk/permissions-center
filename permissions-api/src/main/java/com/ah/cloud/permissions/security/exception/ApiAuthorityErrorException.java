package com.ah.cloud.permissions.security.exception;

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

    private String subCode;

    private String subMsg;

    public ApiAuthorityErrorException(ErrorCodeEnum errorCodeEnum, String subCode, String subMsg) {
        super(errorCodeEnum.getMsg());
        this.errorCodeEnum = errorCodeEnum;
        this.subCode = subCode;
        this.subMsg = subMsg;
    }


    public ApiAuthorityErrorException(String msg) {
        super(msg);
    }

    public ApiAuthorityErrorException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
