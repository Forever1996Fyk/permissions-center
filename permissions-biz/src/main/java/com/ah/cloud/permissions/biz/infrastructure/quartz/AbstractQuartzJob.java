package com.ah.cloud.permissions.biz.infrastructure.quartz;

import com.ah.cloud.permissions.biz.application.manager.QuartzJobTaskManager;
import com.ah.cloud.permissions.biz.domain.quartz.dto.ScheduleJobDTO;
import com.ah.cloud.permissions.biz.infrastructure.constant.PermissionsConstants;
import com.ah.cloud.permissions.biz.infrastructure.constant.ScheduleConstants;
import com.ah.cloud.permissions.biz.infrastructure.quartz.core.ProcessorBeanFactory;
import com.ah.cloud.permissions.biz.infrastructure.quartz.core.processor.BasicProcessor;
import com.ah.cloud.permissions.biz.infrastructure.repository.bean.QuartzJobTask;
import com.ah.cloud.permissions.biz.infrastructure.util.JsonUtils;
import com.ah.cloud.permissions.biz.infrastructure.util.SpringUtils;
import com.ah.cloud.permissions.enums.JobExecuteStatusEnum;
import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-05-01 10:24
 **/
@Slf4j
@Component
public abstract class AbstractQuartzJob implements Job {
    @Resource
    private QuartzJobTaskManager quartzJobTaskManager;

    /**
     *
     * 定时任务的生成，后期考虑异步处
     *
     * @param context
     * @throws JobExecutionException
     */
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        log.info("{}[execute] start execute quartz job", getLogMark());
        ScheduleJobDTO scheduleJobDTO = (ScheduleJobDTO) context.getMergedJobDataMap().get(ScheduleConstants.TASK_PROPERTIES);
        if (Objects.isNull(scheduleJobDTO)) {
            log.warn("{}[execute] start execute quartz job, scheduleJobDTO is empty", getLogMark());
            return;
        }
        QuartzJobTask quartzJobTask = quartzJobTaskManager.initQuartzJobTask(scheduleJobDTO.getJobId());
        String errorMessage = PermissionsConstants.STR_EMPTY;
        JobExecuteStatusEnum jobExecuteStatusEnum = null;
        // 开始执行任务
        try {
            // 执行前处理逻辑
            before(context, scheduleJobDTO);
            quartzJobTaskManager.updateQuartzJobTask(quartzJobTask, JobExecuteStatusEnum.EXECUTING, errorMessage);
            // 开始执行任务
            handle(scheduleJobDTO, quartzJobTask);
            // 执行后处理逻辑
            after(context, scheduleJobDTO);
            log.info("{}[execute] execute quartz job success", getLogMark());
            jobExecuteStatusEnum = JobExecuteStatusEnum.SUCCESS;
        } catch (Exception e) {
            jobExecuteStatusEnum = JobExecuteStatusEnum.FAILED;
            errorMessage = Throwables.getStackTraceAsString(e);
            log.error("{}[execute] execute quartz job error, params:{}, exception:{}", getLogMark(), JsonUtils.toJSONString(scheduleJobDTO), errorMessage);
        }
        quartzJobTaskManager.updateQuartzJobTask(quartzJobTask, jobExecuteStatusEnum, errorMessage);
    }

    /**
     * 日志标记
     *
     * @return
     */
    protected abstract String getLogMark();

    /**
     * 执行前操作
     *
     * @param context
     * @param scheduleJobDTO
     */
    protected void before(JobExecutionContext context, ScheduleJobDTO scheduleJobDTO) {
    }

    /**
     * 执行后操作
     *
     * @param context
     * @param scheduleJobDTO
     */
    protected void after(JobExecutionContext context, ScheduleJobDTO scheduleJobDTO) {
    }

    /**
     * 执行
     *
     * @param job
     * @param quartzJobTask
     */
    private void handle(ScheduleJobDTO job, QuartzJobTask quartzJobTask) {
        BasicProcessor processor = null;
        if (SpringUtils.supportSpringBean()) {
            try {
                processor= SpringUtils.getBean(job.getJobClassName());
            } catch (Exception e) {
                log.warn("{} no spring bean of processor(className={}), reason is {}", getLogMark(), job.getJobClassName(), Throwables.getStackTraceAsString(e));
            }
        }
        if (Objects.isNull(processor)) {
            processor = ProcessorBeanFactory.getInstance().getLocalProcessor(job.getJobClassName());
        }
        processor.process(constructTaskContext(job, quartzJobTask));
    }

    private TaskContext constructTaskContext(ScheduleJobDTO job, QuartzJobTask quartzJobTask) {
        return TaskContext.builder()
                .jobId(job.getJobId())
                .jobParams(job.getJobParams())
                .taskId(quartzJobTask.getId())
                .build();
    }
}
