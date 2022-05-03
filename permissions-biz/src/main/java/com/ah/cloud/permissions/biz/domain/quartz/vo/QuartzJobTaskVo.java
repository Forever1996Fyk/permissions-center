package com.ah.cloud.permissions.biz.domain.quartz.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-05-03 23:22
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuartzJobTaskVo {
    /**
     * 主键
     */
    private Long id;

    /**
     * 任务id
     */
    private Long jobId;

    /**
     * 错误信息
     */
    private String errorMessage;

    /**
     * 开始时间
     */
    private Date startTime;

    /**
     * 结束时间
     */
    private Date endTime;

    /**
     * 状态(1: 执行成功, 2: 执行中, 3: 执行失败)
     */
    private Integer status;
}
