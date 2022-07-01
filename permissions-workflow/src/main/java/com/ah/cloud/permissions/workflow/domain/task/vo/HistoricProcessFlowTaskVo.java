package com.ah.cloud.permissions.workflow.domain.task.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @program: permissions-center
 * @description: 历史流程流转任务
 * @author: YuKai Fan
 * @create: 2022-06-20 14:54
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HistoricProcessFlowTaskVo {

    /**
     * 任务id
     */
    private String taskId;

    /**
     * 任务key
     */
    private String taskKey;

    /**
     * 任务名称
     */
    private String taskName;

    /**
     * 处理人id
     */
    private String assigneeId;

    /**
     * 处理人名称
     */
    private String assigneeName;

    /**
     * 部门名称
     */
    private String deptName;

    /**
     * 表单内容
     */
    private String formContent;

    /**
     * 表单变量
     */
    private Map<String, Object> formVariables;

    /**
     * 任务评论
     */
    private FlowTaskCommentVo commentVo;

    /**
     * 完成时间
     */
    private String finishTime;
}
