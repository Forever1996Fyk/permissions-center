package com.ah.cloud.permissions.edi.infrastructure.factory;

import com.ah.cloud.permissions.edi.infrastructure.exceprion.EdiException;
import com.ah.cloud.permissions.edi.infrastructure.service.RetryExecutor;
import com.ah.cloud.permissions.enums.edi.EdiErrorCodeEnum;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-06 11:02
 **/
@Component
public class RetryExecutorFactory {
    @Resource
    private List<RetryExecutor> retryExecutorList;

    /**
     * 获取重试处理器
     * @param force
     * @param parallel
     * @return
     */
    public RetryExecutor getRetryExecutor(boolean force, boolean parallel) {
        for (RetryExecutor retryExecutor : retryExecutorList) {
            if (retryExecutor.getExecutor(force, parallel)) {
                return retryExecutor;
            }
        }
        throw new EdiException(EdiErrorCodeEnum.RETRY_EXECUTOR_NOT_EXISTED);
    }
}
