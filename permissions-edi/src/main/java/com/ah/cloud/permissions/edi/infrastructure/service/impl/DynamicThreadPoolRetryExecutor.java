package com.ah.cloud.permissions.edi.infrastructure.service.impl;

import com.ah.cloud.permissions.biz.application.manager.ThreadPoolManager;
import com.ah.cloud.permissions.biz.infrastructure.util.DateUtils;
import com.ah.cloud.permissions.biz.infrastructure.util.JsonUtils;
import com.ah.cloud.permissions.edi.domain.common.EdiResult;
import com.ah.cloud.permissions.edi.domain.record.model.RetryBizRecord;
import com.ah.cloud.permissions.edi.infrastructure.config.RetryConfiguration;
import com.ah.cloud.permissions.edi.infrastructure.service.RetryExecutor;
import com.ah.cloud.permissions.edi.infrastructure.service.RetryService;
import com.ah.cloud.permissions.enums.edi.EdiTypeEnum;
import com.google.common.collect.Maps;
import io.netty.util.HashedWheelTimer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import javax.annotation.Resource;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @program: permissions-center
 * @description: 动态线程池重试执行器
 * <p>
 * 适合资源竞争不激烈，但是数据量很⼤对吞吐量要求
 * 很⾼的业务，任务并发执⾏。内部提供了两个多线程线程池:
 * <p>
 * 1. retrySlowThreadPool负责执⾏⽐较耗时的类型的任务 （默认是30分钟内执⾏耗时超过1S的次数超过10次被定义为慢类型）
 * 2. retryFastThreadPool负责执⾏耗时低的类型的任务，进⼀步提⾼了EDI的重试的整体的吞吐量。
 * @author: YuKai Fan
 * @create: 2022-07-06 09:57
 **/
@Slf4j
@Component
public class DynamicThreadPoolRetryExecutor implements RetryExecutor {

    @Resource
    private RetryService retryService;
    @Resource
    private RetryConfiguration retryConfiguration;

    private volatile HashedWheelTimer timer;

    private final ConcurrentMap<Integer, AtomicInteger> retrySlowCountMap = Maps.newConcurrentMap();

    @Override
    public EdiResult<Void> doRetry(RetryBizRecord retryBizRecord, EdiTypeEnum ediTypeEnum) {
        initTimer();
        ThreadPoolExecutor threadPoolExecutor = select(retryBizRecord.getBizType());
        threadPoolExecutor.execute(() -> {
            StopWatch stopWatch = new StopWatch("doRetry Dynamic 耗时统计");
            retryService.doRetry(retryBizRecord, ediTypeEnum);
            stopWatch.stop();
            if (stopWatch.getTotalTimeMillis() > retryConfiguration.getRunTime()) {
                int retryCount = 1;
                AtomicInteger timeoutCount = retrySlowCountMap.putIfAbsent(retryBizRecord.getBizType(), new AtomicInteger(retryCount));
                if (timeoutCount != null) {
                    retryCount = timeoutCount.incrementAndGet();
                }
                log.info("DynamicThreadPoolRetryExecutor[doRetry] retry slow, params is {}, ediType is {}, cost is {}, retryCount is {}"
                        , JsonUtils.toJsonString(retryBizRecord)
                        , ediTypeEnum
                        , stopWatch.getTotalTimeMillis()
                        , retryCount);
            }
        });
        return EdiResult.ofSuccess();
    }

    @Override
    public boolean getExecutor(boolean force, boolean parallel) {
        return (!force) && parallel;
    }


    /**
     * 初始化时间轮
     */
    private void initTimer() {
        if (timer == null) {
            timer = new HashedWheelTimer(Executors.defaultThreadFactory(), 100, TimeUnit.MILLISECONDS, 1024, false);
            timer.newTimeout(timeout -> {
                log.info("TimeTask run retrySlowCountMap clear, now : {}", DateUtils.getCurrentDateTime());
                retrySlowCountMap.clear();
            }, 1200000, TimeUnit.MILLISECONDS);
        }
    }

    /**
     * 动态选择线程池
     *
     * 如果当前业务类型的处理时间超时次数超过阈值, 则设置为慢线程池处理
     *
     * @param bizType
     * @return
     */
    private ThreadPoolExecutor select(Integer bizType) {
        AtomicInteger retrySlowCount = retrySlowCountMap.getOrDefault(bizType, new AtomicInteger(0));
        if (retrySlowCount.get() > retryConfiguration.getHourOverTimes()) {
            return ThreadPoolManager.retrySlowThreadPool;
        } else {
            return ThreadPoolManager.retryFastThreadPool;
        }
    }
}
