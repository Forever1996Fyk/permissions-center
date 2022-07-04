package com.ah.cloud.permissions.workflow.infrastructure.behavior.task;

import cn.hutool.core.util.RandomUtil;
import com.ah.cloud.permissions.biz.infrastructure.repository.bean.WorkflowTaskAssignRule;
import com.ah.cloud.permissions.biz.infrastructure.util.JsonUtils;
import com.ah.cloud.permissions.enums.workflow.TaskAssignRuleTypeEnum;
import com.ah.cloud.permissions.workflow.application.manager.WorkflowTaskAssignRuleManager;
import com.ah.cloud.permissions.workflow.application.strategy.TaskCandidateService;
import com.ah.cloud.permissions.workflow.application.strategy.selector.TaskCandidateServiceSelector;
import com.ah.cloud.permissions.workflow.domain.behavior.dto.UserTaskAssignRuleDTO;
import com.ah.cloud.permissions.workflow.domain.candidate.dto.GetTaskCandidateDTO;
import com.ah.cloud.permissions.workflow.domain.candidate.dto.TaskCandidateDTO;
import com.ah.cloud.permissions.workflow.infrastructure.constant.ProcessVariableConstants;
import lombok.extern.slf4j.Slf4j;
import org.flowable.bpmn.model.UserTask;
import org.flowable.common.engine.api.FlowableException;
import org.flowable.common.engine.impl.el.ExpressionManager;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.impl.bpmn.behavior.UserTaskActivityBehavior;
import org.flowable.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.flowable.engine.impl.util.TaskHelper;
import org.flowable.task.service.TaskService;
import org.flowable.task.service.impl.persistence.entity.TaskEntity;

import java.util.List;
import java.util.Objects;

/**
 * @program: permissions-center
 * @description: 用户任务行为, 自定义用户任务节点, 这里只做对用户任务处理人的设置做自定义chuli
 * @author: YuKai Fan
 * @create: 2022-06-30 16:05
 **/
@Slf4j
public class WorkflowUserTaskActivityBehavior extends UserTaskActivityBehavior {

    private TaskCandidateServiceSelector taskCandidateServiceSelector;
    private WorkflowTaskAssignRuleManager workflowTaskAssignRuleManager;

    public WorkflowUserTaskActivityBehavior(UserTask userTask, TaskCandidateServiceSelector taskCandidateServiceSelector, WorkflowTaskAssignRuleManager workflowTaskAssignRuleManager) {
        this(userTask);
        this.taskCandidateServiceSelector = taskCandidateServiceSelector;
        this.workflowTaskAssignRuleManager = workflowTaskAssignRuleManager;
    }

    public WorkflowUserTaskActivityBehavior(UserTask userTask) {
        super(userTask);
    }

    /**
     * 重写 用户任务 获取处理人的方法
     * 根据TaskAssigneeRule 获取不同的业务处理人
     *
     * @param taskService
     * @param assignee
     * @param owner
     * @param candidateUsers
     * @param candidateGroups
     * @param task
     * @param expressionManager
     * @param execution
     * @param processEngineConfiguration
     */
    @Override
    protected void handleAssignments(TaskService taskService, String assignee, String owner, List<String> candidateUsers, List<String> candidateGroups, TaskEntity task, ExpressionManager expressionManager, DelegateExecution execution, ProcessEngineConfigurationImpl processEngineConfiguration) {
        // 根据流程定义id与任务key获取对应的用户任务规则
        UserTaskAssignRuleDTO userTaskAssignRuleDTO = getTaskRule(task);
        // 根据规则获取任务的候选人
        TaskCandidateService taskCandidateService = taskCandidateServiceSelector.select(userTaskAssignRuleDTO.getTaskAssignRuleTypeEnum());
        GetTaskCandidateDTO getTaskCandidateDTO = GetTaskCandidateDTO.builder().task(task).userTaskAssignRuleDTO(userTaskAssignRuleDTO).build();
        TaskCandidateDTO taskCandidate = taskCandidateService.getTaskCandidate(getTaskCandidateDTO);
        TaskCandidateDTO.Candidate chooseAssignee = taskCandidate.getSelectedAssignee();
        task.setVariable(ProcessVariableConstants.ASSIGNEE, chooseAssignee.getName());
        task.setVariable(ProcessVariableConstants.ASSIGNEE_ID, chooseAssignee.getUserId());
        TaskHelper.changeTaskAssignee(task, String.valueOf(chooseAssignee.getUserId()));
    }


    /**
     * 获取任务规则
     * @param taskEntity
     * @return
     */
    private UserTaskAssignRuleDTO getTaskRule(TaskEntity taskEntity) {
        WorkflowTaskAssignRule workflowTaskAssignRule = workflowTaskAssignRuleManager.findOneTaskAssignRulesByProcessDefinitionIdAndTaskKey(taskEntity.getProcessDefinitionId(), taskEntity.getTaskDefinitionKey());
        if (Objects.isNull(workflowTaskAssignRule)) {
            throw new FlowableException(String.format("流程任务找不到符合的任务规则, taskId is [%s], processDefinitionId is [%s], taskDefinitionKey is [%s]"
                    , taskEntity.getId()
                    , taskEntity.getProcessDefinitionId()
                    , taskEntity.getTaskDefinitionKey()));
        }
        return UserTaskAssignRuleDTO.builder()
                .id(workflowTaskAssignRule.getId())
                .taskDefinitionId(workflowTaskAssignRule.getTaskDefinitionId())
                .modelId(workflowTaskAssignRule.getModelId())
                .processDefinitionId(workflowTaskAssignRule.getProcessDefinitionId())
                .processDefinitionKey(workflowTaskAssignRule.getProcessDefinitionKey())
                .taskDefinitionKey(workflowTaskAssignRule.getTaskDefinitionKey())
                .options(JsonUtils.jsonToSet(workflowTaskAssignRule.getOptions(), String.class))
                .tenantId(workflowTaskAssignRule.getTenantId())
                .taskAssignRuleTypeEnum(TaskAssignRuleTypeEnum.getByType(workflowTaskAssignRule.getType()))
                .build();
    }


}
