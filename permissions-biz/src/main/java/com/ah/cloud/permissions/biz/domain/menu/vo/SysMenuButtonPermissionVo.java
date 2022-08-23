package com.ah.cloud.permissions.biz.domain.menu.vo;

import lombok.*;

import java.util.List;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-08-16 15:23
 **/
@Getter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class SysMenuButtonPermissionVo {

    /**
     * 按钮编码
     */
    private String buttonCode;

    /**
     * 按钮权限
     */
    private ButtonPermission buttonPermission;

    @Getter
    @Builder
    @ToString
    @EqualsAndHashCode
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ButtonPermission {

        /**
         * 权限集合
         */
        private List<String> permission;
    }

}
