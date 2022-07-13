package com.ah.cloud.permissions.edi.infrastructure.service;

import com.ah.cloud.permissions.edi.domain.record.dto.RetryBizTypeDTO;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-06 11:34
 **/
public interface RetryHandle {

    /**
     * 执行重试
     * @param params
     * @throws Exception
     */
    void doRetry(String params) throws Exception;

    /**
     * 初始化类型
     * @return
     */
    RetryBizTypeDTO initRetryBizType();
}
