package com.ah.cloud.permissions.biz.application.manager;

import com.ah.cloud.permissions.biz.application.helper.UserAuthorityHelper;
import com.ah.cloud.permissions.biz.domain.user.UserAuthorityDTO;
import com.ah.cloud.permissions.biz.infrastructure.application.service.*;
import com.ah.cloud.permissions.biz.infrastructure.repository.bean.SysRolePermission;
import com.ah.cloud.permissions.biz.infrastructure.repository.bean.SysUser;
import com.ah.cloud.permissions.biz.infrastructure.repository.bean.SysUserPermission;
import com.ah.cloud.permissions.biz.infrastructure.repository.bean.SysUserRole;
import com.ah.cloud.permissions.enums.common.DeletedEnum;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @program: permissions-center
 * @description: 用户权限管理
 * @author: YuKai Fan
 * @create: 2021-12-22 18:09
 **/
@Slf4j
@Component
public class UserAuthManager {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysUserPermissionService sysUserPermissionService;
    @Autowired
    private SysUserRoleService sysUserRoleService;
    @Autowired
    private SysRolePermissionService sysRolePermissionService;
    @Autowired
    private UserAuthorityHelper userAuthorityHelper;

    /**
     * 根据用户账号 构造用户权限实体数据
     *
     * @param username
     * @return
     */
    public UserAuthorityDTO createUserAuthorityByUsername(String username) {
        SysUser sysUser = sysUserService.getOne(new QueryWrapper<SysUser>().lambda()
                .eq(SysUser::getAccount, username)
                .eq(SysUser::getDeleted, DeletedEnum.NO.value)
        );
        if (Objects.isNull(sysUser)) {
            return null;
        }

        UserAuthorityDTO userAuthorityDTO = userAuthorityHelper.convertDTO(sysUser);
        SysUserPermission sysUserPermission = sysUserPermissionService.getOne(new QueryWrapper<SysUserPermission>().lambda()
                .eq(SysUserPermission::getUserId, sysUser.getId())
                .eq(SysUserPermission::getDeleted, DeletedEnum.NO.value)
        );

        Set<String> allApiCodeList = Sets.newHashSet();

        String apiCodeListStr = sysUserPermission.getApiCodeList();
        if (StringUtils.isNotEmpty(apiCodeListStr)) {
            String[] apiCodeList = apiCodeListStr.split(",");
            allApiCodeList.addAll(Arrays.asList(apiCodeList));
        }

        List<SysUserRole> sysUserRoles = sysUserRoleService.list(new QueryWrapper<SysUserRole>().lambda()
                .eq(SysUserRole::getUserId, sysUser.getId())
                .eq(SysUserRole::getDeleted, DeletedEnum.NO.value)
        );

        if (!CollectionUtils.isEmpty(sysUserRoles)) {
            List<Long> roleIds = sysUserRoles.stream()
                    .map(SysUserRole::getRoleId)
                    .collect(Collectors.toList());

            List<SysRolePermission> sysRolePermissions = sysRolePermissionService.list(new QueryWrapper<SysRolePermission>().lambda()
                    .in(SysRolePermission::getRoleId, roleIds)
                    .eq(SysRolePermission::getDeleted, DeletedEnum.NO.value)
            );

            List<String> roleApiListStr = sysRolePermissions.stream()
                    .map(SysRolePermission::getApiCodeList)
                    .collect(Collectors.toList());

            List<String[]> roleApiListArr = roleApiListStr.stream()
                    .map(this::stringToArray)
                    .collect(Collectors.toList());

            List<List<String>> roleApiList = roleApiListArr.stream()
                    .map(this::arrayToList)
                    .collect(Collectors.toList());

            roleApiList.stream()
                    .forEach(list -> allApiCodeList.addAll(list));
        }

        userAuthorityDTO.setAuthorities(allApiCodeList);
        return userAuthorityDTO;
    }

    private String[] stringToArray(String listStr) {
        if (StringUtils.isNotEmpty(listStr)) {
            return listStr.split(",");
        }
        return new String[]{};
    }

    private List<String> arrayToList(String[] arrStr) {
        return Arrays.asList(arrStr);
    }
}
