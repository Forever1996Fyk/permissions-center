package com.ah.cloud.permissions.biz.application.helper;

import cn.hutool.core.util.RandomUtil;
import com.ah.cloud.permissions.biz.domain.menu.form.SysMenuAddForm;
import com.ah.cloud.permissions.biz.domain.menu.form.SysMenuUpdateForm;
import com.ah.cloud.permissions.biz.domain.menu.vo.SysMenuVo;
import com.ah.cloud.permissions.biz.domain.user.form.SysUserAddForm;
import com.ah.cloud.permissions.biz.domain.user.vo.SysUserVo;
import com.ah.cloud.permissions.biz.infrastructure.constant.PermissionsConstants;
import com.ah.cloud.permissions.biz.infrastructure.repository.bean.SysMenu;
import com.ah.cloud.permissions.biz.infrastructure.repository.bean.SysUser;
import com.ah.cloud.permissions.biz.infrastructure.repository.bean.SysUserRole;
import com.ah.cloud.permissions.biz.infrastructure.util.AppUtils;
import com.ah.cloud.permissions.domain.common.PageResult;
import com.ah.cloud.permissions.enums.UserStatusEnum;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

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
        sysMenu.setCreator(PermissionsConstants.OPERATOR_SYSTEM);
        sysMenu.setModifier(PermissionsConstants.OPERATOR_SYSTEM);
        return sysMenu;
    }

    /**
     * 数据转换
     * @param form
     * @return
     */
    public SysMenu convertToEntity(SysMenuUpdateForm form) {
        SysMenu sysMenu = SysMenuConvert.INSTANCE.convert(form);
        sysMenu.setModifier(PermissionsConstants.OPERATOR_SYSTEM);
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

    @Mapper
    public interface SysMenuConvert {
        SysMenuHelper.SysMenuConvert INSTANCE = Mappers.getMapper(SysMenuHelper.SysMenuConvert.class);

        SysMenu convert(SysMenuAddForm form);

        SysMenu convert(SysMenuUpdateForm form);

        SysMenuVo convert(SysMenu sysMenu);
    }
}
