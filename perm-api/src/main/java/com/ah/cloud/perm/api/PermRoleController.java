package com.ah.cloud.perm.api;

import com.ah.cloud.perm.biz.domain.role.form.PermRoleAddForm;
import com.ah.cloud.perm.biz.infrastructure.application.manager.PermRoleManager;
import com.ah.cloud.perm.domain.common.ResponseResult;
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
public class PermRoleController {

    @Autowired
    private PermRoleManager permRoleManager;

    /**
     * 添加权限角色
     *
     * @param form
     * @return
     */
    @PostMapping("/add")
    public ResponseResult addPermRole(@Valid @RequestBody PermRoleAddForm form) {
        permRoleManager.addPermRole(form);
        return ResponseResult.ok();
    }
}
