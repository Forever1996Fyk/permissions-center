package com.ah.cloud.permissions.biz.infrastructure.mq.redis;

import com.ah.cloud.permissions.biz.application.strategy.cache.impl.RedisCacheHandleStrategy;
import com.ah.cloud.permissions.biz.infrastructure.mq.NewProducer;
import com.ah.cloud.permissions.biz.infrastructure.util.JsonUtils;
import com.ah.cloud.permissions.domain.common.ProducerResult;
import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-05-11 09:40
 **/
@Slf4j
@Component
public abstract class AbstractBaseRedisProducer<T> implements NewProducer<T> {
    @Resource
    protected RedisCacheHandleStrategy redisCacheHandleStrategy;

    @Override
    public ProducerResult<Void> sendMessage(T t) {
        ProducerResult<Void> result = new ProducerResult<>();
        try {
            redisCacheHandleStrategy.publishMessage(getChannel(), t);
            log.info("{} message publish success, msg is [{}]", getChannel(), JsonUtils.toJSONString(t));
            result.setSuccess(true);
        } catch (Throwable throwable) {
            log.error("{} message publish failed, msg is [{}], reason is {}", getChannel(), JsonUtils.toJSONString(t), Throwables.getStackTraceAsString(throwable));
            result.setSuccess(false);
            result.setMsg(throwable.getMessage());
        }
        return result;
    }

    /**
     * 获取redis 发布频道
     * @return
     */
    protected abstract String getChannel();
}
