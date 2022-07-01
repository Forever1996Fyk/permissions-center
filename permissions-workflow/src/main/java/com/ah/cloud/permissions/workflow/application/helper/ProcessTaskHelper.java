package com.ah.cloud.permissions.workflow.application.helper;

import com.ah.cloud.permissions.biz.infrastructure.constant.PermissionsConstants;
import com.ah.cloud.permissions.biz.infrastructure.util.DateUtils;
import com.ah.cloud.permissions.biz.infrastructure.util.SecurityUtils;
import com.ah.cloud.permissions.domain.common.PageResult;
import com.ah.cloud.permissions.workflow.domain.task.query.MyTaskQuery;
import com.ah.cloud.permissions.workflow.domain.task.vo.DoneTaskVo;
import com.ah.cloud.permissions.workflow.domain.task.vo.ToDoTaskVo;
import com.ah.cloud.permissions.workflow.infrastructure.constant.ProcessVariableConstants;
import com.ah.cloud.permissions.workflow.infrastructure.util.FlowableUtils;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.flowable.task.api.Task;
import org.flowable.task.api.TaskQuery;
import org.flowable.task.api.history.HistoricTaskInstance;
import org.flowable.task.api.history.HistoricTaskInstanceQuery;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-06-17 18:03
 **/
@Component
public class ProcessTaskHelper {

    /**
     * 构建任务查询条件
     *
     * @param taskQuery
     * @param query
     */
    public void buildTaskQuery(TaskQuery taskQuery, MyTaskQuery query) {
        taskQuery.active()
                .includeProcessVariables()
                .orderByTaskCreateTime().desc();
        if (query.isGroup()) {
            // 获取当前用户的部门 todo
            Long deptId = 0L;
            taskQuery.taskCandidateGroup(String.valueOf(deptId));
        } else {
            Long currentUserId = SecurityUtils.getUserIdBySession();
            taskQuery.taskAssignee(String.valueOf(currentUserId));
        }
    }

    /**
     * 构建已办任务查询条件
     *
     * @param historicTaskInstanceQuery
     * @param query
     */
    public void buildHistoricTaskQuery(HistoricTaskInstanceQuery historicTaskInstanceQuery, MyTaskQuery query) {
        historicTaskInstanceQuery.includeProcessVariables()
                .finished()
                .orderByHistoricTaskInstanceEndTime()
                .desc();

        if (query.isGroup()) {
            // 获取当前用户的部门 todo
            Long deptId = 0L;
            historicTaskInstanceQuery.taskCandidateGroup(String.valueOf(deptId));
        } else {
            Long currentUserId = SecurityUtils.getUserIdBySession();
            historicTaskInstanceQuery.taskAssignee(String.valueOf(currentUserId));
        }
    }

    /**
     * 数据转换
     *
     * @param taskList
     * @param query
     * @param total
     * @return
     */
    public PageResult<ToDoTaskVo> convertToPageResult(List<Task> taskList, MyTaskQuery query, long total) {
        List<ToDoTaskVo> list = this.convertToDoTaskVoList(taskList, query);
        PageResult<ToDoTaskVo> pageResult = new PageResult<>();
        pageResult.setPageSize(query.getPageSize());
        pageResult.setPageNum(query.getPageNum());
        pageResult.setTotal(total);
        pageResult.setRows(list);
        return pageResult;
    }

    /**
     * 数据转换
     *
     * @param historicTaskList
     * @param query
     * @param total
     * @return
     */
    public PageResult<DoneTaskVo> convertToDonePageResult(List<HistoricTaskInstance> historicTaskList, MyTaskQuery query, long total) {
        List<DoneTaskVo> list = this.convertDoneTaskVoList(historicTaskList, query);
        PageResult<DoneTaskVo> pageResult = new PageResult<>();
        pageResult.setPageSize(query.getPageSize());
        pageResult.setPageNum(query.getPageNum());
        pageResult.setTotal(total);
        pageResult.setRows(list);
        return pageResult;
    }

    /**
     * 数据转换
     *
     * @param historicTaskList
     * @param query
     * @return
     */
    public List<DoneTaskVo> convertDoneTaskVoList(List<HistoricTaskInstance> historicTaskList, MyTaskQuery query) {
        Set<Long> taskSponsorIdSet = query.getTaskSponsorIdSet();
        List<DoneTaskVo> list = Lists.newArrayList();
        for (HistoricTaskInstance historicTaskInstance : historicTaskList) {
            ImmutableTriple<String, Long, String> variablesNameAndProposerId = FlowableUtils.getVariablesNameAndProposerId(historicTaskInstance.getProcessVariables());
            String processName = variablesNameAndProposerId.getLeft();
            Long proposerId = variablesNameAndProposerId.getMiddle();
            String proposer = variablesNameAndProposerId.getRight();
            // 根据条件过滤对应的流程名称
            if (isSupportQueryConditions(query, processName, taskSponsorIdSet, proposerId)) {
                continue;
            }
            DoneTaskVo doneTaskVo = DoneTaskVo.builder()
                    .processDefinitionId(historicTaskInstance.getProcessDefinitionId())
                    .taskId(historicTaskInstance.getId())
                    .processInstanceId(historicTaskInstance.getProcessInstanceId())
                    .taskDefinitionKey(historicTaskInstance.getTaskDefinitionKey())
                    .processName(processName)
                    .proposerId(proposerId)
                    .proposer(proposer)
                    .taskName(historicTaskInstance.getName())
                    .category(historicTaskInstance.getCategory())
                    .build();
            list.add(doneTaskVo);
        }
        return list;
    }

    /**
     * 数据转换
     *
     * @param taskList
     * @param query
     * @return
     */
    public List<ToDoTaskVo> convertToDoTaskVoList(List<Task> taskList, MyTaskQuery query) {
        Set<Long> taskSponsorIdSet = query.getTaskSponsorIdSet();
        List<ToDoTaskVo> list = Lists.newArrayList();
        for (Task task : taskList) {
            ImmutableTriple<String, Long, String> variablesNameAndProposer = FlowableUtils.getVariablesNameAndProposerId(task.getProcessVariables());
            String processName = variablesNameAndProposer.getLeft();
            Long proposerId = variablesNameAndProposer.getMiddle();
            String proposer = variablesNameAndProposer.getRight();
            if (isSupportQueryConditions(query, processName, taskSponsorIdSet, proposerId)) {
                continue;
            }
            ToDoTaskVo toDoTaskVo = ToDoTaskVo.builder()
                    .processDefinitionId(task.getProcessDefinitionId())
                    .taskId(task.getId())
                    .processInstanceId(task.getProcessInstanceId())
                    .taskDefinitionKey(task.getTaskDefinitionKey())
                    .processName(processName)
                    .proposerId(proposerId)
                    .proposer(proposer)
                    .taskName(task.getName())
                    .category(task.getCategory())
                    .startTime(DateUtils.format(task.getCreateTime()))
                    .build();
            list.add(toDoTaskVo);
        }
        return list;
    }

    /**
     * 是否支持查询条件
     * @param query
     * @param processName
     * @param taskSponsorIdSet
     * @param proposerId
     * @return
     */
    private boolean isSupportQueryConditions(MyTaskQuery query, String processName, Set<Long> taskSponsorIdSet, Long proposerId) {
        if (StringUtils.isNotBlank(query.getName()) && !StringUtils.contains(processName, query.getName())) {
            return false;
        }
        // 根据条件过滤流程发起人
        if (!CollectionUtils.isEmpty(taskSponsorIdSet) && !taskSponsorIdSet.contains(proposerId)) {
            return false;
        }
        return true;
    }
}
