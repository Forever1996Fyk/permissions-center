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
import org.apache.poi.ss.formula.functions.T;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.Duration;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
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
        return (T) redisTemplate.opsForValue().get(key);
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
     * 根据key获取所有的hash value
     * @param key
     * @param <T>
     * @return
     */
    public <T> List<T> getCacheHashValues(final String key) {
        if (StringUtils.isEmpty(key)) {
            throw new BizException(RedisErrorCodeEnum.OPS_KEY_NOT_NULL);
        }
        return Optional
                .ofNullable((List<T>) redisTemplate.opsForHash().values(key))
                .orElseThrow(() -> new BizException(RedisErrorCodeEnum.OPS_GET_VALUE_ERROR));
    }

    /**
     * 删除hash值
     * @param key
     * @param hkey
     * @return
     */
    public Long deleteCacheHashByKey(final String key, final String hkey) {
        return redisTemplate.opsForHash().delete(key, hkey);
    }

    /**
     * 移除hash列表
     * @param key
     * @return
     */
    public Boolean removeCacheHashByKey(final String key) {
        return redisTemplate.opsForHash().getOperations().delete(key);
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

    /**
     * list push
     * @param key
     * @param value
     * @param <T>
     * @return
     */
    public <T> Long lpush(String key, T value) {
        return redisTemplate.opsForList().leftPush(key, value);
    }

    /**
     * list right pop
     * @param key
     * @param duration 如果没有弹出数据，则阻塞 duration时长
     * @param <T>
     * @return
     */
    public <T> T brpop(String key, Duration duration) {
        return (T) redisTemplate.opsForList().rightPop(key, duration);
    }

    /**
     * redis set集合添加
     *
     * @param key
     * @param value
     * @param <T>
     */
    public <T> Long setAdd(String key, T value) {
        return redisTemplate.opsForSet().add(key, value);
    }

    /**
     * redis set集合移除
     *
     * @param key
     * @param value
     * @return
     */
    public <T> Long setRemove(String key, T value) {
        return redisTemplate.opsForSet().remove(key, value);
    }

    /**
     * 根据key获取set集合
     *
     * @param key
     * @param <T>
     * @return
     */
    public <T> Set<T> getSet(String key) {
        return (Set<T>) redisTemplate.opsForSet().members(key);
    }

    /**
     * 获取set集合大小
     * @param key
     * @param <T>
     * @return
     */
    public <T> Long getSetSize(String key) {
        return redisTemplate.opsForSet().size(key);
    }

    /**
     * 判断set 值是否存在
     *
     * @param key
     * @param value
     * @param <T>
     * @return
     */
    public <T> boolean setExisted(String key, T value) {
        return redisTemplate.opsForSet().isMember(key, value);
    }

}
