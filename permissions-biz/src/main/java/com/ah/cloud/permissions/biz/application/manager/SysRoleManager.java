package com.ah.cloud.permissions.biz.application.manager;

import com.ah.cloud.permissions.biz.application.checker.SysRoleChecker;
import com.ah.cloud.permissions.biz.application.helper.SysRoleHelper;
import com.ah.cloud.permissions.biz.domain.role.form.SysRoleAddForm;
import com.ah.cloud.permissions.biz.domain.role.form.SysRoleUpdateForm;
import com.ah.cloud.permissions.biz.domain.role.query.SysRoleQuery;
import com.ah.cloud.permissions.biz.domain.role.vo.SysRoleVO;
import com.ah.cloud.permissions.biz.application.service.SysRoleService;
import com.ah.cloud.permissions.biz.infrastructure.constant.PermissionsConstants;
import com.ah.cloud.permissions.biz.infrastructure.repository.bean.SysRole;
import com.ah.cloud.permissions.domain.common.PageResult;
import com.ah.cloud.permissions.enums.common.DeletedEnum;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

    @Autowired
    private SysRoleHelper sysRoleHelper;

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private SysRoleChecker sysRoleChecker;

    /**
     * 添加权限角色
     *
     * @param form
     */
    public void addSysRole(SysRoleAddForm form) {
        sysRoleChecker.checkRoleType(form);
        SysRole SysRole = sysRoleHelper.convert2Entity(form);
        SysRole.setCreator(PermissionsConstants.OPERATOR_SYSTEM);
        SysRole.setModifier(PermissionsConstants.OPERATOR_SYSTEM);
        sysRoleService.save(SysRole);
    }

    /**
     * 编辑权限角色
     *
     * @param form
     */
    public void updateSysRole(SysRoleUpdateForm form) {
        sysRoleChecker.checkRoleType(form);
        SysRole SysRole = sysRoleHelper.convert2Entity(form);
        SysRole.setModifier(PermissionsConstants.OPERATOR_SYSTEM);
        sysRoleService.updateById(SysRole);
    }

    /**
     * 删除权限角色
     *
     * @param id
     */
    public void deleteSysRole(Long id) {
        SysRole SysRole = sysRoleService.getById(id);
        if (Objects.isNull(SysRole)) {
            return;
        }
        SysRole.setDeleted(id);
        sysRoleService.updateById(SysRole);
    }

    /**
     * 根据id查询权限角色
     * @param id
     * @return
     */
    public SysRoleVO findSysRoleById(Long id) {
        SysRole SysRole = sysRoleService.getById(id);
        SysRoleVO SysRoleVO = sysRoleHelper.convert2VO(SysRole);
        return SysRoleVO;
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
        PageResult<SysRoleVO> result = sysRoleHelper.convert2PageResult(pageInfo);
        return result;
    }
}
