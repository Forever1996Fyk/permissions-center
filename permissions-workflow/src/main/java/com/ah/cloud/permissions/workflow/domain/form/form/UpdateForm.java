package com.ah.cloud.permissions.workflow.domain.form.form;

import com.ah.cloud.permissions.biz.infrastructure.annotation.EnumValid;
import com.ah.cloud.permissions.enums.workflow.FormTypeEnum;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-06-15 11:19
 **/
@Data
public class UpdateForm {

    /**
     * 表单id
     */
    @NotNull(message = "表单id不能为空")
    private Long id;

    /**
     * 表单名称
     */
    @NotEmpty(message = "表单名称不能为空")
    private String name;

    /**
     * 表单编码
     */
    @NotEmpty(message = "表单编码不能为空")
    private String code;

    /**
     * 表单配置
     */
    @NotEmpty(message = "表单配置不能为空")
    private String config;

    /**
     * 表单项集合不能为空
     */
    @NotNull(message = "表单项不能为空")
    private List<String> fields;

    /**
     * 租户id
     */
    private String tenantId;

    /**
     * 表单类型
     */
    @EnumValid(enumClass = FormTypeEnum.class, enumMethod = "isValid")
    private Integer formType;
}
