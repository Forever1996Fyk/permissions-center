package com.ah.cloud.permissions.biz.application.manager;

import java.util.concurrent.LinkedBlockingQueue;
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
            new LinkedBlockingQueue<>(DEFAULT_QUEUE_SIZE),
            r -> new Thread(r, "UpdateUserThreadPool-" + r.hashCode()));
}
