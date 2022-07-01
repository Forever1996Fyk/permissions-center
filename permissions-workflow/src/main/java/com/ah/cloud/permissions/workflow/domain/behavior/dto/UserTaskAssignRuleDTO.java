package com.ah.cloud.permissions.workflow.domain.behavior.dto;

import com.ah.cloud.permissions.enums.workflow.TaskAssignRuleTypeEnum;
import lombok.*;

import java.util.Set;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-06-30 17:52
 **/
@Getter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class UserTaskAssignRuleDTO {

    /**
     * 主键id
     */
    private Long id;

    /**
     * 模型id
     */
    private String modelId;

    /**
     * 流程定义key
     */
    private String processDefinitionKey;

    /**
     * 流程定义id
     */
    private String processDefinitionId;

    /**
     * 任务定义id
     */
    private String taskDefinitionId;

    /**
     * 任务定义key
     */
    private String taskDefinitionKey;

    /**
     * 规则类型
     */
    private TaskAssignRuleTypeEnum taskAssignRuleTypeEnum;

    /**
     * 规则集合
     */
    private Set<String> options;

    /**
     * 租户id
     */
    private String tenantId;
}
