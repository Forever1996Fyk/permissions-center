package com.ah.cloud.permissions.biz.infrastructure.sequence.task;

import com.ah.cloud.permissions.biz.infrastructure.sequence.SequenceCache;
import com.ah.cloud.permissions.biz.infrastructure.sequence.repository.SequenceRepository;
import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.K;

import java.util.stream.IntStream;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022/9/18 10:15
 **/
@Slf4j
public class SequenceInitTask implements Runnable {

    /**
     * 线程数
     */
    private final int threadSize;

    /**
     * 序列类型
     */
    private final String sequenceType;


    /**
     * 序列存储
     */
    private final SequenceRepository sequenceRepository;

    /**
     * 序列存储方式
     */
    private final SequenceCache<String, Long> sequenceCache;

    public SequenceInitTask(int threadSize, String sequenceType, SequenceRepository sequenceRepository, SequenceCache<String, Long> sequenceCache) {
        this.threadSize = threadSize;
        this.sequenceType = sequenceType;
        this.sequenceRepository = sequenceRepository;
        this.sequenceCache = sequenceCache;
    }

    @Override
    public void run() {
        try {
            IntStream.range(0, this.threadSize)
                    .mapToObj(i -> sequenceRepository.getBizSequence(this.sequenceType))
                    .forEachOrdered(orderSequence -> sequenceCache.addSequence(sequenceType, orderSequence.getSequenceId()));
        } catch (Exception e) {
            log.error("SequenceInitTask[run] handle addSequence error, reason is {}, threadId is {}, threadSize is {}, sequenceType is {}"
                    , Throwables.getStackTraceAsString(e)
                    , Thread.currentThread().getId()
                    , threadSize
                    , sequenceType);
        }
    }
}
