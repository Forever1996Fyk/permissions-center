package com.ah.cloud.permissions.biz.application.strategy.cache;

import org.springframework.beans.factory.InitializingBean;

import java.util.Collection;

/**
 * @program: permissions-center
 * @description: 缓存处理策略
 * @author: YuKai Fan
 * @create: 2022-01-19 17:54
 **/
public interface CacheHandleStrategy extends InitializingBean {

    /**
     * 缓存数据
     * @param key
     * @param value
     * @param <T>
     * @return
     */
    <T> boolean setCacheObject(final String key, final T value);

    /**
     * 获取数据
     * @param key
     * @param <T>
     * @return
     */
    <T> T getCacheObject(final String key);

    /**
     * 删除数据
     * @param key
     * @return
     */
    boolean deleteObject(final String key);

    /**
     * 批量删除数据
     * @param collection
     * @param <T>
     * @return
     */
    long deleteBatchObject(final Collection<String> collection);


    /**
     * 根据key判断是否存在
     *
     * @param key
     * @return
     */
    boolean existByKey(String key);
}
