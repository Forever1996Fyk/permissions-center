package com.ah.cloud.permissions.biz.application.helper;

import com.ah.cloud.permissions.biz.application.service.SysRoleService;
import com.ah.cloud.permissions.biz.application.service.ext.*;
import com.ah.cloud.permissions.biz.domain.user.UserAuthorityDTO;
import com.ah.cloud.permissions.biz.domain.user.dto.RoleDTO;
import com.ah.cloud.permissions.biz.domain.user.dto.UserScopeInfoDTO;
import com.ah.cloud.permissions.biz.infrastructure.repository.bean.*;
import com.ah.cloud.permissions.biz.infrastructure.util.CollectionUtils;
import com.ah.cloud.permissions.enums.UserStatusEnum;
import com.ah.cloud.permissions.enums.YesOrNoEnum;
import com.ah.cloud.permissions.enums.common.DeletedEnum;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;
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
    private SysRoleService sysRoleService;
    @Resource
    private SysUserExtService sysUserExtService;
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
     * 构建用户角色信息
     * @param sysUser
     * @return
     */
    public UserScopeInfoDTO buildUserScopeInfo(SysUser sysUser) {
        List<SysUserRole> sysUserRoleList = sysUserRoleExtService.list(
                new QueryWrapper<SysUserRole>().lambda()
                        .eq(SysUserRole::getUserId, sysUser.getUserId())
                        .eq(SysUserRole::getIsAvailable, YesOrNoEnum.YES.getType())
                        .eq(SysUserRole::getDeleted, DeletedEnum.NO.value)
        );

        Set<String> roleCodeSet = CollectionUtils.convertSet(sysUserRoleList, SysUserRole::getRoleCode);
        List<SysRole> sysRoleList = sysRoleService.list(
                new QueryWrapper<SysRole>().lambda()
                        .in(SysRole::getRoleCode, roleCodeSet)
                        .eq(SysRole::getDeleted, DeletedEnum.NO.value)
        );
        return UserScopeInfoDTO.builder()
                .roleSet(this.convertToRoleDTOSet(sysRoleList))
                .nickName(sysUser.getNickName())
                .userId(sysUser.getUserId())
                .userName(sysUser.getNickName())
                .build();
    }

    /**
     * 构建用户权限信息
     * @param userIds
     * @return
     */
    public List<UserScopeInfoDTO> buildUserScopeInfoList(Collection<Long> userIds) {
        Set<SysUser> sysUserSet = sysUserExtService.listByUserId(userIds);
        List<SysUserRole> sysUserRoleList = sysUserRoleExtService.list(
                new QueryWrapper<SysUserRole>().lambda()
                        .in(SysUserRole::getUserId, userIds)
                        .eq(SysUserRole::getIsAvailable, YesOrNoEnum.YES.getType())
                        .eq(SysUserRole::getDeleted, DeletedEnum.NO.value)
        );
        Set<String> roleCodeSet = CollectionUtils.convertSet(sysUserRoleList, SysUserRole::getRoleCode);
        List<SysRole> sysRoleList = sysRoleService.list(
                new QueryWrapper<SysRole>().lambda()
                        .in(SysRole::getRoleCode, roleCodeSet)
                        .eq(SysRole::getDeleted, DeletedEnum.NO.value)
        );

        Set<RoleDTO> roleDTOSet = this.convertToRoleDTOSet(sysRoleList);
        Map<String, RoleDTO> roleDTOMap = CollectionUtils.convertMap(roleDTOSet, RoleDTO::getRoleCode);
        Map<Long, SysUser> sysUserMap = CollectionUtils.convertMap(sysUserSet, SysUser::getUserId);
        Map<Long, List<SysUserRole>> sysUserRoleMap = CollectionUtils.convertMultiMap(sysUserRoleList, SysUserRole::getUserId);

        List<UserScopeInfoDTO> list = Lists.newArrayList();
        for (Map.Entry<Long, List<SysUserRole>> entry : sysUserRoleMap.entrySet()) {
            Long userId = entry.getKey();
            List<SysUserRole> userRoleList = entry.getValue();
            SysUser sysUser = sysUserMap.get(userId);
            if (Objects.isNull(sysUser)) {
                continue;
            }
            Set<String> userRoleCodeSet = CollectionUtils.convertSet(userRoleList, SysUserRole::getRoleCode);
            Set<RoleDTO> userRoleDTOSet = Sets.newHashSet();
            for (String roleCode : userRoleCodeSet) {
                RoleDTO roleDTO = roleDTOMap.get(roleCode);
                if (Objects.isNull(roleDTO)) {
                    continue;
                }
                userRoleDTOSet.add(roleDTO);
            }
            list.add(UserScopeInfoDTO.builder()
                    .roleSet(userRoleDTOSet)
                    .nickName(sysUser.getNickName())
                    .userId(sysUser.getUserId())
                    .userName(sysUser.getNickName())
                    .build());
        }
        return list;
    }

    /**
     * 数据转换
     * @param sysRole
     * @return
     */
    public RoleDTO convertToRoleDTO(SysRole sysRole) {
        return RoleDTO.builder()
                .roleId(sysRole.getId())
                .roleCode(sysRole.getRoleCode())
                .roleName(sysRole.getRoleName())
                .build();
    }

    /**
     * 数据转换
     * @param sysRoles
     * @return
     */
    public Set<RoleDTO> convertToRoleDTOSet(Collection<SysRole> sysRoles) {
        return sysRoles.stream()
                .map(this::convertToRoleDTO)
                .collect(Collectors.toSet());
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
                @Mapping(target = "userStatusEnum", source = "status")
        })
        UserAuthorityDTO convert(SysUser sysUser);
    }
}
