package com.ah.cloud.permissions.biz.domain.dept.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-18 16:52
 **/
@Data
public class DeptUpdateForm {

    /**
     * 部门id
     */
    @NotNull(message = "部门id不能为空")
    private Long id;

    /**
     * 部门名称
     */
    @NotEmpty(message = "部门名称不能为空")
    private String name;

    /**
     * 序号
     */
    @NotNull(message = "序号不能为空")
    private Integer deptOrder;
}
