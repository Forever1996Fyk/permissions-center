package com.ah.cloud.permissions.workflow.application.manager;

import com.ah.cloud.permissions.biz.application.manager.UserAuthManager;
import com.ah.cloud.permissions.biz.application.service.WorkflowFormProcessService;
import com.ah.cloud.permissions.biz.application.service.ext.WorkflowFormModelExtService;
import com.ah.cloud.permissions.biz.domain.user.dto.RoleDTO;
import com.ah.cloud.permissions.biz.domain.user.dto.UserScopeInfoDTO;
import com.ah.cloud.permissions.biz.infrastructure.exception.BizException;
import com.ah.cloud.permissions.biz.infrastructure.repository.bean.WorkflowBusinessTaskForm;
import com.ah.cloud.permissions.biz.infrastructure.repository.bean.WorkflowFormModel;
import com.ah.cloud.permissions.biz.infrastructure.repository.bean.WorkflowFormProcess;
import com.ah.cloud.permissions.biz.infrastructure.util.CollectionUtils;
import com.ah.cloud.permissions.biz.infrastructure.util.DateUtils;
import com.ah.cloud.permissions.biz.infrastructure.util.JsonUtils;
import com.ah.cloud.permissions.domain.common.PageResult;
import com.ah.cloud.permissions.enums.common.DeletedEnum;
import com.ah.cloud.permissions.enums.common.ErrorCodeEnum;
import com.ah.cloud.permissions.enums.workflow.WorkflowErrorCodeEnum;
import com.ah.cloud.permissions.workflow.application.helper.ProcessTaskHelper;
import com.ah.cloud.permissions.workflow.application.service.ProcessTaskService;
import com.ah.cloud.permissions.workflow.domain.task.dto.CompleteTaskDTO;
import com.ah.cloud.permissions.workflow.domain.task.form.CompleteTaskForm;
import com.ah.cloud.permissions.workflow.domain.task.query.MyTaskQuery;
import com.ah.cloud.permissions.workflow.domain.task.vo.DoneTaskVo;
import com.ah.cloud.permissions.workflow.domain.task.vo.TaskFormVo;
import com.ah.cloud.permissions.workflow.domain.task.vo.ToDoTaskVo;
import com.ah.cloud.permissions.workflow.domain.task.vo.detail.BaseTaskInfo;
import com.ah.cloud.permissions.workflow.domain.task.vo.detail.TaskAssigneeInfo;
import com.ah.cloud.permissions.workflow.domain.task.vo.detail.TaskFormInfo;
import com.ah.cloud.permissions.workflow.domain.task.vo.detail.TaskInstanceDetailVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.flowable.task.api.Task;
import org.flowable.task.api.history.HistoricTaskInstance;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-06-17 10:25
 **/
@Slf4j
@Component
public class WorkflowTaskManager {
    @Resource
    private UserAuthManager userAuthManager;
    @Resource
    private ProcessTaskHelper processTaskHelper;
    @Resource
    private ProcessTaskService processTaskService;
    @Resource
    private WorkflowTaskFormManager workflowTaskFormManager;
    @Resource
    private WorkflowFormProcessService workflowFormProcessService;
    @Resource
    private WorkflowFormModelExtService workflowFormModelExtService;

    /**
     * 分页获取当前用户人的待办列表
     *
     * @param query
     * @return
     */
    public PageResult<ToDoTaskVo> pageCurrentUserToDoTaskList(MyTaskQuery query) {
        ImmutablePair<Long, List<Task>> pair = processTaskService.listPageAndCount(query);
        return processTaskHelper.convertToPageResult(pair.getRight(), query, pair.getLeft());
    }

    /**
     * 分页获取当前用户人的已办列表
     *
     * @param query
     * @return
     */
    public PageResult<DoneTaskVo> pageCurrentUserDoneTaskList(MyTaskQuery query) {
        ImmutablePair<Long, List<HistoricTaskInstance>> pair = processTaskService.listHisPageAndCount(query);
        return processTaskHelper.convertToDonePageResult(pair.getRight(), query, pair.getLeft());
    }

    /**
     * 根据任务key获取关联的任务表单
     *
     * @param processDefinitionId
     * @param taskKey
     * @return
     */
    public TaskFormVo findTaskFormByTaskKey(String processDefinitionId, String taskKey) {
        if (StringUtils.isBlank(taskKey)) {
            throw new BizException(ErrorCodeEnum.PARAM_MISS, "任务定义key");
        }
        if (StringUtils.isBlank(processDefinitionId)) {
            throw new BizException(ErrorCodeEnum.PARAM_MISS, "流程id");
        }
        WorkflowFormProcess workflowFormProcess = workflowFormProcessService.getOne(
                new QueryWrapper<WorkflowFormProcess>().lambda()
                        .select(WorkflowFormProcess::getFormCode, WorkflowFormProcess::getWorkflowProcessId)
                        .eq(WorkflowFormProcess::getProcessDefinitionId, processDefinitionId)
                        .eq(WorkflowFormProcess::getTaskKey, taskKey)
                        .eq(WorkflowFormProcess::getDeleted, DeletedEnum.NO.value)
        );
        TaskFormVo taskFormVo = TaskFormVo.builder()
                .workflowProcessId(workflowFormProcess.getWorkflowProcessId())
                .processDefinitionId(processDefinitionId)
                .build();
        if (Objects.nonNull(workflowFormProcess)) {
            WorkflowFormModel workflowFormModel = workflowFormModelExtService.findOneFormModelByCode(workflowFormProcess.getFormCode(), workflowFormProcess.getTenantId());
            taskFormVo.setOptions(JsonUtils.jsonToList(workflowFormModel.getContent(), String.class));
            taskFormVo.setFormCode(workflowFormModel.getCode());
            taskFormVo.setConfig(workflowFormModel.getConfig());
        }
        return taskFormVo;
    }

    /**
     * 完成任务
     * <p>
     * 完成任务时, 通过监听器更新 流程信息, 添加任务表单信息
     *
     * @param form
     */
    @Transactional(rollbackFor = Exception.class)
    public void completeTask(CompleteTaskForm form) {
        Task task = processTaskService.getOneByTaskId(form.getTaskId());
        if (Objects.isNull(task)) {
            log.error("ProcessTaskManager[completeTask] handle task failed, reason task not existed, params is {}", JsonUtils.toJSONString(form));
            throw new BizException(WorkflowErrorCodeEnum.WORKFLOW_TASK_NOT_EXISTED);
        }
        // 完成任务
        CompleteTaskDTO completeTaskDTO = CompleteTaskDTO.builder()
                .taskId(form.getTaskId())
                .commentType(form.getCommentType())
                .comment(form.getComment())
                .processInstanceId(form.getProcessInstanceId())
                .build();
        processTaskService.completeTask(completeTaskDTO);
    }

    /**
     * 获取流程任务详情列表
     *
     * @param processInstanceId
     * @return
     */
    public List<TaskInstanceDetailVo> listProcessDetailTaskByProcessInstanceId(String processInstanceId) {
        List<HistoricTaskInstance> historicTaskInstanceList = processTaskService.listHisTaskOrderByHistoricTaskInstanceStartTimeDesc(processInstanceId);
        List<TaskInstanceDetailVo> taskInstanceDetailVoList = Lists.newArrayList();
        if (CollectionUtils.isEmpty(historicTaskInstanceList)) {
            return taskInstanceDetailVoList;
        }
        Map<String, WorkflowBusinessTaskForm> taskFormMap = workflowTaskFormManager.getTaskFormMapByProcessInstanceId(processInstanceId);
        Set<Long> userIdSet = CollectionUtils.convertSet(historicTaskInstanceList, HistoricTaskInstance::getAssignee)
                        .stream().map(Long::valueOf).collect(Collectors.toSet());
        List<UserScopeInfoDTO> userScopeInfoList = userAuthManager.listUserRoleInfoByUserIds(userIdSet);
        Map<Long, UserScopeInfoDTO> userScopeInfoMap = CollectionUtils.convertMap(userScopeInfoList, UserScopeInfoDTO::getUserId);
        for (HistoricTaskInstance historicTaskInstance : historicTaskInstanceList) {
            BaseTaskInfo baseTaskInfo = BaseTaskInfo.builder()
                    .durationInMillis(historicTaskInstance.getDurationInMillis())
                    .endTime(DateUtils.format(historicTaskInstance.getEndTime()))
                    .startTime(DateUtils.format(historicTaskInstance.getClaimTime()))
                    .taskDefinitionKey(historicTaskInstance.getTaskDefinitionKey())
                    .taskId(historicTaskInstance.getTaskDefinitionId())
                    .taskName(historicTaskInstance.getName())
                    .build();

            TaskInstanceDetailVo taskInstanceDetailVo = TaskInstanceDetailVo.builder().baseTaskInfo(baseTaskInfo).build();

            WorkflowBusinessTaskForm workflowBusinessTaskForm = taskFormMap.get(historicTaskInstance.getTaskDefinitionKey());
            if (Objects.nonNull(workflowBusinessTaskForm)) {
                TaskFormInfo taskFormInfo = TaskFormInfo.builder()
                        .formCode(workflowBusinessTaskForm.getCode())
                        .formType(workflowBusinessTaskForm.getFormType())
                        .config(workflowBusinessTaskForm.getConfig())
                        .options(JsonUtils.jsonToList(workflowBusinessTaskForm.getContent(), String.class))
                        .variables(JsonUtils.stringToMap(workflowBusinessTaskForm.getVariables()))
                        .build();
                taskInstanceDetailVo.setTaskFormInfo(taskFormInfo);
            }
            UserScopeInfoDTO userScopeInfoDTO = userScopeInfoMap.get(Long.valueOf(historicTaskInstance.getAssignee()));
            if (Objects.nonNull(userScopeInfoDTO)) {
                TaskAssigneeInfo taskAssigneeInfo = TaskAssigneeInfo.builder()
                        .assigneeName(userScopeInfoDTO.getNickName())
                        .deptName(userScopeInfoDTO.getDeptDTO().getDeptName())
                        .roleNameSet(CollectionUtils.convertSet(userScopeInfoDTO.getRoleSet(), RoleDTO::getRoleName))
                        .build();
                taskInstanceDetailVo.setTaskAssigneeInfo(taskAssigneeInfo);
            }
            taskInstanceDetailVoList.add(taskInstanceDetailVo);
        }
        return taskInstanceDetailVoList;
    }

    /**
     * 根据流程实例id获取当前活跃的任务节点
     *
     * @param processInstanceId
     * @return
     */
    public Task getCurrentActiveTaskNode(String processInstanceId) {
        return processTaskService.getCurrentActiveTask(processInstanceId);
    }
}
