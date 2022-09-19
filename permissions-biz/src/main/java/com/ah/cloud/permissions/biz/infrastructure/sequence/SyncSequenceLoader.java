package com.ah.cloud.permissions.biz.infrastructure.sequence;

/**
 * @program: permissions-center
 * @description: 同步加载器
 * @author: YuKai Fan
 * @create: 2022/9/16 15:21
 **/
public interface SyncSequenceLoader<K, V> {

    /**
     * 加载
     * @param type
     * @return
     */
    V load(K type);
}
