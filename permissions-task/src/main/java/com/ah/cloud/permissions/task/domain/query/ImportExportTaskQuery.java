package com.ah.cloud.permissions.task.domain.query;

import com.ah.cloud.permissions.domain.common.PageQuery;
import lombok.Data;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-14 14:27
 **/
@Data
public class ImportExportTaskQuery extends PageQuery {

    /**
     * 任务类型
     */
    private Integer taskType;

    /**
     * 业务类型
     */
    private String bizType;

    /**
     * 任务状态
     */
    private Integer taskStatus;

    /**
     * 任务开始时间
     */
    private String taskStartTime;

    /**
     * 任务结束时间
     */
    private String taskEndTime;

}
