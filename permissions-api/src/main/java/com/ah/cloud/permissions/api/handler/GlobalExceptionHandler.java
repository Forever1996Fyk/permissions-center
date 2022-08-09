package com.ah.cloud.permissions.api.handler;

import com.ah.cloud.permissions.enums.common.ErrorCodeEnum;
import com.ah.cloud.permissions.biz.infrastructure.exception.BizException;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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
     * 上传大小超出限制
     *
     * @param e
     * @return
     */
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseErrorResult handleMaxUploadSizeExceededException(MaxUploadSizeExceededException e) {
        return ResponseErrorResult.builder()
                .msg(ErrorCodeEnum.PARAM_ILLEGAL.getMsg())
                .code(ErrorCodeEnum.PARAM_ILLEGAL.getCode())
                .build();
    }


    /**
     * 自定义异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(BizException.class)
    public ResponseErrorResult handleCustomException(BizException e) {
        log.error(e.getMessage(), e);
        return ResponseErrorResult.builder()
                .code(e.getErrorCode().getCode())
                .msg(e.getErrorMessage())
                .build();
    }

    /**
     * Spring校验 表单参数异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseErrorResult constraintViolationException(ConstraintViolationException e) {
        Set<ConstraintViolation<?>> cves = e.getConstraintViolations();
        StringBuilder errorMsg = new StringBuilder();
        cves.forEach(ex -> errorMsg.append(ex.getMessage()).append("; "));
        return ResponseErrorResult.builder().code(ErrorCodeEnum.PARAM_ILLEGAL.getCode()).msg(errorMsg.toString()).build();
    }

    /**
     * Spring校验 json参数异常
     *
     * @param ex
     * @return
     */
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseErrorResult methodArgumentNotValidException(Exception ex) {
        MethodArgumentNotValidException c = (MethodArgumentNotValidException) ex;
        List<ObjectError> errors = c.getBindingResult().getAllErrors();
        StringBuffer errorMsg = new StringBuffer();
        errors.stream().forEach(x -> {
            errorMsg.append(x.getDefaultMessage()).append(";");
        });
        return ResponseErrorResult.builder().code(ErrorCodeEnum.PARAM_ILLEGAL.getCode()).msg(errorMsg.toString()).build();
    }


    /**
     * Spring请求验证参数
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(BindException.class)
    public ResponseErrorResult handleBindException(BindException ex) {
        // ex.getFieldError():随机返回一个对象属性的异常信息。如果要一次性返回所有对象属性异常信息，则调用ex.getAllErrors()
        FieldError fieldError = ex.getFieldError();
        String errorMsg = ErrorCodeEnum.PARAM_ILLEGAL.getMsg();
        if (Objects.nonNull(fieldError)) {
            errorMsg = fieldError.getDefaultMessage();
        }
        // 生成返回结果
        return ResponseErrorResult.builder().code(ErrorCodeEnum.PARAM_ILLEGAL.getCode()).msg(errorMsg).build();
    }

    @Getter
    @Builder
    private static class ResponseErrorResult {
        private int code;
        private String msg;
    }

}
