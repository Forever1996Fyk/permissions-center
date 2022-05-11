package com.ah.cloud.permissions.biz.infrastructure.mq;

import com.ah.cloud.permissions.domain.common.ProducerResult;

/**
 * @program: permissions-center
 * @description: 基础生产者
 * @author: YuKai Fan
 * @create: 2022-05-11 09:35
 **/
public interface NewProducer<T> {

    /**
     * 发送消息
     *
     * @param t message
     * @return
     */
    ProducerResult<Void> sendMessage(T t);
}
