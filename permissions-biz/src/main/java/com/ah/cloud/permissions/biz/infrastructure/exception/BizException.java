package com.ah.cloud.permissions.biz.infrastructure.exception;

import com.ah.cloud.permissions.biz.infrastructure.util.AppUtils;
import com.ah.cloud.permissions.enums.common.ChatRoomErrorCodeEnum;
import com.ah.cloud.permissions.enums.common.ErrorCodeEnum;
import com.ah.cloud.permissions.enums.common.FileErrorCodeEnum;
import com.ah.cloud.permissions.enums.common.RedisErrorCodeEnum;
import com.ah.cloud.permissions.enums.common.push.PushErrorCode;
import com.ah.cloud.permissions.exception.ErrorCode;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * @Description 业务异常
 * @Author yin.jinbiao
 * @Date 2021/9/27 14:26
 * @Version 1.0
 */
@Data
public class BizException extends RuntimeException {

    /**
     * 错误码
     */
    private ErrorCode errorCode;

    private String errorMessage;

    public BizException(ErrorCode errorCode) {
        super(errorCode.getMsg());
        this.errorCode = errorCode;
    }

    public BizException(ErrorCode errorCode, String... args) {
        super(AppUtils.getErrorMsg(errorCode, args));
        this.errorMessage = AppUtils.getErrorMsg(errorCode, args);
        this.errorCode = errorCode;
    }
}
