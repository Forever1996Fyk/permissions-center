package com.ah.cloud.permissions.biz.domain.quartz.form;

import com.ah.cloud.permissions.biz.infrastructure.annotation.EnumValid;
import com.ah.cloud.permissions.enums.ExecutePolicyEnum;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-04-30 10:24
 **/
@Data
public class QuartzJobAddForm {

    /**
     * 任务名称
     */
    @NotEmpty(message = "任务名称不能为空")
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
    @NotEmpty(message = "任务类路径不能为空")
    private String jobClassName;

    /**
     * cron表达式
     */
    @NotEmpty(message = "cron表达式不能为空")
    private String cronExpression;

    /**
     * 执行计划策略
     */
    @EnumValid(message = "执行计划策略错误", enumClass = ExecutePolicyEnum.class, enumMethod = "isValid")
    private Integer executePolicy;

    /**
     * 是否支持并发
     */
    private Integer isConcurrent;
}
