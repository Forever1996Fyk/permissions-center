package com.ah.cloud.permissions.workflow.domain.task.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-06-17 15:38
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ToDoTaskVo {
    /**
     * 任务编号
     */
    private String taskId;

    /**
     * 任务名称
     */
    private String taskName;

    /**
     * 任务定义key
     */
    private String taskDefinitionKey;

    /**
     * 流程发起人Id
     */
    private Long proposerId;

    /**
     * 流程发起人名称
     */
    private String proposer;

    /**
     * 流程类型
     */
    private String category;

    /**
     * 流程定义id
     */
    private String processDefinitionId;

    /**
     * 流程实例id
     */
    private String processInstanceId;

    /**
     * 流程名称
     */
    private String processName;

    /**
     * 任务key
     */
    private String taskKey;

    /**
     * 任务开始
     */
    private String startTime;
}
