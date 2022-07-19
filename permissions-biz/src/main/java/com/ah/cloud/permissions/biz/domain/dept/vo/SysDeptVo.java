package com.ah.cloud.permissions.biz.domain.dept.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-18 16:55
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SysDeptVo {

    /**
     * 部门ID
     */
    private Long id;

    /**
     * 父部门id
     */
    private Long parentId;

    /**
     * 部门编码
     */
    private String deptCode;

    /**
     * 父部门编码
     */
    private String parentDeptCode;

    /**
     * 部门名称
     */
    private String name;

    /**
     * 部门序号
     */
    private Integer deptOrder;
}
