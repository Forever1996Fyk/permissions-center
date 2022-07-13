package com.ah.cloud.permissions.edi.infrastructure.service;

import com.ah.cloud.permissions.edi.domain.common.EdiResult;
import com.ah.cloud.permissions.edi.domain.record.model.RetryBizRecord;
import com.ah.cloud.permissions.enums.edi.EdiTypeEnum;

import java.util.List;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-05 22:05
 **/
public interface RetryService extends RetryExecutor {

    /**
     * 重试
     * @param retryBizRecord
     * @param ediTypeEnum
     * @param force
     * @return
     */
    EdiResult<Void> retry(RetryBizRecord retryBizRecord, EdiTypeEnum ediTypeEnum, boolean force);

    /**
     * 重试
     * @param retryBizRecordList
     * @param ediTypeEnum
     * @param force
     * @return
     */
    EdiResult<Void> retry(List<RetryBizRecord> retryBizRecordList, EdiTypeEnum ediTypeEnum, boolean force);
}
