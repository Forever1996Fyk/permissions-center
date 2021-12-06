package com.ah.cloud.permissions.biz.infrastructure.application.helper;

import com.ah.cloud.permissions.biz.domain.role.form.PermissionsRoleAddForm;
import com.ah.cloud.permissions.biz.domain.role.form.PermissionsRoleUpdateForm;
import com.ah.cloud.permissions.biz.domain.role.vo.PermissionsRoleVO;
import com.ah.cloud.permissions.biz.infrastructure.repository.bean.PermissionsRole;
import com.ah.cloud.permissions.domain.common.PageResult;
import com.github.pagehelper.PageInfo;
import org.mapstruct.Mapper;
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
public class PermissionsRoleHelper {

    /**
     * 数据转换
     * @param list
     * @return
     */
    public List<PermissionsRoleVO> convert2VO(List<PermissionsRole> list) {
        return PermissionsRoleConvert.INSTANCE.convert(list);
    }

    /**
     * 数据转换
     * @param permissionsRole
     * @return
     */
    public PermissionsRoleVO convert2VO(PermissionsRole permissionsRole) {
        return PermissionsRoleConvert.INSTANCE.convert(permissionsRole);
    }

    /**
     * 数据转换
     * @param form
     * @return
     */
    public PermissionsRole convert2Entity(PermissionsRoleAddForm form) {
        PermissionsRole permissionsRole = PermissionsRoleConvert.INSTANCE.convert(form);
        return permissionsRole;
    }

    /**
     * 数据转换
     * @param form
     * @return
     */
    public PermissionsRole convert2Entity(PermissionsRoleUpdateForm form) {
        PermissionsRole permissionsRole = PermissionsRoleConvert.INSTANCE.convert(form);
        return permissionsRole;
    }

    public PageResult<PermissionsRoleVO> convert2PageResult(PageInfo<PermissionsRole> pageInfo) {
        PageResult<PermissionsRoleVO> result = new PageResult<>();
        result.setTotal(pageInfo.getTotal());
        result.setPageNum(pageInfo.getPageNum());
        result.setRows(PermissionsRoleConvert.INSTANCE.convert(pageInfo.getList()));
        result.setPageSize(pageInfo.getSize());
        result.setPages(pageInfo.getPages());
        return result;
    }

    @Mapper
    public interface PermissionsRoleConvert {
        PermissionsRoleConvert INSTANCE = Mappers.getMapper(PermissionsRoleConvert.class);

        PermissionsRole convert(PermissionsRoleAddForm form);

        PermissionsRole convert(PermissionsRoleUpdateForm form);

        PermissionsRoleVO convert(PermissionsRole permissionsRole);

        List<PermissionsRoleVO> convert(List<PermissionsRole> permissionsRoles);
    }
}
