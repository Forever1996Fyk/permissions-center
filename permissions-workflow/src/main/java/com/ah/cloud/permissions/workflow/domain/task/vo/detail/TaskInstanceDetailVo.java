package com.ah.cloud.permissions.workflow.domain.task.vo.detail;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-06-26 19:03
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskInstanceDetailVo {

    /**
     * 基础任务信息
     */
    private BaseTaskInfo baseTaskInfo;

    /**
     * 任务处理人信息
     */
    private TaskAssigneeInfo taskAssigneeInfo;

    /**
     * 任务表单信息
     */
    private TaskFormInfo taskFormInfo;

}
