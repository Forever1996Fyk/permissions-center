package com.ah.cloud.permissions.biz.application.helper;

import com.ah.cloud.permissions.biz.domain.role.form.SysRoleAddForm;
import com.ah.cloud.permissions.biz.domain.role.form.SysRoleUpdateForm;
import com.ah.cloud.permissions.biz.domain.role.vo.SysRoleVO;
import com.ah.cloud.permissions.biz.infrastructure.repository.bean.SysRole;
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
     * @param SysRole
     * @return
     */
    public SysRoleVO convert2VO(SysRole SysRole) {
        return SysRoleConvert.INSTANCE.convert(SysRole);
    }

    /**
     * 数据转换
     * @param form
     * @return
     */
    public SysRole convert2Entity(SysRoleAddForm form) {
        SysRole SysRole = SysRoleConvert.INSTANCE.convert(form);
        return SysRole;
    }

    /**
     * 数据转换
     * @param form
     * @return
     */
    public SysRole convert2Entity(SysRoleUpdateForm form) {
        SysRole SysRole = SysRoleConvert.INSTANCE.convert(form);
        return SysRole;
    }

    public PageResult<SysRoleVO> convert2PageResult(PageInfo<SysRole> pageInfo) {
        PageResult<SysRoleVO> result = new PageResult<>();
        result.setTotal(pageInfo.getTotal());
        result.setPageNum(pageInfo.getPageNum());
        result.setRows(SysRoleConvert.INSTANCE.convert(pageInfo.getList()));
        result.setPageSize(pageInfo.getSize());
        result.setPages(pageInfo.getPages());
        return result;
    }

    @Mapper
    public interface SysRoleConvert {
        SysRoleConvert INSTANCE = Mappers.getMapper(SysRoleConvert.class);

        SysRole convert(SysRoleAddForm form);

        SysRole convert(SysRoleUpdateForm form);

        SysRoleVO convert(SysRole SysRole);

        List<SysRoleVO> convert(List<SysRole> SysRoles);
    }
}
