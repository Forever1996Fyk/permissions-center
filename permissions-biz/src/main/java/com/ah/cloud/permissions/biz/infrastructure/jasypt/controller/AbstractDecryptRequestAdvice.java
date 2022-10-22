package com.ah.cloud.permissions.biz.infrastructure.jasypt.controller;

import com.ah.cloud.permissions.biz.application.strategy.selector.AlgorithmEncryptorSelector;
import com.ah.cloud.permissions.biz.domain.jasypt.dto.AlgorithmType;
import com.ah.cloud.permissions.biz.domain.jasypt.dto.Decrypt;
import com.ah.cloud.permissions.biz.domain.jasypt.dto.Encrypt;
import com.ah.cloud.permissions.biz.infrastructure.annotation.ParamDecrypt;
import com.ah.cloud.permissions.biz.infrastructure.annotation.ParamsDecrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.util.Objects;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022/10/12 15:06
 **/
@Component
public class AbstractDecryptRequestAdvice<E extends Encrypt, D extends Decrypt> implements DecryptRequestAdvice<E, D> {
    private final AlgorithmEncryptorSelector selector;

    @Autowired
    public AbstractDecryptRequestAdvice(AlgorithmEncryptorSelector selector) {
        this.selector = selector;
    }

    /**
     * 支持解密的条件
     * 1、参数必须要有ParamsDecrypt注解，且解密作用域必须为ALL
     *    @see com.ah.cloud.permissions.biz.infrastructure.annotation.ParamsDecrypt.DecryptScope
     * @param methodParameter
     * @param targetType
     * @param converterType
     * @return
     */
    @Override
    public boolean support(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        Parameter parameter = methodParameter.getParameter();
        ParamsDecrypt paramsDecrypt = parameter.getAnnotation(ParamsDecrypt.class);
        return Objects.nonNull(paramsDecrypt) && Objects.equals(paramsDecrypt.scope(), ParamsDecrypt.DecryptScope.ALL);
    }

    @Override
    public String decrypt(AlgorithmType<E, D> algorithmType) {
        return selector.select(algorithmType)
                .decrypt(algorithmType)
                .getResult();
    }
}
