package com.ah.cloud.permissions.biz.infrastructure.util;

import org.quartz.CronExpression;

/**
 * @program: permissions-center
 * @description: cron 工具类
 * @author: YuKai Fan
 * @create: 2022-05-01 09:17
 **/
public class CronUtils {

    /**
     * 返回一个布尔值代表一个给定的Cron表达式的有效性
     *
     * @param expression
     * @return
     */
    public static boolean isValid(String expression) {
        return CronExpression.isValidExpression(expression);
    }
}
