package com.ah.cloud.permissions.biz.application.helper;

import com.ah.cloud.permissions.biz.domain.dept.form.DeptAddForm;
import com.ah.cloud.permissions.biz.domain.dept.vo.SysDeptTreeSelectVo;
import com.ah.cloud.permissions.biz.domain.dept.vo.SysDeptTreeVo;
import com.ah.cloud.permissions.biz.domain.dept.vo.SysDeptVo;
import com.ah.cloud.permissions.biz.domain.menu.tree.SysMenuTreeSelectVo;
import com.ah.cloud.permissions.biz.domain.menu.vo.RouterVo;
import com.ah.cloud.permissions.biz.infrastructure.constant.PermissionsConstants;
import com.ah.cloud.permissions.biz.infrastructure.repository.bean.SysDept;
import com.ah.cloud.permissions.biz.infrastructure.repository.bean.SysDeptRelation;
import com.ah.cloud.permissions.biz.infrastructure.repository.bean.SysMenu;
import com.ah.cloud.permissions.biz.infrastructure.util.SecurityUtils;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-18 17:06
 **/
@Component
public class SysDeptHelper {

    /**
     * 数据转换
     * @param form
     * @return
     */
    public SysDept convert(DeptAddForm form) {
        SysDept sysDept = Convert.INSTANCE.convert(form);
        sysDept.setCreator(SecurityUtils.getUserNameBySession());
        sysDept.setModifier(SecurityUtils.getUserNameBySession());
        return sysDept;
    }


    /**
     * 数据转换
     * @param form
     * @return
     */
    public SysDeptRelation convertToRelation(DeptAddForm form) {
        SysDeptRelation sysDeptRelation = new SysDeptRelation();
        sysDeptRelation.setAncestorCode(form.getDeptCode());
        sysDeptRelation.setDescendantCode(StringUtils.equals(form.getParentDeptCode(), PermissionsConstants.ZERO_STR) ? form.getDeptCode() : form.getParentDeptCode());
        sysDeptRelation.setCreator(SecurityUtils.getUserNameBySession());
        sysDeptRelation.setModifier(SecurityUtils.getUserNameBySession());
        return sysDeptRelation;
    }

    /**
     * 数据转换
     * @param sysDept
     * @return
     */
    public SysDeptVo convertToVo(SysDept sysDept) {
        return Convert.INSTANCE.convertToVo(sysDept);
    }

    /**
     * 数据转换
     * @param sysDeptList
     * @return
     */
    public List<SysDeptTreeVo> convertToTree(List<SysDept> sysDeptList) {
        return convertToTree(sysDeptList, PermissionsConstants.ZERO);
    }

    private List<SysDeptTreeVo> convertToTree(List<SysDept> sysDeptList, Long pid) {
        List<SysDeptTreeVo> sysDeptVoList = Lists.newArrayList();
        for (SysDept sysDept : sysDeptList) {
            if (Objects.equals(sysDept.getParentId(), pid)) {
                SysDeptTreeVo sysDeptVo = Convert.INSTANCE.convertToTreeVo(sysDept);
                List<SysDeptTreeVo> childRouterVos = convertToTree(sysDeptList, sysDept.getId());
                sysDeptVo.setChildren(childRouterVos);
                sysDeptVoList.add(sysDeptVo);
            }
        }
        return sysDeptVoList;
    }

    /**
     * 数据转换
     * @param sysDeptList
     * @return
     */
    public List<SysDeptTreeSelectVo> convertToTreeSelectVo(List<SysDept> sysDeptList) {
        return convertToTreeSelectVo(sysDeptList, PermissionsConstants.ZERO);
    }

    private List<SysDeptTreeSelectVo> convertToTreeSelectVo(List<SysDept> sysDeptList, Long pid) {
        List<SysDeptTreeSelectVo> sysDeptTreeSelectVoList = Lists.newArrayList();
        for (SysDept sysDept : sysDeptList) {
            if (Objects.equals(sysDept.getParentId(), pid)) {
                SysDeptTreeSelectVo sysDeptTreeSelectVo = buildSysDeptTreeSelectVoByDept(sysDept);
                List<SysDeptTreeSelectVo> childSysMenuTreeSelectVoList = convertToTreeSelectVo(sysDeptList, sysDept.getId());
                sysDeptTreeSelectVo.setChildren(childSysMenuTreeSelectVoList);
                sysDeptTreeSelectVoList.add(sysDeptTreeSelectVo);
            }
        }
        return sysDeptTreeSelectVoList;
    }

    private SysDeptTreeSelectVo buildSysDeptTreeSelectVoByDept(SysDept sysDept) {
        return SysDeptTreeSelectVo.builder()
                .id(String.valueOf(sysDept.getId()))
                .label(sysDept.getName())
                .code(sysDept.getDeptCode())
                .build();
    }


    @Mapper
    public interface Convert {
        SysDeptHelper.Convert INSTANCE = Mappers.getMapper(SysDeptHelper.Convert.class);

        /**
         * 数据转换
         * @param form
         * @return
         */
        SysDept convert(DeptAddForm form);

        /**
         * 数据转换
         * @param sysDept
         * @return
         */
        SysDeptVo convertToVo(SysDept sysDept);

        /**
         * 数据转换
         * @param sysDept
         * @return
         */
        SysDeptTreeVo convertToTreeVo(SysDept sysDept);
    }
}
