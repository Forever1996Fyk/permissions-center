package com.ah.cloud.permissions.biz.application.manager;

import com.ah.cloud.permissions.biz.application.checker.SysMenuChecker;
import com.ah.cloud.permissions.biz.application.helper.SysMenuHelper;
import com.ah.cloud.permissions.biz.application.service.SysApiService;
import com.ah.cloud.permissions.biz.application.service.SysMenuService;
import com.ah.cloud.permissions.biz.application.service.ext.SysMenuApiExtService;
import com.ah.cloud.permissions.biz.application.service.ext.SysRoleMenuExtService;
import com.ah.cloud.permissions.biz.application.service.ext.SysUserMenuExtService;
import com.ah.cloud.permissions.biz.domain.menu.form.SysMenuAddForm;
import com.ah.cloud.permissions.biz.domain.menu.form.SysMenuApiAddForm;
import com.ah.cloud.permissions.biz.domain.menu.form.SysMenuUpdateForm;
import com.ah.cloud.permissions.biz.domain.menu.query.SelectMenuApiQuery;
import com.ah.cloud.permissions.biz.domain.menu.query.SysMenuButtonPermissionQuery;
import com.ah.cloud.permissions.biz.domain.menu.query.SysMenuQuery;
import com.ah.cloud.permissions.biz.domain.menu.query.SysMenuTreeSelectQuery;
import com.ah.cloud.permissions.biz.domain.menu.tree.SysMenuTreeSelectVo;
import com.ah.cloud.permissions.biz.domain.menu.tree.SysMenuTreeVo;
import com.ah.cloud.permissions.biz.domain.menu.vo.RouterVo;
import com.ah.cloud.permissions.biz.domain.menu.vo.SelectMenuApiVo;
import com.ah.cloud.permissions.biz.domain.menu.vo.SysMenuButtonPermissionVo;
import com.ah.cloud.permissions.biz.domain.menu.vo.SysMenuVo;
import com.ah.cloud.permissions.biz.domain.role.vo.SelectRoleApiVo;
import com.ah.cloud.permissions.biz.infrastructure.constant.PermissionsConstants;
import com.ah.cloud.permissions.biz.infrastructure.exception.BizException;
import com.ah.cloud.permissions.biz.infrastructure.repository.bean.*;
import com.ah.cloud.permissions.biz.infrastructure.util.CollectionUtils;
import com.ah.cloud.permissions.biz.infrastructure.util.SecurityUtils;
import com.ah.cloud.permissions.domain.common.PageResult;
import com.ah.cloud.permissions.enums.MenuQueryTypeEnum;
import com.ah.cloud.permissions.enums.MenuTypeEnum;
import com.ah.cloud.permissions.enums.common.DeletedEnum;
import com.ah.cloud.permissions.enums.common.ErrorCodeEnum;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
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
    private SysApiService sysApiService;
    @Resource
    private SysMenuHelper sysMenuHelper;
    @Resource
    private SysMenuChecker sysMenuChecker;
    @Resource
    private SysMenuService sysMenuService;
    @Resource
    private SysMenuApiExtService sysMenuApiExtService;
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
        sysMenuChecker.checkAndAssign(form);
        // 判断menuCode是否存在
        List<SysMenu> sysMenus = sysMenuService.list(
                new QueryWrapper<SysMenu>().lambda()
                        .eq(SysMenu::getMenuCode, form.getMenuCode())
                        .eq(SysMenu::getDeleted, DeletedEnum.NO.value)
        );
        if (CollectionUtils.isNotEmpty(sysMenus)) {
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
        sysMenuDelete.setId(id);
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
        if (CollectionUtils.isEmpty(userMenuSet)) {
            throw new BizException(ErrorCodeEnum.AUTHORITY_MENU_EXCEPTION);
        }
        List<SysMenu> sysMenuList = sysMenuService.list(
                new QueryWrapper<SysMenu>().lambda()
                        .in(SysMenu::getId, userMenuSet)
                        .ne(SysMenu::getMenuType, MenuTypeEnum.BUTTON.getType())
                        .eq(SysMenu::getDeleted, DeletedEnum.NO.value)
        );

        return sysMenuHelper.convertToRouteVo(sysMenuList);
    }

    /**
     * 获取菜单树列表
     * <p>
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
            List<SysUserMenu> sysUserMenuList = sysUserMenuExtService.list(
                    new QueryWrapper<SysUserMenu>().lambda()
                            .eq(SysUserMenu::getUserId, query.getUserId())
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
     *
     * @param query
     * @return
     */
    public List<SysMenuTreeVo> listSysMenuTree(SysMenuQuery query) {
        List<SysMenu> sysMenuList = sysMenuService.list(
                new QueryWrapper<SysMenu>().lambda()
                        .eq(SysMenu::getDeleted, DeletedEnum.NO.value)
        );
        return sysMenuHelper.convertToTreeVo(sysMenuList);
    }

    /**
     * 设置菜单api
     *
     * @param form
     */
    @Transactional(rollbackFor = Exception.class)
    public void setSysApiForMenu(SysMenuApiAddForm form) {
        List<String> apiCodeList = form.getApiCodeList();
        // 删除原有的角色api信息
        sysMenuApiExtService.delete(
                new QueryWrapper<SysMenuApi>().lambda()
                        .eq(SysMenuApi::getMenuId, form.getMenuId())
                        .in(SysMenuApi::getApiCode, apiCodeList)
                        .eq(SysMenuApi::getDeleted, DeletedEnum.NO.value)
        );
        // 重新添加
        if (CollectionUtils.isEmpty(apiCodeList)) {
            log.warn("SysRoleManager[setSysApiForRole] delete SysRoleApi allData, menuId is {}", form.getMenuId());
            return;
        }

        List<SysMenuApi> sysMenuApiList = sysMenuHelper.getSysMenuApiEntityList(form);
        sysMenuApiExtService.saveBatch(sysMenuApiList);
    }

    /**
     * 取消菜单api
     *
     * @param form
     */
    @Transactional(rollbackFor = Exception.class)
    public void cancelSysApiForMenu(SysMenuApiAddForm form) {
        List<String> apiCodeList = form.getApiCodeList();
        if (CollectionUtils.isEmpty(apiCodeList)) {
            return;
        }
        List<SysMenuApi> sysMenuApiList = sysMenuApiExtService.list(
                new QueryWrapper<SysMenuApi>().lambda()
                        .eq(SysMenuApi::getMenuId, form.getMenuId())
                        .in(SysMenuApi::getApiCode, apiCodeList)
                        .eq(SysMenuApi::getDeleted, DeletedEnum.NO.value)
        );

        String userNameBySession = SecurityUtils.getUserNameBySession();
        List<SysMenuApi> cancelSysMenuApiList = sysMenuApiList.stream()
                .map(sysMenuApi -> {
                    SysMenuApi cancelSysMenuApi = new SysMenuApi();
                    cancelSysMenuApi.setId(sysMenuApi.getId());
                    cancelSysMenuApi.setDeleted(sysMenuApi.getId());
                    cancelSysMenuApi.setModifier(userNameBySession);
                    return cancelSysMenuApi;
                }).collect(Collectors.toList());
        sysMenuApiExtService.updateBatchById(cancelSysMenuApiList);
    }

    /**
     * 根据菜单编码分页获取菜单的api信息
     *
     * @param query
     * @return
     */
    public PageResult<SelectMenuApiVo.ApiInfoVo> pageSelectMenuApi(SelectMenuApiQuery query) {
        Long menuId = query.getMenuId();
        if (Objects.isNull(menuId) || Objects.equals(menuId, PermissionsConstants.ZERO)) {
            throw new BizException(ErrorCodeEnum.PARAM_ILLEGAL);
        }
        PageInfo<SysMenuApi> pageInfo = PageMethod.startPage(query.getPageNo(), query.getPageSize())
                .doSelectPageInfo(
                        () -> sysMenuApiExtService.list(
                                new QueryWrapper<SysMenuApi>().lambda()
                                        .orderByDesc(SysMenuApi::getCreatedTime)
                                        .eq(SysMenuApi::getMenuId, menuId)
                                        .eq(StringUtils.isNotBlank(query.getApiCode()), SysMenuApi::getApiCode, query.getApiCode())
                                        .eq(SysMenuApi::getDeleted, DeletedEnum.NO.value)
                        )
                );
        PageResult<SelectMenuApiVo.ApiInfoVo> pageResult = new PageResult<>();
        pageResult.setPageSize(pageInfo.getPageSize());
        pageResult.setPageNum(pageInfo.getPageNum());
        pageResult.setTotal(pageInfo.getTotal());
        List<SysMenuApi> sysMenuApiList = pageInfo.getList();
        if (CollectionUtils.isNotEmpty(sysMenuApiList)) {
            List<String> apiCodeList = sysMenuApiList.stream().map(SysMenuApi::getApiCode).collect(Collectors.toList());
            List<SysApi> sysApiList = sysApiService.list(
                    new QueryWrapper<SysApi>().lambda()
                            .select(SysApi::getApiName, SysApi::getApiCode, SysApi::getId)
                            .in(SysApi::getApiCode, apiCodeList)
                            .eq(SysApi::getDeleted, DeletedEnum.NO.value)
            );
            pageResult.setRows(
                    sysApiList.stream()
                            .map(item -> SelectMenuApiVo.ApiInfoVo.builder().id(item.getId()).apiCode(item.getApiCode()).apiName(item.getApiName()).build())
                            .collect(Collectors.toList())
            );
        }
        return pageResult;
    }

    /**
     * 查询按钮权限集合
     *
     * @param query
     * @return
     */
    public List<SysMenuButtonPermissionVo> listSysMenuButtonPermissionVo(SysMenuButtonPermissionQuery query) {
        Set<String> buttonCodes = query.getButtonCodes();
        if (CollectionUtils.isEmpty(buttonCodes)) {
            throw new BizException(ErrorCodeEnum.PARAM_MISS, "按钮编码");
        }
        List<SysMenuApi> sysMenuApiList = sysMenuApiExtService.list(
                new QueryWrapper<SysMenuApi>().lambda()
                        .select(SysMenuApi::getMenuCode, SysMenuApi::getApiCode)
                        .in(SysMenuApi::getMenuCode, buttonCodes)
                        .eq(SysMenuApi::getDeleted, DeletedEnum.NO.value)
        );
        Map<String, List<SysMenuApi>> menuCodeApiMap = sysMenuApiList.stream().collect(Collectors.groupingBy(SysMenuApi::getMenuCode));
        List<SysMenuButtonPermissionVo> list = Lists.newArrayList();
        menuCodeApiMap.forEach((k, v) ->
                list.add(SysMenuButtonPermissionVo.builder()
                        .buttonPermission(
                                SysMenuButtonPermissionVo.ButtonPermission.builder()
                                        .permission(CollectionUtils.convertList(v, SysMenuApi::getApiCode))
                                        .build()
                        )
                        .buttonCode(k)
                        .build()));
        return list;
    }
}
