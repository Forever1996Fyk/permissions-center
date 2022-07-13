package com.ah.cloud.permissions.edi.infrastructure.service.impl;

import com.ah.cloud.permissions.biz.application.manager.ThreadPoolManager;
import com.ah.cloud.permissions.edi.domain.common.EdiResult;
import com.ah.cloud.permissions.edi.domain.record.model.RetryBizRecord;
import com.ah.cloud.permissions.edi.infrastructure.service.RetryExecutor;
import com.ah.cloud.permissions.edi.infrastructure.service.RetryService;
import com.ah.cloud.permissions.enums.edi.EdiTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-06 10:55
 **/
@Slf4j
@Component
public class SingleThreadPoolRetryExecutor implements RetryExecutor {
    @Resource
    private RetryService retryService;

    @Override
    public EdiResult<Void> doRetry(RetryBizRecord retryBizRecord, EdiTypeEnum ediTypeEnum) {
        ThreadPoolManager.retrySingleThreadPool.execute(() -> retryService.doRetry(retryBizRecord, ediTypeEnum));
        return EdiResult.ofSuccess();
    }

    @Override
    public boolean getExecutor(boolean force, boolean parallel) {
        return ! (force && parallel);
    }
}
