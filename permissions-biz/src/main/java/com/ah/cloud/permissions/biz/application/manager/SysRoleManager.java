package com.ah.cloud.permissions.biz.application.manager;

import com.ah.cloud.permissions.biz.application.helper.SysRoleHelper;
import com.ah.cloud.permissions.biz.application.service.SysRoleService;
import com.ah.cloud.permissions.biz.application.service.SysUserRoleService;
import com.ah.cloud.permissions.biz.application.service.ext.SysRoleApiExtService;
import com.ah.cloud.permissions.biz.application.service.ext.SysRoleMenuExtService;
import com.ah.cloud.permissions.biz.domain.role.dto.SelectSysRoleDTO;
import com.ah.cloud.permissions.biz.domain.role.form.SysRoleAddForm;
import com.ah.cloud.permissions.biz.domain.role.form.SysRoleApiAddForm;
import com.ah.cloud.permissions.biz.domain.role.form.SysRoleMenuAddForm;
import com.ah.cloud.permissions.biz.domain.role.form.SysRoleUpdateForm;
import com.ah.cloud.permissions.biz.domain.role.query.SysRoleQuery;
import com.ah.cloud.permissions.biz.domain.role.vo.SelectSysRoleVo;
import com.ah.cloud.permissions.biz.domain.role.vo.SysRoleVO;
import com.ah.cloud.permissions.biz.infrastructure.constant.PermissionsConstants;
import com.ah.cloud.permissions.biz.infrastructure.exception.BizException;
import com.ah.cloud.permissions.biz.infrastructure.repository.bean.SysRole;
import com.ah.cloud.permissions.biz.infrastructure.repository.bean.SysRoleApi;
import com.ah.cloud.permissions.biz.infrastructure.repository.bean.SysRoleMenu;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
    private SysUserRoleService sysUserRoleService;
    @Resource
    private SysRoleApiExtService sysRoleApiExtService;
    @Resource
    private SysRoleMenuExtService sysRoleMenuExtService;
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
     * 设置角色菜单
     *
     * @param form
     */
    @Transactional(rollbackFor = Exception.class)
    public void setSysMenuForRole(SysRoleMenuAddForm form) {
        // 删除原有的角色菜单信息
        int countDeleted = sysRoleMenuExtService.delete(
                new QueryWrapper<SysRoleMenu>().lambda()
                        .eq(SysRoleMenu::getRoleCode, form.getRoleCode())
                        .eq(SysRoleMenu::getDeleted, DeletedEnum.NO.value)
        );
        log.info("SysRoleManager[setSysMenuForRole] delete SysRoleMenu count={}", countDeleted);
        // 重新添加
        if (CollectionUtils.isEmpty(form.getMenuIdList())) {
            log.warn("SysRoleManager[setSysMenuForRole] delete SysRoleMenu allData, roleId={}", form.getRoleCode());
            return;
        }

        List<SysRoleMenu> sysRoleMenuList = sysRoleHelper.buildSysRoleMenuEntityList(form);
        sysRoleMenuExtService.saveBatch(sysRoleMenuList);
    }

    /**
     * 设置角色api
     * @param form
     */
    @Transactional(rollbackFor = Exception.class)
    public void setSysApiForRole(SysRoleApiAddForm form) {
        // 删除原有的角色api信息
        sysRoleApiExtService.delete(
                new QueryWrapper<SysRoleApi>().lambda()
                        .eq(SysRoleApi::getRoleCode, form.getRoleCode())
                        .eq(SysRoleApi::getDeleted, DeletedEnum.NO.value)
        );
        // 重新添加
        if (CollectionUtils.isEmpty(form.getApiCodeList())) {
            log.warn("SysRoleManager[setSysApiForRole] delete SysRoleApi allData, roleId={}", form.getRoleCode());
            return;
        }

        List<SysRoleApi> sysRoleApiList = sysRoleHelper.getSysRoleApiEntityList(form);
        sysRoleApiExtService.saveBatch(sysRoleApiList);
    }

    /**
     * 获取角色选择列表
     * @return
     */
    public List<SelectSysRoleVo> selectSysRoleVoList() {
        List<SysRole> sysRoleList = sysRoleService.list(
                new QueryWrapper<SysRole>().lambda()
                        .eq(SysRole::getDeleted, DeletedEnum.NO.value)
        );
        return sysRoleList.stream()
                .map(sysRole -> SelectSysRoleVo.builder().roleCode(sysRole.getRoleCode()).roleName(sysRole.getRoleName()).build())
                .collect(Collectors.toList());
    }

    /**
     * 获取角色选择列表
     * @return
     */
    public List<SelectSysRoleDTO> selectSysRoleDTOList() {
        List<SysRole> sysRoleList = sysRoleService.list(
                new QueryWrapper<SysRole>().lambda()
                        .eq(SysRole::getDeleted, DeletedEnum.NO.value)
        );
        return sysRoleList.stream()
                .map(sysRole -> SelectSysRoleDTO.builder().code(sysRole.getRoleCode()).name(sysRole.getRoleName()).build())
                .collect(Collectors.toList());
    }
}
