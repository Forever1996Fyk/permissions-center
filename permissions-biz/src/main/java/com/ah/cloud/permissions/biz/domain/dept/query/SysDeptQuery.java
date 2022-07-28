package com.ah.cloud.permissions.biz.domain.dept.query;

import lombok.Data;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-18 16:54
 **/
@Data
public class SysDeptQuery {

    /**
     * 部门名称
     */
    private String deptName;

    /**
     * 部门编码
     */
    private String deptCode;
}
