package com.ah.cloud.permissions.api.controller;

import com.ah.cloud.permissions.biz.application.manager.SysUserManager;
import com.ah.cloud.permissions.biz.domain.user.form.SysUserAddForm;
import com.ah.cloud.permissions.biz.domain.user.query.SysUserQuery;
import com.ah.cloud.permissions.biz.domain.user.vo.SysUserVo;
import com.ah.cloud.permissions.domain.common.PageResult;
import com.ah.cloud.permissions.domain.common.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    @Autowired
    private SysUserManager sysUserManager;

    /**
     * 添加新用户
     * @param form
     * @return
     */
    @PostMapping("/add")
    public ResponseResult add(@Valid @RequestBody SysUserAddForm form) {
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
    public ResponseResult delete(@PathVariable(value = "id") Long id) {
        sysUserManager.deleteSysUserById(id);
        return ResponseResult.ok();
    }

    /**
     * 根据id查询用户信息
     *
     * @param id
     * @return
     */
    @GetMapping("/find/{id}")
    public ResponseResult<SysUserVo> find(@PathVariable(value = "id") Long id) {
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
}
