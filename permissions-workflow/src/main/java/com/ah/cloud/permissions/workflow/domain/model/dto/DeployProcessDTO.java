package com.ah.cloud.permissions.workflow.domain.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-06-16 16:19
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeployProcessDTO {

    /**
     * 流程定义id
     */
    private String processDefinitionId;

    /**
     * 流程定义key
     */
    private String processDefinitionKey;

    /***
     * 流程部署id
     */
    private String processDeployId;
}
