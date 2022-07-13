package com.ah.cloud.permissions.edi.infrastructure.exceprion;

import com.ah.cloud.permissions.biz.infrastructure.util.AppUtils;
import com.ah.cloud.permissions.exception.ErrorCode;
import lombok.Data;

/**
 * @program: permissions-center
 * @description: 适用于某些无返回调用，但需要部分处理结果数据，通过异常抛出标记处理结果为成功，异常中将通过message携带一些处理结果数据
 * @author: YuKai Fan
 * @create: 2022-07-06 13:49
 **/
@Data
public class BizSuccessException extends RuntimeException {

    /**
     * 错误信息
     */
    private String errorMessage;

    /**
     * 错误码
     */
    private ErrorCode errorCode;

    public BizSuccessException(String errorMessage) {
        super(errorMessage);
    }

    public BizSuccessException(ErrorCode errorCode, String args) {
        super(AppUtils.getErrorMsg(errorCode, args));
        this.errorCode = errorCode;
        this.errorMessage = errorCode.getMsg();
    }
}
