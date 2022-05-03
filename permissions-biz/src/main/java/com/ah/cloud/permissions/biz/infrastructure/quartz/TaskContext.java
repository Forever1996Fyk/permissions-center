package com.ah.cloud.permissions.biz.infrastructure.quartz;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-05-03 08:49
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskContext {

    /**
     * 定时任务id
     */
    private Long jobId;

    /**
     * 执行任务id
     */
    private Long taskId;

    /**
     * 任务参数
     */
    private String jobParams;

    /**
     * 扩展字段
     */
    private Map<String, Object> ext;
}
