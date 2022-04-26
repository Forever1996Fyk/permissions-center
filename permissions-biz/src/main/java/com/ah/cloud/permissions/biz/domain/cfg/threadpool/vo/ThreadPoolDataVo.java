package com.ah.cloud.permissions.biz.domain.cfg.threadpool.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-04-26 10:39
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ThreadPoolDataVo {

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 线程池名称
     */
    private String name;

    /**
     * 客户端
     */
    private String client;

    /**
     * 核心线程数
     */
    private Integer corePoolSize;

    /**
     * 最大线程数
     */
    private Integer maximumPoolSize;

    /**
     * 正在执行任务的大致线程数
     */
    private Integer activeCount;

    /**
     * 当前线程数
     */
    private Integer poolSize;

    /**
     * 历史最大线程数
     */
    private Integer largestPoolSize;

    /**
     * 任务总数
     */
    private Long taskCount;

    /**
     * 任务完成数
     */
    private Long completedTaskCount;

    /**
     * 阻塞队列总容量【总容量=已使用容量+剩余容量】
     */
    private Integer queueCapacity;

    /**
     * 阻塞队列已使用容量
     */
    private Integer queueSize;

    /**
     * 阻塞队列剩余容量
     */
    private Integer queueRemainingCapacity;
}
