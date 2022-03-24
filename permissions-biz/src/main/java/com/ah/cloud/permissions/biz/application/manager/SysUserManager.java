package com.ah.cloud.permissions.biz.application.manager;

import com.ah.cloud.permissions.biz.application.checker.SysUserChecker;
import com.ah.cloud.permissions.biz.application.helper.SysUserHelper;
import com.ah.cloud.permissions.biz.application.service.SysRoleApiService;
import com.ah.cloud.permissions.biz.application.service.SysRoleMenuService;
import com.ah.cloud.permissions.biz.application.service.SysUserMenuService;
import com.ah.cloud.permissions.biz.application.service.SysUserService;
import com.ah.cloud.permissions.biz.application.service.ext.SysUserApiExtService;
import com.ah.cloud.permissions.biz.application.service.ext.SysUserMenuExtService;
import com.ah.cloud.permissions.biz.application.service.ext.SysUserRoleExtService;
import com.ah.cloud.permissions.biz.domain.user.form.SysUserAddForm;
import com.ah.cloud.permissions.biz.domain.user.form.SysUserApiAddForm;
import com.ah.cloud.permissions.biz.domain.user.form.SysUserMenuAddForm;
import com.ah.cloud.permissions.biz.domain.user.form.SysUserRoleAddForm;
import com.ah.cloud.permissions.biz.domain.user.query.SysUserQuery;
import com.ah.cloud.permissions.biz.domain.user.vo.SysUserVo;
import com.ah.cloud.permissions.biz.infrastructure.exception.BizException;
import com.ah.cloud.permissions.biz.infrastructure.repository.bean.*;
import com.ah.cloud.permissions.biz.infrastructure.util.JsonUtils;
import com.ah.cloud.permissions.biz.infrastructure.util.SecurityUtils;
import com.ah.cloud.permissions.domain.common.PageResult;
import com.ah.cloud.permissions.enums.common.DeletedEnum;
import com.ah.cloud.permissions.enums.common.ErrorCodeEnum;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @program: permissions-center
 * @description: 系统用户管理器
 * @author: YuKai Fan
 * @create: 2021-12-27 16:59
 **/
@Slf4j
@Component
public class SysUserManager {
    @Resource
    private SysUserHelper sysUserHelper;
    @Resource
    private SysUserChecker sysUserChecker;
    @Resource
    private SysUserService sysUserService;
    @Resource
    private SysRoleApiService sysRoleApiService;
    @Resource
    private SysRoleMenuService sysRoleMenuService;
    @Resource
    private SysUserApiExtService sysUserApiExtService;
    @Resource
    private SysUserMenuExtService sysUserMenuExtService;
    @Resource
    private SysUserRoleExtService sysUserRoleExtService;

    /**
     * 添加新用户
     * 记录操作日志 TODO
     * @param form
     */
    public void addSysUser(SysUserAddForm form) {
        /*
        参数校验
         */
        sysUserChecker.checkSysUserAddForm(form);

        /*
        判断用户手机号是否已存在
         */
        List<SysUser> sysUsers = sysUserService.list(
                new QueryWrapper<SysUser>().lambda()
                        .eq(SysUser::getPhone, form.getPhone())
                        .eq(SysUser::getDeleted, DeletedEnum.NO.value)
        );
        if (!CollectionUtils.isEmpty(sysUsers)) {
            throw new BizException(ErrorCodeEnum.USER_PHONE_IS_EXISTED, form.getPhone());
        }
        /*
        数据转换
         */
        SysUser sysUser = sysUserHelper.buildSysUserEntity(form);

        /*
        保存用户信息
         */
        sysUserService.save(sysUser);
    }

    /**
     * 逻辑删除用户
     * 记录操作日志 TODO
     * @param id
     */
    public void deleteSysUserById(Long id) {
        /*
        获取当前用户
         */
        SysUser sysUser = sysUserService.getById(id);
        if (Objects.isNull(sysUser)) {
            throw new BizException(ErrorCodeEnum.USER_NOT_EXIST);
        }
        /*
        更新用户删除标识
         */
        sysUser.setDeleted(id);
        sysUser.setModifier(String.valueOf(SecurityUtils.getBaseUserInfo().getUserId()));
        sysUserService.updateById(sysUser);

        /*
        逻辑删除用户角色 todo
         */

        /*
        逻辑删除用户菜单接口权限 todo
         */

    }

    /**
     * 根据用户id查询用户信息
     *
     * @param id
     * @return
     */
    public SysUserVo findSysUserById(Long id) {
        SysUser sysUser = sysUserService.getById(id);
        if (Objects.isNull(sysUser)) {
            throw new BizException(ErrorCodeEnum.USER_NOT_EXIST);
        }
        return sysUserHelper.convertToVo(sysUser);
    }

    /**
     * 分页查询用户信息
     *
     * @param query
     * @return
     */
    public PageResult<SysUserVo> pageSysUsers(SysUserQuery query) {
        PageInfo<SysUser> pageInfo = PageMethod.startPage(query.getPageNum(), query.getPageSize())
                .doSelectPageInfo(
                        () -> sysUserService.list(
                                new QueryWrapper<SysUser>().lambda()
                                        .like(
                                                !StringUtils.isEmpty(query.getNickName()),
                                                SysUser::getNickName, query.getNickName())
                                        .eq(
                                                !StringUtils.isEmpty(query.getPhone()),
                                                SysUser::getPhone, query.getPhone())
                                        .like(
                                                !StringUtils.isEmpty(query.getEmail()),
                                                SysUser::getEmail, query.getEmail())
                                        .eq(
                                                SysUser::getDeleted,
                                                DeletedEnum.NO.value)
                        ));
        PageResult<SysUserVo> result = sysUserHelper.convert2PageResult(pageInfo);
        return result;
    }

    /**
     * 设置用户角色
     *
     * 记录操作日志 TODO
     * @param form
     */
    @Transactional(rollbackFor = Exception.class)
    public void setSysUserRole(SysUserRoleAddForm form) {
        /*
        先删除原数据, 在更新用户角色
         */
        int countDeleted = sysUserRoleExtService.delete(
                new QueryWrapper<SysUserRole>().lambda()
                        .eq(SysUserRole::getUserId, form.getUserId())
        );

        if (CollectionUtils.isEmpty(form.getRoleCodeList())) {
            log.warn("SysUserManager[setSysUserRole] delete SysUserRole allData, userId={}", form.getUserId());
            return;
        }

        log.info("SysUserManager[setSysUserRole] delete SysUserRole data, count={}, param={}", countDeleted, JsonUtils.toJSONString(form));
        List<SysUserRole> sysUserRoleList = sysUserHelper.buildSysUserRoleEntity(form.getUserId(), form.getRoleCodeList());
        sysUserRoleExtService.saveBatch(sysUserRoleList);

        /*
        更新用户菜单列表
         */
        List<SysRoleMenu> sysRoleMenuList = sysRoleMenuService.list(
                new QueryWrapper<SysRoleMenu>().lambda()
                        .in(SysRoleMenu::getRoleId, form.getRoleIdList())
                        .eq(SysRoleMenu::getDeleted, DeletedEnum.NO.value)
        );
        List<Long> menuIdList = sysRoleMenuList.stream()
                .map(SysRoleMenu::getMenuId)
                .collect(Collectors.toList());
        setSysMenuForUser(form.getUserId(), menuIdList);

        /*
        更新用户api列表
         */
        List<SysRoleApi> sysRoleApiList = sysRoleApiService.list(
                new QueryWrapper<SysRoleApi>().lambda()
                        .in(SysRoleApi::getRoleId, form.getRoleIdList())
                        .eq(SysRoleApi::getDeleted, DeletedEnum.NO.value)
        );

        List<String> apiCodeList = sysRoleApiList.stream()
                .map(SysRoleApi::getApiCode)
                .collect(Collectors.toList());
        setSysApiForUser(form.getUserId(), apiCodeList);
    }

    /**
     * 设置用户菜单
     * @param form
     */
    @Transactional(rollbackFor = Exception.class)
    public void setSysMenuForUser(SysUserMenuAddForm form) {
        setSysMenuForUser(form.getUserId(), form.getMenuIdList());
    }

    /**
     * 设置用户api
     * @param form
     */
    @Transactional(rollbackFor = Exception.class)
    public void setSysApiForUser(SysUserApiAddForm form) {
        setSysApiForUser(form.getUserId(), form.getApiCodeList());
    }

    private void setSysMenuForUser(Long userId, List<Long> menuIdList) {
        /*
        先删除原有的用户菜单列表, 再重新添加
         */
        sysUserMenuExtService.delete(
                new QueryWrapper<SysUserMenu>().lambda()
                        .eq(SysUserMenu::getUserId, userId)
                        .in(SysUserMenu::getMenuId, menuIdList)
                        .eq(SysUserMenu::getDeleted, DeletedEnum.NO.value)
        );

        if (CollectionUtils.isEmpty(menuIdList)) {
            return;
        }

        List<SysUserMenu> sysUserMenuList = Lists.newArrayList();
        for (Long menuId : menuIdList) {
            SysUserMenu sysUserMenu = new SysUserMenu();
            sysUserMenu.setUserId(userId);
            sysUserMenu.setMenuId(menuId);
            sysUserMenuList.add(sysUserMenu);
        }
        sysUserMenuExtService.saveBatch(sysUserMenuList);

        /*
        更新用户api列表
         */
    }

    private void setSysApiForUser(Long userId, List<String> apiCodeList) {
        /*
        先删除原有的用户权限列表, 再重新添加
         */
        sysUserApiExtService.delete(
                new QueryWrapper<SysUserApi>().lambda()
                        .eq(SysUserApi::getUserId, userId)
                        .in(SysUserApi::getApiCode, apiCodeList)
                        .eq(SysUserApi::getDeleted, DeletedEnum.NO.value)
        );
        if (CollectionUtils.isEmpty(apiCodeList)) {
            return;
        }
        List<SysUserApi> sysUserApiList = Lists.newArrayList();
        for (String apiCode : apiCodeList) {
            SysUserApi sysUserApi = new SysUserApi();
            sysUserApi.setUserId(userId);
            sysUserApi.setApiCode(apiCode);
            sysUserApiList.add(sysUserApi);
        }
        sysUserApiExtService.saveBatch(sysUserApiList);
    }
}
