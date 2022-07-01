package com.ah.cloud.permissions.workflow.domain.task.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-06-20 18:10
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssignRuleTaskVo {

    /**
     * 规则id
     */
    private Long id;

    /**
     * 模型id
     */
    private String modelId;

    /**
     * 流程定义id
     */
    private String processDefinitionId;

    /**
     * 任务节点定义id
     */
    private String taskDefinitionId;

    /**
     * 任务节点定义key
     */
    private String taskDefinitionKey;

    /**
     * 任务节点定义name
     */
    private String taskDefinitionName;

    /**
     * 规则类型
     */
    private Integer type;

    /**
     * 规则参数
     */
    private Set<Long> options;
}
