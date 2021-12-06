package com.ah.cloud.permissions.biz.domain.role.query;

import lombok.*;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2021-12-06 16:37
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PermissionsRoleQuery extends PageQuery {

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色编码
     */
    private String roleCode;
}
