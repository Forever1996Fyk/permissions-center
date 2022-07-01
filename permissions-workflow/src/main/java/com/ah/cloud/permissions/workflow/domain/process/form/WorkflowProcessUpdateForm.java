package com.ah.cloud.permissions.workflow.domain.process.form;

import com.ah.cloud.permissions.biz.infrastructure.annotation.EnumValid;
import com.ah.cloud.permissions.enums.workflow.BusinessCategoryEnum;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-06-15 13:38
 **/
@Data
public class WorkflowProcessUpdateForm {

    /**
     * 流程id
     */
    @NotNull(message = "流程id不能为空")
    private Long id;


    /**
     * 流程名称
     */
    @NotEmpty(message = "流程名称不能为空")
    private String name;

    /**
     * 流程编码
     */
    @NotEmpty(message = "流程编码不能为空")
    private String key;

    /**
     * 表单编码
     */
    private String formCode;

    /**
     * 流程icon
     */
    private String processIcon;

    /**
     * 租户id
     */
    private String tenantId;

    /**
     * 流程类别
     */
    @EnumValid(enumClass = BusinessCategoryEnum.class, enumMethod = "isValid")
    private String processCategory;

    /**
     * 描述
     */
    private String description;
}
