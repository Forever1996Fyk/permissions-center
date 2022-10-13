package com.ah.cloud.permissions.api.advice;

import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022/10/12 14:59
 **/
public class ApiDecryptRequestAdvice extends RequestBodyAdviceAdapter {

    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return false;
    }

    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) throws IOException {
        return super.beforeBodyRead(inputMessage, parameter, targetType, converterType);
    }


}
