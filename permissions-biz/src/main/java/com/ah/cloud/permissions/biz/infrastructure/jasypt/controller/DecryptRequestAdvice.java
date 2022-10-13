package com.ah.cloud.permissions.biz.infrastructure.jasypt.controller;

import com.ah.cloud.permissions.biz.domain.jasypt.dto.AlgorithmType;
import com.ah.cloud.permissions.biz.domain.jasypt.dto.Decrypt;
import com.ah.cloud.permissions.biz.domain.jasypt.dto.Encrypt;
import org.springframework.core.MethodParameter;
import org.springframework.http.converter.HttpMessageConverter;

import java.lang.reflect.Type;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022/10/12 15:03
 **/
public interface DecryptRequestAdvice<E extends Encrypt, D extends Decrypt> {

    /**
     * 是否支持解密
     * @param methodParameter
     * @param targetType
     * @param converterType
     * @return
     */
    boolean support(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType);

    /**
     * 解密
     * @param algorithmType
     * @return
     */
    String decrypt(AlgorithmType<E, D> algorithmType);
}
