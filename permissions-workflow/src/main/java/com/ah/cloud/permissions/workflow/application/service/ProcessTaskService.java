package com.ah.cloud.permissions.workflow.application.service;

import com.ah.cloud.permissions.workflow.domain.task.dto.CompleteTaskDTO;
import com.ah.cloud.permissions.workflow.domain.task.query.MyTaskQuery;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.flowable.task.api.Task;
import org.flowable.task.api.history.HistoricTaskInstance;

import java.util.List;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-06-30 14:26
 **/
public interface ProcessTaskService {

    /**
     * 根据任务id获取任务
     * @param taskId
     * @return
     */
    Task getOneByTaskId(String taskId);

    /**
     * 获取当前活跃的任务
     * @param processInstanceId
     * @return
     */
    Task getCurrentActiveTask(String processInstanceId);

    /**
     * 完成任务
     * @param completeTaskDTO
     */
    void completeTask(CompleteTaskDTO completeTaskDTO);

    /**
     * 分页查询任务列表
     * @param query
     * @return
     */
    ImmutablePair<Long, List<Task>> listPageAndCount(MyTaskQuery query);

    /**
     * 历史任务实例
     * @param query
     * @return
     */
    ImmutablePair<Long, List<HistoricTaskInstance>> listHisPageAndCount(MyTaskQuery query);

    /**
     * 根据流程实例获取历史任务实例
     * @param processInstanceId
     * @return
     */
    List<HistoricTaskInstance> listHisTaskOrderByHistoricTaskInstanceStartTimeDesc(String processInstanceId);

    /**
     * 统计任务数
     *
     * @param query
     * @return
     */
    Long count(MyTaskQuery query);
}
