package com.ah.cloud.permissions.api.controller;

import com.ah.cloud.permissions.biz.application.manager.SysRoleManager;
import com.ah.cloud.permissions.biz.domain.role.form.*;
import com.ah.cloud.permissions.biz.domain.role.query.SelectRoleApiQuery;
import com.ah.cloud.permissions.biz.domain.role.query.SysRoleQuery;
import com.ah.cloud.permissions.biz.domain.role.vo.SelectRoleApiVo;
import com.ah.cloud.permissions.biz.domain.role.vo.SysRoleVO;
import com.ah.cloud.permissions.biz.domain.user.form.SysUserApiAddForm;
import com.ah.cloud.permissions.biz.domain.user.query.SelectUserApiQuery;
import com.ah.cloud.permissions.biz.domain.user.vo.SelectUserApiVo;
import com.ah.cloud.permissions.biz.infrastructure.annotation.ApiMethodLog;
import com.ah.cloud.permissions.domain.common.PageResult;
import com.ah.cloud.permissions.domain.common.ResponseResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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

    @Resource
    private SysRoleManager sysRoleManager;

    /**
     * 添加权限角色
     *
     * @param form
     * @return
     */
    @ApiMethodLog
    @PostMapping("/add")
    public ResponseResult<Void> add(@Valid @RequestBody SysRoleAddForm form) {
        sysRoleManager.addSysRole(form);
        return ResponseResult.ok();
    }

    /**
     * 编辑权限角色
     *
     * @param form
     * @return
     */
    @ApiMethodLog
    @PostMapping("/update")
    public ResponseResult<Void> update(@Valid @RequestBody SysRoleUpdateForm form) {
        sysRoleManager.updateSysRole(form);
        return ResponseResult.ok();
    }

    /**
     * 根据id删除角色
     *
     * @param id
     * @return
     */
    @ApiMethodLog
    @PostMapping("/deleteById/{id}")
    public ResponseResult<Void> delete(@PathVariable(value = "id") Long id) {
        sysRoleManager.deleteSysRole(id);
        return ResponseResult.ok();
    }

    /**
     * 根据id查询权限角色信息
     *
     * @param id
     * @return
     */
    @GetMapping("/findById/{id}")
    public ResponseResult<SysRoleVO> findById(@PathVariable(value = "id") Long id) {
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

    /**
     * 设置角色菜单
     *
     * @param form
     * @return
     */
    @ApiMethodLog
    @PostMapping("/setSysMenuForRole")
    public ResponseResult<Void> setSysMenuForRole(@RequestBody SysRoleMenuAddForm form) {
        sysRoleManager.setSysMenuForRole(form);
        return ResponseResult.ok();
    }

    /**
     * 设置角色api
     *
     * @param form
     * @return
     */
    @ApiMethodLog
    @PostMapping("/setSysApiForRole")
    public ResponseResult<Void> setSysApiForRole(@RequestBody SysRoleApiAddForm form) {
        sysRoleManager.setSysApiForRole(form);
        return ResponseResult.ok();
    }


    /**
     * 取消角色接口权限
     * @param form
     * @return
     */
    @ApiMethodLog
    @PostMapping("/cancelSysApiForRole")
    public ResponseResult<Void> cancelSysApiForRole(@Valid @RequestBody SysRoleApiAddForm form) {
        sysRoleManager.cancelSysApiForRole(form);
        return ResponseResult.ok();
    }


    /**
     * 根据角色编码分页获取角色的api信息
     * @param query
     * @return
     */
    @GetMapping("/pageSelectRoleApi")
    public ResponseResult<PageResult<SelectRoleApiVo.ApiInfoVo>> pageSelectRoleApi(SelectRoleApiQuery query) {
        return ResponseResult.ok(sysRoleManager.pageSelectRoleApi(query));
    }

}
