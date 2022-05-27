package com.ah.cloud.permissions.biz.application.manager;

import com.ah.cloud.permissions.biz.infrastructure.threadpool.ResizeLinkedBlockingQueue;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @program: permissions-center
 * @description: 线程池管理
 * @author: YuKai Fan
 * @create: 2022-04-20 22:52
 **/
public class ThreadPoolManager {

    /**
     * 默认队列大小 1000
     */
    private static final Integer DEFAULT_QUEUE_SIZE = Integer.MAX_VALUE;


    /**
     * 更新用户缓存信息线程池
     * updateUserThreadPool
     */
    public static ThreadPoolExecutor updateUserThreadPool = new ThreadPoolExecutor(
            5,
            10,
            60L,
            TimeUnit.SECONDS,
            new ResizeLinkedBlockingQueue<>(DEFAULT_QUEUE_SIZE),
            r -> new Thread(r, "UpdateUserThreadPool-" + r.hashCode()));


    /**
     * 记录任务调度线程池
     * 单线程顺序执行
     * updateUserThreadPool
     */
    public static ThreadPoolExecutor recordQuartzJobThreadPool = new ThreadPoolExecutor(
            1,
            1,
            0L,
            TimeUnit.MILLISECONDS,
            new ResizeLinkedBlockingQueue<>(DEFAULT_QUEUE_SIZE),
            r -> new Thread(r, "RecordQuartzJobThreadPool-" + r.hashCode()));

    /**
     * im节点监听线程线程池
     */
    public static ThreadPoolExecutor imNodeListenerThreadPool = new ThreadPoolExecutor(
            1,
            1,
            0L,
            TimeUnit.MILLISECONDS,
            new ResizeLinkedBlockingQueue<>(DEFAULT_QUEUE_SIZE),
            r -> new Thread(r, "imNodeListenerThreadPool-" + r.hashCode()));

    /**
     * 消息存储线程池
     */
    public static ThreadPoolExecutor messageStoreThreadPool = new ThreadPoolExecutor(
            30,
            50,
            60L,
            TimeUnit.SECONDS,
            new ResizeLinkedBlockingQueue<>(DEFAULT_QUEUE_SIZE),
            r -> new Thread(r, "messageStoreThreadPool-" + r.hashCode()));

    /**
     * 离线消息存储线程池
     */
    public static ThreadPoolExecutor offlineMessageStoreThreadPool = new ThreadPoolExecutor(
            30,
            50,
            60L,
            TimeUnit.SECONDS,
            new ResizeLinkedBlockingQueue<>(DEFAULT_QUEUE_SIZE),
            r -> new Thread(r, "offlineMessageStoreThreadPool-" + r.hashCode()));
}
