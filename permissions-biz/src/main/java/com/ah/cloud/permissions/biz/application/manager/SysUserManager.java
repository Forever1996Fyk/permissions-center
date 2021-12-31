package com.ah.cloud.permissions.biz.application.manager;

import com.ah.cloud.permissions.biz.application.helper.SysUserHelper;
import com.ah.cloud.permissions.biz.domain.user.form.SysUserAddForm;
import com.ah.cloud.permissions.biz.infrastructure.application.service.SysUserService;
import com.ah.cloud.permissions.biz.infrastructure.repository.bean.SysUser;
import com.ah.cloud.permissions.biz.infrastructure.util.AppUtils;
import com.ah.cloud.permissions.enums.UserStatusEnum;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @program: permissions-center
 * @description: 系统用户管理器
 * @author: YuKai Fan
 * @create: 2021-12-27 16:59
 **/
@Slf4j
@Component
public class SysUserManager {
    @Autowired
    private SysUserHelper sysUserHelper;
    @Autowired
    private SysUserService sysUserService;

    public void addSysUser(SysUserAddForm form) {
        SysUser sysUser = sysUserHelper.convertToEntity(form);
        sysUserService.save(sysUser);
    }
}
