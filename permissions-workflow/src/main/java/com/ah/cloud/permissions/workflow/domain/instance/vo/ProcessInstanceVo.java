package com.ah.cloud.permissions.workflow.domain.instance.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: permissions-center
 * @description: 流程实例vo
 * @author: YuKai Fan
 * @create: 2022-06-10 17:58
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProcessInstanceVo {

    /**
     * 流程实例id
     */
    private String processInstanceId;

    /**
     * 流程实例名称
     */
    private String processInstanceName;

    /**
     * 描述
     */
    private String description;

    /**
     * 本地名称(待研究)
     */
    private String localizedName;

    /**
     * 流程定义id
     */
    private String processDefinitionId;

    /**
     * 流程定义名称
     */
    private String processDefinitionName;

    /**
     * 流程定义key
     */
    private String processDefinitionKey;

    /**
     * 业务key
     */
    private String businessKey;

    /**
     * 流程定义版本
     */
    private Integer processDefinitionVersion;

    /**
     * 流程实例状态
     */
    private Integer processInstanceState;

    /**
     * 租户id
     */
    private String tenantId;

    /**
     * 启动时间
     */
    private String startTime;

    /**
     * 启动人名称
     */
    private String startUserName;

    /**
     * 启动人id
     */
    private Long startUserId;

    /**
     * 流程部署id
     */
    private String deploymentId;
}
