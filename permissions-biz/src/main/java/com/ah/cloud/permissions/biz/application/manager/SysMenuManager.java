package com.ah.cloud.permissions.biz.application.manager;

import com.ah.cloud.permissions.biz.application.helper.SysMenuHelper;
import com.ah.cloud.permissions.biz.application.service.SysMenuService;
import com.ah.cloud.permissions.biz.domain.menu.form.SysMenuAddForm;
import com.ah.cloud.permissions.biz.domain.menu.form.SysMenuUpdateForm;
import com.ah.cloud.permissions.biz.domain.menu.vo.RouterVo;
import com.ah.cloud.permissions.biz.domain.menu.vo.SysMenuVo;
import com.ah.cloud.permissions.biz.infrastructure.constant.PermissionsConstants;
import com.ah.cloud.permissions.biz.infrastructure.exception.BizException;
import com.ah.cloud.permissions.biz.infrastructure.repository.bean.SysMenu;
import com.ah.cloud.permissions.enums.common.DeletedEnum;
import com.ah.cloud.permissions.enums.common.ErrorCodeEnum;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

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
    private SysMenuService sysMenuService;

    /**
     * 添加菜单
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
     * @param id
     */
    public void deleteSysMenuById(Long id) {
        SysMenu sysMenu = sysMenuService.getById(id);
        if (Objects.isNull(sysMenu)) {
            return;
        }
        SysMenu sysMenuDelete = new SysMenu();
        sysMenuDelete.setDeleted(id);
        sysMenuDelete.setModifier(PermissionsConstants.OPERATOR_SYSTEM);
        sysMenuDelete.setVersion(sysMenu.getVersion());
        final boolean deleteResult = sysMenuService.updateById(sysMenuDelete);
        if (!deleteResult) {
            throw new BizException(ErrorCodeEnum.VERSION_ERROR);
        }
    }

    /**
     * 根据id查询菜单信息
     * @param id
     * @return
     */
    public SysMenuVo findSysMenuById(Long id) {
        SysMenu sysMenu = sysMenuService.getById(id);
        return sysMenuHelper.convertEntityToVo(sysMenu);
    }

    /**
     * 获取所有菜单路由
     * @return
     */
    public List<RouterVo> listRouterVo() {
        Long currentUserId = PermissionsConstants.SUPER_ADMIN;
        if (Objects.equals(currentUserId, PermissionsConstants.SUPER_ADMIN)) {

        } else {

        }
        return null;
    }
}
