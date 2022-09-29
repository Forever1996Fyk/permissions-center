package com.ah.cloud.permissions.biz.infrastructure.sequence;

import com.ah.cloud.permissions.biz.infrastructure.sequence.task.SequenceInitTask;
import lombok.NoArgsConstructor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

/**
 * @program: permissions-center
 * @description: 异步加载器
 * @author: YuKai Fan
 * @create: 2022/9/16 15:21
 **/
@NoArgsConstructor
public class DefaultAsyncSequenceLoader implements AsyncSequenceLoader<String> {

    private final static int DEFAULT_SEQUENCE_CACHE_SIZE = 100;
    private final static int DEFAULT_SEQUENCE_THREAD_SIZE = 10;
    /**
     * 队列阈值
     */
    private int sequenceCacheSize = DEFAULT_SEQUENCE_CACHE_SIZE;

    /**
     * 这块目前使用newCachedThreadPool，如果业务并发极端情况下服务器性能下架，
     * 可能会产生问题，但是如果限制线程数可能影响序列号产生性能，这块需要综合考虑，目前没有问题
     */
    private final ExecutorService executorService = Executors.newCachedThreadPool();


    /**
     * 线程数
     */
    private int sequenceThreadSize = DEFAULT_SEQUENCE_THREAD_SIZE;

    public DefaultAsyncSequenceLoader(int sequenceCacheSize, int sequenceThreadSize) {
        this.sequenceCacheSize = sequenceCacheSize;
        this.sequenceThreadSize = sequenceThreadSize;
    }

    @Override
    public void load(String type) {
        int threadSize = sequenceCacheSize / sequenceThreadSize;
        IntStream.range(0, sequenceThreadSize)
                .forEach(i -> executorService.submit(new SequenceInitTask(
                        threadSize
                        , type
                        , null
                        , null
                )));
    }
}
