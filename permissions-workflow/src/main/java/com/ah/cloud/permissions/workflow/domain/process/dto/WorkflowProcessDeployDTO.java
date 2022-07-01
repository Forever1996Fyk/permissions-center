package com.ah.cloud.permissions.workflow.domain.process.dto;

import com.ah.cloud.permissions.biz.infrastructure.annotation.EnumValid;
import com.ah.cloud.permissions.enums.workflow.BusinessCategoryEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-06-16 15:05
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkflowProcessDeployDTO {

    /**
     * 流程名称
     */
    private String name;

    /**
     * 流程定义key
     */
    private String processDefinitionKey;

    /**
     * 流程定义id
     */
    private String processDefinitionId;

    /**
     * 模型id
     */
    private String modelId;

    /**
     * 流程类别
     */
    private BusinessCategoryEnum businessCategoryEnum;

    /**
     * 表单编码
     */
    private String formCode;

    /**
     * 流程icon
     */
    private String processIcon;

    /**
     * 租户id
     */
    private String tenantId;
}
