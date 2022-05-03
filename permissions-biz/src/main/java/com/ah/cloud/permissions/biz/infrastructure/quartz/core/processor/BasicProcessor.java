package com.ah.cloud.permissions.biz.infrastructure.quartz.core.processor;

import com.ah.cloud.permissions.biz.infrastructure.quartz.TaskContext;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-05-03 08:47
 **/
public interface BasicProcessor {

    /**
     * 执行逻辑
     * @param context
     */
    void process(TaskContext context);
}
