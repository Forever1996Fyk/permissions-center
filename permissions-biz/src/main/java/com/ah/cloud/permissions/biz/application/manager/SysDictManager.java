package com.ah.cloud.permissions.biz.application.manager;

import com.ah.cloud.permissions.biz.application.helper.SysDictHelper;
import com.ah.cloud.permissions.biz.application.service.ext.SysDictExtService;
import com.ah.cloud.permissions.biz.domain.dict.form.SysDictAddForm;
import com.ah.cloud.permissions.biz.domain.dict.form.SysDictUpdateForm;
import com.ah.cloud.permissions.biz.domain.dict.query.SysDictQuery;
import com.ah.cloud.permissions.biz.domain.dict.vo.SysDictVo;
import com.ah.cloud.permissions.biz.infrastructure.exception.BizException;
import com.ah.cloud.permissions.biz.infrastructure.repository.bean.SysDict;
import com.ah.cloud.permissions.domain.common.PageResult;
import com.ah.cloud.permissions.enums.common.DeletedEnum;
import com.ah.cloud.permissions.enums.common.ErrorCodeEnum;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-19 16:00
 **/
@Slf4j
@Component
public class SysDictManager {
    @Resource
    private SysDictHelper sysDictHelper;
    @Resource
    private SysDictExtService sysDictExtService;

    /**
     * 添加字典
     * @param form
     */
    public void addSysDict(SysDictAddForm form) {
        SysDict existedSysDict = sysDictExtService.getOneByCode(form.getDictCode());
        if (Objects.nonNull(existedSysDict)) {
            throw new BizException(ErrorCodeEnum.DICT_CODE_IS_EXISTED, form.getDictCode());
        }
        SysDict sysDict = sysDictHelper.convert(form);
        sysDictExtService.save(sysDict);
    }

    /**
     * 更新字典
     * @param form
     */
    public void updateSysDict(SysDictUpdateForm form) {
        SysDict existedSysDict = sysDictExtService.getOne(
                new QueryWrapper<SysDict>().lambda()
                        .eq(SysDict::getId, form.getId())
                        .eq(SysDict::getDeleted, DeletedEnum.NO.value)
        );
        if (Objects.isNull(existedSysDict)) {
            throw new BizException(ErrorCodeEnum.DEPT_NOT_EXISTED);
        }
        if (!Objects.equals(form.getDictCode(), existedSysDict.getDictCode())) {
            SysDict sysDict = sysDictExtService.getOneByCode(form.getDictCode());
            if (Objects.nonNull(sysDict)) {
                throw new BizException(ErrorCodeEnum.DICT_CODE_IS_EXISTED, form.getDictCode());
            }
        }
        SysDict updateSysDict = sysDictHelper.convert(form);
        sysDictExtService.updateById(updateSysDict);
    }

    /**
     * 根据id删除字典
     * @param id
     */
    public void deleteById(Long id) {
        SysDict existedSysDict = sysDictExtService.getOne(
                new QueryWrapper<SysDict>().lambda()
                        .eq(SysDict::getId, id)
                        .eq(SysDict::getDeleted, DeletedEnum.NO.value)
        );
        if (Objects.isNull(existedSysDict)) {
            throw new BizException(ErrorCodeEnum.DEPT_NOT_EXISTED);
        }
        SysDict deleteSysDict = new SysDict();
        deleteSysDict.setDeleted(id);
        deleteSysDict.setId(id);
        sysDictExtService.updateById(deleteSysDict);
    }

    /**
     * 根据id查询字典
     * @param id
     * @return
     */
    public SysDictVo findById(Long id) {
        SysDict existedSysDict = sysDictExtService.getOne(
                new QueryWrapper<SysDict>().lambda()
                        .eq(SysDict::getId, id)
                        .eq(SysDict::getDeleted, DeletedEnum.NO.value)
        );
        if (Objects.isNull(existedSysDict)) {
            throw new BizException(ErrorCodeEnum.DEPT_NOT_EXISTED);
        }
        return sysDictHelper.convertToVo(existedSysDict);
    }

    /**
     * 分页查询字典列表
     * @param query
     * @return
     */
    public PageResult<SysDictVo> pageSysDictList(SysDictQuery query) {
        PageInfo<SysDict> pageInfo = PageMethod.startPage(query.getPageNo(), query.getPageSize())
                .doSelectPageInfo(
                        () -> sysDictExtService.list(
                                new QueryWrapper<SysDict>().lambda()
                                        .eq(StringUtils.isNotBlank(query.getDictCode()), SysDict::getDictCode, query.getDictCode())
                                        .like(StringUtils.isNotBlank(query.getDictName()), SysDict::getDictName, query.getDictName())
                                        .eq(SysDict::getDeleted, DeletedEnum.NO.value)
                        )
                );
        return sysDictHelper.convertToPageResult(pageInfo);
    }


}
