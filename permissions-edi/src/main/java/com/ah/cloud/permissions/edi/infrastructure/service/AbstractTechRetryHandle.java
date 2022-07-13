package com.ah.cloud.permissions.edi.infrastructure.service;

import com.ah.cloud.permissions.edi.infrastructure.factory.RetryHandleFactory;
import org.springframework.beans.factory.InitializingBean;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-06 11:38
 **/
public abstract class AbstractTechRetryHandle implements RetryHandle, InitializingBean {

    @Override
    public void afterPropertiesSet() throws Exception {
        RetryHandleFactory.registerTechRetryHandle(initRetryBizType(), getHandle());
    }

    /**
     * 获取处理器
     * @return
     */
    protected abstract RetryHandle getHandle();
}
