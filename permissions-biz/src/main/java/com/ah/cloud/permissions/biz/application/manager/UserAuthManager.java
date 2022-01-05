package com.ah.cloud.permissions.biz.application.manager;

import com.ah.cloud.permissions.biz.application.helper.UserAuthorityHelper;
import com.ah.cloud.permissions.biz.application.service.SysRolePermissionService;
import com.ah.cloud.permissions.biz.application.service.SysUserPermissionService;
import com.ah.cloud.permissions.biz.application.service.SysUserRoleService;
import com.ah.cloud.permissions.biz.application.service.SysUserService;
import com.ah.cloud.permissions.biz.domain.user.UserAuthorityDTO;
import com.ah.cloud.permissions.biz.infrastructure.repository.bean.SysRolePermission;
import com.ah.cloud.permissions.biz.infrastructure.repository.bean.SysUser;
import com.ah.cloud.permissions.biz.infrastructure.repository.bean.SysUserPermission;
import com.ah.cloud.permissions.biz.infrastructure.repository.bean.SysUserRole;
import com.ah.cloud.permissions.biz.infrastructure.util.JsonUtils;
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
        /*
        1. 根据账号查询用户信息
         */
        SysUser sysUser = sysUserService.getOne(new QueryWrapper<SysUser>().lambda()
                .eq(SysUser::getAccount, username)
                .eq(SysUser::getDeleted, DeletedEnum.NO.value)
        );
        if (Objects.isNull(sysUser)) {
            log.warn("UserAuthManager[createUserAuthorityByUsername] ==> 根据账号查询用户信息为空 ==> account:{}", username);
            return null;
        }

        /*
        数据转换成用户权限实体
         */
        UserAuthorityDTO userAuthorityDTO = userAuthorityHelper.convertDTO(sysUser);

        /*
        根据用户id查询用户apiCode数据
         */
        SysUserPermission sysUserPermission = sysUserPermissionService.getOne(new QueryWrapper<SysUserPermission>().lambda()
                .eq(SysUserPermission::getUserId, sysUser.getId())
                .eq(SysUserPermission::getDeleted, DeletedEnum.NO.value)
        );

        log.info("UserAuthManager[createUserAuthorityByUsername] ==> 根据用户id查询用户apiCode数据 ==> sysUserPermission:{}", JsonUtils.toJSONString(sysUserPermission));

        Set<String> allApiCodeList = Sets.newHashSet();
        String apiCodeListStr = sysUserPermission.getApiCodeList();
        allApiCodeList.addAll(this.arrayToList(this.stringToArray(apiCodeListStr)));

        /*
        根据用户id, 查询该用户的用户组
         */
        List<SysUserRole> sysUserRoles = sysUserRoleService.list(new QueryWrapper<SysUserRole>().lambda()
                .eq(SysUserRole::getUserId, sysUser.getId())
                .eq(SysUserRole::getDeleted, DeletedEnum.NO.value)
        );

        log.info("UserAuthManager[createUserAuthorityByUsername] ==> 根据用户id查询该用户的用户组 ==> sysUserRoles:{}", JsonUtils.toJSONString(sysUserRoles));

        if (!CollectionUtils.isEmpty(sysUserRoles)) {
            List<Long> roleIds = sysUserRoles.stream()
                    .map(SysUserRole::getRoleId)
                    .collect(Collectors.toList());

            /*
            根据roleId集合查询用户组所需的apiCode
             */
            List<SysRolePermission> sysRolePermissions = sysRolePermissionService.list(new QueryWrapper<SysRolePermission>().lambda()
                    .in(SysRolePermission::getRoleId, roleIds)
                    .eq(SysRolePermission::getDeleted, DeletedEnum.NO.value)
            );

            log.info("UserAuthManager[createUserAuthorityByUsername] ==> 根据roleId集合查询用户组所需的apiCode ==> sysRolePermissions:{}", JsonUtils.toJSONString(sysRolePermissions));

            String roleApiListStr = sysRolePermissions.stream()
                    .map(SysRolePermission::getApiCodeList)
                    .map(item -> StringUtils.join(item, ","))
                    .findFirst()
                    .get();

            List<String> roleApiList = this.arrayToList(this.stringToArray(roleApiListStr));
            allApiCodeList.addAll(roleApiList);
        }

        userAuthorityDTO.setAuthorities(allApiCodeList);
        return userAuthorityDTO;
    }

    private String[] stringToArray(String listStr) {
        if (StringUtils.isNotEmpty(listStr)) {
            return StringUtils.split(listStr, ",");
        }
        return new String[]{};
    }

    private List<String> arrayToList(String[] arrStr) {
        return Arrays.asList(arrStr);
    }
}
