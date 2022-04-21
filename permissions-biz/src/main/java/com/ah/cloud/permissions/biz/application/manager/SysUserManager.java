package com.ah.cloud.permissions.biz.application.manager;

import com.ah.cloud.permissions.biz.application.checker.SysUserChecker;
import com.ah.cloud.permissions.biz.application.helper.SysUserHelper;
import com.ah.cloud.permissions.biz.application.service.SysRoleMenuService;
import com.ah.cloud.permissions.biz.application.service.SysUserService;
import com.ah.cloud.permissions.biz.application.service.ext.SysUserApiExtService;
import com.ah.cloud.permissions.biz.application.service.ext.SysUserMenuExtService;
import com.ah.cloud.permissions.biz.application.service.ext.SysUserRoleExtService;
import com.ah.cloud.permissions.biz.domain.menu.vo.RouterVo;
import com.ah.cloud.permissions.biz.domain.user.form.SysUserAddForm;
import com.ah.cloud.permissions.biz.domain.user.form.SysUserApiAddForm;
import com.ah.cloud.permissions.biz.domain.user.form.SysUserMenuAddForm;
import com.ah.cloud.permissions.biz.domain.user.form.SysUserRoleAddForm;
import com.ah.cloud.permissions.biz.domain.user.query.SysUserQuery;
import com.ah.cloud.permissions.biz.domain.user.vo.SysUserVo;
import com.ah.cloud.permissions.biz.infrastructure.exception.BizException;
import com.ah.cloud.permissions.biz.infrastructure.repository.bean.*;
import com.ah.cloud.permissions.biz.infrastructure.util.SecurityUtils;
import com.ah.cloud.permissions.domain.common.PageResult;
import com.ah.cloud.permissions.enums.common.DeletedEnum;
import com.ah.cloud.permissions.enums.common.ErrorCodeEnum;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
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
    private AccessManager accessManager;
    @Resource
    private SysUserHelper sysUserHelper;
    @Resource
    private SysUserChecker sysUserChecker;
    @Resource
    private SysMenuManager sysMenuManager;
    @Resource
    private SysUserService sysUserService;
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

        List<SysUserRole> sysUserRoleList = sysUserHelper.buildSysUserRoleEntityList(form.getUserId(), form.getRoleCodeList());
        sysUserRoleExtService.saveBatch(sysUserRoleList);

        // 异步处理用户权限更新
        ThreadPoolManager.updateUserThreadPool.execute(() -> accessManager.updateUserCache(form.getUserId()));
    }

    /**
     * 设置用户菜单
     * @param form
     */
    @Transactional(rollbackFor = Exception.class)
    public void setSysMenuForUser(SysUserMenuAddForm form) {
        setSysMenuForUser(form.getUserId(), form.getMenuIdList());
        // 异步处理用户权限更新
        ThreadPoolManager.updateUserThreadPool.execute(() -> accessManager.updateUserCache(form.getUserId()));
    }

    /**
     * 设置用户api
     * @param form
     */
    @Transactional(rollbackFor = Exception.class)
    public void setSysApiForUser(SysUserApiAddForm form) {
        setSysApiForUser(form.getUserId(), form.getApiCodeList());
        // 异步处理用户权限更新
        ThreadPoolManager.updateUserThreadPool.execute(() -> accessManager.updateUserCache(form.getUserId()));
    }


    /**
     * 获取当前用户菜单路由
     * @return
     */
    public List<RouterVo> listRouterVoByUserId(Long userId) {
        // 根据用户id获取菜单列表
        List<SysUserMenu> sysUserMenuList = sysUserMenuExtService.list(
                new QueryWrapper<SysUserMenu>().lambda()
                        .eq(SysUserMenu::getUserId, userId)
                        .eq(SysUserMenu::getDeleted, DeletedEnum.NO.value)
        );

        // 根据用户id获取角色列表
        List<SysUserRole> sysUserRoleList = sysUserRoleExtService.list(
                new QueryWrapper<SysUserRole>().lambda()
                        .select(SysUserRole::getRoleCode)
                        .eq(SysUserRole::getUserId, userId)
                        .eq(SysUserRole::getDeleted, DeletedEnum.NO.value)
        );

        // 根据角色编码获取菜单列表
        Set<String> roleSet = sysUserRoleList.stream()
                .map(SysUserRole::getRoleCode)
                .collect(Collectors.toSet());
        List<SysRoleMenu> sysRoleMenuList = sysRoleMenuService.list(
                new QueryWrapper<SysRoleMenu>().lambda()
                        .in(SysRoleMenu::getRoleCode, roleSet)
                        .eq(SysRoleMenu::getDeleted, DeletedEnum.NO.value)
        );

        Set<Long> userMenuSet = sysUserMenuList.stream()
                .map(SysUserMenu::getMenuId)
                .collect(Collectors.toSet());

        Set<Long> roleMenuSet = sysRoleMenuList.stream()
                .map(SysRoleMenu::getMenuId)
                .collect(Collectors.toSet());
        userMenuSet.addAll(roleMenuSet);
        return sysMenuManager.assembleMenuRouteByUser(userMenuSet);
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
            log.warn("SysUserManager[setSysMenuForUser] delete SysUserMenu allData, userId={}", userId);
            return;
        }
        List<SysUserMenu> sysUserMenuList = sysUserHelper.getSysUserMenuEntityList(userId, menuIdList);
        sysUserMenuExtService.saveBatch(sysUserMenuList);
    }

    private void setSysApiForUser(Long userId, Collection<String> apiCodeList) {
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
        // 去重apiCode
        List<SysUserApi> sysUserApiList = sysUserHelper.getSysUserApiEntityList(userId, apiCodeList);
        sysUserApiExtService.saveBatch(sysUserApiList);
    }
}
