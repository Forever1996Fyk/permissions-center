package com.ah.cloud.permissions.biz.domain.user.vo;

import lombok.*;

import java.util.Set;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-08-12 13:56
 **/
@Getter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class SelectUserRoleVo {

    /**
     * 已分配角色集合
     */
    private Set<RoleInfoVo> allocatedRoleSet;

    /**
     * 未分配角色集合
     */
    private Set<RoleInfoVo> allRoleSet;


    @Getter
    @Builder
    @ToString
    @EqualsAndHashCode
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RoleInfoVo {

        /**
         * 角色编码
         */
        private String roleCode;

        /**
         * 角色id
         */
        private Long roleId;

        /**
         * 角色名称
         */
        private String roleName;
    }
}
