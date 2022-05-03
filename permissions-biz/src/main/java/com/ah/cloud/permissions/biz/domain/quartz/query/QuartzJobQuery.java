package com.ah.cloud.permissions.biz.domain.quartz.query;

import com.ah.cloud.permissions.domain.common.PageQuery;
import lombok.Data;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-05-03 18:02
 **/
@Data
public class QuartzJobQuery extends PageQuery {

    /**
     * 任务名称
     */
    private String jobName;

    /**
     * 任务组名
     */
    private String jobGroup;

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
