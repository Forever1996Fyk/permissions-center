package com.ah.cloud.permissions.api.controller;

import com.ah.cloud.permissions.biz.domain.role.form.PermissionsRoleAddForm;
import com.ah.cloud.permissions.biz.domain.role.form.PermissionsRoleUpdateForm;
import com.ah.cloud.permissions.biz.domain.role.query.PermissionsRoleQuery;
import com.ah.cloud.permissions.biz.infrastructure.application.manager.PermissionsRoleManager;
import com.ah.cloud.permissions.biz.infrastructure.repository.bean.PermissionsRole;
import com.ah.cloud.permissions.domain.common.PageResult;
import com.ah.cloud.permissions.domain.common.ResponseResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @program: permissions-center
 * @description: 权限角色controller
 * @author: YuKai Fan
 * @create: 2021-12-03 15:54
 **/
@RestController
@RequestMapping("/permissions/role")
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

    /**
     * 编辑权限角色
     *
     * @param form
     * @return
     */
    @PutMapping("/update")
    public ResponseResult updatePermissionsRoleById(@Valid @RequestBody PermissionsRoleUpdateForm form) {
        permissionsRoleManager.updatePermissionsRole(form);
        return ResponseResult.ok();
    }

    /**
     * 根据id删除角色
     *
     * @param id
     * @return
     */
    @DeleteMapping("/delete/{id}")
    public ResponseResult deletePermissionsRoleById(@PathVariable(value = "id") Long id) {
        permissionsRoleManager.deletePermissionsRole(id);
        return ResponseResult.ok();
    }

    /**
     * 分页查询角色列表
     *
     * @param query
     * @return
     */
    @GetMapping("/page")
    public ResponseResult pagePermissionsRoles(PermissionsRoleQuery query) {
        return ResponseResult.ok(permissionsRoleManager.pagePermissionsRoles(query));
    }
}
