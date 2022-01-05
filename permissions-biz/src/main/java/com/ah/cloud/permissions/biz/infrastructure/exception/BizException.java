package com.ah.cloud.permissions.biz.infrastructure.exception;

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
        super(getErrorMsg(errorCode, args));
        this.errorMessage = getErrorMsg(errorCode, args);
        this.errorCode = errorCode;
    }

    public static String getErrorMsg(ErrorCode errorCode, String... args) {
        String errorMsg = String.format(errorCode.getMsg(), args);
        // 说明StringFormat不启作用
        if (StringUtils.equals(errorMsg, errorCode.getMsg())) {
            StringBuffer sb = new StringBuffer();
            sb.append(errorCode.getMsg()).append("，详情：");
            if (args != null && args.length > 0) {
                for (String arg : args) {
                    sb.append(arg).append(" ");
                }
            }
            errorMsg = sb.toString();
        }
        return errorMsg;
    }
}
