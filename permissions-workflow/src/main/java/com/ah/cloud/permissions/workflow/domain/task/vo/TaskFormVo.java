package com.ah.cloud.permissions.workflow.domain.task.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @program: permissions-center
 * @description: 任务表单
 * @author: YuKai Fan
 * @create: 2022-06-19 12:04
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskFormVo {

    /**
     * 流程id
     */
    private Long workflowProcessId;

    /**
     * 流程定义id
     */
    private String processDefinitionId;

    /**
     * 表单编码
     */
    private String formCode;

    /**
     * 表单域
     */
    private List<String> options;

    /**
     * 表单配置
     */
    private String config;
}
