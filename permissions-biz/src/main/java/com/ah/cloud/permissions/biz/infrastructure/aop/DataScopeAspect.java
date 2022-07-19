package com.ah.cloud.permissions.biz.infrastructure.aop;

import com.ah.cloud.permissions.biz.infrastructure.annotation.DataScope;
import com.ah.cloud.permissions.biz.infrastructure.config.mp.DataScopeContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-19 10:15
 **/
@Aspect
@Component
public class DataScopeAspect {
    /**
     * 前置通知，提取数据过滤需要的参数
     * @param joinPoint
     */
    @Before("@annotation(datascope)")
    public void doBefore(JoinPoint joinPoint, DataScope datascope){
        DataScopeContextHolder.setContext(datascope);
    }


    /**
     * 最终通知 移除 ThreadLocal，防止影响线程复用与内存泄漏
     */
    @After("@annotation(datascope)")
    public void clearThreadLocal(DataScope datascope){
        DataScopeContextHolder.clearContext();
    }
}
