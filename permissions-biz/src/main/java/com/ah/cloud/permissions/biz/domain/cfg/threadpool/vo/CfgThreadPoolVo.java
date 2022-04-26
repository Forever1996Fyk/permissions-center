package com.ah.cloud.permissions.biz.domain.cfg.threadpool.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-04-26 10:03
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CfgThreadPoolVo {

    /**
     * 线程池id
     */
    private Long id;

    /**
     * 线程池名称
     */
    private String name;

    /**
     * 核心线程数
     */
    private Integer corePoolSize;

    /**
     * 最大线程数
     */
    private Integer maximumPoolSize;

    /**
     * 存活时间
     */
    private Long keepAliveTime;

    /**
     * 队列类型
     */
    private String queueType;

    /**
     * 存放带执行任务的队列大小
     */
    private Integer workQueueSize;
}
