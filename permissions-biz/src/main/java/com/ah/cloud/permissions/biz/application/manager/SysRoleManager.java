package com.ah.cloud.permissions.biz.application.manager;

import com.ah.cloud.permissions.biz.application.checker.SysRoleChecker;
import com.ah.cloud.permissions.biz.application.helper.SysRoleHelper;
import com.ah.cloud.permissions.biz.application.service.SysRoleService;
import com.ah.cloud.permissions.biz.application.service.SysUserRoleService;
import com.ah.cloud.permissions.biz.domain.role.form.SysRoleAddForm;
import com.ah.cloud.permissions.biz.domain.role.form.SysRolePermissionForm;
import com.ah.cloud.permissions.biz.domain.role.form.SysRoleUpdateForm;
import com.ah.cloud.permissions.biz.domain.role.query.SysRoleQuery;
import com.ah.cloud.permissions.biz.domain.role.vo.SysRoleVO;
import com.ah.cloud.permissions.biz.infrastructure.constant.PermissionsConstants;
import com.ah.cloud.permissions.biz.infrastructure.exception.BizException;
import com.ah.cloud.permissions.biz.infrastructure.repository.bean.SysRole;
import com.ah.cloud.permissions.biz.infrastructure.repository.bean.SysUserRole;
import com.ah.cloud.permissions.domain.common.PageResult;
import com.ah.cloud.permissions.enums.common.DeletedEnum;
import com.ah.cloud.permissions.enums.common.ErrorCodeEnum;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2021-12-03 15:33
 **/
@Component
@Slf4j
public class SysRoleManager {

    @Resource
    private SysRoleHelper sysRoleHelper;

    @Resource
    private SysRoleService sysRoleService;

    @Resource
    private SysRoleChecker sysRoleChecker;

    @Resource
    private SysUserRoleService sysUserRoleService;
    /**
     * 添加权限角色
     *
     * @param form
     */
    public void addSysRole(SysRoleAddForm form) {
        /*
        判断角色编码是否存在
         */
        List<SysRole> sysRoles = sysRoleService.list(
                new QueryWrapper<SysRole>().lambda()
                        .eq(SysRole::getRoleCode, form.getRoleCode())
                        .eq(SysRole::getDeleted, DeletedEnum.NO.value)
        );
        if (!CollectionUtils.isEmpty(sysRoles)) {
            throw new BizException(ErrorCodeEnum.ROLE_CODE_IS_EXISTED, form.getRoleCode());
        }
        SysRole sysRole = sysRoleHelper.convert2Entity(form);
        sysRoleService.save(sysRole);
    }

    /**
     * 编辑权限角色
     *
     * @param form
     */
    public void updateSysRole(SysRoleUpdateForm form) {
        // 存在判断, 同时为了获取版本号
        SysRole sysRole = sysRoleService.getById(form.getId());
        if (Objects.isNull(sysRole)) {
            throw new BizException(ErrorCodeEnum.CURRENT_ROLE_IS_NOT_EXISTED_UPDATE_FAILED, String.valueOf(form.getId()));
        }
        // 角色编码是不可变的
        if (!StringUtils.equals(form.getRoleCode(), sysRole.getRoleCode())) {
            throw new BizException(ErrorCodeEnum.ROLE_CODE_CANNOT_CHANGE_UPDATE_FAILED, String.valueOf(form.getId()));
        }
        // 更新数据封装
        SysRole sysRoleUpdate = sysRoleHelper.convert2Entity(form);
        sysRoleUpdate.setVersion(sysRole.getVersion());
        // todo 获取当前登录人信息
        sysRoleUpdate.setModifier(PermissionsConstants.OPERATOR_SYSTEM);
        // 数据更新
        final boolean updateResult = sysRoleService.updateById(sysRoleUpdate);
        if (!updateResult) {
            throw new BizException(ErrorCodeEnum.VERSION_ERROR);
        }
    }

    /**
     * 删除权限角色
     * <p>
     * 权限角色存在多个关联数据, 删除需要谨慎
     *
     * @param id
     */
    public void deleteSysRole(Long id) {
        final SysRole sysRole = sysRoleService.getOne(
                new QueryWrapper<SysRole>().lambda()
                        .eq(SysRole::getId, id)
                        .eq(SysRole::getDeleted, DeletedEnum.NO.value)
        );
        if (Objects.isNull(sysRole)) {
            return;
        }
        // 判断角色是否关联用户, 如果存在用户则无法被删除
        List<SysUserRole> sysUserRoles = sysUserRoleService.list(
                new QueryWrapper<SysUserRole>().lambda()
                        .eq(SysUserRole::getRoleCode, sysRole.getRoleCode())
                        .eq(SysUserRole::getDeleted, DeletedEnum.NO.value)
        );
        if (!CollectionUtils.isEmpty(sysUserRoles)) {
            throw new BizException(ErrorCodeEnum.CURRENT_ROLE_ASSOCIATION_USER_DELETE_FAILED, sysRole.getRoleCode());
        }

        // 删除角色信息
        SysRole sysRoleDel = new SysRole();
        sysRoleDel.setId(id);
        sysRoleDel.setDeleted(id);
        sysRoleDel.setVersion(sysRole.getVersion());
        // todo 当前用户信息
        sysRoleDel.setModifier(PermissionsConstants.OPERATOR_SYSTEM);
        final boolean deleteResult = sysRoleService.updateById(sysRoleDel);
        if (!deleteResult) {
            throw new BizException(ErrorCodeEnum.VERSION_ERROR);
        }
    }

    /**
     * 根据id查询权限角色
     *
     * @param id
     * @return
     */
    public SysRoleVO findSysRoleById(Long id) {
        return sysRoleHelper.convert2VO(sysRoleService.getById(id));
    }

    /**
     * 分页查询角色列表
     *
     * @param query
     * @return
     */
    public PageResult<SysRoleVO> pageSysRoles(SysRoleQuery query) {
        PageInfo<SysRole> pageInfo = PageMethod.startPage(query.getPageNum(), query.getPageSize())
                .doSelectPageInfo(
                        () -> sysRoleService.list(
                                new QueryWrapper<SysRole>().lambda()
                                        .like(
                                                !StringUtils.isEmpty(query.getRoleName()),
                                                SysRole::getRoleName, query.getRoleName())
                                        .eq(
                                                !StringUtils.isEmpty(query.getRoleCode()),
                                                SysRole::getRoleCode, query.getRoleCode())
                                        .eq(
                                                SysRole::getDeleted,
                                                DeletedEnum.NO.value)
                        ));
        return sysRoleHelper.convert2PageResult(pageInfo);
    }

    /**
     * 设置菜单和接口编码
     *
     * @param form
     */
    public void setSysMenuAndApiCode(SysRolePermissionForm form) {
    }
}
