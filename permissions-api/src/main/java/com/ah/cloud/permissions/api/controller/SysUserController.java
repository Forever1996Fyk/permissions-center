package com.ah.cloud.permissions.api.controller;

import com.ah.cloud.permissions.biz.domain.user.form.SysUserAddForm;
import com.ah.cloud.permissions.biz.infrastructure.application.service.SysUserService;
import com.ah.cloud.permissions.biz.infrastructure.constant.PermissionsConstants;
import com.ah.cloud.permissions.biz.infrastructure.repository.bean.SysUser;
import com.ah.cloud.permissions.domain.common.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2021-12-27 14:19
 **/
@RestController
@RequestMapping("/user")
public class SysUserController {
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/add")
    public ResponseResult add(@Valid @RequestBody SysUserAddForm form) {
        SysUser sysUser = new SysUser();
        sysUser.setId(1L);
        sysUser.setNickName("超级管理员");
        sysUser.setAccount(form.getAccount());
        sysUser.setPhone(form.getPhone());
        sysUser.setEmail(form.getEmail());
        sysUser.setSex(form.getSex());
        sysUser.setPassword(passwordEncoder.encode("123456"));
        sysUser.setCreator(PermissionsConstants.OPERATOR_SYSTEM);
        sysUser.setModifier(PermissionsConstants.OPERATOR_SYSTEM);
        sysUserService.save(sysUser);

        return ResponseResult.ok();
    }

    @GetMapping("/list")
    public ResponseResult list() {
        List<SysUser> list = sysUserService.list();
        return ResponseResult.ok(list);
    }
}
