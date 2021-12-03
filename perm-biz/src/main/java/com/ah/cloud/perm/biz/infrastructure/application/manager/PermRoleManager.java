package com.ah.cloud.perm.biz.infrastructure.application.manager;

import com.ah.cloud.perm.biz.domain.role.request.PermRoleRequest;
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
     * @param request
     */
    public void addPermRole(PermRoleRequest request) {
        permRoleChecker.checkRoleType(request);
        PermRole permRole = permRoleHelper.convert2Entity(request);
        permRoleService.save(permRole);
    }
}
