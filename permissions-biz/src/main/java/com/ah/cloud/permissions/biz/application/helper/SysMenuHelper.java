package com.ah.cloud.permissions.biz.application.helper;

import com.ah.cloud.permissions.biz.domain.menu.form.SysMenuAddForm;
import com.ah.cloud.permissions.biz.domain.menu.form.SysMenuApiAddForm;
import com.ah.cloud.permissions.biz.domain.menu.form.SysMenuUpdateForm;
import com.ah.cloud.permissions.biz.domain.menu.tree.SysMenuTreeSelectVo;
import com.ah.cloud.permissions.biz.domain.menu.tree.SysMenuTreeVo;
import com.ah.cloud.permissions.biz.domain.menu.vo.RouterVo;
import com.ah.cloud.permissions.biz.domain.menu.vo.SysMenuVo;
import com.ah.cloud.permissions.biz.infrastructure.constant.PermissionsConstants;
import com.ah.cloud.permissions.biz.infrastructure.repository.bean.SysMenu;
import com.ah.cloud.permissions.biz.infrastructure.repository.bean.SysMenuApi;
import com.ah.cloud.permissions.biz.infrastructure.repository.bean.SysRoleApi;
import com.ah.cloud.permissions.biz.infrastructure.util.AppUtils;
import com.ah.cloud.permissions.biz.infrastructure.util.SecurityUtils;
import com.ah.cloud.permissions.enums.MenuOpenTypeEnum;
import com.ah.cloud.permissions.enums.YesOrNoEnum;
import com.google.common.collect.Lists;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2021-12-27 16:59
 **/
@Component
public class SysMenuHelper {

    /**
     * 数据转换
     * @param form
     * @return
     */
    public SysMenu convertToEntity(SysMenuAddForm form) {
        SysMenu sysMenu = SysMenuConvert.INSTANCE.convert(form);
        sysMenu.setParentId(sysMenu.getParentId() != null ? sysMenu.getParentId() : PermissionsConstants.ZERO);
        sysMenu.setCreator(SecurityUtils.getUserNameBySession());
        sysMenu.setModifier(SecurityUtils.getUserNameBySession());
        return sysMenu;
    }

    /**
     * 数据转换
     * @param form
     * @return
     */
    public SysMenu convertToEntity(SysMenuUpdateForm form) {
        SysMenu sysMenu = SysMenuConvert.INSTANCE.convert(form);
        sysMenu.setModifier(SecurityUtils.getUserNameBySession());
        return sysMenu;
    }

    /**
     * 数据转换
     * @param sysMenu
     * @return
     */
    public SysMenuVo convertEntityToVo(SysMenu sysMenu) {
        return SysMenuConvert.INSTANCE.convert(sysMenu);
    }

    /**
     * 数据转换菜单路由
     * @param sysMenuList
     * @return
     */
    public List<RouterVo> convertToRouteVo(List<SysMenu> sysMenuList) {
        return convertToRouteTree(sysMenuList, PermissionsConstants.ZERO);
    }

    private List<RouterVo> convertToRouteTree(List<SysMenu> sysMenuList, Long pid) {
        List<RouterVo> routerVoList = Lists.newArrayList();
        for (SysMenu sysMenu : sysMenuList) {
            if (Objects.equals(sysMenu.getParentId(), pid)) {
                RouterVo routerVo = buildRouterVoByMenu(sysMenu);
                List<RouterVo> childRouterVos = convertToRouteTree(sysMenuList, sysMenu.getId());
                routerVo.setChildren(childRouterVos);
                routerVoList.add(routerVo);
            }
        }
        return routerVoList;
    }

    private RouterVo buildRouterVoByMenu(SysMenu sysMenu) {
        return RouterVo.builder()
                .name(sysMenu.getMenuCode())
                .hidden(isHidden(sysMenu))
                .component(sysMenu.getComponent())
                .path(sysMenu.getMenuPath())
                .meta(buildMetaVoByMenu(sysMenu))
                .build();
    }

    private RouterVo.MetaVo buildMetaVoByMenu(SysMenu sysMenu) {
        return RouterVo.MetaVo.builder()
                .icon(sysMenu.getMenuIcon())
                .hidden(AppUtils.convertIntToBool(sysMenu.getHidden()))
                .title(sysMenu.getMenuName())
                .levelHidden(false)
                .activeMenu(sysMenu.getActiveMenu())
                .dynamicNewTab(AppUtils.convertIntToBool(sysMenu.getDynamicNewTab()))
                .build();
    }

    /**
     * 菜单是否隐藏
     * @param sysMenu
     * @return
     */
    private boolean isHidden(SysMenu sysMenu) {
        return Objects.equals(sysMenu.getHidden(), YesOrNoEnum.YES.getType());
    }

    /**
     *  判断是否为菜单内部跳转
     * @param sysMenu
     * @return
     */
    private boolean isMenuFrame(SysMenu sysMenu) {
        return Objects.equals(sysMenu.getOpenType(), MenuOpenTypeEnum.FRAME_TARGET.getType());
    }

    /**
     * 数据转换
     *
     * 菜单树选择列表转换
     *
     * @param sysMenuList
     * @param roleMenuIdSet
     * @return
     */
    public List<SysMenuTreeSelectVo> convertToTreeSelectVo(List<SysMenu> sysMenuList, Set<Long> roleMenuIdSet) {
        return convertToTreeSelectVo(sysMenuList, PermissionsConstants.ZERO, roleMenuIdSet);
    }

    private List<SysMenuTreeSelectVo> convertToTreeSelectVo(List<SysMenu> sysMenuList, Long pid, Set<Long> roleMenuIdSet) {
        List<SysMenuTreeSelectVo> sysMenuTreeSelectVoList = Lists.newArrayList();
        for (SysMenu sysMenu : sysMenuList) {
            if (Objects.equals(sysMenu.getParentId(), pid)) {
                SysMenuTreeSelectVo sysMenuTreeSelectVo = buildSysMenuTreeSelectVoByMenu(sysMenu, roleMenuIdSet);
                List<SysMenuTreeSelectVo> childSysMenuTreeSelectVoList = convertToTreeSelectVo(sysMenuList, sysMenu.getId(), roleMenuIdSet);
                sysMenuTreeSelectVo.setChildren(childSysMenuTreeSelectVoList);
                sysMenuTreeSelectVoList.add(sysMenuTreeSelectVo);
            }
        }
        return sysMenuTreeSelectVoList;
    }

    private SysMenuTreeSelectVo buildSysMenuTreeSelectVoByMenu(SysMenu sysMenu,Set<Long> roleMenuIdSet) {
        return SysMenuTreeSelectVo.builder()
                .id(String.valueOf(sysMenu.getId()))
                .label(sysMenu.getMenuName())
                .disabled(false)
                .selected(roleMenuIdSet.contains(sysMenu.getId()))
                .build();
    }

    /**
     * 数据转换
     *
     * 菜单树列表 转换
     * @param sysMenuList
     * @return
     */
    public List<SysMenuTreeVo> convertToTreeVo(List<SysMenu> sysMenuList) {
        return convertToMenuTreeVo(sysMenuList, PermissionsConstants.ZERO);
    }

    private List<SysMenuTreeVo> convertToMenuTreeVo(List<SysMenu> sysMenuList, Long pid) {
        List<SysMenuTreeVo> routerVoList = Lists.newArrayList();
        for (SysMenu sysMenu : sysMenuList) {
            if (Objects.equals(sysMenu.getParentId(), pid)) {
                SysMenuTreeVo sysMenuTreeVo = buildMenuTreeVoByMenu(sysMenu);
                List<SysMenuTreeVo> childRouterVos = convertToMenuTreeVo(sysMenuList, sysMenu.getId());
                sysMenuTreeVo.setChildren(childRouterVos);
                routerVoList.add(sysMenuTreeVo);
            }
        }
        return routerVoList;
    }

    private SysMenuTreeVo buildMenuTreeVoByMenu(SysMenu sysMenu) {
        return SysMenuConvert.INSTANCE.convertToTree(sysMenu);
    }

    public List<SysMenuApi> getSysMenuApiEntityList(SysMenuApiAddForm form) {
        List<SysMenuApi> sysMenuApiList = Lists.newArrayList();
        String userNameBySession = SecurityUtils.getUserNameBySession();
        for(String apiCode : form.getApiCodeList()) {
            SysMenuApi sysMenuApi = new SysMenuApi();
            sysMenuApi.setMenuId(form.getMenuId());
            sysMenuApi.setMenuCode(form.getMenuCode());
            sysMenuApi.setApiCode(apiCode);
            sysMenuApi.setCreator(userNameBySession);
            sysMenuApi.setModifier(userNameBySession);
            sysMenuApiList.add(sysMenuApi);
        }
        return sysMenuApiList;
    }

    @Mapper
    public interface SysMenuConvert {
        SysMenuHelper.SysMenuConvert INSTANCE = Mappers.getMapper(SysMenuHelper.SysMenuConvert.class);

        /**
         * 数据转换
         * @param form
         * @return
         */
        SysMenu convert(SysMenuAddForm form);

        /**
         * 数据换砖
         * @param form
         * @return
         */
        SysMenu convert(SysMenuUpdateForm form);

        /**
         * 数据转换
         * @param sysMenu
         * @return
         */
        SysMenuVo convert(SysMenu sysMenu);

        /**
         * 数据转换
         * @param sysMenu
         * @return
         */
        @Mappings({
                @Mapping(target = "menuTypeName", expression = "java(com.ah.cloud.permissions.enums.MenuTypeEnum.getByType(sysMenu.getMenuType()).getDesc())"),
                @Mapping(target = "openTypeName", expression = "java(com.ah.cloud.permissions.enums.MenuOpenTypeEnum.getByType(sysMenu.getOpenType()).getDesc())")
        })
        SysMenuTreeVo convertToTree(SysMenu sysMenu);
    }
}
