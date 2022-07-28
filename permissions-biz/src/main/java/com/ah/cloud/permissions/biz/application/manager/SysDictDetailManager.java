package com.ah.cloud.permissions.biz.application.manager;

import com.ah.cloud.permissions.biz.application.helper.SysDictDetailHelper;
import com.ah.cloud.permissions.biz.application.service.SysDictDetailService;
import com.ah.cloud.permissions.biz.domain.dict.form.SysDictDetailAddForm;
import com.ah.cloud.permissions.biz.domain.dict.form.SysDictDetailUpdateForm;
import com.ah.cloud.permissions.biz.domain.dict.query.SysDictDetailQuery;
import com.ah.cloud.permissions.biz.domain.dict.vo.SelectDictLabelVo;
import com.ah.cloud.permissions.biz.domain.dict.vo.SysDictDetailVo;
import com.ah.cloud.permissions.biz.domain.dict.vo.SysDictVo;
import com.ah.cloud.permissions.biz.infrastructure.exception.BizException;
import com.ah.cloud.permissions.biz.infrastructure.repository.bean.SysDictDetail;
import com.ah.cloud.permissions.biz.infrastructure.util.CollectionUtils;
import com.ah.cloud.permissions.domain.common.PageResult;
import com.ah.cloud.permissions.enums.common.DeletedEnum;
import com.ah.cloud.permissions.enums.common.ErrorCodeEnum;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-19 16:55
 **/
@Slf4j
@Component
public class SysDictDetailManager {
    @Resource
    private SysDictDetailHelper sysDictDetailHelper;
    @Resource
    private SysDictDetailService sysDictDetailService;

    /**
     * 添加字典详情
     *
     * @param form
     */
    public void addSysDictDetail(SysDictDetailAddForm form) {
        SysDictDetail existedSysDictDetail = sysDictDetailService.getOne(
                new QueryWrapper<SysDictDetail>().lambda()
                        .eq(SysDictDetail::getDictCode, form.getDictCode())
                        .eq(SysDictDetail::getDictDetailCode, form.getDictDetailCode())
                        .eq(SysDictDetail::getDeleted, DeletedEnum.NO.value)
        );
        if (Objects.nonNull(existedSysDictDetail)) {
            throw new BizException(ErrorCodeEnum.DICT_DETAIL_CODE_IS_EXISTED, form.getDictCode(), form.getDictDetailCode());
        }
        SysDictDetail sysDictDetail = sysDictDetailHelper.convert(form);
        sysDictDetailService.save(sysDictDetail);
    }

    /**
     * 更新字典详情
     *
     * @param form
     */
    public void updateSysDictDetail(SysDictDetailUpdateForm form) {
        SysDictDetail existedSysDictDetail = sysDictDetailService.getOne(
                new QueryWrapper<SysDictDetail>().lambda()
                        .eq(SysDictDetail::getId, form.getId())
                        .eq(SysDictDetail::getDeleted, DeletedEnum.NO.value)
        );
        if (Objects.isNull(existedSysDictDetail)) {
            throw new BizException(ErrorCodeEnum.DICT_DETAIL_NOT_EXISTED);
        }
        if (!StringUtils.equals(form.getDictDetailCode(), existedSysDictDetail.getDictDetailCode())) {
            SysDictDetail sysDictDetail = sysDictDetailService.getOne(
                    new QueryWrapper<SysDictDetail>().lambda()
                            .eq(SysDictDetail::getDictCode, existedSysDictDetail.getDictCode())
                            .eq(SysDictDetail::getDictDetailCode, form.getDictDetailCode())
                            .eq(SysDictDetail::getDeleted, DeletedEnum.NO.value)
            );
            if (Objects.nonNull(sysDictDetail)) {
                throw new BizException(ErrorCodeEnum.DICT_DETAIL_CODE_IS_EXISTED, sysDictDetail.getDictCode(), sysDictDetail.getDictDetailCode());
            }
        }
        SysDictDetail updateSysDictDetail = sysDictDetailHelper.convert(form);
        sysDictDetailService.updateById(updateSysDictDetail);
    }

    /**
     * 根据id删除字典详情
     *
     * @param id
     */
    public void deleteById(Long id) {
        SysDictDetail existedSysDictDetail = sysDictDetailService.getOne(
                new QueryWrapper<SysDictDetail>().lambda()
                        .eq(SysDictDetail::getId, id)
                        .eq(SysDictDetail::getDeleted, DeletedEnum.NO.value)
        );
        if (Objects.isNull(existedSysDictDetail)) {
            throw new BizException(ErrorCodeEnum.DICT_DETAIL_NOT_EXISTED);
        }
        SysDictDetail deleteSysDictDetail = new SysDictDetail();
        deleteSysDictDetail.setDeleted(id);
        deleteSysDictDetail.setId(id);
        sysDictDetailService.updateById(deleteSysDictDetail);
    }

    /**
     * 根据id查询字典详情
     *
     * @param id
     * @return
     */
    public SysDictDetailVo findById(Long id) {
        SysDictDetail existedSysDictDetail = sysDictDetailService.getOne(
                new QueryWrapper<SysDictDetail>().lambda()
                        .eq(SysDictDetail::getId, id)
                        .eq(SysDictDetail::getDeleted, DeletedEnum.NO.value)
        );
        if (Objects.isNull(existedSysDictDetail)) {
            throw new BizException(ErrorCodeEnum.DICT_DETAIL_NOT_EXISTED);
        }
        return sysDictDetailHelper.convertToVo(existedSysDictDetail);
    }

    /**
     * 分页查询列表
     *
     * @param query
     * @return
     */
    public PageResult<SysDictDetailVo> pageSysDictDetailList(SysDictDetailQuery query) {
        if (StringUtils.isBlank(query.getDictCode())) {
            throw new BizException(ErrorCodeEnum.PARAM_MISS, "字典编码");
        }
        PageInfo<SysDictDetail> pageInfo = PageMethod.startPage(query.getPageNum(), query.getPageSize())
                .doSelectPageInfo(
                        () -> sysDictDetailService.list(
                                new QueryWrapper<SysDictDetail>().lambda()
                                        .eq(SysDictDetail::getDictCode, query.getDictCode())
                                        .eq(StringUtils.isNotBlank(query.getDictDetailCode()), SysDictDetail::getDictDetailCode, query.getDictDetailCode())
                                        .eq(StringUtils.isNotBlank(query.getDictDetailName()), SysDictDetail::getDictDetailDesc, query.getDictDetailName())
                                        .eq(SysDictDetail::getDeleted, DeletedEnum.NO.value)
                        )
                );
        return sysDictDetailHelper.convertToPageResult(pageInfo);
    }

    /**
     * 选择标签列表
     *
     * @param dictCode
     * @return
     */
    public List<SelectDictLabelVo> selectDictLabelList(String dictCode) {
        List<SysDictDetail> sysDictDetailList = sysDictDetailService.list(
                new QueryWrapper<SysDictDetail>().lambda()
                        .eq(SysDictDetail::getDictCode, dictCode)
                        .eq(SysDictDetail::getDeleted, DeletedEnum.NO.value)
        );
        if (CollectionUtils.isEmpty(sysDictDetailList)) {
            return Lists.newArrayList();
        }
        return sysDictDetailList.stream()
                .map(sysDictDetail ->
                        SelectDictLabelVo.builder()
                                .labelId(sysDictDetail.getId())
                                .labelCode(sysDictDetail.getDictDetailCode())
                                .labelName(sysDictDetail.getDictDetailDesc()).build()
                )
                .collect(Collectors.toList());
    }
}
