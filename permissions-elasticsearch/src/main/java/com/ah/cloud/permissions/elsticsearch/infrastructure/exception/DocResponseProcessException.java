package com.ah.cloud.permissions.elsticsearch.infrastructure.exception;

import com.ah.cloud.permissions.exception.ErrorCode;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-09-02 13:48
 **/
public class DocResponseProcessException extends ElasticSearchException {
    public DocResponseProcessException(ErrorCode errorCode) {
        super(errorCode);
    }

    public DocResponseProcessException(ErrorCode errorCode, String... args) {
        super(errorCode, args);
    }
}
