package com.ah.cloud.permissions.biz.application.strategy.cache.impl;

import com.ah.cloud.permissions.biz.application.strategy.cache.CacheHandleStrategy;
import com.ah.cloud.permissions.biz.application.strategy.facroty.CacheHandleStrategyFactory;
import com.ah.cloud.permissions.biz.infrastructure.constant.PermissionsConstants;
import com.ah.cloud.permissions.biz.infrastructure.exception.BizException;
import com.ah.cloud.permissions.enums.RepositoryModeEnum;
import com.ah.cloud.permissions.enums.common.ErrorCodeEnum;
import com.ah.cloud.permissions.enums.common.RedisErrorCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-01-19 18:02
 **/
@Slf4j
@Component
public class RedisCacheHandleStrategy implements CacheHandleStrategy {
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public <T> boolean setCacheObject(String key, T value) {
        // 这里不捕获异常, 直接向外抛出
        redisTemplate.opsForValue().set(key, value);
        return true;
    }

    @Override
    public <T> T getCacheObject(String key) {
        return Optional
                .ofNullable((T) redisTemplate.opsForValue().get(key))
                .orElseThrow(() -> new BizException(ErrorCodeEnum.SYSTEM_ERROR));
    }

    @Override
    public boolean deleteObject(String key) {
        return Optional
                .ofNullable(redisTemplate.delete(key))
                .orElse(Boolean.FALSE);
    }

    @Override
    public long deleteBatchObject(Collection<String> collection) {
        return Optional
                .ofNullable(redisTemplate.delete(collection))
                .orElse(PermissionsConstants.ZERO);
    }

    @Override
    public boolean existByKey(String key) {
        return Optional
                .ofNullable(redisTemplate.hasKey(key))
                .orElse(Boolean.FALSE);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        CacheHandleStrategyFactory.register(RepositoryModeEnum.REDIS, this);
    }

    /**
     * 设置过期时间
     *
     * @param key
     * @param timeout 时间
     * @param unit    时间单位
     * @return
     */
    public boolean expire(final String key, final long timeout, final TimeUnit unit) {
        return Optional
                .ofNullable(redisTemplate.expire(key, timeout, unit))
                .orElse(Boolean.FALSE);
    }

    /**
     * redis存储hash
     *
     * @param key
     * @param hKey
     * @param value
     * @param <T>
     */
    public <T> void setCacheHashValue(final String key, final String hKey, final T value) {
        redisTemplate.opsForHash().put(key, hKey, value);
    }

    /**
     * redis 获取 hash 值
     *
     * @param key
     * @param hKey
     * @param <T>
     * @return
     */
    public <T> T getCacheHashByKey(final String key, final String hKey) {
        if (StringUtils.isEmpty(key)
                || StringUtils.isEmpty(hKey)) {
            throw new BizException(RedisErrorCodeEnum.OPS_KEY_NOT_NULL);
        }
        return Optional
                .ofNullable((T) redisTemplate.opsForHash().get(key, hKey))
                .orElseThrow(() -> new BizException(RedisErrorCodeEnum.OPS_GET_VALUE_ERROR));
    }

    /**
     * 缓存过期时间的基本对象
     *
     * @param key
     * @param value
     * @param timeout
     * @param unit
     * @param <T>
     * @return
     */
    public <T> boolean setCacheObjectByExpire(String key, T value, final long timeout, final TimeUnit unit) {
        // 这里不捕获异常, 直接向外抛出
        redisTemplate.opsForValue().set(key, value, timeout, unit);
        return true;
    }

    /**
     * 缓存过期时间的基本对象
     *
     * @param key
     * @param hKey
     * @param value
     * @param timeout
     * @param unit
     * @param <T>
     * @return
     */
    public <T> boolean setCacheHashByExpire(String key, String hKey, T value, final long timeout, final TimeUnit unit) {
        // 这里不捕获异常, 直接向外抛出
        redisTemplate.opsForHash().put(key, hKey, value);
        this.expire(key, timeout, unit);
        return true;
    }

    /**
     * 根据key获取当前过期剩余时间
     *
     * @param key
     * @return
     */
    public long getExpireTimeByKey(String key) {
        Long expire = redisTemplate.opsForValue().getOperations().getExpire(key);
        return Optional
                .ofNullable(expire)
                .orElse(PermissionsConstants.ZERO);
    }

    /**
     * redis发布消息
     * @param channel
     * @param t
     * @param <T>
     */
    public <T> void publishMessage(String channel, T t) {
        redisTemplate.convertAndSend(channel, t);
    }

}
