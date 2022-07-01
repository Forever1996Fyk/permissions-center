package com.ah.cloud.permissions.workflow.domain.task.vo.detail;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * @program: permissions-center
 * @description: 任务表单信息
 * @author: YuKai Fan
 * @create: 2022-06-26 19:17
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskFormInfo {

    /**
     * 表单域
     */
    private List<String> options;

    /**
     * 配置
     */
    private String config;

    /**
     * 表单类型
     */
    private Integer formType;

    /**
     * 表单编码
     */
    private String formCode;

    /**
     * 变量
     */
    private Map<String, Object> variables;
}


