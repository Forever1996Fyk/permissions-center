package com.ah.cloud.permissions.biz.infrastructure.repository.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 线程池监控数据
 * </p>
 *
 * @author auto_generation
 * @since 2022-04-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("thread_pool_data")
public class ThreadPoolData implements Serializable {

    private static final long serialVersionUID = 1L;

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

    /**
     * 分表字段【年月，例如202103】
     */
    private Integer shardingKey;

    /**
     * 扩展字段
     */
    private String ext;

    /**
     * 描述
     */
    private String remark;

    /**
     * 版本号
     */
    @Version
    private Integer version;

    /**
     * 创建人
     */
    private String creator;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新人
     */
    private String modifier;

    /**
     * 修改时间
     */
    private Date modifyTime;

    /**
     * 是否删除
     */
    private Long deleted;


}
