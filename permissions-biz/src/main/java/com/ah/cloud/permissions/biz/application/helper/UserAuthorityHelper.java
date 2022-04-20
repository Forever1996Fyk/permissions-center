package com.ah.cloud.permissions.biz.application.helper;

import com.ah.cloud.permissions.biz.application.service.ext.*;
import com.ah.cloud.permissions.biz.domain.user.UserAuthorityDTO;
import com.ah.cloud.permissions.biz.infrastructure.repository.bean.*;
import com.ah.cloud.permissions.enums.UserStatusEnum;
import com.ah.cloud.permissions.enums.common.DeletedEnum;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Sets;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2021-12-22 18:14
 **/
@Component
public class UserAuthorityHelper {
    @Resource
    private SysUserApiExtService sysUserApiExtService;
    @Resource
    private SysRoleApiExtService sysRoleApiExtService;
    @Resource
    private SysMenuApiExtService sysMenuApiExtService;
    @Resource
    private SysUserMenuExtService sysUserMenuExtService;
    @Resource
    private SysUserRoleExtService sysUserRoleExtService;
    @Resource
    private SysRoleMenuExtService sysRoleMenuExtService;

    /**
     * 数据转换
     *
     * @param sysUser
     * @return
     */
    public UserAuthorityDTO convertDTO(SysUser sysUser) {
        return UserAuthorityConvert.INSTANCE.convert(sysUser);
    }

    /**
     * 构建角色编码
     * @param userId
     * @return
     */
    public Set<String> buildRoleCodeSet(Long userId) {
        Set<String> roleCodeSet = Sets.newHashSet();
        // 查询用户角色
        List<SysUserRole> sysUserRoleList = sysUserRoleExtService.list(
                new QueryWrapper<SysUserRole>().lambda()
                        .select(SysUserRole::getRoleCode)
                        .eq(SysUserRole::getUserId, userId)
                        .eq(SysUserRole::getDeleted, DeletedEnum.NO.value)
        );
        if (!CollectionUtils.isEmpty(sysUserRoleList)) {
            roleCodeSet = sysUserRoleList.stream()
                    .map(SysUserRole::getRoleCode)
                    .collect(Collectors.toSet());
        }
        return roleCodeSet;
    }

    /**
     * 构建菜单id集合
     * @param userId
     * @param roleCodeSet
     * @return
     */
    public Set<Long> buildMenuIdSet(Long userId, Set<String> roleCodeSet) {
        Set<Long> menuIdSet = Sets.newHashSet();
        // 查询角色菜单
        if (!CollectionUtils.isEmpty(roleCodeSet)) {
            // 查询角色菜单
            List<SysRoleMenu> sysRoleMenuList = sysRoleMenuExtService.list(
                    new QueryWrapper<SysRoleMenu>().lambda()
                            .select(SysRoleMenu::getMenuId)
                            .in(SysRoleMenu::getRoleCode, roleCodeSet)
                            .eq(SysRoleMenu::getDeleted, DeletedEnum.NO.value)
            );

            if (!CollectionUtils.isEmpty(sysRoleMenuList)) {
                Set<Long> roleMenuIdSet = sysRoleMenuList.stream()
                        .map(SysRoleMenu::getMenuId)
                        .collect(Collectors.toSet());
                menuIdSet.addAll(roleMenuIdSet);
            }
        }

        // 查询用户菜单
        List<SysUserMenu> sysUserMenuList = sysUserMenuExtService.list(
                new QueryWrapper<SysUserMenu>().lambda()
                        .select(SysUserMenu::getMenuId)
                        .eq(SysUserMenu::getUserId, userId)
                        .eq(SysUserMenu::getDeleted, DeletedEnum.NO.value)
        );
        if (!CollectionUtils.isEmpty(sysUserMenuList)) {
            Set<Long> userMenuIdSet = sysUserMenuList.stream()
                    .map(SysUserMenu::getMenuId)
                    .collect(Collectors.toSet());
            menuIdSet.addAll(userMenuIdSet);
        }
        return menuIdSet;
    }

    /**
     * 构建apiCode集合
     * @param userId
     * @param roleCodeSet
     * @param menuIdSet
     * @return
     */
    public Set<String> buildApiCodeSet(Long userId, Set<String> roleCodeSet, Set<Long> menuIdSet) {
        Set<String> apiCodeSet = Sets.newHashSet();
        // 查询用户api
        List<SysUserApi> sysUserApiList = sysUserApiExtService.list(
                new QueryWrapper<SysUserApi>().lambda()
                        .select(SysUserApi::getApiCode)
                        .eq(SysUserApi::getUserId, userId)
                        .eq(SysUserApi::getDeleted, DeletedEnum.NO.value)
        );
        if (!CollectionUtils.isEmpty(sysUserApiList)) {
            Set<String> userApiCodeSet = sysUserApiList.stream()
                    .map(SysUserApi::getApiCode)
                    .collect(Collectors.toSet());
            apiCodeSet.addAll(userApiCodeSet);
        }

        // 查询角色菜单
        if (!CollectionUtils.isEmpty(roleCodeSet)) {
            // 查询角色api
            List<SysRoleApi> sysRoleApiList = sysRoleApiExtService.list(
                    new QueryWrapper<SysRoleApi>().lambda()
                            .select(SysRoleApi::getApiCode)
                            .in(SysRoleApi::getRoleCode, roleCodeSet)
                            .eq(SysRoleApi::getDeleted, DeletedEnum.NO.value)
            );

            if (!CollectionUtils.isEmpty(sysRoleApiList)) {
                Set<String> roleApiCodeSet = sysRoleApiList.stream()
                        .map(SysRoleApi::getApiCode)
                        .collect(Collectors.toSet());
                apiCodeSet.addAll(roleApiCodeSet);
            }
        }

        // 查询菜单api
        if (!CollectionUtils.isEmpty(menuIdSet)) {
            List<SysMenuApi> sysMenuApiList = sysMenuApiExtService.list(
                    new QueryWrapper<SysMenuApi>().lambda()
                            .select(SysMenuApi::getApiCode)
                            .in(SysMenuApi::getMenuId, menuIdSet)
                            .eq(SysMenuApi::getDeleted, DeletedEnum.NO.value)
            );

            if (!CollectionUtils.isEmpty(sysMenuApiList)) {
                Set<String> menuApiCodeSet = sysMenuApiList.stream()
                        .map(SysMenuApi::getApiCode)
                        .collect(Collectors.toSet());
                apiCodeSet.addAll(menuApiCodeSet);
            }
        }
        return apiCodeSet;
    }


    @Mapper(uses = UserStatusEnum.class)
    public interface UserAuthorityConvert {
        UserAuthorityHelper.UserAuthorityConvert INSTANCE = Mappers.getMapper(UserAuthorityHelper.UserAuthorityConvert.class);

        /**
         * 数据转换
         *
         * @param sysUser
         * @return
         */
        @Mappings({
                @Mapping(target = "userId", source = "id"),
                @Mapping(target = "userStatusEnum", source = "status")
        })
        UserAuthorityDTO convert(SysUser sysUser);
    }
}
