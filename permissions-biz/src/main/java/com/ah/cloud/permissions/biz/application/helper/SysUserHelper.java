package com.ah.cloud.permissions.biz.application.helper;

import com.ah.cloud.permissions.biz.domain.user.form.SysUserAddForm;
import com.ah.cloud.permissions.biz.infrastructure.repository.bean.SysUser;
import com.ah.cloud.permissions.biz.infrastructure.util.AppUtils;
import com.ah.cloud.permissions.enums.UserStatusEnum;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2021-12-27 16:59
 **/
@Component
public class SysUserHelper {

    /**
     * 数据转换
     * @param form
     * @return
     */
    public SysUser convertToEntity(SysUserAddForm form) {
        SysUser sysUser = SysUserConvert.INSTANCE.convert(form);
        sysUser.setId(AppUtils.randomLongId());
        sysUser.setStatus(UserStatusEnum.NORMAL.getStatus());
        return sysUser;
    }

    @Mapper
    public interface SysUserConvert {
        SysUserHelper.SysUserConvert INSTANCE = Mappers.getMapper(SysUserHelper.SysUserConvert.class);

        SysUser convert(SysUserAddForm form);
    }
}
