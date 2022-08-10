package com.ah.cloud.permissions.edi.application.manager;

import com.ah.cloud.permissions.edi.application.service.EdiCfgBizRetryService;
import com.ah.cloud.permissions.edi.application.service.EdiCfgTechBizRetryService;
import com.ah.cloud.permissions.edi.infrastructure.repository.bean.EdiCfgBizRetry;
import com.ah.cloud.permissions.edi.infrastructure.repository.bean.EdiCfgTechBizRetry;
import com.ah.cloud.permissions.domain.common.PageResult;
import com.ah.cloud.permissions.edi.application.adapter.RetryBizConfigAdapterService;
import com.ah.cloud.permissions.edi.application.helper.RetryBizConfigHelper;
import com.ah.cloud.permissions.edi.domain.config.form.AddCfgBizRetryForm;
import com.ah.cloud.permissions.edi.domain.config.form.UpdateCfgBizRetryForm;
import com.ah.cloud.permissions.edi.domain.config.query.CfgBizRetryPageQuery;
import com.ah.cloud.permissions.edi.domain.config.vo.CfgBizRetryVo;
import com.ah.cloud.permissions.enums.common.DeletedEnum;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-05 15:27
 **/
@Slf4j
@Component
public class RetryBizConfigManager {
    @Resource
    private RetryBizConfigHelper retryBizConfigHelper;
    @Resource
    private EdiCfgBizRetryService ediCfgBizRetryService;
    @Resource
    private EdiCfgTechBizRetryService ediCfgTechBizRetryService;
    @Resource
    private RetryBizConfigAdapterService retryBizConfigAdapterService;

    /**
     * 添加配置
     * @param form
     */
    public void addRetryBizConfig(AddCfgBizRetryForm form) {
        retryBizConfigAdapterService.addBizRetryConfig(form);
    }

    /**
     * 更新配置
     * @param form
     */
    public void updateRetryBizConfig(UpdateCfgBizRetryForm form) {
        retryBizConfigAdapterService.updateBizRetryConfig(form);
    }

    /**
     * 启用配置
     * @param id
     * @param isTech
     */
    public void startBizRetryConfig(Long id, boolean isTech) {
        retryBizConfigAdapterService.startBizRetryConfig(id, isTech);
    }

    /**
     * 停用配置
     * @param id
     * @param isTech
     */
    public void stopBizRetryConfig(Long id, boolean isTech) {
        retryBizConfigAdapterService.stopBizRetryConfig(id, isTech);
    }

    /**
     * 根据id查询配置
     * @param id
     * @param isTech
     * @return
     */
    public CfgBizRetryVo findById(Long id, boolean isTech) {
        if (isTech) {
            EdiCfgTechBizRetry ediCfgTechBizRetry = ediCfgTechBizRetryService.getOne(
                    new QueryWrapper<EdiCfgTechBizRetry>().lambda()
                            .eq(EdiCfgTechBizRetry::getId, id)
                            .eq(EdiCfgTechBizRetry::getDeleted, DeletedEnum.NO.value)
            );
            return retryBizConfigHelper.convertToVo(ediCfgTechBizRetry);
        } else {
            EdiCfgBizRetry ediCfgBizRetry = ediCfgBizRetryService.getOne(
                    new QueryWrapper<EdiCfgBizRetry>().lambda()
                            .eq(EdiCfgBizRetry::getId, id)
                            .eq(EdiCfgBizRetry::getDeleted, DeletedEnum.NO.value)
            );
            return retryBizConfigHelper.convertToVo(ediCfgBizRetry);
        }
    }

    /**
     * 分页查询配置
     * @param query
     * @return
     */
    public PageResult<CfgBizRetryVo> pageCfgBizRetryList(CfgBizRetryPageQuery query) {
        if (query.isTech()) {
            PageInfo<EdiCfgTechBizRetry> pageInfo = PageMethod.startPage(query.getPageNo(), query.getPageSize())
                    .doSelectPageInfo(
                            () -> ediCfgTechBizRetryService.list(
                                    new QueryWrapper<EdiCfgTechBizRetry>().lambda()
                                            .eq(StringUtils.isNotBlank(query.getBizName()), EdiCfgTechBizRetry::getBizName, query.getBizName())
                                            .eq(query.getBizType() != null, EdiCfgTechBizRetry::getBizType, query.getBizType())
                                            .eq(EdiCfgTechBizRetry::getDeleted, DeletedEnum.NO.value)
                            )
                    );
            return retryBizConfigHelper.convertToTechPageResult(pageInfo);
        } else {
            PageInfo<EdiCfgBizRetry> pageInfo = PageMethod.startPage(query.getPageNo(), query.getPageSize())
                    .doSelectPageInfo(
                            () -> ediCfgBizRetryService.list(
                                    new QueryWrapper<EdiCfgBizRetry>().lambda()
                                            .eq(StringUtils.isNotBlank(query.getBizName()), EdiCfgBizRetry::getBizName, query.getBizName())
                                            .eq(query.getBizType() != null, EdiCfgBizRetry::getBizType, query.getBizType())
                                            .eq(EdiCfgBizRetry::getDeleted, DeletedEnum.NO.value)
                            )
                    );
            return retryBizConfigHelper.convertToPageResult(pageInfo);
        }
    }
}
