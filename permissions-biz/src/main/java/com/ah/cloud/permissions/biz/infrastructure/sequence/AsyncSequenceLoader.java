package com.ah.cloud.permissions.biz.infrastructure.sequence;

/**
 * @program: permissions-center
 * @description: 异步加载器
 * @author: YuKai Fan
 * @create: 2022/9/16 15:21
 **/
public interface AsyncSequenceLoader<K> {

    /**
     * 加载
     * @param type
     * @return
     */
    void load(K type);
}
