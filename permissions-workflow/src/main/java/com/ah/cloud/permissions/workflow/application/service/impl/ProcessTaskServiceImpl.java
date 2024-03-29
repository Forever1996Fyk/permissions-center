package com.ah.cloud.permissions.workflow.application.service.impl;

import com.ah.cloud.permissions.workflow.application.helper.ProcessTaskHelper;
import com.ah.cloud.permissions.workflow.application.service.ProcessTaskService;
import com.ah.cloud.permissions.workflow.domain.task.dto.CompleteTaskDTO;
import com.ah.cloud.permissions.workflow.domain.task.query.MyTaskQuery;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.flowable.engine.HistoryService;
import org.flowable.engine.TaskService;
import org.flowable.task.api.Task;
import org.flowable.task.api.TaskQuery;
import org.flowable.task.api.history.HistoricTaskInstance;
import org.flowable.task.api.history.HistoricTaskInstanceQuery;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-06-30 14:29
 **/
@Slf4j
@Service
public class ProcessTaskServiceImpl implements ProcessTaskService {
    @Resource
    private TaskService taskService;
    @Resource
    private HistoryService historyService;
    @Resource
    private ProcessTaskHelper processTaskHelper;

    @Override
    public Task getOneByTaskId(String taskId) {
        return taskService.createTaskQuery().taskId(taskId).singleResult();
    }

    @Override
    public Task getCurrentActiveTask(String processInstanceId) {
        return taskService.createTaskQuery()
                .processInstanceId(processInstanceId)
                .active()
                .singleResult();
    }

    @Override
    public void completeTask(CompleteTaskDTO completeTaskDTO) {
        // 完成任务
        taskService.complete(completeTaskDTO.getTaskId(), completeTaskDTO.getVariables());
        if (StringUtils.isNotBlank(completeTaskDTO.getComment())) {
            // 更新任务意见
            taskService.addComment(completeTaskDTO.getTaskId(), completeTaskDTO.getProcessInstanceId(), completeTaskDTO.getCommentType(), completeTaskDTO.getComment());
        }
    }

    @Override
    public ImmutablePair<Long, List<Task>> listPageAndCount(MyTaskQuery query) {
        TaskQuery taskQuery = taskService.createTaskQuery();
        // 构建任务查询条件
        processTaskHelper.buildTaskQuery(taskQuery, query);
        List<Task> taskList = taskQuery.listPage((query.getPageNo() - 1) * query.getPageSize(), query.getPageSize());
        return ImmutablePair.of(taskQuery.count(), taskList);
    }

    @Override
    public ImmutablePair<Long, List<HistoricTaskInstance>> listHisPageAndCount(MyTaskQuery query) {
        HistoricTaskInstanceQuery historicTaskInstanceQuery = historyService.createHistoricTaskInstanceQuery();
        // 构建任务查询条件
        processTaskHelper.buildHistoricTaskQuery(historicTaskInstanceQuery, query);
        List<HistoricTaskInstance> historicTaskList = historicTaskInstanceQuery.listPage((query.getPageNo() - 1) * query.getPageSize(), query.getPageSize());
        return ImmutablePair.of(historicTaskInstanceQuery.count(), historicTaskList);
    }

    @Override
    public List<HistoricTaskInstance> listHisTaskOrderByHistoricTaskInstanceStartTimeDesc(String processInstanceId) {
        return historyService.createHistoricTaskInstanceQuery()
                .orderByHistoricTaskInstanceStartTime()
                .desc()
                .processInstanceId(processInstanceId)
                .list();
    }

    @Override
    public Long count(MyTaskQuery query) {
        TaskQuery taskQuery = taskService.createTaskQuery();
        // 构建任务查询条件
        processTaskHelper.buildTaskQuery(taskQuery, query);
        return taskQuery.count();
    }
}
