package com.ah.cloud.permissions.biz.application.strategy.facroty;

import com.ah.cloud.permissions.biz.application.strategy.cache.CacheHandleStrategy;
import com.ah.cloud.permissions.biz.infrastructure.exception.CacheHandleStrategyException;
import com.ah.cloud.permissions.enums.RepositoryModeEnum;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Objects;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-01-19 18:04
 **/
@Slf4j
public class CacheHandleStrategyFactory {
    private static Map<RepositoryModeEnum, CacheHandleStrategy> FACTORY = Maps.newConcurrentMap();
    private static final String EXCEPTION_MSG_NOT_SUPPORT = "缓存处理策略不支持";
    private static final String EXCEPTION_MSG_NULL = "缓存处理策略为空";
    private static final String EXCEPTION_MSG_REPEATED = "缓存处理策略已存在";

    /**
     * 注册策略
     *
     * @see com.ah.cloud.permissions.enums.RepositoryModeEnum repositoryModeEnum
     * @param strategy
     */
    public static void register(RepositoryModeEnum repositoryModeEnum, CacheHandleStrategy strategy) {
        if (Objects.isNull(repositoryModeEnum)) {
            throw new CacheHandleStrategyException(EXCEPTION_MSG_NOT_SUPPORT);
        }
        if (Objects.isNull(strategy)) {
            throw new CacheHandleStrategyException(EXCEPTION_MSG_NULL);
        }

        if (FACTORY.containsKey(repositoryModeEnum)) {
            throw new CacheHandleStrategyException(EXCEPTION_MSG_REPEATED);
        }
        log.info("订单消息变更策略注册, 类型为{},", repositoryModeEnum);
        FACTORY.put(repositoryModeEnum, strategy);
    }

    /**
     * 查找对应策略
     *
     * @see com.ah.cloud.permissions.enums.RepositoryModeEnum repositoryModeEnum
     *
     * @return
     */
    public static CacheHandleStrategy getStrategy(RepositoryModeEnum repositoryModeEnum) {
        if (Objects.isNull(repositoryModeEnum)) {
            throw new CacheHandleStrategyException(EXCEPTION_MSG_NOT_SUPPORT);
        }

        CacheHandleStrategy cacheHandleStrategy = FACTORY.get(repositoryModeEnum);
        if (Objects.isNull(cacheHandleStrategy)) {
            throw new CacheHandleStrategyException(EXCEPTION_MSG_NULL);
        }
        return cacheHandleStrategy;
    }
}
