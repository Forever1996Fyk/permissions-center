package com.ah.cloud.permissions.biz.domain.role.query;

import com.ah.cloud.permissions.domain.common.PageQuery;
import lombok.*;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2021-12-06 16:37
 **/
@Data
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
