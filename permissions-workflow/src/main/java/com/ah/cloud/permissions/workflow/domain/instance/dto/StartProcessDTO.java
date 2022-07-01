package com.ah.cloud.permissions.workflow.domain.instance.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.Map;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-06-10 16:20
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StartProcessDTO {

    /**
     * 流程定义key
     */
    private String processDefinitionKey;

    /**
     * 流程定义id
     */
    private String processDefinitionId;

    /**
     * 流程变量
     */
    private Map<String, Object> variables;

    /**
     * 启动名称
     */
    private String processName;

    /**
     * 业务key
     */
    private String businessKey;
}
