package com.ah.cloud.permissions.biz.application.checker;

import com.ah.cloud.permissions.biz.application.service.SysUserService;
import com.ah.cloud.permissions.biz.domain.user.form.SysUserAddForm;
import com.ah.cloud.permissions.biz.infrastructure.exception.BizException;
import com.ah.cloud.permissions.biz.infrastructure.repository.bean.SysUser;
import com.alibaba.excel.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
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
    @Resource
    private SysUserService sysUserService;

    /**
     * 校验参数
     * @param form
     */
    public void checkSysUserAddForm(SysUserAddForm form) {
    }

    public void checkBatchSysUserList(List<SysUser> sysUserList) {

    }

    public void checkSysUser(SysUser sysUser) {
        if (StringUtils.isBlank(sysUser.getAccount())) {
            throw new BizException();
        }
    }
}
