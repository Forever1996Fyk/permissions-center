package com.ah.cloud.permissions.biz.infrastructure.aop;

import com.ah.cloud.permissions.biz.infrastructure.util.SecurityUtils;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * @program: permissions-center
 * @description: 接口操作日志打印
 * @author: YuKai Fan
 * @create: 2022-04-07 18:07
 **/
@Slf4j
@Aspect
@Component
public class ApiInvocationOpLog {

    @Pointcut("execution(com.ah.cloud.permissions.biz.infrastructure.annotation.ApiMethodLog)")
    public void devOpsApiService() {
        // this is aop pointcuts
    }

    /**
     * 切面
     *
     * @param joinPoint
     * @return
     */
    @Around("devOpsApiService()")
    public Object devops(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        Class<?> targetClass = joinPoint.getTarget().getClass();
        String methodName = joinPoint.getSignature().getName();
        List<Object> array = Arrays.asList(args);
        log.info("begin invoke some special method, user is {}, clazz is {}, method is {}, param is {}"
                , SecurityUtils.getUserNameBySession()
                , targetClass.getName()
                , methodName
                , array);
        return joinPoint.proceed();
    }
}
