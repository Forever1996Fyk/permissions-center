package com.ah.cloud.permissions.api.controller;

import com.ah.cloud.permissions.biz.application.manager.SysMenuManager;
import com.ah.cloud.permissions.biz.domain.menu.form.SysMenuAddForm;
import com.ah.cloud.permissions.biz.domain.menu.form.SysMenuUpdateForm;
import com.ah.cloud.permissions.biz.domain.menu.query.SysMenuQuery;
import com.ah.cloud.permissions.biz.domain.menu.query.SysMenuTreeSelectQuery;
import com.ah.cloud.permissions.biz.domain.menu.tree.SysMenuTreeSelectVo;
import com.ah.cloud.permissions.biz.domain.menu.tree.SysMenuTreeVo;
import com.ah.cloud.permissions.biz.domain.menu.vo.SysMenuVo;
import com.ah.cloud.permissions.biz.infrastructure.annotation.ApiMethodLog;
import com.ah.cloud.permissions.domain.common.ResponseResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * @program: permissions-center
 * @description: 系统菜单controller
 * @author: YuKai Fan
 * @create: 2022-03-22 00:20
 **/
@RestController
@RequestMapping("/menu")
public class SysMenuController {
    @Resource
    private SysMenuManager sysMenuManager;
    /**
     * 添加权限菜单
     * @param form
     * @return
     */
    @ApiMethodLog
    @PostMapping("/add")
    public ResponseResult<Void> add(@Valid @RequestBody SysMenuAddForm form) {
        sysMenuManager.addSysMenu(form);
        return ResponseResult.ok();
    }

    /**
     * 更新权限菜单
     * @param form
     * @return
     */
    @ApiMethodLog
    @PostMapping("/update")
    public ResponseResult<Void> update(@Valid @RequestBody SysMenuUpdateForm form) {
        sysMenuManager.updateSysMenu(form);
        return ResponseResult.ok();
    }

    /**
     * 根据id删除权限菜单
     *
     * @param id
     * @return
     */
    @ApiMethodLog
    @PostMapping("/delete/{id}")
    public ResponseResult<Void> delete(@PathVariable(value = "id") Long id) {
        sysMenuManager.deleteSysMenuById(id);
        return ResponseResult.ok();
    }

    /**
     * 根据id查询权限菜单信息
     *
     * @param id
     * @return
     */
    @GetMapping("/findById/{id}")
    public ResponseResult<SysMenuVo> findById(@PathVariable(value = "id") Long id) {
        return ResponseResult.ok(sysMenuManager.findSysMenuById(id));
    }

    /**
     * 获取菜单树列表 todo 待办
     * @param query
     * @return
     */
    @GetMapping("/listSysMenuTree")
    public ResponseResult<List<SysMenuTreeVo>> listSysMenuTree(SysMenuQuery query) {
        return ResponseResult.ok(sysMenuManager.listSysMenuTree(query));
    }

    /**
     * 获取菜单树形选择结构列表
     *
     * @param query
     * @return
     */
    @GetMapping("/listMenuSelectTree")
    public ResponseResult<List<SysMenuTreeSelectVo>> listMenuSelectTree(SysMenuTreeSelectQuery query) {
        return ResponseResult.ok(sysMenuManager.listMenuSelectTree(query));
    }
}
