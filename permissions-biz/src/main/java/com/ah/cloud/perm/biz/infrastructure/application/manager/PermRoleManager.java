package com.ah.cloud.perm.biz.infrastructure.application.manager;

import com.ah.cloud.perm.biz.domain.role.form.PermRoleAddForm;
import com.ah.cloud.perm.biz.infrastructure.application.checker.PermRoleChecker;
import com.ah.cloud.perm.biz.infrastructure.application.helper.PermRoleHelper;
import com.ah.cloud.perm.biz.infrastructure.application.service.PermRoleService;
import com.ah.cloud.perm.biz.infrastructure.repository.bean.PermRole;
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
public class PermRoleManager {

    @Autowired
    private PermRoleHelper permRoleHelper;

    @Autowired
    private PermRoleService permRoleService;

    @Autowired
    private PermRoleChecker permRoleChecker;

    /**
     * 添加权限角色
     * @param form
     */
    public void addPermRole(PermRoleAddForm form) {
        permRoleChecker.checkRoleType(form);
        PermRole permRole = permRoleHelper.convert2Entity(form);
        permRole.setCreator("system");
        permRole.setModifier("system");
        permRoleService.save(permRole);
    }
}
