package com.ah.cloud.permissions.biz.domain.menu.query;

import lombok.Data;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-03-29 17:25
 **/
@Data
public class SysMenuTreeSelectQuery {

    /**
     * 角色编码
     */
    private String roleCode;

    /**
     * 菜单树查询类型(1: 全量查询, 2: 角色菜单查询, 3: 用户菜单查询)
     */
    private Integer sysMenuQueryType;
}
