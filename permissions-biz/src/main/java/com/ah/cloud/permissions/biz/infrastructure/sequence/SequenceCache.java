package com.ah.cloud.permissions.biz.infrastructure.sequence;

import com.google.common.collect.Queues;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.Semaphore;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022/9/16 15:14
 **/
@Slf4j
public class SequenceCache<K, V> {

    private static final int NUMBER_ZERO = 0;

    private static final int NUMBER_ONE = 1;

    /**
     * 内存队列
     * <p>
     * ConcurrentLinkedDeque是一个双向队列
     */
    private final ConcurrentHashMap<K, ConcurrentLinkedDeque<V>> queueMap = new ConcurrentHashMap<>();

    /**
     * 计数信号量
     */
    private final ConcurrentHashMap<K, Semaphore> queueCounterMap = new ConcurrentHashMap<>();

    /**
     * 同步加载器
     */
    private final SyncSequenceLoader<? super K, V> syncLoader;

    /**
     * 异步加载器
     */
    private final AsyncSequenceLoader<? super K> asyncLoader;

    /**
     * 构造序列缓存
     *
     * @param keyList
     * @param syncLoader
     * @param asyncLoader
     */
    public SequenceCache(List<K> keyList, SyncSequenceLoader<? super K, V> syncLoader, AsyncSequenceLoader<? super K> asyncLoader) {
        this.syncLoader = syncLoader;
        this.asyncLoader = asyncLoader;
        keyList.forEach(type -> {
            queueCounterMap.putIfAbsent(type, new Semaphore(NUMBER_ZERO));
            ConcurrentLinkedDeque<V> queue = new ConcurrentLinkedDeque<>();
            queueMap.putIfAbsent(type, queue);
        });
    }

    /**
     * 获取序列
     *
     * @param type
     * @return
     */
    public V getSequence(K type) {
        Semaphore semaphore = queueCounterMap.get(type);
        if (semaphore != null && semaphore.tryAcquire(NUMBER_ONE)) {
            // 队首出列 获取队列首部第一个元素
            V cacheSequence = queueMap.get(type).poll();
            if (cacheSequence != null) {
                return cacheSequence;
            }
        }
        V sequence = syncLoader.load(type);
        log.warn("SequenceCache[getSequence] use syncLoader, type is {}, result is {}", type, sequence);

        asyncLoader.load(type);
        log.warn("SequenceCache[getSequence] use asyncLoader, type is {}", type);
        return sequence;
    }

    /**
     * 添加序列到队列
     * @param type
     * @param sequence
     */
    public void addSequence(K type, V sequence) {
        queueMap.get(type).add(sequence);
        queueCounterMap.get(type).release(NUMBER_ONE);
    }
}
