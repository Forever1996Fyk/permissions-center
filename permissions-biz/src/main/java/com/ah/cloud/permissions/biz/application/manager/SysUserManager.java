package com.ah.cloud.permissions.biz.application.manager;

import com.ah.cloud.permissions.biz.application.checker.SysUserChecker;
import com.ah.cloud.permissions.biz.application.helper.SysRoleHelper;
import com.ah.cloud.permissions.biz.application.helper.SysUserHelper;
import com.ah.cloud.permissions.biz.application.service.SysApiService;
import com.ah.cloud.permissions.biz.application.service.SysRoleMenuService;
import com.ah.cloud.permissions.biz.application.service.SysRoleService;
import com.ah.cloud.permissions.biz.application.service.ext.SysUserApiExtService;
import com.ah.cloud.permissions.biz.application.service.ext.SysUserExtService;
import com.ah.cloud.permissions.biz.application.service.ext.SysUserMenuExtService;
import com.ah.cloud.permissions.biz.application.service.ext.SysUserRoleExtService;
import com.ah.cloud.permissions.biz.domain.menu.vo.RouterVo;
import com.ah.cloud.permissions.biz.domain.user.LocalUser;
import com.ah.cloud.permissions.biz.domain.user.dto.SelectSysUserDTO;
import com.ah.cloud.permissions.biz.domain.user.form.*;
import com.ah.cloud.permissions.biz.domain.user.query.SelectUserApiQuery;
import com.ah.cloud.permissions.biz.domain.user.query.SysUserExportQuery;
import com.ah.cloud.permissions.biz.domain.user.query.SysUserQuery;
import com.ah.cloud.permissions.biz.domain.user.vo.*;
import com.ah.cloud.permissions.biz.infrastructure.constant.PermissionsConstants;
import com.ah.cloud.permissions.biz.infrastructure.exception.BizException;
import com.ah.cloud.permissions.biz.infrastructure.repository.bean.*;
import com.ah.cloud.permissions.biz.infrastructure.util.CollectionUtils;
import com.ah.cloud.permissions.biz.infrastructure.util.SecurityUtils;
import com.ah.cloud.permissions.domain.common.PageResult;
import com.ah.cloud.permissions.enums.UserStatusEnum;
import com.ah.cloud.permissions.enums.common.DeletedEnum;
import com.ah.cloud.permissions.enums.common.ErrorCodeEnum;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.ResultHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.AntPathMatcher;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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
    private SysApiService sysApiService;
    @Resource
    private AccessManager accessManager;
    @Resource
    private SysUserHelper sysUserHelper;
    @Resource
    private SysRoleHelper sysRoleHelper;
    @Resource
    private SysDeptManager sysDeptManager;
    @Resource
    private SysRoleService sysRoleService;
    @Resource
    private SysUserChecker sysUserChecker;
    @Resource
    private SysMenuManager sysMenuManager;
    @Resource
    private ResourceManager resourceManager;
    @Resource
    private SysUserExtService sysUserExtService;
    @Resource
    private SysRoleMenuService sysRoleMenuService;
    @Resource
    private SysUserIntroManager sysUserIntroManager;
    @Resource
    private SysUserApiExtService sysUserApiExtService;
    @Resource
    private SysUserMenuExtService sysUserMenuExtService;
    @Resource
    private SysUserRoleExtService sysUserRoleExtService;

    /**
     * 添加新用户
     *
     *  @param form
     */
    public void addSysUser(SysUserAddForm form) {
        /*
        参数校验
         */
        sysUserChecker.checkSysUserAddForm(form);

        /*
        判断用户手机号是否已存在
         */
        List<SysUser> sysUsers = sysUserExtService.list(
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
        sysUserExtService.save(sysUser);
    }

    /**
     * 逻辑删除用户
     *
     * @param id
     */
    public void deleteSysUserById(Long id) {
        /*
        获取当前用户
         */
        SysUser sysUser = sysUserExtService.getById(id);
        if (Objects.isNull(sysUser)) {
            throw new BizException(ErrorCodeEnum.USER_NOT_EXIST);
        }
        /*
        更新用户删除标识
         */
        sysUser.setDeleted(id);
        sysUser.setModifier(String.valueOf(SecurityUtils.getBaseUserInfo().getUserId()));
        sysUserExtService.updateById(sysUser);
    }

    /**
     * 根据用户id查询用户信息
     *
     * @param id
     * @return
     */
    public SysUserVo findSysUserById(Long id) {
        SysUser sysUser = sysUserExtService.getById(id);
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
        PageInfo<SysUser> pageInfo = PageMethod.startPage(query.getPageNo(), query.getPageSize())
                .doSelectPageInfo(
                        () -> sysUserExtService.list(
                                new QueryWrapper<SysUser>().lambda()
                                        .like(
                                                StringUtils.isNotBlank(query.getNickName()),
                                                SysUser::getNickName, query.getNickName())
                                        .eq(
                                                StringUtils.isNotBlank(query.getPhone()),
                                                SysUser::getPhone, query.getPhone())
                                        .like(
                                                StringUtils.isNotBlank(query.getEmail()),
                                                SysUser::getEmail, query.getEmail())
                                        .eq(
                                                StringUtils.isNotBlank(query.getDeptCode()),
                                                SysUser::getDeptCode, query.getDeptCode()
                                        )
                                        .eq(
                                                SysUser::getDeleted,
                                                DeletedEnum.NO.value)
                        ));
        return sysUserHelper.convert2PageResult(pageInfo);
    }

    /**
     * 设置用户角色
     * <p>
     *
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
            log.warn("SysUserManager[setSysUserRole] delete SysUserRole allData, userId is {}, oldUserRoleCount is {}", form.getUserId(), countDeleted);
            return;
        }

        List<SysUserRole> sysUserRoleList = sysUserHelper.buildSysUserRoleEntityList(form.getUserId(), form.getRoleCodeList());
        sysUserRoleExtService.saveBatch(sysUserRoleList);

        // 异步处理用户权限更新
        ThreadPoolManager.updateUserThreadPool.execute(() -> accessManager.updateUserCache(form.getUserId()));
    }

    /**
     * 设置用户菜单
     *
     * @param form
     */
    @Transactional(rollbackFor = Exception.class)
    public void setSysMenuForUser(SysUserMenuAddForm form) {
        Long userId = form.getUserId();
        List<Long> menuIdList = form.getMenuIdList();
        /*
        先删除原有的用户菜单列表, 再重新添加
         */
        sysUserMenuExtService.delete(
                new QueryWrapper<SysUserMenu>().lambda()
                        .eq(SysUserMenu::getUserId, userId)
                        .eq(SysUserMenu::getDeleted, DeletedEnum.NO.value)
        );

        if (CollectionUtils.isEmpty(menuIdList)) {
            log.warn("SysUserManager[setSysMenuForUser] delete SysUserMenu allData, userId={}", userId);
            return;
        }
        List<SysUserMenu> sysUserMenuList = sysUserHelper.getSysUserMenuEntityList(userId, menuIdList);
        sysUserMenuExtService.saveBatch(sysUserMenuList);
        // 异步处理用户权限更新
        ThreadPoolManager.updateUserThreadPool.execute(() -> accessManager.updateUserCache(form.getUserId()));
    }

    /**
     * 设置用户api
     *
     * @param form
     */
    @Transactional(rollbackFor = Exception.class)
    public void setSysApiForUser(SysUserApiAddForm form) {
        Long userId = form.getUserId();
        List<String> apiCodeList = form.getApiCodeList();
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
        // 异步处理用户权限更新
        ThreadPoolManager.updateUserThreadPool.execute(() -> accessManager.updateUserCache(userId));
    }

    /**
     * 取消用户api
     *
     * @param form
     */
    @Transactional(rollbackFor = Exception.class)
    public void cancelSysApiForUser(SysUserApiAddForm form) {
        List<String> apiCodeList = form.getApiCodeList();
        if (CollectionUtils.isEmpty(apiCodeList)) {
            return;
        }
        List<SysUserApi> sysUserApiList = sysUserApiExtService.list(
                new QueryWrapper<SysUserApi>().lambda()
                        .eq(SysUserApi::getUserId, form.getUserId())
                        .in(SysUserApi::getApiCode, apiCodeList)
                        .eq(SysUserApi::getDeleted, DeletedEnum.NO.value)
        );

        String userNameBySession = SecurityUtils.getUserNameBySession();
        List<SysUserApi> cancelSysUserApiList = sysUserApiList.stream()
                .map(sysUserApi -> {
                    SysUserApi cancelSysUserApi = new SysUserApi();
                    cancelSysUserApi.setId(sysUserApi.getId());
                    cancelSysUserApi.setDeleted(sysUserApi.getId());
                    cancelSysUserApi.setModifier(userNameBySession);
                    return cancelSysUserApi;
                }).collect(Collectors.toList());
        sysUserApiExtService.updateBatchById(cancelSysUserApiList);
        // 异步处理用户权限更新
        ThreadPoolManager.updateUserThreadPool.execute(() -> accessManager.updateUserCache(form.getUserId()));
    }


    /**
     * 获取当前用户菜单路由
     *
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

        Set<Long> userMenuSet = sysUserMenuList.stream()
                .map(SysUserMenu::getMenuId)
                .collect(Collectors.toSet());

        if (CollectionUtils.isNotEmpty(roleSet)) {
            List<SysRoleMenu> sysRoleMenuList = sysRoleMenuService.list(
                    new QueryWrapper<SysRoleMenu>().lambda()
                            .in(SysRoleMenu::getRoleCode, roleSet)
                            .eq(SysRoleMenu::getDeleted, DeletedEnum.NO.value)
            );
            Set<Long> roleMenuSet = sysRoleMenuList.stream()
                    .map(SysRoleMenu::getMenuId)
                    .collect(Collectors.toSet());
            userMenuSet.addAll(roleMenuSet);
        }
        return sysMenuManager.assembleMenuRouteByUser(userMenuSet);
    }

    /**
     * 上传用户头像
     *
     * @param request
     * @return
     */
    public String uploadUserAvatar(HttpServletRequest request) {
        Long userId = SecurityUtils.getUserIdBySession();
        String url = resourceManager.uploadUrl(request, userId);
        SysUser updateSysUser = new SysUser();
        updateSysUser.setUserId(userId);
        updateSysUser.setAvatar(url);
        boolean updateResult = sysUserExtService.update(
                updateSysUser,
                new UpdateWrapper<SysUser>().lambda()
                        .eq(SysUser::getUserId, userId)
                        .eq(SysUser::getDeleted, DeletedEnum.NO.value)
        );
        if (!updateResult) {
            throw new BizException(ErrorCodeEnum.USER_AVATAR_UPLOAD_FAILED);
        }
        return url;
    }

    /**
     * 根据用户id集合获取系统用户
     *
     * @param userIds
     * @return
     */
    public List<SysUser> listSysUserByUserIds(Collection<Long> userIds) {
        return sysUserExtService.list(
                new QueryWrapper<SysUser>().lambda()
                        .in(SysUser::getUserId, userIds)
                        .eq(SysUser::getDeleted, DeletedEnum.NO.value)
        );
    }

    /**
     * 根据用户id获取当前部门负责人id
     *
     * @param proposerId
     * @return
     */
    public SysUser findLeaderL1ByUserId(Long proposerId) {
        SysUser sysUser = sysUserExtService.getOneByUserId(proposerId);
        String deptCode = sysUser.getDeptCode();
        // 根据部门编码获取部门负责人id
        Long deptLeaderId = 0L;
        return Objects.equals(proposerId, deptLeaderId) ? sysUser : sysUserExtService.getOneByUserId(deptLeaderId);
    }

    /**
     * 获取用户选择list
     *
     * @return
     */
    public List<SelectSysUserDTO> selectSysUserDTOList() {
        List<SysUser> sysUserList = sysUserExtService.list(
                new QueryWrapper<SysUser>().lambda()
                        .eq(SysUser::getDeleted, DeletedEnum.NO.value)
                        .eq(SysUser::getStatus, UserStatusEnum.NORMAL.getStatus())
        );
        return sysUserList.stream()
                .map(sysUser -> SelectSysUserDTO.builder().code(String.valueOf(sysUser.getUserId())).name(sysUser.getNickName()).build())
                .collect(Collectors.toList());
    }

    /**
     * 流式查询用户导出列表
     *
     * @param query
     * @param resultHandler
     */
    public void listSysUserForExport(SysUserExportQuery query, ResultHandler<SysUser> resultHandler) {
        sysUserExtService.streamQueryForExport(query, resultHandler);
    }

    /**
     * 批量导入用户数据
     *
     * @param sysUserList
     */
    public void batchImportSysUser(List<SysUser> sysUserList) {
        sysUserExtService.saveBatch(sysUserList);
    }

    /**
     * 获取当前登录人信息
     *
     * @return
     */
    public GetUserInfoVo getCurrentUserInfo() {
        LocalUser localUser = SecurityUtils.getLocalUser();
        if (Objects.isNull(localUser)) {
            throw new BizException(ErrorCodeEnum.LOGIN_INVALID);
        }
        return sysUserHelper.convertToGetUserInfo(localUser);
    }

    /**
     * 更新用户
     *
     * @param form
     */
    public void updateSysUser(SysUserUpdateForm form) {
        /*
        参数校验
         */
        sysUserChecker.checkSysUserAddForm(form);

        SysUser existSysUser = sysUserExtService.getOne(
                new QueryWrapper<SysUser>().lambda()
                        .eq(SysUser::getId, form.getId())
                        .eq(SysUser::getDeleted, DeletedEnum.NO.value)
        );
        if (Objects.isNull(existSysUser)) {
            throw new BizException(ErrorCodeEnum.USER_NOT_EXIST);
        }
        SysUser updateSysUser = sysUserHelper.convertToEntity(form);
        sysUserExtService.updateById(updateSysUser);
    }

    /**
     * 根据用户id查询用户已分配和未分配的角色集合
     *
     * @param userId
     * @return
     */
    public SelectUserRoleVo listSelectUserRoleByUserId(Long userId) {
        if (Objects.isNull(userId) || Objects.equals(userId, PermissionsConstants.ZERO)) {
            throw new BizException(ErrorCodeEnum.PARAM_ILLEGAL);
        }
        List<SysRole> sysRoleList = sysRoleService.list(
                new QueryWrapper<SysRole>().lambda()
                        .eq(SysRole::getDeleted, DeletedEnum.NO.value)
        );
        List<SysUserRole> sysUserRoleList = sysUserRoleExtService.list(
                new QueryWrapper<SysUserRole>().lambda()
                        .eq(SysUserRole::getUserId, userId)
                        .eq(SysUserRole::getDeleted, DeletedEnum.NO.value)
        );
        List<String> sysUserRoleCodeList = sysUserRoleList.stream().map(SysUserRole::getRoleCode).collect(Collectors.toList());
        Set<SelectUserRoleVo.RoleInfoVo> allocatedRoleSet = Sets.newHashSet();

        List<SelectUserRoleVo.RoleInfoVo> roleInfoVoList = sysRoleHelper.convert2SelectUserRoleVoList(sysRoleList);
        roleInfoVoList.stream().filter(item -> sysUserRoleCodeList.contains(item.getRoleCode())).forEach(allocatedRoleSet::add);
        return SelectUserRoleVo.builder()
                .allocatedRoleSet(allocatedRoleSet)
                .allRoleSet(Sets.newHashSet(roleInfoVoList))
                .build();
    }

    /**
     * 根据用户id查询用户已分配接口集合
     *
     * @param query
     * @return
     */
    public PageResult<SelectUserApiVo.ApiInfoVo> pageSelectUserApi(SelectUserApiQuery query) {
        Long userId = query.getUserId();
        if (Objects.isNull(userId) || Objects.equals(userId, PermissionsConstants.ZERO)) {
            throw new BizException(ErrorCodeEnum.PARAM_ILLEGAL);
        }
        PageInfo<SysUserApi> pageInfo = PageMethod.startPage(query.getPageNo(), query.getPageSize())
                .doSelectPageInfo(
                        () -> sysUserApiExtService.list(
                                new QueryWrapper<SysUserApi>().lambda()
                                        .orderByDesc(SysUserApi::getCreatedTime)
                                        .eq(SysUserApi::getUserId, userId)
                                        .eq(StringUtils.isNotBlank(query.getApiCode()), SysUserApi::getApiCode, query.getApiCode())
                                        .eq(SysUserApi::getDeleted, DeletedEnum.NO.value)
                        )
                );
        PageResult<SelectUserApiVo.ApiInfoVo> pageResult = new PageResult<>();
        pageResult.setPageSize(pageInfo.getPageSize());
        pageResult.setPageNum(pageInfo.getPageNum());
        pageResult.setTotal(pageInfo.getTotal());
        List<SysUserApi> sysUserApiList = pageInfo.getList();
        if (CollectionUtils.isNotEmpty(sysUserApiList)) {
            List<String> apiCodeList = sysUserApiList.stream().map(SysUserApi::getApiCode).collect(Collectors.toList());
            List<SysApi> sysApiList = sysApiService.list(
                    new QueryWrapper<SysApi>().lambda()
                            .select(SysApi::getApiName, SysApi::getApiCode, SysApi::getId)
                            .in(SysApi::getApiCode, apiCodeList)
                            .eq(SysApi::getDeleted, DeletedEnum.NO.value)
            );
            pageResult.setRows(
                    sysApiList.stream()
                            .map(item -> SelectUserApiVo.ApiInfoVo.builder().id(item.getId()).apiCode(item.getApiCode()).apiName(item.getApiName()).build())
                            .collect(Collectors.toList())
            );
        }
        return pageResult;
    }

    /**
     * 根据用户id获取个人信息
     *
     * @param userId
     * @return
     */
    public SysUserPersonalInfoVo findSysUserPersonalInfoVo(Long userId) {
        if (Objects.equals(userId, PermissionsConstants.ZERO) || userId == null) {
            userId = SecurityUtils.getUserIdBySession();
            if (Objects.equals(userId, PermissionsConstants.ZERO)) {
                throw new BizException(ErrorCodeEnum.LOGIN_INVALID);
            }
        }
        return SysUserPersonalInfoVo.builder()
                .baseUserInfo(this.findBaseUserInfoVoByUserId(userId))
                .sysUserIntro(sysUserIntroManager.findSysUserIntroVoByUserId(userId))
                .build();
    }

    /**
     * 根据用户id查询用户基本信息
     *
     * @param userId
     * @return
     */
    public BaseUserInfoVo findBaseUserInfoVoByUserId(Long userId) {
        SysUser sysUser = sysUserExtService.getOneByUserId(userId);
        String deptName = sysDeptManager.getDeptNameByCode(sysUser.getDeptCode());
        return sysUserHelper.convertToBaseUserInfoVo(sysUser, deptName);
    }

    /**
     * 修改用户昵称
     *
     * @param form
     */
    public void updatePersonalBaseUserInfo(SysUserInfoUpdateForm form) {
        Long userId = SecurityUtils.getUserIdBySession();
        if (Objects.equals(userId, PermissionsConstants.ZERO)) {
            throw new BizException(ErrorCodeEnum.LOGIN_INVALID);
        }
        SysUser sysUser = sysUserExtService.getOneByUserId(userId);
        if (Objects.isNull(sysUser)) {
            throw new BizException(ErrorCodeEnum.USER_NOT_EXIST);
        }
        SysUser updateSysUser = sysUserHelper.convertToInfoUpdateForm(form);
        updateSysUser.setId(sysUser.getId());
        boolean result = sysUserExtService.updateById(updateSysUser);
        if (!result) {
            throw new BizException(ErrorCodeEnum.USER_INFO_UPDATE_FAILED);
        }
    }

    /**
     * 变更用户状态
     * @param form
     */
    public void changeSysUserStatus(ChangeSysUserStatusForm form) {
        SysUser existSysUser = sysUserExtService.getOne(
                new QueryWrapper<SysUser>().lambda()
                        .eq(SysUser::getId, form.getId())
                        .eq(SysUser::getDeleted, DeletedEnum.NO.value)
        );
        if (Objects.isNull(existSysUser)) {
            throw new BizException(ErrorCodeEnum.USER_NOT_EXIST);
        }
        SysUser sysUser = new SysUser();
        sysUser.setStatus(form.getStatus());
        sysUser.setVersion(existSysUser.getVersion());
        boolean updateResult = sysUserExtService.update(
                sysUser,
                new UpdateWrapper<SysUser>().lambda()
                        .eq(SysUser::getId, form.getId())
                        .eq(SysUser::getVersion, existSysUser.getVersion())
        );
        if (!updateResult) {
            throw new BizException(ErrorCodeEnum.VERSION_ERROR);
        }
        // 异步处理用户权限更新
        ThreadPoolManager.updateUserThreadPool.execute(() -> accessManager.updateUserCache(existSysUser.getUserId()));
    }
}
