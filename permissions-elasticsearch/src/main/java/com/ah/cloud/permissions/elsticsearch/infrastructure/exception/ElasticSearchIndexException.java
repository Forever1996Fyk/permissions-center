package com.ah.cloud.permissions.elsticsearch.infrastructure.exception;

import com.ah.cloud.permissions.exception.ErrorCode;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-09-01 14:51
 **/
public class ElasticSearchIndexException extends ElasticSearchException {
    public ElasticSearchIndexException(ErrorCode errorCode) {
        super(errorCode);
    }

    public ElasticSearchIndexException(ErrorCode errorCode, String... args) {
        super(errorCode, args);
    }


}
