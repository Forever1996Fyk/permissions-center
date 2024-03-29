package com.ah.cloud.permissions.biz.application.manager;

import com.ah.cloud.permissions.biz.infrastructure.threadpool.ResizeLinkedBlockingQueue;

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

    /**
     * 消息推送线程池
     */
    public static ThreadPoolExecutor pushMsgThreadPool = new ThreadPoolExecutor(
            30,
            50,
            60L,
            TimeUnit.SECONDS,
            new ResizeLinkedBlockingQueue<>(DEFAULT_QUEUE_SIZE),
            r -> new Thread(r, "pushMsgThreadPool-" + r.hashCode()));

    /**
     * 错误日志告警
     */
    public static ThreadPoolExecutor errorLogAlarmPool = new ThreadPoolExecutor(
            30,
            50,
            60L,
            TimeUnit.SECONDS,
            new ResizeLinkedBlockingQueue<>(DEFAULT_QUEUE_SIZE),
            r -> new Thread(r, "errorLogAlarmPool-" + r.hashCode()));

    /**
     * 重试线程池
     */
    public static ThreadPoolExecutor retryThreadPool = new ThreadPoolExecutor(
            20,
            20,
            0L,
            TimeUnit.SECONDS,
            new ResizeLinkedBlockingQueue<>(DEFAULT_QUEUE_SIZE),
            r -> new Thread(r, "retryThread-" + r.hashCode()));

    /**
     * 慢线程处理
     */
    public static ThreadPoolExecutor retrySlowThreadPool = new ThreadPoolExecutor(
            10,
            20,
            60L,
            TimeUnit.SECONDS,
            new ResizeLinkedBlockingQueue<>(20000),
            r -> new Thread(r, "retrySlowThread-" + r.hashCode()),
            new ThreadPoolExecutor.CallerRunsPolicy());

    /**
     * 快线程处理
     */
    public static ThreadPoolExecutor retryFastThreadPool = new ThreadPoolExecutor(
            10,
            20,
            60L,
            TimeUnit.SECONDS,
            new ResizeLinkedBlockingQueue<>(20000),
            r -> new Thread(r, "retryFastThread-" + r.hashCode()),
            new ThreadPoolExecutor.CallerRunsPolicy());

    /**
     * 重试单个线程
     */
    public static ThreadPoolExecutor retrySingleThreadPool = new ThreadPoolExecutor(
            1,
            1,
            60L,
            TimeUnit.SECONDS,
            new ResizeLinkedBlockingQueue<>(20000),
            r -> new Thread(r, "retrySingleThread" + r.hashCode()),
            new ThreadPoolExecutor.CallerRunsPolicy());

    /**
     * 导入导出，按照逻辑数据源列表处理
     */
    public static ThreadPoolExecutor importExportTaskLogicThreadPool = new ThreadPoolExecutor(
            2,
            2,
            0L,
            TimeUnit.SECONDS,
            new ResizeLinkedBlockingQueue<Runnable>(DEFAULT_QUEUE_SIZE),
            r -> new Thread(r, "ImportExportTaskLogicThreadPool-" + r.hashCode()));

    /**
     * 查询出待执行导入导出任务之后，放入线程池执行任务
     */
    public static ThreadPoolExecutor exportTaskThreadPool = new ThreadPoolExecutor(
            5,
            5,
            0L,
            TimeUnit.SECONDS,
            new ResizeLinkedBlockingQueue<Runnable>(DEFAULT_QUEUE_SIZE),
            r -> new Thread(r, "ExportTaskThreadPool-" + r.hashCode()));

    /**
     * 查询出待执行导入导出任务之后，放入线程池执行任务
     */
    public static ThreadPoolExecutor importTaskThreadPool = new ThreadPoolExecutor(
            10,
            10,
            0L,
            TimeUnit.SECONDS,
            new ResizeLinkedBlockingQueue<Runnable>(DEFAULT_QUEUE_SIZE),
            r -> new Thread(r, "ImportTaskThreadPool-" + r.hashCode()));

}
