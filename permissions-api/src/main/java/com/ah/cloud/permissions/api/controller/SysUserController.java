package com.ah.cloud.permissions.api.controller;

import com.ah.cloud.permissions.biz.application.manager.SysUserManager;
import com.ah.cloud.permissions.biz.domain.user.form.SysUserAddForm;
import com.ah.cloud.permissions.biz.domain.user.form.SysUserApiAddForm;
import com.ah.cloud.permissions.biz.domain.user.form.SysUserMenuAddForm;
import com.ah.cloud.permissions.biz.domain.user.form.SysUserRoleAddForm;
import com.ah.cloud.permissions.biz.domain.user.query.SysUserQuery;
import com.ah.cloud.permissions.biz.domain.user.vo.SysUserVo;
import com.ah.cloud.permissions.domain.common.PageResult;
import com.ah.cloud.permissions.domain.common.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2021-12-27 14:19
 **/
@RestController
@RequestMapping("/user")
public class SysUserController {
    @Resource
    private SysUserManager sysUserManager;

    /**
     * 添加新用户
     * @param form
     * @return
     */
    @PostMapping("/add")
    public ResponseResult<Void> add(@Valid @RequestBody SysUserAddForm form) {
        sysUserManager.addSysUser(form);
        return ResponseResult.ok();
    }

    /**
     * 根据id删除用户
     *
     * @param id
     * @return
     */
    @PostMapping("/delete/{id}")
    public ResponseResult<Void> delete(@PathVariable(value = "id") Long id) {
        sysUserManager.deleteSysUserById(id);
        return ResponseResult.ok();
    }

    /**
     * 根据id查询用户信息
     *
     * @param id
     * @return
     */
    @GetMapping("/findById/{id}")
    public ResponseResult<SysUserVo> findById(@PathVariable(value = "id") Long id) {
        return ResponseResult.ok(sysUserManager.findSysUserById(id));
    }

    /**
     * 分页查询角色列表
     *
     * @param query
     * @return
     */
    @GetMapping("/page")
    public ResponseResult<PageResult<SysUserVo>> page(SysUserQuery query) {
        return ResponseResult.ok(sysUserManager.pageSysUsers(query));
    }

    /**
     * 设置用户角色
     * @param form
     * @return
     */
    @PostMapping("/setSysUserRole")
    public ResponseResult<Void> setSysUserRole(@Valid @RequestBody SysUserRoleAddForm form) {
        sysUserManager.setSysUserRole(form);
        return ResponseResult.ok();
    }

    /**
     * 设置菜单
     * @param form
     * @return
     */
    @PostMapping("/setSysMenuForUser")
    public ResponseResult<Void> setSysMenuForUser(@Valid @RequestBody SysUserMenuAddForm form) {
        sysUserManager.setSysMenuForUser(form);
        return ResponseResult.ok();
    }

    /**
     * 设置接口权限
     * @param form
     * @return
     */
    @PostMapping("/setSysApiForUser")
    public ResponseResult<Void> setSysApiForUser(@Valid @RequestBody SysUserApiAddForm form) {
        sysUserManager.setSysApiForUser(form);
        return ResponseResult.ok();
    }

}
