package com.ah.cloud.permissions.biz.application.helper;

import com.ah.cloud.permissions.biz.domain.dict.form.SysDictAddForm;
import com.ah.cloud.permissions.biz.domain.dict.form.SysDictUpdateForm;
import com.ah.cloud.permissions.biz.domain.dict.vo.SysDictVo;
import com.ah.cloud.permissions.biz.infrastructure.repository.bean.SysDict;
import com.ah.cloud.permissions.biz.infrastructure.util.SecurityUtils;
import com.ah.cloud.permissions.domain.common.PageResult;
import com.github.pagehelper.PageInfo;
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
 * @create: 2022-07-19 16:23
 **/
@Component
public class SysDictHelper {

    /**
     * 数据转换
     * @param form
     * @return
     */
    public SysDict convert(SysDictAddForm form) {
        SysDict sysDict = Convert.INSTANCE.convert(form);
        sysDict.setCreator(SecurityUtils.getUserNameBySession());
        sysDict.setModifier(SecurityUtils.getUserNameBySession());
        return sysDict;
    }

    /**
     * 数据转换
     * @param form
     * @return
     */
    public SysDict convert(SysDictUpdateForm form) {
        SysDict sysDict = Convert.INSTANCE.convert(form);
        sysDict.setModifier(SecurityUtils.getUserNameBySession());
        return sysDict;
    }

    /**
     * 数据转换
     * @param sysDict
     * @return
     */
    public SysDictVo convertToVo(SysDict sysDict) {
        return Convert.INSTANCE.convertToVo(sysDict);
    }

    /**
     * 数据转换
     * @param pageInfo
     * @return
     */
    public PageResult<SysDictVo> convertToPageResult(PageInfo<SysDict> pageInfo) {
        PageResult<SysDictVo> pageResult = new PageResult<>();
        pageResult.setPageSize(pageInfo.getPageSize());
        pageResult.setPageNum(pageInfo.getPageNum());
        pageResult.setRows(Convert.INSTANCE.convertToVoList(pageInfo.getList()));
        pageResult.setTotal(pageInfo.getTotal());
        return pageResult;
    }

    @Mapper
    public interface Convert {
        SysDictHelper.Convert INSTANCE = Mappers.getMapper(SysDictHelper.Convert.class);

        /**
         * 数据转换
         * @param form
         * @return
         */
        @Mappings({
                @Mapping(target = "isChange", source = "change")
        })
        SysDict convert(SysDictAddForm form);

        /**
         * 数据转换
         * @param form
         * @return
         */
        @Mappings({
                @Mapping(target = "isChange", source = "change")
        })
        SysDict convert(SysDictUpdateForm form);

        /**
         * 数据转换
         * @param sysDict
         * @return
         */
        @Mappings({
                @Mapping(target = "changeName", expression = "java(com.ah.cloud.permissions.enums.YesOrNoEnum.getByType(sysDict.getIsChange()).getDesc())"),
                @Mapping(target = "change", source = "isChange")
        })
        SysDictVo convertToVo(SysDict sysDict);

        /**
         * 数据转换
         * @param sysDictList
         * @return
         */
        List<SysDictVo> convertToVoList(List<SysDict> sysDictList);
    }
}
