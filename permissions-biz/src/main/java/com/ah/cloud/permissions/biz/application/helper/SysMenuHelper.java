package com.ah.cloud.permissions.biz.application.helper;

import cn.hutool.core.util.RandomUtil;
import com.ah.cloud.permissions.biz.domain.menu.form.SysMenuAddForm;
import com.ah.cloud.permissions.biz.domain.menu.form.SysMenuUpdateForm;
import com.ah.cloud.permissions.biz.domain.menu.tree.SysMenuTreeSelectVo;
import com.ah.cloud.permissions.biz.domain.menu.vo.RouterVo;
import com.ah.cloud.permissions.biz.domain.menu.vo.SysMenuVo;
import com.ah.cloud.permissions.biz.domain.user.form.SysUserAddForm;
import com.ah.cloud.permissions.biz.domain.user.vo.SysUserVo;
import com.ah.cloud.permissions.biz.infrastructure.constant.PermissionsConstants;
import com.ah.cloud.permissions.biz.infrastructure.repository.bean.SysMenu;
import com.ah.cloud.permissions.biz.infrastructure.repository.bean.SysUser;
import com.ah.cloud.permissions.biz.infrastructure.repository.bean.SysUserRole;
import com.ah.cloud.permissions.biz.infrastructure.util.AppUtils;
import com.ah.cloud.permissions.biz.infrastructure.util.SecurityUtils;
import com.ah.cloud.permissions.domain.common.PageResult;
import com.ah.cloud.permissions.enums.MenuOpenType;
import com.ah.cloud.permissions.enums.MenuTypeEnum;
import com.ah.cloud.permissions.enums.UserStatusEnum;
import com.ah.cloud.permissions.enums.YesOrNoEnum;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
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
        return convertToMenuTree(sysMenuList, PermissionsConstants.ZERO);
    }

    private List<RouterVo> convertToMenuTree(List<SysMenu> sysMenuList, Long pid) {
        List<RouterVo> routerVoList = Lists.newArrayList();
        for (SysMenu sysMenu : sysMenuList) {
            if (Objects.equals(sysMenu.getParentId(), pid)) {
                RouterVo routerVo = buildRouterVoByMenu(sysMenu);
                List<RouterVo> childRouterVos = convertToMenuTree(sysMenuList, sysMenu.getId());
                routerVo.setChildren(childRouterVos);
                routerVoList.add(routerVo);
            }
        }
        return routerVoList;
    }

    private RouterVo buildRouterVoByMenu(SysMenu sysMenu) {
        return RouterVo.builder()
                .name(getRouteName(sysMenu))
                .hidden(isHidden(sysMenu))
                .build();
    }

    /**
     * 获取路由名称
     * @param sysMenu
     * @return
     */
    private String getRouteName(SysMenu sysMenu) {
        String routerName = StringUtils.capitalize(sysMenu.getMenuPath());
        // 非外链并且是一级目录（类型为菜单）
        if (isMenuFrame(sysMenu)) {
            routerName = StringUtils.EMPTY;
        }
        return routerName;
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
        return Objects.equals(sysMenu.getOpenType(), MenuOpenType.FRAME_TARGET.getType());
    }

    /**
     * 判断是否是顶级菜单
     *
     * @param menuId
     * @return
     */
    public boolean isParentMenu(Long menuId) {
        return Objects.equals(menuId, PermissionsConstants.ZERO);
    }

    /**
     * 数据转换
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
                sysMenuTreeSelectVo.setChildNode(childSysMenuTreeSelectVoList);
                sysMenuTreeSelectVoList.add(sysMenuTreeSelectVo);
            }
        }
        return sysMenuTreeSelectVoList;
    }

    private SysMenuTreeSelectVo buildSysMenuTreeSelectVoByMenu(SysMenu sysMenu,Set<Long> roleMenuIdSet) {
        return SysMenuTreeSelectVo.builder()
                .id(String.valueOf(sysMenu.getId()))
                .label(sysMenu.getMenuName())
                .disabled(isHidden(sysMenu))
                .selected(roleMenuIdSet.contains(sysMenu.getId()))
                .build();
    }

//    /**
//     * 获取路由路径
//     * @param sysMenu
//     * @return
//     */
//    private String getRouterPath(SysMenu sysMenu) {
//        String url = sysMenu.getMenuPath();
//        if (isDirFrame(sysMenu)) {
//            // 非外链并且是一级目录（类型为目录）
//            url = "/" + sysMenu.getUrl();
//        } else if (isMenuFrame(sysMenu)) {
//            // 非外链并且是一级目录（类型为菜单）
//            url = "/";
//        }
//        return url;
//    }
//
//    /**
//     * 获取组件信息
//     * @param sysMenu
//     * @return
//     */
//    private static String getComponent(SystemMenuVo sysMenu) {
//        String component = LAYOUT;
//        if (StringUtils.isNotBlank(sysMenu.getComponent()) && !isMenuFrame(sysMenu)) {
//            component = sysMenu.getComponent();
//        }
//        return component;
//    }
//
//    /**
//     *  判断是否为目录内部跳转
//     * @param sysMenu
//     * @return
//     */
//    private static boolean isDirFrame(SystemMenuVo sysMenu) {
//        return sysMenu.getParentId().longValue() == 0L && TYPE_DIR.equals(sysMenu.getMenuType())
//                && NO_FRAME_TARGET.equals(sysMenu.getTarget());
//    }


    @Mapper
    public interface SysMenuConvert {
        SysMenuHelper.SysMenuConvert INSTANCE = Mappers.getMapper(SysMenuHelper.SysMenuConvert.class);

        SysMenu convert(SysMenuAddForm form);

        SysMenu convert(SysMenuUpdateForm form);

        SysMenuVo convert(SysMenu sysMenu);
    }
}
