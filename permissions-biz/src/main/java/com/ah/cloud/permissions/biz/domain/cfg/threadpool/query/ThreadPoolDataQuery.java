package com.ah.cloud.permissions.biz.domain.cfg.threadpool.query;

import com.ah.cloud.permissions.domain.common.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-04-27 16:24
 **/
@Data
@EqualsAndHashCode(callSuper = true)
public class ThreadPoolDataQuery extends PageQuery {

    /**
     * 线程池名称
     */
    private String name;

    /**
     * 客户端
     */
    private String client;
    /**
     * 开始时间
     */
    @NotEmpty(message = "开始时间不能为空")
    private String startTime;

    /**
     * 结束时间
     */
    @NotEmpty(message = "结束时间不能为空")
    private String endTime;
}
