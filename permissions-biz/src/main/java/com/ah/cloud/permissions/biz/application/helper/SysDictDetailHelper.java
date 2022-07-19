package com.ah.cloud.permissions.biz.application.helper;

import com.ah.cloud.permissions.biz.domain.dict.form.SysDictDetailAddForm;
import com.ah.cloud.permissions.biz.domain.dict.form.SysDictDetailUpdateForm;
import com.ah.cloud.permissions.biz.domain.dict.vo.SysDictDetailVo;
import com.ah.cloud.permissions.biz.domain.dict.vo.SysDictVo;
import com.ah.cloud.permissions.biz.infrastructure.repository.bean.SysDict;
import com.ah.cloud.permissions.biz.infrastructure.repository.bean.SysDictDetail;
import com.ah.cloud.permissions.biz.infrastructure.util.SecurityUtils;
import com.ah.cloud.permissions.domain.common.PageResult;
import com.github.pagehelper.PageInfo;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-19 17:01
 **/
@Component
public class SysDictDetailHelper {

    /**
     * 数据转换
     * @param form
     * @return
     */
    public SysDictDetail convert(SysDictDetailAddForm form) {
        SysDictDetail sysDictDetail = Convert.INSTANCE.convert(form);
        sysDictDetail.setCreator(SecurityUtils.getUserNameBySession());
        sysDictDetail.setModifier(SecurityUtils.getUserNameBySession());
        return sysDictDetail;
    }

    /**
     * 数据转换
     * @param form
     * @return
     */
    public SysDictDetail convert(SysDictDetailUpdateForm form) {
        SysDictDetail sysDictDetail = Convert.INSTANCE.convert(form);
        sysDictDetail.setModifier(SecurityUtils.getUserNameBySession());
        return sysDictDetail;
    }

    /**
     * 数据转换
     * @param sysDictDetail
     * @return
     */
    public SysDictDetailVo convertToVo(SysDictDetail sysDictDetail) {
        return Convert.INSTANCE.convertToVo(sysDictDetail);
    }

    /**
     * 数据转换
     * @param pageInfo
     * @return
     */
    public PageResult<SysDictDetailVo> convertToPageResult(PageInfo<SysDictDetail> pageInfo) {
        PageResult<SysDictDetailVo> pageResult = new PageResult<>();
        pageResult.setTotal(pageInfo.getTotal());
        pageResult.setPageNum(pageInfo.getPageNum());
        pageResult.setPageSize(pageInfo.getPageSize());
        pageResult.setRows(Convert.INSTANCE.convertToVoList(pageInfo.getList()));
        return pageResult;
    }

    public interface Convert {
        SysDictDetailHelper.Convert INSTANCE = Mappers.getMapper(SysDictDetailHelper.Convert.class);

        /**
         * 数据转换
         * @param form
         * @return
         */
        SysDictDetail convert(SysDictDetailAddForm form);

        /**
         * 数据转换
         * @param form
         * @return
         */
        SysDictDetail convert(SysDictDetailUpdateForm form);

        /**
         * 数据转换
         * @param sysDictDetail
         * @return
         */
        SysDictDetailVo convertToVo(SysDictDetail sysDictDetail);

        /**
         * 数据转换
         * @param sysDictDetailList
         * @return
         */
        List<SysDictDetailVo> convertToVoList(List<SysDictDetail> sysDictDetailList);
    }
}
