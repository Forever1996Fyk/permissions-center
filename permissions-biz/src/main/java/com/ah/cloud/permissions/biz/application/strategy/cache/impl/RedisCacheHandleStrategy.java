package com.ah.cloud.permissions.biz.application.strategy.cache.impl;

import com.ah.cloud.permissions.biz.application.strategy.cache.CacheHandleStrategy;
import com.ah.cloud.permissions.biz.application.strategy.facroty.CacheHandleStrategyFactory;
import com.ah.cloud.permissions.enums.RepositoryModeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-01-19 18:02
 **/
@Slf4j
@Component
public class RedisCacheHandleStrategy implements CacheHandleStrategy {

    @Override
    public <T> boolean setCacheObject(String key, T value) {
        return false;
    }

    @Override
    public <T> T getCacheObject(String key) {
        return null;
    }

    @Override
    public boolean deleteObject(String key) {
        return false;
    }

    @Override
    public <T> long deleteBatchObject(Collection<T> collection) {
        return 0;
    }

    @Override
    public boolean existByKey(String key) {
        return false;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        CacheHandleStrategyFactory.register(RepositoryModeEnum.REDIS, this);
    }
}
