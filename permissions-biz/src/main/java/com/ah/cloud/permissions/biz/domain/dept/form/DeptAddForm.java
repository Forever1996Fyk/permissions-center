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
public class DeptAddForm {

    /**
     * 部门编码
     */
    @NotEmpty(message = "部门编码不能为空")
    private String deptCode;

    /**
     * 父部门编码
     */
    @NotEmpty(message = "父部门编码不能为空")
    private String parentDeptCode;

    /**
     * 父部门id
     */
    @NotNull(message = "父部门id不能为空")
    private Long parentId;

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
