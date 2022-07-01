package com.ah.cloud.permissions.workflow.domain.task.vo.detail;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * @program: permissions-center
 * @description: 任务处理人信息
 * @author: YuKai Fan
 * @create: 2022-06-26 19:16
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskAssigneeInfo {

    /**
     * 处理人名称
     */
    private String assigneeName;

    /**
     * 处理人部门名称
     */
    private String deptName;

    /**
     * 处理人角色名称集合
     */
    private Set<String> roleNameSet;
}
