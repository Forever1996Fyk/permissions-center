package com.ah.cloud.permissions.biz.infrastructure.application.manager;

import com.ah.cloud.permissions.biz.domain.role.form.PermissionsRoleAddForm;
import com.ah.cloud.permissions.biz.infrastructure.application.checker.PermissionsRoleChecker;
import com.ah.cloud.permissions.biz.infrastructure.application.helper.PermissionsRoleHelper;
import com.ah.cloud.permissions.biz.infrastructure.application.service.PermissionsRoleService;
import com.ah.cloud.permissions.biz.infrastructure.constant.PermissionsConstants;
import com.ah.cloud.permissions.biz.infrastructure.repository.bean.PermissionsRole;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2021-12-03 15:33
 **/
@Component
@Slf4j
public class PermissionsRoleManager {

    @Autowired
    private PermissionsRoleHelper permissionsRoleHelper;

    @Autowired
    private PermissionsRoleService permissionsRoleService;

    @Autowired
    private PermissionsRoleChecker permissionsRoleChecker;

    /**
     * 添加权限角色
     * @param form
     */
    public void addPermissionsRole(PermissionsRoleAddForm form) {
        permissionsRoleChecker.checkRoleType(form);
        PermissionsRole permissionsRole = permissionsRoleHelper.convert2Entity(form);
        permissionsRole.setCreator(PermissionsConstants.OPERATOR_SYSTEM);
        permissionsRole.setModifier(PermissionsConstants.OPERATOR_SYSTEM);
        permissionsRoleService.save(permissionsRole);
    }
}
