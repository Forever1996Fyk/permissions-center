package com.ah.cloud.permissions.biz.domain.cfg.threadpool.form;

import com.ah.cloud.permissions.biz.infrastructure.annotation.EnumValid;
import com.ah.cloud.permissions.enums.BlockingQueueEnum;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-04-27 15:20
 **/
@Data
public class CfgThreadPoolBaseForm {

    /**
     * 线程池名称
     */
    @NotEmpty(message = "线程池名称不能为空")
    private String name;

    /**
     * 核心线程数
     */
    @NotNull(message = "核心线程数不能为空")
    @Max(value = 100, message = "核心线程数不能超过100")
    @Min(value = 5, message = "核心线程数不能低于5")
    private Integer corePoolSize;

    /**
     * 最大线程数
     */
    @NotNull(message = "最大线程数不能为空")
    @Max(value = Integer.MAX_VALUE, message = "核心线程数超过Integer最大限制")
    @Min(value = 10, message = "最大线程数不能低于10")
    private Integer maximumPoolSize;

    /**
     * 存活时间
     */
    @NotNull(message = "存活时间不能为空")
    private Long keepAliveTime;

    /**
     * 队列类型
     */
    @EnumValid(message = "队列类型不正确", enumClass = BlockingQueueEnum.class, enumMethod = "isValid")
    private String queueType;

    /**
     * 存放带执行任务的队列大小
     */
    @NotNull(message = "队列大小不能为空")
    private Integer workQueueSize;

    /**
     * 扩展字段
     */
    private String ext;

    /**
     * 描述
     */
    private String remark;
}
