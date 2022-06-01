package com.ah.cloud.permissions.biz.application.manager;

import com.ah.cloud.permissions.biz.application.helper.ScheduleHelper;
import com.ah.cloud.permissions.biz.domain.quartz.dto.ScheduleJobDTO;
import com.ah.cloud.permissions.biz.infrastructure.constant.ScheduleConstants;
import com.ah.cloud.permissions.biz.infrastructure.exception.BizException;
import com.ah.cloud.permissions.biz.infrastructure.util.JsonUtils;
import com.ah.cloud.permissions.enums.ExecutePolicyEnum;
import com.ah.cloud.permissions.enums.JobStatusEnum;
import com.ah.cloud.permissions.enums.common.ErrorCodeEnum;
import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-05-01 09:44
 **/
@Slf4j
@Component
public class SchedulerManager {
    @Resource
    private Scheduler scheduler;
    @Resource
    private ScheduleHelper scheduleHelper;

    /**
     * 创建任务调度
     *
     * @param job
     */
    public void createScheduleJob(ScheduleJobDTO job) {
        Class<? extends Job> jobClass = scheduleHelper.buildQuartzJobClass(job.getIsConcurrent());
        Long jobId = job.getJobId();
        String jobGroup = job.getJobGroup();
        JobKey jobKey = scheduleHelper.getJobKey(jobId, jobGroup);
        JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(jobKey).build();

        // 构建cron表达式
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());
        selectExecutePolicy(job.getExecutePolicyEnum(), cronScheduleBuilder);

        // 按新的CronExpression表达式构建一个新的trigger
        CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(scheduleHelper.getTriggerKey(jobId, jobGroup)).withSchedule(cronScheduleBuilder).build();

        // 设置参数
        jobDetail.getJobDataMap().put(ScheduleConstants.TASK_PROPERTIES, job);

        try {
            // 判断当前任务是否已存在
            if (scheduler.checkExists(jobKey)) {
                // 删除已存在的任务
                scheduler.deleteJob(jobKey);
            }

            scheduler.scheduleJob(jobDetail, trigger);

            // 暂停任务
            if (Objects.equals(job.getJobStatusEnum(), JobStatusEnum.PAUSE)) {
                scheduler.pauseJob(jobKey);
            }
        } catch (Exception e) {
            log.error("SchedulerManager[createScheduleJob] build schedule job error, reason is {}", Throwables.getStackTraceAsString(e));
            throw new BizException(ErrorCodeEnum.QUARTZ_BUILD_JOB_FAILED, jobKey.toString());
        }
    }

    /**
     * 更新定时任务
     * @param job
     */
    public void updateScheduleJob(ScheduleJobDTO job) {
        Long jobId = job.getJobId();
        String jobGroup = job.getJobGroup();
        JobKey jobKey = scheduleHelper.getJobKey(jobId, jobGroup);
        try {
            if (scheduler.checkExists(jobKey)) {
                // 防止创建时存在数据问题 先移除，然后在执行创建操作
                scheduler.deleteJob(jobKey);
            }
            this.createScheduleJob(job);
        } catch (Exception e) {
            log.error("SchedulerManager[updateScheduleJob] build schedule job error, reason is {}", Throwables.getStackTraceAsString(e));
            throw new BizException(ErrorCodeEnum.QUARTZ_BUILD_JOB_FAILED, jobKey.toString());
        }
    }


    /**
     * 删除定时任务
     * @param jobId
     * @param jobGroup
     */
    public void deleteQuartzJob(Long jobId, String jobGroup) {
        JobKey jobKey = scheduleHelper.getJobKey(jobId, jobGroup);
        try {
            scheduler.deleteJob(jobKey);
        } catch (SchedulerException e) {
            log.error("SchedulerManager[deleteQuartzJob] delete schedule job error, reason is {}", Throwables.getStackTraceAsString(e));
            throw new BizException(ErrorCodeEnum.QUARTZ_DELETE_JOB_FAILED, jobKey.toString());
        }
    }

    /**
     * 选择策略
     * @param executePolicyEnum
     * @param csb
     */
    public void selectExecutePolicy(ExecutePolicyEnum executePolicyEnum, CronScheduleBuilder csb) {
        switch (executePolicyEnum) {
            case EXECUTE_IMMEDIATELY:
                csb.withMisfireHandlingInstructionIgnoreMisfires();
                break;
            case EXECUTE_ONCE:
                csb.withMisfireHandlingInstructionFireAndProceed();
                break;
            case EXECUTE_NOTHING:
                csb.withMisfireHandlingInstructionDoNothing();
                break;
            default:
                break;
        }
    }

    /**
     * 触发任务
     * @param job
     */
    public void triggerJob(ScheduleJobDTO job) {
        JobDataMap dataMap = new JobDataMap();
        dataMap.put(ScheduleConstants.TASK_PROPERTIES, job);
        try {
            scheduler.triggerJob(scheduleHelper.getJobKey(job.getJobId(), job.getJobGroup()), dataMap);
        } catch (SchedulerException e) {
            log.error("SchedulerManager[triggerJob] trigger schedule job error, job:{},  reason is {}", JsonUtils.toJSONString(job), Throwables.getStackTraceAsString(e));
            throw new BizException(ErrorCodeEnum.QUARTZ_JOB_TRIGGER_FAILED, String.valueOf(job.getJobId()));
        }
    }

    /**
     * 恢复任务
     * @param job
     */
    public void resumeJob(ScheduleJobDTO job) {
        try {
            scheduler.resumeJob(scheduleHelper.getJobKey(job.getJobId(), job.getJobGroup()));
        } catch (SchedulerException e) {
            log.error("SchedulerManager[resumeJob] resume schedule job error, job:{},  reason is {}", JsonUtils.toJSONString(job), Throwables.getStackTraceAsString(e));
            throw new BizException(ErrorCodeEnum.QUARTZ_JOB_RESUME_FAILED, String.valueOf(job.getJobId()));
        }
    }

    /**
     * 暂停任务
     * @param job
     */
    public void pauseJob(ScheduleJobDTO job) {
        try {
            scheduler.pauseJob(scheduleHelper.getJobKey(job.getJobId(), job.getJobGroup()));
        } catch (SchedulerException e) {
            log.error("SchedulerManager[pauseJob] pause schedule job error, job:{},  reason is {}", JsonUtils.toJSONString(job), Throwables.getStackTraceAsString(e));
            throw new BizException(ErrorCodeEnum.QUARTZ_JOB_PAUSE_FAILED, String.valueOf(job.getJobId()));
        }
    }

    /**
     * 清除所有定时任务
     *
     * @throws SchedulerException
     */
    public void clearAllJob() throws SchedulerException {
        scheduler.clear();
    }
}
