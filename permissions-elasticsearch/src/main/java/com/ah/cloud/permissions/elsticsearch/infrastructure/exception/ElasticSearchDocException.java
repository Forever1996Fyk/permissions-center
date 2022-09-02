package com.ah.cloud.permissions.elsticsearch.infrastructure.exception;

import com.ah.cloud.permissions.biz.infrastructure.util.AppUtils;
import com.ah.cloud.permissions.exception.ErrorCode;
import lombok.Data;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-09-01 14:51
 **/
public class ElasticSearchDocException extends ElasticSearchException {
    public ElasticSearchDocException(ErrorCode errorCode) {
        super(errorCode);
    }

    public ElasticSearchDocException(ErrorCode errorCode, String... args) {
        super(errorCode, args);
    }


}
