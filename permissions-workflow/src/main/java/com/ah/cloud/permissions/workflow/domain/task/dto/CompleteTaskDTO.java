package com.ah.cloud.permissions.workflow.domain.task.dto;

import lombok.*;

import java.util.Map;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-06-30 15:33
 **/
@Getter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class CompleteTaskDTO {

    /**
     * 任务id
     */
    private String taskId;

    /**
     * 流程实例id
     */
    private String processInstanceId;

    /**
     * 任务变量
     */
    private Map<String, Object> variables;

    /**
     * 意见内容
     */
    private String comment;


    /**
     * 意见类型
     */
    private String commentType;
}
