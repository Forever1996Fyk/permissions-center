package com.ah.cloud.permissions.biz.domain.quartz.dto;

import com.ah.cloud.permissions.enums.ExecutePolicyEnum;
import com.ah.cloud.permissions.enums.JobStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: permissions-center
 * @description: 任务调度
 * @author: YuKai Fan
 * @create: 2022-05-01 09:26
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleJobDTO {

    /**
     * 任务id
     */
    private Long jobId;

    /**
     * 任务名称
     */
    private String jobName;

    /**
     * 任务组名
     */
    private String jobGroup;

    /**
     * 任务参数
     */
    private String jobParams;

    /**
     * 任务类路径
     */
    private String jobClassName;

    /**
     * cron表达式
     */
    private String cronExpression;

    /**
     * 执行计划策略
     */
    private ExecutePolicyEnum executePolicyEnum;

    /**
     * 任务状态
     */
    private JobStatusEnum jobStatusEnum;

    /**
     * 是否支持并发
     */
    private Integer isConcurrent;
}
