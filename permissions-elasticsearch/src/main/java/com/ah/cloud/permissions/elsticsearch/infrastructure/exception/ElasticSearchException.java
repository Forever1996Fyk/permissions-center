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
@Data
public class ElasticSearchException extends RuntimeException {

    /**
     * 错误码
     */
    private ErrorCode errorCode;

    private String errorMessage;

    public ElasticSearchException(ErrorCode errorCode) {
        super(errorCode.getMsg());
        this.errorCode = errorCode;
        this.errorMessage = errorCode.getMsg();
    }

    public ElasticSearchException(ErrorCode errorCode, String... args) {
        super(AppUtils.getErrorMsg(errorCode, args));
        this.errorMessage = AppUtils.getErrorMsg(errorCode, args);
        this.errorCode = errorCode;
    }


}
