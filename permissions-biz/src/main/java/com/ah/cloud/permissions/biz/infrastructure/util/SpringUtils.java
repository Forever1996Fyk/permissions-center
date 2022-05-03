package com.ah.cloud.permissions.biz.infrastructure.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;

import java.util.Objects;

/**
 * @program: permissions-center
 * @description: Spring ApplicationContext 工具类
 * @author: YuKai Fan
 * @create: 2022-05-03 09:38
 **/
@Slf4j
public class SpringUtils {
    /**
     * 是否支持spring
     */
    private static boolean SUPPORT_SPRING_BEAN = false;

    private static ApplicationContext context;

    public static void inject(ApplicationContext ctx) {
        context = ctx;
        SUPPORT_SPRING_BEAN = true;
    }

    /**
     * 是否支持Spring
     * @return
     */
    public static boolean supportSpringBean() {
        return SUPPORT_SPRING_BEAN;
    }

    /**
     * 根据class获取bean对象
     * @param clz
     * @param <T>
     * @return
     */
    public static <T> T getBean(Class<T> clz) {
        return context.getBean(clz);
    }

    /**
     * 根据类名
     * @param className
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T> T getBean(String className) throws Exception {
        // ClassLoader存在, 则直接使用 clazz加载
        ClassLoader classLoader = context.getClassLoader();
        if (!Objects.isNull(classLoader)) {
            return (T) context.getBean(classLoader.loadClass(className));
        }
        // 如果类加载器不存在, 尝试用类名小写加载
        String[] split = className.split("\\.");
        String beanName = split[split.length - 1];
        // 首字母 小写转大写
        char[] cs = beanName.toCharArray();
        cs[0] += 32;
        String beanName0 = String.valueOf(cs);
        log.warn("[SpringUtils] can't get ClassLoader from context[{}], try to load by beanName:{}", context, beanName0);
        return (T) context.getBean(beanName0);
    }

}
