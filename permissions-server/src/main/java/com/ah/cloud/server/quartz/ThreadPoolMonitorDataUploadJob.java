package com.ah.cloud.server.quartz;

import com.ah.cloud.permissions.biz.application.manager.ThreadPoolConfigManager;
import com.ah.cloud.permissions.biz.infrastructure.quartz.TaskContext;
import com.ah.cloud.permissions.biz.infrastructure.quartz.core.processor.BasicProcessor;
import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @program: permissions-center
 * @description: 定时任务执行线程池数据监控
 * @author: YuKai Fan
 * @create: 2022-04-28 18:10
 **/
@Slf4j
@Component
public class ThreadPoolMonitorDataUploadJob implements BasicProcessor {
    @Resource
    private ThreadPoolConfigManager threadPoolConfigManager;

    @Override
    public void process(TaskContext context) {
        try {
            threadPoolConfigManager.uploadThreadPoolMonitorData();
        } catch (Exception e) {
            log.error("ThreadPoolMonitorDataUploadJob[process] quartz job handle exception:{}", Throwables.getStackTraceAsString(e));
        }
    }
}
