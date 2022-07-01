package com.ah.cloud.permissions.workflow.domain.business.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @program: permissions-center
 * @description: 业务变量
 * @author: YuKai Fan
 * @create: 2022-06-19 23:30
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BusinessVariablesDTO {

    /**
     * 任务名称
     */
    private String taskName;

    /**
     * 任务定义key
     */
    private String taskDefinitionKey;

    /**
     * 任务定义id
     */
    private String taskDefinitionId;

    /**
     * 流程参数
     */
    private Map<String, Object> variables;
}
