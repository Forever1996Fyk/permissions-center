package com.ah.cloud.permissions.biz.domain.quartz.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-05-03 17:59
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuartzJobVo {

    /**
     * 任务id
     */
    private Long id;

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
    private Integer executePolicy;

    /**
     * 是否支持并发
     */
    private Integer concurrent;

    /**
     * 任务状态
     */
    private Integer status;
}
