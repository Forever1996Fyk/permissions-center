package com.ah.cloud.permissions.workflow.domain.configuration.form;

import com.ah.cloud.permissions.biz.infrastructure.annotation.EnumValid;
import com.ah.cloud.permissions.enums.workflow.FormProcessTypeEnum;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-06-15 17:03
 **/
@Data
public class ProcessFormConfigurationAddForm {

    /**
     * 表单编码
     */
    @NotEmpty(message = "表单编码不能为空")
    private String formCode;


    /**
     * 流程id
     */
    @NotEmpty(message = "流程id不能为空")
    private String workflowProcessId;

    /**
     * 任务key
     */
    @NotEmpty(message = "任务key不能为空")
    private String taskKey;

    /**
     * 表单类型
     */
    @EnumValid(enumClass = FormProcessTypeEnum.class, enumMethod = "isValid")
    private Integer type;

    /**
     * 业务标题表达式
     */
    private String businessTitleExpression;

    /**
     * 租户id
     */
    private String tenantId;
}
