package com.ah.cloud.permissions.workflow.domain.task.vo.detail;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: permissions-center
 * @description: 基本任务信息
 * @author: YuKai Fan
 * @create: 2022-06-26 19:16
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BaseTaskInfo {

    /**
     * 任务id
     */
    private String taskId;

    /**
     * 任务定义key
     */
    private String taskDefinitionKey;

    /**
     * 任务名称
     */
    private String taskName;

    /**
     * 开始时间
     */
    private String startTime;

    /**
     * 结束时间
     */
    private String endTime;

    /**
     * 耗时
     */
    private Long durationInMillis;
}
