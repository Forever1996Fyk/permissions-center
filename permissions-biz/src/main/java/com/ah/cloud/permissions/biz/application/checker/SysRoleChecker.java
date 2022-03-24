package com.ah.cloud.permissions.biz.application.checker;

import com.ah.cloud.permissions.biz.domain.role.form.SysRoleAddForm;
import com.ah.cloud.permissions.biz.domain.role.form.SysRolePermissionForm;
import com.ah.cloud.permissions.biz.domain.role.form.SysRoleUpdateForm;
import com.ah.cloud.permissions.biz.infrastructure.constant.PermissionsConstants;
import com.ah.cloud.permissions.biz.infrastructure.exception.BizException;
import com.ah.cloud.permissions.enums.common.ErrorCodeEnum;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Set;

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

    /**
     * 设置菜单和接口编码
     * @param form
     */
    public void checkSysMenuAndApiCode(SysRolePermissionForm form) {
        if (CollectionUtils.isEmpty(form.getApiCodeList())) {
            throw new BizException(ErrorCodeEnum.PARAM_MISS, "接口api编码集合apiCodeList");
        }
    }

    /**
     * 组装api code List
     * @param apiCodeList
     * @param apiCodeListStr
     * @return
     */
    public String assemblyApiCode(List<String> apiCodeList, String apiCodeListStr) {
        String[] apiCodeArr = StringUtils.split(apiCodeListStr, PermissionsConstants.COMMA_SEPARATOR);
        List<String> existApiCode = Lists.newArrayList(apiCodeArr);
        Set<String> allApiCodeSet = Sets.newHashSet();
        allApiCodeSet.addAll(apiCodeList);
        allApiCodeSet.addAll(existApiCode);
        return StringUtils.join(allApiCodeSet, PermissionsConstants.COMMA_SEPARATOR);
    }
}
