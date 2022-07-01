package com.ah.cloud.permissions.workflow.domain.configuration.query;

import com.ah.cloud.permissions.domain.common.PageQuery;
import lombok.Data;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-06-15 17:25
 **/
@Data
public class ProcessFormConfigurationQuery extends PageQuery {

    /**
     * 任务定义key
     */
    private String taskKey;

    /**
     * 流程id
     */
    private Long workflowProcessId;

    /**
     * 流程定义id
     */
    private String processDefinitionId;

    /**
     * 租户id
     */
    private String tenantId;
}
