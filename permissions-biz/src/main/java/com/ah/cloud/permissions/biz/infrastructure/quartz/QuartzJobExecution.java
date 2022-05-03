package com.ah.cloud.permissions.biz.infrastructure.quartz;

/**
 * @program: permissions-center
 * @description: 定时任务处理（允许并发执行）
 * @author: YuKai Fan
 * @create: 2022-05-01 10:24
 **/
public class QuartzJobExecution extends AbstractQuartzJob {
    private final static String LOG_MARK = "QuartzJobExecution";

    @Override
    protected String getLogMark() {
        return LOG_MARK;
    }
}
