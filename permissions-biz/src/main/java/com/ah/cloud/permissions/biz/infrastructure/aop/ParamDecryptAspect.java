package com.ah.cloud.permissions.biz.infrastructure.aop;

import com.ah.cloud.permissions.biz.application.builder.AlgorithmTypeBuilder;
import com.ah.cloud.permissions.biz.application.strategy.selector.AlgorithmEncryptorSelector;
import com.ah.cloud.permissions.biz.application.strategy.selector.AlgorithmTypeBuilderSelector;
import com.ah.cloud.permissions.biz.domain.jasypt.dto.AlgorithmType;
import com.ah.cloud.permissions.biz.domain.jasypt.dto.Decrypt;
import com.ah.cloud.permissions.biz.domain.jasypt.dto.Encrypt;
import com.ah.cloud.permissions.biz.domain.jasypt.dto.result.DecryptResult;
import com.ah.cloud.permissions.biz.infrastructure.annotation.ParamDecrypt;
import com.ah.cloud.permissions.biz.infrastructure.annotation.ParamsDecrypt;
import com.ah.cloud.permissions.biz.infrastructure.jasypt.encrypt.EncryptTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Objects;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022/10/12 15:43
 **/
@Slf4j
@Aspect
@Component
public class ParamDecryptAspect {
    @Resource
    private AlgorithmEncryptorSelector selector;


    @Pointcut("@annotation(com.ah.cloud.permissions.biz.infrastructure.annotation.EnableDecrypt)")
    public void enableDecrypt() {
        // this is aop pointcuts
    }

    /**
     * 在执行业务前, 获取到需要解密的入参
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Before("enableDecrypt()")
    public void doDecrypt(JoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        Method method = signature.getMethod();
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        for (Annotation[] parameterAnnotation : parameterAnnotations) {
            int paramIndex= ArrayUtils.indexOf(parameterAnnotations, parameterAnnotation);
            for (Annotation annotation : parameterAnnotation) {
                if (annotation instanceof ParamsDecrypt) {
                    ParamsDecrypt paramsDecrypt = (ParamsDecrypt) annotation;
                    if (!Objects.equals(paramsDecrypt.scope(), ParamsDecrypt.DecryptScope.FIELD)) {
                        continue;
                    }
                    Object arg = args[paramIndex];
                    Class<?> clazz = arg.getClass();
                    Field[] fields = clazz.getDeclaredFields();
                    for (Field field : fields) {
                        field.setAccessible(true);
                        if (field.isAnnotationPresent(ParamDecrypt.class)) {
                            ParamDecrypt paramDecrypt = field.getAnnotation(ParamDecrypt.class);
                            EncryptTypeEnum encryptTypeEnum = paramDecrypt.type();
                            AlgorithmTypeBuilder<? extends Encrypt, ? extends Decrypt> algorithmTypeBuilder = AlgorithmTypeBuilderSelector.select(encryptTypeEnum);
                            if (Objects.isNull(algorithmTypeBuilder)) {
                                continue;
                            }
                            Object value = field.get(arg);
                            // 如果加密的值不是String类型也跳过
                            if (!field.getType().isAssignableFrom(String.class)) {
                                continue;
                            }
                            AlgorithmType<? extends Encrypt, ? extends Decrypt> algorithmType = algorithmTypeBuilder.build(String.valueOf(value));
                            DecryptResult result = selector.select(algorithmType).decrypt(algorithmType);
                            log.info("当前密文为: {}, 原文为: {}", value, result.getResult());
                            // 把解密后值赋值给参数
                            field.set(arg, result.getResult());
                        }
                    }
                }
            }
        }
    }
}
