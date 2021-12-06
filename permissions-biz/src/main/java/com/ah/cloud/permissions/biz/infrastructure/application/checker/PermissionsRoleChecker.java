package com.ah.cloud.permissions.biz.infrastructure.application.checker;

import com.ah.cloud.permissions.biz.domain.role.form.PermissionsRoleAddForm;
import com.ah.cloud.permissions.biz.domain.role.form.PermissionsRoleUpdateForm;
import com.ah.cloud.permissions.biz.infrastructure.exception.CustomException;
import com.ah.cloud.permissions.enums.common.ErrorCodeEnum;
import org.springframework.stereotype.Component;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2021-12-03 16:05
 **/
@Component
public class PermissionsRoleChecker {

    /**
     * 检查type是否合法
     * @param form
     */
    public void checkRoleType(PermissionsRoleAddForm form) {
        if (form.getRoleType() != 1) {
            // 自定义异常
            throw new CustomException(ErrorCodeEnum.BUSINESS_FAIL, "Parameters of illegal.", "参数非法。");
        }
    }

    public void checkRoleType(PermissionsRoleUpdateForm form) {
        if (form.getRoleType() != 1) {
            // 自定义异常
            throw new CustomException(ErrorCodeEnum.BUSINESS_FAIL, "Parameters of illegal.", "参数非法。");
        }
    }
}
