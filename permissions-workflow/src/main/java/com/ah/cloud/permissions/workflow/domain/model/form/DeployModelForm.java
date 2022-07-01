package com.ah.cloud.permissions.workflow.domain.model.form;

import com.ah.cloud.permissions.biz.infrastructure.annotation.EnumValid;
import com.ah.cloud.permissions.enums.workflow.WorkflowTypeEnum;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-06-13 14:09
 **/
@Data
public class DeployModelForm {

    /**
     * 流程模型id
     */
    @NotEmpty(message = "模型id不能为空")
    private String modelId;

    /**
     * 部署名称
     */
    private String deployName;


    /**
     * 工作流类型
     */
    @EnumValid(enumClass = WorkflowTypeEnum.class, enumMethod = "isValid")
    private Integer workflowType;

}
