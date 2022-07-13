package com.ah.cloud.permissions.edi.application.adapter;

import com.ah.cloud.permissions.biz.infrastructure.exception.BizException;
import com.ah.cloud.permissions.biz.infrastructure.util.JsonUtils;
import com.ah.cloud.permissions.biz.infrastructure.util.SecurityUtils;
import com.ah.cloud.permissions.edi.application.helper.RetryBizConfigHelper;
import com.ah.cloud.permissions.edi.application.service.EdiCfgBizRetryService;
import com.ah.cloud.permissions.edi.application.service.EdiCfgTechBizRetryService;
import com.ah.cloud.permissions.edi.domain.config.dto.RetryBizConfigCacheDTO;
import com.ah.cloud.permissions.edi.domain.config.dto.RetryBizConfigDTO;
import com.ah.cloud.permissions.edi.domain.config.form.AddCfgBizRetryForm;
import com.ah.cloud.permissions.edi.domain.config.form.UpdateCfgBizRetryForm;
import com.ah.cloud.permissions.edi.domain.config.query.CfgBizRetryQuery;
import com.ah.cloud.permissions.edi.infrastructure.repository.bean.EdiCfgBizRetry;
import com.ah.cloud.permissions.edi.infrastructure.repository.bean.EdiCfgTechBizRetry;
import com.ah.cloud.permissions.enums.YesOrNoEnum;
import com.ah.cloud.permissions.enums.common.DeletedEnum;
import com.ah.cloud.permissions.enums.edi.EdiErrorCodeEnum;
import com.ah.cloud.permissions.enums.edi.EdiTypeEnum;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.base.Throwables;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-05 14:09
 **/
@Slf4j
@Service
public class RetryBizConfigAdapterServiceImpl implements RetryBizConfigAdapterService {
    @Resource
    private RetryBizConfigHelper retryBizConfigHelper;
    @Resource
    private EdiCfgBizRetryService ediCfgBizRetryService;
    @Resource
    private EdiCfgTechBizRetryService ediCfgTechBizRetryService;

    private final LoadingCache<Integer, RetryBizConfigCacheDTO> retryBizConfigLocalCache = CacheBuilder.newBuilder()
            .refreshAfterWrite(10, TimeUnit.MINUTES)
            .maximumSize(100)
            .build(new CacheLoader<Integer, RetryBizConfigCacheDTO>() {
                @Override
                public RetryBizConfigCacheDTO load(Integer type) throws Exception {
                    return getRetryBizConfigByType(type, false);
                }
            });

    private final LoadingCache<Integer, RetryBizConfigCacheDTO> retryTechBizConfigLocalCache = CacheBuilder.newBuilder()
            .refreshAfterWrite(10, TimeUnit.MINUTES)
            .maximumSize(100)
            .build(new CacheLoader<Integer, RetryBizConfigCacheDTO>() {
                @Override
                public RetryBizConfigCacheDTO load(Integer type) throws Exception {
                    return getRetryBizConfigByType(type, true);
                }
            });

    @Override
    public void addBizRetryConfig(AddCfgBizRetryForm form) {
        log.info("RetryBizConfigAdapterServiceImpl[addBizRetryConfig] params is {}", JsonUtils.toJSONString(form));
        boolean result;
        if (form.isTech()) {
            EdiCfgTechBizRetry ediCfgTechBizRetry = retryBizConfigHelper.convertToTech(form);
            result = ediCfgTechBizRetryService.save(ediCfgTechBizRetry);
        } else {
            EdiCfgBizRetry ediCfgBizRetry = retryBizConfigHelper.convert(form);
            result = ediCfgBizRetryService.save(ediCfgBizRetry);
        }
        if (!result) {
            throw new BizException(EdiErrorCodeEnum.RETRY_BIZ_CONFIG_ADD_FAIL, EdiTypeEnum.get(form.isTech()).getDesc());
        }
    }

    @Override
    public void updateBizRetryConfig(UpdateCfgBizRetryForm form) {
        log.info("RetryBizConfigAdapterServiceImpl[updateBizRetryConfig] params is {}", JsonUtils.toJSONString(form));
        // 清除缓存
        retryBizConfigLocalCache.invalidateAll();
        retryTechBizConfigLocalCache.invalidateAll();
        boolean result;
        if (form.isTech()) {
            EdiCfgTechBizRetry ediCfgTechBizRetry = retryBizConfigHelper.convertToTech(form);
            result = ediCfgTechBizRetryService.updateById(ediCfgTechBizRetry);
        } else {
            EdiCfgBizRetry ediCfgBizRetry = retryBizConfigHelper.convert(form);
            result = ediCfgBizRetryService.updateById(ediCfgBizRetry);
        }
        if (!result) {
            throw new BizException(EdiErrorCodeEnum.RETRY_BIZ_CONFIG_UPDATE_FAIL, EdiTypeEnum.get(form.isTech()).getDesc());
        }
    }

    @Override
    public void startBizRetryConfig(Long id, boolean isTech) {
        boolean result = operateBizRetryConfig(id, isTech, YesOrNoEnum.YES);
        if (!result) {
            throw new BizException(EdiErrorCodeEnum.RETRY_BIZ_CONFIG_START_FAILED, EdiTypeEnum.get(isTech).getDesc());
        }
        // 清除缓存
        retryBizConfigLocalCache.invalidateAll();
        retryTechBizConfigLocalCache.invalidateAll();
    }

    @Override
    public void stopBizRetryConfig(Long id, boolean isTech) {
        boolean result = operateBizRetryConfig(id, isTech, YesOrNoEnum.NO);
        if (!result) {
            throw new BizException(EdiErrorCodeEnum.RETRY_BIZ_CONFIG_STOP_FAILED, EdiTypeEnum.get(isTech).getDesc());
        }
        // 清除缓存
        retryBizConfigLocalCache.invalidateAll();
        retryTechBizConfigLocalCache.invalidateAll();
    }

    @Override
    public RetryBizConfigCacheDTO getRetryBizConfigByLocalCache(Integer bizType, EdiTypeEnum ediTypeEnum) {
        try {
            if (ediTypeEnum.isTech()) {
                return retryTechBizConfigLocalCache.get(bizType);
            } else {
                return retryBizConfigLocalCache.get(bizType);
            }
        } catch (Exception e) {
            log.error("RetryBizConfigAdapterServiceImpl[getRetryBizConfigByLocalCache] throw exception reason is {}, bizType is {}, ediType is {}"
                    , Throwables.getStackTraceAsString(e)
                    , bizType
                    , ediTypeEnum);
            return null;
        }
    }

    @Override
    public List<RetryBizConfigDTO> listRetryBizConfig(CfgBizRetryQuery query) {
        if (query.isTech()) {
            List<EdiCfgTechBizRetry> ediCfgTechBizRetryList = ediCfgTechBizRetryService.list(
                    new QueryWrapper<EdiCfgTechBizRetry>().lambda()
                            .eq(StringUtils.isNotBlank(query.getBizName()), EdiCfgTechBizRetry::getBizName, query.getBizName())
                            .eq(query.getBizType() != null, EdiCfgTechBizRetry::getBizType, query.getBizType())
                            .eq(query.getStatus() != null, EdiCfgTechBizRetry::getStatus, query.getStatus().getType())
                            .eq(EdiCfgTechBizRetry::getDeleted, DeletedEnum.NO.value)
            );
            return retryBizConfigHelper.convertToTechDTO(ediCfgTechBizRetryList);
        } else {
            List<EdiCfgBizRetry> ediCfgBizRetryList = ediCfgBizRetryService.list(
                    new QueryWrapper<EdiCfgBizRetry>().lambda()
                            .eq(StringUtils.isNotBlank(query.getBizName()), EdiCfgBizRetry::getBizName, query.getBizName())
                            .eq(query.getBizType() != null, EdiCfgBizRetry::getBizType, query.getBizType())
                            .eq(query.getStatus() != null, EdiCfgBizRetry::getStatus, query.getStatus().getType())
                            .eq(EdiCfgBizRetry::getDeleted, DeletedEnum.NO.value)
            );
            return retryBizConfigHelper.convertToDTO(ediCfgBizRetryList);
        }
    }

    private boolean operateBizRetryConfig(Long id, boolean isTech, YesOrNoEnum yesOrNoEnum) {
        log.info("RetryBizConfigAdapterServiceImpl[startBizRetryConfig] id is {}, isTech is {}", id, isTech);
        boolean result;
        if (isTech) {
            EdiCfgTechBizRetry updateEdiCfgTechBizRetry = new EdiCfgTechBizRetry();
            updateEdiCfgTechBizRetry.setStatus(yesOrNoEnum.getType());
            updateEdiCfgTechBizRetry.setModifier(SecurityUtils.getUserNameBySession());
            updateEdiCfgTechBizRetry.setId(id);
            result = ediCfgTechBizRetryService.updateById(updateEdiCfgTechBizRetry);
        } else {
            EdiCfgBizRetry updateEdiCfgBizRetry = new EdiCfgBizRetry();
            updateEdiCfgBizRetry.setStatus(yesOrNoEnum.getType());
            updateEdiCfgBizRetry.setModifier(SecurityUtils.getUserNameBySession());
            updateEdiCfgBizRetry.setId(id);
            result = ediCfgBizRetryService.updateById(updateEdiCfgBizRetry);
        }
        return result;
    }

    private RetryBizConfigCacheDTO getRetryBizConfigByType(Integer type, boolean isTech) {
        if (isTech) {
            EdiCfgTechBizRetry ediCfgTechBizRetry = ediCfgTechBizRetryService.getOne(
                    new QueryWrapper<EdiCfgTechBizRetry>().lambda()
                            .eq(EdiCfgTechBizRetry::getBizType, type)
                            .eq(EdiCfgTechBizRetry::getStatus, YesOrNoEnum.YES.getType())
                            .eq(EdiCfgTechBizRetry::getDeleted, DeletedEnum.NO.value)
            );
            return retryBizConfigHelper.convertToDTO(ediCfgTechBizRetry);
        } else {
            EdiCfgBizRetry ediCfgBizRetry = ediCfgBizRetryService.getOne(
                    new QueryWrapper<EdiCfgBizRetry>().lambda()
                            .eq(EdiCfgBizRetry::getBizType, type)
                            .eq(EdiCfgBizRetry::getStatus, YesOrNoEnum.YES.getType())
                            .eq(EdiCfgBizRetry::getDeleted, DeletedEnum.NO.value)
            );
            return retryBizConfigHelper.convertToDTO(ediCfgBizRetry);
        }
    }
}
