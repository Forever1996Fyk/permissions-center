package com.ah.cloud.permissions.biz.domain.user.dto;

import lombok.*;

/**
 * @program: permissions-center
 * @description: 角色dto
 * @author: YuKai Fan
 * @create: 2022-06-27 07:47
 **/
@Getter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class RoleDTO {

    /**
     * 角色id
     */
    private Long roleId;

    /**
     * 角色编码
     */
    private String roleCode;

    /**
     * 角色名称
     */
    private String roleName;
}
