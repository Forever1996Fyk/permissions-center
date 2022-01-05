package com.ah.cloud.permissions.api.controller;

import com.ah.cloud.permissions.biz.application.manager.SysRoleManager;
import com.ah.cloud.permissions.biz.domain.role.form.SysRoleAddForm;
import com.ah.cloud.permissions.biz.domain.role.form.SysRoleUpdateForm;
import com.ah.cloud.permissions.biz.domain.role.query.SysRoleQuery;
import com.ah.cloud.permissions.biz.domain.role.vo.SysRoleVO;
import com.ah.cloud.permissions.domain.common.PageResult;
import com.ah.cloud.permissions.domain.common.ResponseResult;
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
@RequestMapping("/role")
public class SysRoleController {

    @Autowired
    private SysRoleManager sysRoleManager;

    /**
     * 添加权限角色
     *
     * @param form
     * @return
     */
    @PostMapping("/add")
    public ResponseResult add(@Valid @RequestBody SysRoleAddForm form) {
        sysRoleManager.addSysRole(form);
        return ResponseResult.ok();
    }

    /**
     * 编辑权限角色
     *
     * @param form
     * @return
     */
    @PostMapping("/update")
    public ResponseResult update(@Valid @RequestBody SysRoleUpdateForm form) {
        sysRoleManager.updateSysRole(form);
        return ResponseResult.ok();
    }

    /**
     * 根据id删除角色
     *
     * @param id
     * @return
     */
    @PostMapping("/delete/{id}")
    public ResponseResult delete(@PathVariable(value = "id") Long id) {
        sysRoleManager.deleteSysRole(id);
        return ResponseResult.ok();
    }

    /**
     * 根据id查询权限角色信息
     *
     * @param id
     * @return
     */
    @GetMapping("/find/{id}")
    public ResponseResult<SysRoleVO> find(@PathVariable(value = "id") Long id) {
        return ResponseResult.ok(sysRoleManager.findSysRoleById(id));
    }

    /**
     * 分页查询角色列表
     *
     * @param query
     * @return
     */
    @GetMapping("/page")
    public ResponseResult<PageResult<SysRoleVO>> page(SysRoleQuery query) {
        return ResponseResult.ok(sysRoleManager.pageSysRoles(query));
    }
}
