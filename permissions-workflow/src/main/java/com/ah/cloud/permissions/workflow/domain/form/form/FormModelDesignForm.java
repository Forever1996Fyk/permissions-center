package com.ah.cloud.permissions.workflow.domain.form.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-06-15 11:19
 **/
@Data
public class FormModelDesignForm {

    /**
     * 表单id
     */
    @NotNull(message = "表单id不能为空")
    private Long id;

    /**
     * 表单内容
     */
    @NotEmpty(message = "表单内容不能为空")
    private String content;
}
