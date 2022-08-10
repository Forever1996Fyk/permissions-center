package com.ah.cloud.permissions.biz.application.helper;

import com.ah.cloud.permissions.biz.domain.role.form.SysRoleAddForm;
import com.ah.cloud.permissions.biz.domain.role.form.SysRoleApiAddForm;
import com.ah.cloud.permissions.biz.domain.role.form.SysRoleMenuAddForm;
import com.ah.cloud.permissions.biz.domain.role.form.SysRoleUpdateForm;
import com.ah.cloud.permissions.biz.domain.role.vo.SysRoleVO;
import com.ah.cloud.permissions.biz.infrastructure.constant.PermissionsConstants;
import com.ah.cloud.permissions.biz.infrastructure.repository.bean.SysRole;
import com.ah.cloud.permissions.biz.infrastructure.repository.bean.SysRoleApi;
import com.ah.cloud.permissions.biz.infrastructure.repository.bean.SysRoleMenu;
import com.ah.cloud.permissions.domain.common.PageResult;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2021-12-03 16:05
 **/
@Component
public class SysRoleHelper {

    /**
     * 数据转换
     * @param list
     * @return
     */
    public List<SysRoleVO> convert2VO(List<SysRole> list) {
        return SysRoleConvert.INSTANCE.convert(list);
    }

    /**
     * 数据转换
     * @param sysRole
     * @return
     */
    public SysRoleVO convert2VO(SysRole sysRole) {
        return SysRoleConvert.INSTANCE.convert(sysRole);
    }

    /**
     * 数据转换
     * @param form
     * @return
     */
    public SysRole convert2Entity(SysRoleAddForm form) {
        SysRole sysRole = SysRoleConvert.INSTANCE.convert(form);
        sysRole.setCreator(PermissionsConstants.OPERATOR_SYSTEM);
        sysRole.setModifier(PermissionsConstants.OPERATOR_SYSTEM);
        return sysRole;
    }

    /**
     * 数据转换
     * @param form
     * @return
     */
    public SysRole convert2Entity(SysRoleUpdateForm form) {
        SysRole sysRole = SysRoleConvert.INSTANCE.convert(form);
        sysRole.setModifier(PermissionsConstants.OPERATOR_SYSTEM);
        return sysRole;
    }

    public PageResult<SysRoleVO> convert2PageResult(PageInfo<SysRole> pageInfo) {
        PageResult<SysRoleVO> result = new PageResult<>();
        result.setTotal(pageInfo.getTotal());
        result.setPageNum(pageInfo.getPageNum());
        result.setRows(SysRoleConvert.INSTANCE.convert(pageInfo.getList()));
        result.setPageSize(pageInfo.getPageSize());
        return result;
    }

    public List<SysRoleMenu> buildSysRoleMenuEntityList(SysRoleMenuAddForm form) {
        List<SysRoleMenu> sysRoleMenuList = Lists.newArrayList();
        for(Long menuId : form.getMenuIdList()) {
            SysRoleMenu sysRoleMenu = new SysRoleMenu();
            sysRoleMenu.setMenuId(menuId);
            sysRoleMenu.setRoleCode(form.getRoleCode());
            sysRoleMenuList.add(sysRoleMenu);
        }
        return sysRoleMenuList;
    }

    public List<SysRoleApi> getSysRoleApiEntityList(SysRoleApiAddForm form) {
        List<SysRoleApi> sysRoleApiList = Lists.newArrayList();
        for(String apiCode : form.getApiCodeList()) {
            SysRoleApi sysRoleApi = new SysRoleApi();
            sysRoleApi.setRoleCode(form.getRoleCode());
            sysRoleApi.setApiCode(apiCode);
            sysRoleApiList.add(sysRoleApi);
        }
        return sysRoleApiList;
    }

    @Mapper
    public interface SysRoleConvert {
        SysRoleConvert INSTANCE = Mappers.getMapper(SysRoleConvert.class);

        SysRole convert(SysRoleAddForm form);

        SysRole convert(SysRoleUpdateForm form);

        SysRoleVO convert(SysRole sysRole);

        /**
         * 数据转换
         * @param sysRoles
         * @return
         */
        List<SysRoleVO> convert(List<SysRole> sysRoles);
    }
}
