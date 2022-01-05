package com.ah.cloud.permissions.biz.application.checker;

import com.ah.cloud.permissions.biz.application.service.SysUserService;
import com.ah.cloud.permissions.biz.infrastructure.exception.BizException;
import com.ah.cloud.permissions.biz.infrastructure.repository.bean.SysUser;
import com.ah.cloud.permissions.enums.common.DeletedEnum;
import com.ah.cloud.permissions.enums.common.ErrorCodeEnum;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @program: permissions-center
 * @description: 系统用户检查类
 * @author: YuKai Fan
 * @create: 2022-01-04 15:40
 **/
@Slf4j
@Component
public class SysUserChecker {
    @Autowired
    private SysUserService sysUserService;

    /**
     * 根据手机号判断当前用户是否已存在
     * @param phone
     */
    public void checkUserIsExist(String phone) {
        List<SysUser> sysUsers = sysUserService.list(new QueryWrapper<SysUser>().lambda()
                .eq(SysUser::getPhone, phone)
                .eq(SysUser::getDeleted, DeletedEnum.NO.value)
        );

        if (!CollectionUtils.isEmpty(sysUsers)) {
            throw new BizException(ErrorCodeEnum.USER_PHONE_IS_EXISTED, phone);
        }
    }
}
