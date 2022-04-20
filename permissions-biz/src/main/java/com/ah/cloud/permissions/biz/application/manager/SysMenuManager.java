package com.ah.cloud.permissions.biz.application.manager;

import com.ah.cloud.permissions.biz.application.checker.SysMenuChecker;
import com.ah.cloud.permissions.biz.application.helper.SysMenuHelper;
import com.ah.cloud.permissions.biz.application.service.SysMenuService;
import com.ah.cloud.permissions.biz.application.service.ext.SysRoleMenuExtService;
import com.ah.cloud.permissions.biz.application.service.ext.SysUserMenuExtService;
import com.ah.cloud.permissions.biz.domain.menu.form.SysMenuAddForm;
import com.ah.cloud.permissions.biz.domain.menu.form.SysMenuUpdateForm;
import com.ah.cloud.permissions.biz.domain.menu.query.SysMenuQuery;
import com.ah.cloud.permissions.biz.domain.menu.query.SysMenuTreeSelectQuery;
import com.ah.cloud.permissions.biz.domain.menu.tree.SysMenuTreeSelectVo;
import com.ah.cloud.permissions.biz.domain.menu.tree.SysMenuTreeVo;
import com.ah.cloud.permissions.biz.domain.menu.vo.RouterVo;
import com.ah.cloud.permissions.biz.domain.menu.vo.SysMenuVo;
import com.ah.cloud.permissions.biz.infrastructure.constant.PermissionsConstants;
import com.ah.cloud.permissions.biz.infrastructure.exception.BizException;
import com.ah.cloud.permissions.biz.infrastructure.repository.bean.SysMenu;
import com.ah.cloud.permissions.biz.infrastructure.repository.bean.SysRoleMenu;
import com.ah.cloud.permissions.biz.infrastructure.repository.bean.SysUserMenu;
import com.ah.cloud.permissions.biz.infrastructure.util.SecurityUtils;
import com.ah.cloud.permissions.enums.MenuQueryTypeEnum;
import com.ah.cloud.permissions.enums.MenuTypeEnum;
import com.ah.cloud.permissions.enums.common.DeletedEnum;
import com.ah.cloud.permissions.enums.common.ErrorCodeEnum;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2021-12-03 15:33
 **/
@Component
@Slf4j
public class SysMenuManager {
    @Resource
    private SysMenuHelper sysMenuHelper;
    @Resource
    private SysMenuChecker sysMenuChecker;
    @Resource
    private SysMenuService sysMenuService;
    @Resource
    private SysUserMenuExtService sysUserMenuExtService;
    @Resource
    private SysRoleMenuExtService sysRoleMenuExtService;

    /**
     * 添加菜单
     *
     * @param form
     */
    public void addSysMenu(SysMenuAddForm form) {
        // 判断menuCode是否存在
        List<SysMenu> sysMenus = sysMenuService.list(
                new QueryWrapper<SysMenu>().lambda()
                        .eq(SysMenu::getMenuCode, form.getMenuCode())
                        .eq(SysMenu::getDeleted, DeletedEnum.NO.value)
        );
        if (!CollectionUtils.isEmpty(sysMenus)) {
            throw new BizException(ErrorCodeEnum.MENU_CODE_IS_EXISTED, form.getMenuCode());
        }
        SysMenu sysMenu = sysMenuHelper.convertToEntity(form);
        sysMenuService.save(sysMenu);
    }

    /**
     * 更新菜单
     *
     * @param form
     */
    public void updateSysMenu(SysMenuUpdateForm form) {
        SysMenu sysMenu = sysMenuService.getOne(
                new QueryWrapper<SysMenu>().lambda()
                        .eq(SysMenu::getId, form.getId())
                        .eq(SysMenu::getDeleted, DeletedEnum.NO.value)
        );
        if (Objects.isNull(sysMenu)) {
            throw new BizException(ErrorCodeEnum.MENU_INFO_IS_NOT_EXISTED_UPDATE_FAILED);
        }
        // menuCode不能修改
        if (!StringUtils.equals(form.getMenuCode(), sysMenu.getMenuCode())) {
            throw new BizException(ErrorCodeEnum.MENU_CODE_CANNOT_CHANGE_UPDATE_FAILED);
        }

        SysMenu sysMenuUpdate = sysMenuHelper.convertToEntity(form);
        sysMenuUpdate.setVersion(sysMenu.getVersion());
        sysMenuService.updateById(sysMenuUpdate);
    }

    /**
     * 删除菜单
     *
     * @param id
     */
    public void deleteSysMenuById(Long id) {
        SysMenu sysMenu = sysMenuService.getById(id);
        if (Objects.isNull(sysMenu)) {
            return;
        }
        SysMenu sysMenuDelete = new SysMenu();
        sysMenuDelete.setDeleted(id);
        sysMenuDelete.setModifier(SecurityUtils.getUserNameBySession());
        sysMenuDelete.setVersion(sysMenu.getVersion());
        final boolean deleteResult = sysMenuService.updateById(sysMenuDelete);
        if (!deleteResult) {
            throw new BizException(ErrorCodeEnum.VERSION_ERROR);
        }
    }

    /**
     * 根据id查询菜单信息
     *
     * @param id
     * @return
     */
    public SysMenuVo findSysMenuById(Long id) {
        SysMenu sysMenu = sysMenuService.getById(id);
        return sysMenuHelper.convertEntityToVo(sysMenu);
    }

    /**
     * 组装用户菜单路由
     *
     * @param userMenuSet 用户菜单集合
     * @return
     */
    public List<RouterVo> assembleMenuRouteByUser(Set<Long> userMenuSet) {
        List<SysMenu> sysMenuList = sysMenuService.list(
                new QueryWrapper<SysMenu>().lambda()
                        .in(SysMenu::getId, userMenuSet)
                        .ne(SysMenu::getMenuType, MenuTypeEnum.BUTTON)
                        .eq(SysMenu::getDeleted, DeletedEnum.NO.value)
        );

        // todo 待完成
        return sysMenuHelper.convertToRouteVo(sysMenuList);
    }

    /**
     * 获取菜单树列表
     *
     * 这个方法用途
     * 1、新增角色选择菜单树;
     * 2、修改角色权限, 选择菜单树, 包含之前已选的菜单节点;
     * 3、新增用户, 选择菜单树;
     * 4、修改用户, 选择菜单树, 包含之前已选的菜单节点;
     *
     * @param query
     * @return
     */
    public List<SysMenuTreeSelectVo> listMenuSelectTree(SysMenuTreeSelectQuery query) {
        sysMenuChecker.checkSysMenuQueryType(query);
        List<SysMenu> sysMenuList = sysMenuService.list(
                new QueryWrapper<SysMenu>().lambda()
                        .eq(SysMenu::getDeleted, DeletedEnum.NO.value)
        );
        Set<Long> selectedMenuIdSet = Sets.newHashSet();
        MenuQueryTypeEnum menuQueryTypeEnum = MenuQueryTypeEnum.getByType(query.getSysMenuQueryType());
        if (menuQueryTypeEnum.isRoleMenu()) {
            List<SysRoleMenu> sysRoleMenuList = sysRoleMenuExtService.list(
                    new QueryWrapper<SysRoleMenu>().lambda()
                            .eq(SysRoleMenu::getRoleCode, query.getRoleCode())
                            .eq(SysRoleMenu::getDeleted, DeletedEnum.NO.value)
            );

            selectedMenuIdSet = sysRoleMenuList.stream()
                    .map(SysRoleMenu::getMenuId)
                    .collect(Collectors.toSet());
        }

        if (menuQueryTypeEnum.isUserMenu()) {
            // 获取当前登录人id todo
            Long currentUserId = PermissionsConstants.SUPER_ADMIN;
            List<SysUserMenu> sysUserMenuList = sysUserMenuExtService.list(
                    new QueryWrapper<SysUserMenu>().lambda()
                            .eq(SysUserMenu::getUserId, currentUserId)
                            .eq(SysUserMenu::getDeleted, DeletedEnum.NO.value)
            );
            selectedMenuIdSet = sysUserMenuList.stream()
                    .map(SysUserMenu::getMenuId)
                    .collect(Collectors.toSet());
        }

        return sysMenuHelper.convertToTreeSelectVo(sysMenuList, selectedMenuIdSet);
    }

    /**
     * 获取菜单树
     * @param query
     * @return
     */
    public List<SysMenuTreeVo> listSysMenuTree(SysMenuQuery query) {
        List<SysMenu> sysMenuList = sysMenuService.list(
                new QueryWrapper<SysMenu>().lambda()
                        .eq(SysMenu::getDeleted, DeletedEnum.NO.value)
        );
        return null;
    }
}
