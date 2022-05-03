package com.ah.cloud.permissions.biz.infrastructure.quartz;

import org.quartz.DisallowConcurrentExecution;

/**
 * @program: permissions-center
 * @description: 定时任务处理（禁止并发执行）
 * @author: YuKai Fan
 * @create: 2022-05-01 10:24
 **/
@DisallowConcurrentExecution
public class QuartzJobDisallowConcurrentExecution extends AbstractQuartzJob {
    private final static String LOG_MARK = "QuartzJobDisallowConcurrentExecution";

    @Override
    protected String getLogMark() {
        return LOG_MARK;
    }
}
