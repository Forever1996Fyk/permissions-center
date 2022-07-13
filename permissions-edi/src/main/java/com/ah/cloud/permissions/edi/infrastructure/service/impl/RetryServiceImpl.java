package com.ah.cloud.permissions.edi.infrastructure.service.impl;

import cn.hutool.core.date.StopWatch;
import com.ah.cloud.permissions.biz.infrastructure.util.CollectionUtils;
import com.ah.cloud.permissions.biz.infrastructure.util.JsonUtils;
import com.ah.cloud.permissions.edi.application.adapter.RetryBizConfigAdapterService;
import com.ah.cloud.permissions.edi.application.adapter.RetryBizRecordAdapterService;
import com.ah.cloud.permissions.edi.domain.common.EdiResult;
import com.ah.cloud.permissions.edi.domain.config.dto.RetryBizConfigCacheDTO;
import com.ah.cloud.permissions.edi.domain.record.dto.RetryBizTypeDTO;
import com.ah.cloud.permissions.edi.domain.record.model.RetryBizRecord;
import com.ah.cloud.permissions.edi.infrastructure.config.RetryConfiguration;
import com.ah.cloud.permissions.edi.infrastructure.exceprion.EdiException;
import com.ah.cloud.permissions.edi.infrastructure.factory.RetryExecutorFactory;
import com.ah.cloud.permissions.edi.infrastructure.factory.RetryHandleFactory;
import com.ah.cloud.permissions.edi.infrastructure.service.RetryExecutor;
import com.ah.cloud.permissions.edi.infrastructure.service.RetryHandle;
import com.ah.cloud.permissions.edi.infrastructure.service.RetryService;
import com.ah.cloud.permissions.enums.edi.BizRetryStatusEnum;
import com.ah.cloud.permissions.enums.edi.EdiErrorCodeEnum;
import com.ah.cloud.permissions.enums.edi.EdiTypeEnum;
import com.google.common.base.Throwables;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-05 22:21
 **/
@Slf4j
@Service
public class RetryServiceImpl implements RetryService {
    @Resource
    private RetryConfiguration retryConfiguration;
    @Resource
    private RetryExecutorFactory retryExecutorFactory;
    @Resource
    private RetryBizRecordAdapterService retryBizRecordAdapterService;
    @Resource
    private RetryBizConfigAdapterService retryBizConfigAdapterService;

    @Override
    public EdiResult<Void> doRetry(RetryBizRecord retryBizRecord, EdiTypeEnum ediTypeEnum) {
        StopWatch stopWatch = new StopWatch("doRetry统计耗时");
        stopWatch.start();
        try {
            retryBizRecordAdapterService.updateStatus(retryBizRecord, BizRetryStatusEnum.RETRY_ING, ediTypeEnum);
            retryBizRecord.doRetry();
            retryBizRecordAdapterService.updateStatus(retryBizRecord, retryBizRecord.getRecordStatus(), ediTypeEnum);
        } catch (Throwable throwable) {
            log.error("RetryServiceImpl[doRetry] doRetry failed with throwable, reason is {}, params is {}, ediType is {}"
                    , Throwables.getStackTraceAsString(throwable)
                    , retryBizRecord
                    , ediTypeEnum);
            return EdiResult.ofFail(EdiErrorCodeEnum.RETRY_RECORD_BIZ_ERROR);
        } finally {
            stopWatch.stop();
            if (stopWatch.getTotalTimeMillis() > retryConfiguration.getRetryTimout()) {
                log.warn("RetryServiceImpl[doRetry] doRetry execute timeout, params is {}, ediType is {}, configRetryTimout is {}, totalTime is {}"
                        , retryBizRecord
                        , ediTypeEnum
                        , retryConfiguration.getRetryTimout()
                        , stopWatch.getTotalTimeMillis());
            }
        }
        return EdiResult.ofSuccess();
    }

    @Override
    public boolean getExecutor(boolean force, boolean parallel) {
        return force;
    }

    @Override
    public EdiResult<Void> retry(RetryBizRecord retryBizRecord, EdiTypeEnum ediTypeEnum, boolean force) {
        log.info("RetryServiceImpl[retry] start single retry, params is {}, ediType is {}", JsonUtils.toJSONString(retryBizRecord), ediTypeEnum);
        try {
            this.filter(retryBizRecord, ediTypeEnum, force);
        } catch (EdiException ediException) {
            log.error("RetryServiceImpl[retry] retry filter failed, reason is {}, params is {}, ediType is {}, force is {}"
                    , Throwables.getStackTraceAsString(ediException)
                    , retryBizRecord.toString()
                    , ediTypeEnum
                    , force);
            return EdiResult.ofFail(ediException.getErrorCode());
        }
        RetryBizTypeDTO retryBizType = RetryHandleFactory.getRetryBizType(retryBizRecord.getBizType(), ediTypeEnum);
        RetryExecutor retryExecutor = retryExecutorFactory.getRetryExecutor(force, retryBizType.getParallel());
        return retryExecutor.doRetry(retryBizRecord, ediTypeEnum);
    }

    @Override
    public EdiResult<Void> retry(List<RetryBizRecord> retryBizRecordList, EdiTypeEnum ediTypeEnum, boolean force) {
        List<String> errorMsgList = Lists.newArrayList();
        retryBizRecordList.forEach(retryBizRecord -> {
            EdiResult<Void> result = this.retry(retryBizRecord, ediTypeEnum, force);
            if (result.isFailed()) {
                String format = String.format("RetryServiceImpl[retry] 重试失败, reason is [%s], params is [%s], ediType is [%s]"
                        , result.getErrorMsg()
                        , retryBizRecord.toString()
                        , ediTypeEnum.getDesc());
                errorMsgList.add(format);
            }
        });
        if (CollectionUtils.isEmpty(errorMsgList)) {
            return EdiResult.ofSuccess();
        }
        return EdiResult.ofFail(EdiErrorCodeEnum.RETRY_RECORD_BIZ_ERROR, JsonUtils.toJSONString(errorMsgList));
    }

    private void filter(RetryBizRecord retryBizRecord, EdiTypeEnum ediTypeEnum, boolean force) {
        RetryBizConfigCacheDTO retryBizConfig = retryBizConfigAdapterService.getRetryBizConfigByLocalCache(retryBizRecord.getBizType(), ediTypeEnum);
        if (Objects.isNull(retryBizConfig)) {
            throw new EdiException(EdiErrorCodeEnum.RETRY_BIZ_CONFIG_IS_NULL);
        }
        retryBizRecord.setRetryBizConfig(retryBizConfig);
        if (Objects.equals(retryBizRecord.getRecordStatus(), BizRetryStatusEnum.RETRY_ING)) {
            throw new EdiException(EdiErrorCodeEnum.RETRY_RECORD_IS_RETRYING);
        }
        RetryHandle retryHandle = RetryHandleFactory.getRetryHandle(retryBizRecord.getBizType(), ediTypeEnum);
        retryBizRecord.setRetryHandle(retryHandle);
        if (force) {
            retryBizRecord.doFilter();
        }
    }
}
