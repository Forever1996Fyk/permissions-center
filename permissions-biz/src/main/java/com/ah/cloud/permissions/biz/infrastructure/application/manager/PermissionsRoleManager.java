package com.ah.cloud.permissions.biz.infrastructure.application.manager;

import com.ah.cloud.permissions.biz.domain.role.form.PermissionsRoleAddForm;
import com.ah.cloud.permissions.biz.domain.role.form.PermissionsRoleUpdateForm;
import com.ah.cloud.permissions.biz.domain.role.query.PermissionsRoleQuery;
import com.ah.cloud.permissions.biz.domain.role.vo.PermissionsRoleVO;
import com.ah.cloud.permissions.biz.infrastructure.application.checker.PermissionsRoleChecker;
import com.ah.cloud.permissions.biz.infrastructure.application.helper.PermissionsRoleHelper;
import com.ah.cloud.permissions.biz.infrastructure.application.service.PermissionsRoleService;
import com.ah.cloud.permissions.biz.infrastructure.constant.PermissionsConstants;
import com.ah.cloud.permissions.biz.infrastructure.repository.bean.PermissionsRole;
import com.ah.cloud.permissions.domain.common.PageResult;
import com.ah.cloud.permissions.enums.common.DeletedEnum;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
public class PermissionsRoleManager {

    @Autowired
    private PermissionsRoleHelper permissionsRoleHelper;

    @Autowired
    private PermissionsRoleService permissionsRoleService;

    @Autowired
    private PermissionsRoleChecker permissionsRoleChecker;

    /**
     * 添加权限角色
     *
     * @param form
     */
    public void addPermissionsRole(PermissionsRoleAddForm form) {
        permissionsRoleChecker.checkRoleType(form);
        PermissionsRole permissionsRole = permissionsRoleHelper.convert2Entity(form);
        permissionsRole.setCreator(PermissionsConstants.OPERATOR_SYSTEM);
        permissionsRole.setModifier(PermissionsConstants.OPERATOR_SYSTEM);
        permissionsRoleService.save(permissionsRole);
    }

    /**
     * 编辑权限角色
     *
     * @param form
     */
    public void updatePermissionsRole(PermissionsRoleUpdateForm form) {
        permissionsRoleChecker.checkRoleType(form);
        PermissionsRole permissionsRole = permissionsRoleHelper.convert2Entity(form);
        permissionsRole.setModifier(PermissionsConstants.OPERATOR_SYSTEM);
        permissionsRoleService.updateById(permissionsRole);
    }

    /**
     * 删除权限角色
     *
     * @param id
     */
    public void deletePermissionsRole(Long id) {
        PermissionsRole permissionsRole = permissionsRoleService.getById(id);
        if (Objects.isNull(permissionsRole)) {
            return;
        }
        permissionsRole.setDeleted(id);
        permissionsRoleService.updateById(permissionsRole);
    }

    /**
     * 分页查询角色列表
     *
     * @param query
     * @return
     */
    public PageResult<PermissionsRoleVO> pagePermissionsRoles(PermissionsRoleQuery query) {
        PageInfo<PermissionsRole> pageInfo = PageMethod.startPage(query.getPageNum(), query.getPageSize())
                .doSelectPageInfo(
                        () -> permissionsRoleService.list(
                                new QueryWrapper<PermissionsRole>().lambda()
                                        .like(
                                                !StringUtils.isEmpty(query.getRoleName()),
                                                PermissionsRole::getRoleName, query.getRoleName())
                                        .eq(
                                                !StringUtils.isEmpty(query.getRoleCode()),
                                                PermissionsRole::getRoleCode, query.getRoleCode())
                                        .eq(
                                                PermissionsRole::getDeleted,
                                                DeletedEnum.NO.value)
                        ));
        PageResult<PermissionsRoleVO> result = permissionsRoleHelper.convert2PageResult(pageInfo);
        fillListProperties(result.getRows());
        return result;
    }

    /**
     * 填充集合属性
     *
     * @param permissionsRoleVOS
     */
    private void fillListProperties(List<PermissionsRoleVO> permissionsRoleVOS) {
        permissionsRoleVOS.stream().forEach(permissionsRoleVO -> permissionsRoleVO.setRoleTypeName(""));
    }

}
