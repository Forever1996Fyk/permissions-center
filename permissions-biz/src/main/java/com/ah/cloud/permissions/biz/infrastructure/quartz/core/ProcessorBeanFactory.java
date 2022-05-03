package com.ah.cloud.permissions.biz.infrastructure.quartz.core;

import com.ah.cloud.permissions.biz.infrastructure.quartz.core.processor.BasicProcessor;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.util.Map;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-05-03 09:13
 **/
@Slf4j
public class ProcessorBeanFactory {

    /**
     * key（用来防止不同jar包同名类的冲突） -> (className -> Processor)
     */
    private final Map<String, Map<String, BasicProcessor>> cache;
    private static final String LOCAL_KEY = "local";

    private static volatile ProcessorBeanFactory processorBeanFactory;

    public ProcessorBeanFactory() {
        // 初始化对象缓存
        cache = Maps.newConcurrentMap();
        Map<String, BasicProcessor> className2Processor = Maps.newConcurrentMap();
        cache.put(LOCAL_KEY, className2Processor);
    }

    /**
     * 获取本地processor
     * @param className
     * @return
     */
    public BasicProcessor getLocalProcessor(String className) {
        return cache.get(LOCAL_KEY).computeIfAbsent(className, ignore -> {
            try {

                Class<?> clz = Class.forName(className);
                return (BasicProcessor) clz.getDeclaredConstructor().newInstance();

            }catch (Exception e) {
                log.warn("[ProcessorBeanFactory] load local Processor(className = {}) failed.", className, e);
                ExceptionUtils.rethrow(e);
            }
            return null;
        });
    }

    public static ProcessorBeanFactory getInstance() {
        if (processorBeanFactory != null) {
            return processorBeanFactory;
        }
        synchronized (ProcessorBeanFactory.class) {
            if (processorBeanFactory == null) {
                processorBeanFactory = new ProcessorBeanFactory();
            }
        }
        return processorBeanFactory;
    }
}
