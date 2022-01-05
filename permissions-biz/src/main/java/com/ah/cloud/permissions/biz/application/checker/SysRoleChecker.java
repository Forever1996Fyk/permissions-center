package com.ah.cloud.permissions.biz.application.checker;

import com.ah.cloud.permissions.biz.domain.role.form.SysRoleAddForm;
import com.ah.cloud.permissions.biz.domain.role.form.SysRoleUpdateForm;
import com.ah.cloud.permissions.biz.infrastructure.exception.BizException;
import com.ah.cloud.permissions.enums.common.ErrorCodeEnum;
import org.springframework.stereotype.Component;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2021-12-03 16:05
 **/
@Component
public class SysRoleChecker {

    /**
     * 检查type是否合法
     * @param form
     */
    public void checkRoleType(SysRoleAddForm form) {
        if (form.getRoleType() != 1) {
            // 自定义异常
            throw new BizException(ErrorCodeEnum.BUSINESS_FAIL);
        }
    }

    public void checkRoleType(SysRoleUpdateForm form) {
        if (form.getRoleType() != 1) {
            // 自定义异常
            throw new BizException(ErrorCodeEnum.BUSINESS_FAIL);
        }
    }
}
