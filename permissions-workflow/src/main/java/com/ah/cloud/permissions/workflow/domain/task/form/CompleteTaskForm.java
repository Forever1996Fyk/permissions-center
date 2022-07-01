package com.ah.cloud.permissions.workflow.domain.task.form;

import com.ah.cloud.permissions.biz.infrastructure.annotation.EnumValid;
import com.ah.cloud.permissions.enums.workflow.FlowCommentTypeEnum;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-06-19 10:57
 **/
@Data
public class CompleteTaskForm {

    /**
     * 任务id
     */
    @NotEmpty(message = "任务主键不能为空")
    private String taskId;

    /**
     * 流程id
     */
    @NotNull(message = "流程主键不能为空")
    private Long processId;

    /**
     * 流程实例id
     */
    @NotNull(message = "流程实例主键不能为空")
    private String processInstanceId;

    /**
     * 任务意见
     */
    private String comment;

    /**
     * 意见类型
     */
    private String commentType;

    /**
     * 流程变量
     */
    private Map<String, Object> variables;
}
