package com.ah.cloud.permissions.api.controller;

import com.ah.cloud.permissions.biz.domain.role.form.PermissionsRoleAddForm;
import com.ah.cloud.permissions.biz.infrastructure.application.manager.PermissionsRoleManager;
import com.ah.cloud.permissions.biz.infrastructure.repository.bean.PermissionsRole;
import com.ah.cloud.permissions.domain.common.PageResult;
import com.ah.cloud.permissions.domain.common.ResponseResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @program: permissions-center
 * @description: 权限角色controller
 * @author: YuKai Fan
 * @create: 2021-12-03 15:54
 **/
@RestController
@RequestMapping("/perm/role")
public class PermissionsRoleController {

    @Autowired
    private PermissionsRoleManager permissionsRoleManager;

    /**
     * 添加权限角色
     *
     * @param form
     * @return
     */
    @PostMapping("/add")
    public ResponseResult addPermissionsRole(@Valid @RequestBody PermissionsRoleAddForm form) {
        permissionsRoleManager.addPermissionsRole(form);
        return ResponseResult.ok();
    }

    public ResponseResult pagePermissionsRoles() {
        return ResponseResult.ok(new PageResult<PermissionsRole>());
    }
}
