package com.ah.cloud.permissions.biz.application.helper;

import com.ah.cloud.permissions.biz.infrastructure.constant.ScheduleConstants;
import com.ah.cloud.permissions.biz.infrastructure.quartz.QuartzJobDisallowConcurrentExecution;
import com.ah.cloud.permissions.biz.infrastructure.quartz.QuartzJobExecution;
import com.ah.cloud.permissions.enums.YesOrNoEnum;
import org.quartz.Job;
import org.quartz.JobKey;
import org.quartz.TriggerKey;
import org.springframework.stereotype.Component;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-05-01 10:03
 **/
@Component
public class ScheduleHelper {

    /**
     * 构建任务
     * @param concurrent
     * @return
     */
    public Class<? extends Job> buildQuartzJobClass(Integer concurrent) {
        return YesOrNoEnum.getByType(concurrent).isYes() ? QuartzJobExecution.class : QuartzJobDisallowConcurrentExecution.class;
    }


    /**
     * 构建任务键对象
     * @param jobId
     * @param jobGroup
     * @return
     */
    public JobKey getJobKey(Long jobId, String jobGroup) {
        return JobKey.jobKey(ScheduleConstants.TASK_KEY_PREFIX + jobId, jobGroup);
    }

    /**
     * 构建触发器对象
     * @param jobId
     * @param jobGroup
     * @return
     */
    public TriggerKey getTriggerKey(Long jobId, String jobGroup) {
        return TriggerKey.triggerKey(ScheduleConstants.TASK_KEY_PREFIX + jobId, jobGroup);
    }
}
