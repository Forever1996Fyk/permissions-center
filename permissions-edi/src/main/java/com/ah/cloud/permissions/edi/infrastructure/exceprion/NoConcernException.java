package com.ah.cloud.permissions.edi.infrastructure.exceprion;

/**
 * @program: permissions-center
 * @description: 无需担心的异常
 * @author: YuKai Fan
 * @create: 2022-07-06 13:48
 **/
public class NoConcernException extends BizSuccessException {

    public NoConcernException(String errorMessage) {
        super(errorMessage);
    }
}
