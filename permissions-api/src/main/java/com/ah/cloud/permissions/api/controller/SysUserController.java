package com.ah.cloud.permissions.api.controller;

import com.ah.cloud.permissions.biz.application.manager.ResourceManager;
import com.ah.cloud.permissions.biz.application.manager.SysUserManager;
import com.ah.cloud.permissions.biz.application.strategy.cache.impl.RedisCacheHandleStrategy;
import com.ah.cloud.permissions.biz.domain.menu.vo.RouterVo;
import com.ah.cloud.permissions.biz.domain.user.form.*;
import com.ah.cloud.permissions.biz.domain.user.query.SelectUserApiQuery;
import com.ah.cloud.permissions.biz.domain.user.query.SysUserQuery;
import com.ah.cloud.permissions.biz.domain.user.vo.*;
import com.ah.cloud.permissions.biz.infrastructure.annotation.ApiMethodLog;
import com.ah.cloud.permissions.biz.infrastructure.constant.PermissionsConstants;
import com.ah.cloud.permissions.biz.infrastructure.util.SecurityUtils;
import com.ah.cloud.permissions.domain.common.PageResult;
import com.ah.cloud.permissions.domain.common.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

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
    @ApiMethodLog
    @PostMapping("/add")
    public ResponseResult<Void> add(@Valid @RequestBody SysUserAddForm form) {
        sysUserManager.addSysUser(form);
        return ResponseResult.ok();
    }

    /**
     * 更新用户
     * @param form
     * @return
     */
    @ApiMethodLog
    @PostMapping("/update")
    public ResponseResult<Void> update(@Valid @RequestBody SysUserUpdateForm form) {
        sysUserManager.updateSysUser(form);
        return ResponseResult.ok();
    }


    /**
     * 根据id删除用户
     *
     * @param id
     * @return
     */
    @ApiMethodLog
    @PostMapping("/deleteById/{id}")
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
    @ApiMethodLog
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
    @ApiMethodLog
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
    @ApiMethodLog
    @PostMapping("/setSysApiForUser")
    public ResponseResult<Void> setSysApiForUser(@Valid @RequestBody SysUserApiAddForm form) {
        sysUserManager.setSysApiForUser(form);
        return ResponseResult.ok();
    }

    /**
     * 取消用户接口权限
     * @param form
     * @return
     */
    @ApiMethodLog
    @PostMapping("/cancelSysApiForUser")
    public ResponseResult<Void> cancelSysApiForUser(@Valid @RequestBody SysUserApiAddForm form) {
        sysUserManager.cancelSysApiForUser(form);
        return ResponseResult.ok();
    }

    /**
     * 上传用户头像
     *
     * @param request
     * @return
     */
    @PostMapping("/uploadUserAvatar")
    public ResponseResult<String> uploadUserAvatar(HttpServletRequest request) {
        return ResponseResult.ok(sysUserManager.uploadUserAvatar(request));
    }

    /**
     * 获取用户信息
     * @return
     */
    @GetMapping("/getUserInfoVo")
    public ResponseResult<GetUserInfoVo> getUserInfoVo() {
        return ResponseResult.ok(sysUserManager.getCurrentUserInfo());
    }

    /**
     * 根据用户id获取用户的角色信息
     * @param userId
     * @return
     */
    @GetMapping("/listSelectUserRole/{userId}")
    public ResponseResult<SelectUserRoleVo> listSelectUserRoleByUserId(@PathVariable("userId") Long userId) {
        return ResponseResult.ok(sysUserManager.listSelectUserRoleByUserId(userId));
    }

    /**
     * 根据用户id分页获取用户的角色信息
     * @param query
     * @return
     */
    @GetMapping("/pageSelectUserApi")
    public ResponseResult<PageResult<SelectUserApiVo.ApiInfoVo>> pageSelectUserApi(SelectUserApiQuery query) {
        return ResponseResult.ok(sysUserManager.pageSelectUserApi(query));
    }

    /**
     * 获取用户个人信息
     *
     * @param userId
     * @return
     */
    @GetMapping("/findUserPersonalInfo/{userId}")
    public ResponseResult<SysUserPersonalInfoVo> findUserPersonalInfoVo(@PathVariable(value = "userId", required = false) Long userId) {
        return ResponseResult.ok(sysUserManager.findSysUserPersonalInfoVo(userId));
    }

    /**
     * 更新个人中心基本信息
     * @param form
     * @return
     */
    @PostMapping("/updatePersonalBaseUserInfo")
    public ResponseResult<Void> updatePersonalBaseUserInfo(@Valid @RequestBody SysUserInfoUpdateForm form) {
        sysUserManager.updatePersonalBaseUserInfo(form);
        return ResponseResult.ok();
    }

    /**
     * 变更用户状态
     * @param form
     * @return
     */
    @PostMapping("/changeSysUserStatus")
    public ResponseResult<Void> changeSysUserStatus(@RequestBody @Valid ChangeSysUserStatusForm form) {
        sysUserManager.changeSysUserStatus(form);
        return ResponseResult.ok();
    }
}
