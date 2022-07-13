package com.ah.cloud.permissions.edi.infrastructure.service;

import com.ah.cloud.permissions.edi.domain.common.EdiResult;
import com.ah.cloud.permissions.edi.domain.record.model.RetryBizRecord;
import com.ah.cloud.permissions.enums.edi.EdiTypeEnum;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-05 22:06
 **/
public interface RetryExecutor {

    /**
     * 重试执行
     *
     * @param retryBizRecord
     * @param ediTypeEnum
     * @return
     */
    EdiResult<Void> doRetry(RetryBizRecord retryBizRecord, EdiTypeEnum ediTypeEnum);

    /**
     * 获取执行器
     * @param force
     * @param parallel
     * @return
     */
    boolean getExecutor(boolean force, boolean parallel);

}
