package com.ah.cloud.perm.biz.infrastructure.application.checker;

import com.ah.cloud.perm.biz.domain.role.form.PermRoleAddForm;
import com.ah.cloud.perm.exception.CustomException;
import com.ah.cloud.perm.enums.common.ErrorCodeEnum;
import org.springframework.stereotype.Component;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2021-12-03 16:05
 **/
@Component
public class PermRoleChecker {

    /**
     * 检查type是否合法
     * @param form
     */
    public void checkRoleType(PermRoleAddForm form) {
        if (form.getRoleType() != 1) {
            // 自定义异常
            throw new CustomException(ErrorCodeEnum.BUSINESS_FAIL, "Parameters of illegal.", "参数非法。");
        }
    }
}
