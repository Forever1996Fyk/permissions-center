package com.ah.cloud.permissions.api.handler;

import com.ah.cloud.permissions.enums.common.ErrorCodeEnum;
import com.ah.cloud.permissions.biz.infrastructure.exception.BizException;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

/**
 * @Description 全局异常处理
 * @Author yin.jinbiao
 * @Date 2021/10/1 15:00
 * @Version 1.0
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 参数校验
     * @param e
     * @return
     */
    @ExceptionHandler(BindException.class)
    public ResponseErrorResult handleMethodArgumentNotValidException(BindException e) {
        ResponseErrorResult response = new ResponseErrorResult();
        response.setCode(ErrorCodeEnum.BUSINESS_FAIL.getCode());
        response.setMsg(ErrorCodeEnum.BUSINESS_FAIL.getMsg());
        response.setSubCode(e.getFieldError().getField().concat(e.getBindingResult().getFieldError().getCode()));
        response.setSubMsg(e.getBindingResult().getFieldError().getDefaultMessage());
        return response;
    }

    /**
     * 上传大小超出限制
     * @param e
     * @return
     */
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseErrorResult handleMaxUploadSizeExceededException(MaxUploadSizeExceededException e){
        ResponseErrorResult response = new ResponseErrorResult();
        response.setCode(ErrorCodeEnum.PARAM_ILLEGAL.getCode());
        response.setMsg(ErrorCodeEnum.PARAM_ILLEGAL.getMsg());
        response.setSubCode("max upload size exceeded.");
        response.setSubMsg("上传大小超出限制。");
        return response;
    }


    /**
     * 自定义异常
     * @param e
     * @return
     */
    @ExceptionHandler(BizException.class)
    public ResponseErrorResult handleCustomException(BizException e) {
        log.error(e.getMessage(), e);
        ResponseErrorResult response = new ResponseErrorResult();
        response.setCode(e.getErrorCode().getCode());
        response.setMsg(e.getErrorCode().getMsg());
        return response;
    }

    @Data
    private class ResponseErrorResult{
        private int code;
        private String msg;
        private String subCode;
        private String subMsg;

    }

}
