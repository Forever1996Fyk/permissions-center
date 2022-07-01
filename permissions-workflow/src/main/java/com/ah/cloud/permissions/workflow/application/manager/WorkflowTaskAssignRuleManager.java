package com.ah.cloud.permissions.workflow.application.manager;

import com.ah.cloud.permissions.biz.application.service.WorkflowTaskAssignRuleService;
import com.ah.cloud.permissions.biz.infrastructure.exception.BizException;
import com.ah.cloud.permissions.biz.infrastructure.repository.bean.WorkflowTaskAssignRule;
import com.ah.cloud.permissions.enums.common.DeletedEnum;
import com.ah.cloud.permissions.enums.common.WorkflowErrorCodeEnum;
import com.ah.cloud.permissions.enums.workflow.TaskAssignRuleTypeEnum;
import com.ah.cloud.permissions.workflow.application.helper.WorkflowTaskAssignRuleHelper;
import com.ah.cloud.permissions.workflow.application.service.ProcessDefinitionService;
import com.ah.cloud.permissions.workflow.application.strategy.TaskCandidateService;
import com.ah.cloud.permissions.workflow.application.strategy.selector.TaskCandidateServiceSelector;
import com.ah.cloud.permissions.workflow.domain.task.form.AssignRuleTaskAddForm;
import com.ah.cloud.permissions.workflow.domain.task.form.AssignRuleTaskUpdateForm;
import com.ah.cloud.permissions.workflow.domain.task.vo.AssignRuleTaskVo;
import com.ah.cloud.permissions.workflow.domain.task.vo.SelectAssignRuleOptionsVo;
import com.ah.cloud.permissions.workflow.infrastructure.util.FlowableUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.bpmn.model.UserTask;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-06-20 17:58
 **/
@Slf4j
@Component
public class WorkflowTaskAssignRuleManager {
    /**
     * 这里用懒加载的原因是为了解决循环依赖!!!
     *
     * {@link com.ah.cloud.permissions.workflow.infrastructure.config.FlowableConfiguration#processEngineConfigurationEngineConfigurationConfigurer}
     *
     * 循环依赖关系如下
     *
     * SpringProcessEngineConfiguration
     * -> WorkflowBehaviorFactory
     * -> WorkflowTaskAssignRuleManager
     * -> ProcessDefinitionService
     * -> RepositoryService
     * -> SpringProcessEngineConfiguration
     *
     * RepositoryServiceImpl 继承了CommonEngineServiceImpl, 而CommonEngineServiceImpl是依赖于SpringProcessEngineConfiguration
     *
     * 所以这个直接对 ProcessDefinitionService 使用懒加载
     *
     * 懒加载原理就是, 生成的ProcessDefinitionService bean其实是一个ProcessDefinitionService的代理对象, WorkflowTaskAssignRuleManager依赖的其实是代理对象
     */
    @Lazy
    @Resource
    private ProcessDefinitionService processDefinitionService;
    @Resource
    private TaskCandidateServiceSelector taskCandidateServiceSelector;
    @Resource
    private WorkflowTaskAssignRuleHelper workflowTaskAssignRuleHelper;
    @Resource
    private WorkflowTaskAssignRuleService workflowTaskAssignRuleService;

    /**
     * 获取处理人规则
     *
     * @param modelId
     * @param processDefinitionId
     * @return
     */
    public List<AssignRuleTaskVo> listAssignRuleTaskVos(String modelId, String processDefinitionId) {
        List<WorkflowTaskAssignRule> workflowTaskAssignRuleList = workflowTaskAssignRuleService.list(
                new QueryWrapper<WorkflowTaskAssignRule>().lambda()
                        .eq(StringUtils.isNotBlank(modelId), WorkflowTaskAssignRule::getModelId, modelId)
                        .eq(StringUtils.isNotBlank(processDefinitionId), WorkflowTaskAssignRule::getProcessDefinitionId, processDefinitionId)
                        .eq(WorkflowTaskAssignRule::getDeleted, DeletedEnum.NO.value)
        );
        BpmnModel bpmnModel = null;
        if (StringUtils.isNotBlank(modelId)) {
            bpmnModel = processDefinitionService.getBpmnModel(modelId);
        } else if (StringUtils.isNotBlank(processDefinitionId)) {
            bpmnModel = processDefinitionService.getBpmnModelByProcessDefinitionId(processDefinitionId);
        }
        if (Objects.isNull(bpmnModel) || CollectionUtils.isEmpty(workflowTaskAssignRuleList)) {
            return Collections.emptyList();
        }

        // 获取用户任务, 只有用户任务才能设置分配规则
        List<UserTask> userTaskList = FlowableUtils.getBpmnModelElements(bpmnModel, UserTask.class);
        if (CollectionUtils.isEmpty(userTaskList)) {
            return Collections.emptyList();
        }
        return workflowTaskAssignRuleHelper.convertRuleTaskVoList(userTaskList, workflowTaskAssignRuleList);
    }

    /**
     * 创建任务规则
     *
     * @param form
     * @return
     */
    public Long createAssignRuleTask(AssignRuleTaskAddForm form) {
        WorkflowTaskAssignRule existedWorkflowTaskAssignRule = workflowTaskAssignRuleService.getOne(
                new QueryWrapper<WorkflowTaskAssignRule>().lambda()
                        .eq(WorkflowTaskAssignRule::getModelId, form.getModelId())
                        .eq(WorkflowTaskAssignRule::getTaskDefinitionKey, form.getTaskDefinitionKey())
                        .eq(WorkflowTaskAssignRule::getDeleted, DeletedEnum.NO.value)
        );
        if (Objects.nonNull(existedWorkflowTaskAssignRule)) {
            throw new BizException(WorkflowErrorCodeEnum.WORKFLOW_TASK_ASSIGN_RULE_REPEAT);
        }
        WorkflowTaskAssignRule workflowTaskAssignRule = workflowTaskAssignRuleHelper.convert(form);
        workflowTaskAssignRuleService.save(workflowTaskAssignRule);
        return workflowTaskAssignRule.getId();
    }

    /**
     * 更新任务规则
     *
     * @param form
     */
    public void updateAssignRuleTask(AssignRuleTaskUpdateForm form) {
        WorkflowTaskAssignRule existedWorkflowTaskAssignRule = workflowTaskAssignRuleService.getOne(
                new QueryWrapper<WorkflowTaskAssignRule>().lambda()
                        .eq(WorkflowTaskAssignRule::getModelId, form.getId())
                        .eq(WorkflowTaskAssignRule::getDeleted, DeletedEnum.NO.value)
        );
        if (Objects.isNull(existedWorkflowTaskAssignRule)) {
            throw new BizException(WorkflowErrorCodeEnum.WORKFLOW_TASK_ASSIGN_RULE_REPEAT);
        }
        WorkflowTaskAssignRule updateWorkflowTaskAssignRule = workflowTaskAssignRuleHelper.convert(form);
        updateWorkflowTaskAssignRule.setId(existedWorkflowTaskAssignRule.getId());
        workflowTaskAssignRuleService.updateById(updateWorkflowTaskAssignRule);
    }

    /**
     * 校验任务规则是否存在
     *
     * @param modelId
     */
    public void checkModelTaskAssignRuleConfig(String modelId) {
        BpmnModel bpmnModel = processDefinitionService.getBpmnModel(modelId);
        if (Objects.isNull(bpmnModel)) {
            log.error("WorkflowTaskAssignRuleManager[checkModelTaskAssignRuleConfig] check task assign rule config failed, reason is bpmnModel is null, modelId is {}", modelId);
            throw new BizException(WorkflowErrorCodeEnum.WORKFLOW_PROCESS_MODEL_BPMN_NOT_EXISTED);
        }
        // 获取用户任务, 只有用户任务才能设置分配规则
        List<UserTask> userTaskList = FlowableUtils.getBpmnModelElements(bpmnModel, UserTask.class);
        if (CollectionUtils.isEmpty(userTaskList)) {
            log.warn("WorkflowTaskAssignRuleManager[checkModelTaskAssignRuleConfig] current model have not userTask, modelId is {}", modelId);
            return;
        }
        List<WorkflowTaskAssignRule> workflowTaskAssignRuleList = workflowTaskAssignRuleService.list(
                new QueryWrapper<WorkflowTaskAssignRule>().lambda()
                        .eq(WorkflowTaskAssignRule::getModelId, modelId)
                        .eq(WorkflowTaskAssignRule::getDeleted, DeletedEnum.NO.value)
        );
        if (CollectionUtils.isEmpty(workflowTaskAssignRuleList)) {
            throw new BizException(WorkflowErrorCodeEnum.WORKFLOW_PROCESS_MODEL_HAVE_NOT_TASK_RULE);
        }

        // 判断规则配置是否正确
        boolean present = workflowTaskAssignRuleList.stream()
                .anyMatch(workflowTaskAssignRule -> StringUtils.isBlank(workflowTaskAssignRule.getOptions()));
        if (present) {
            throw new BizException(WorkflowErrorCodeEnum.WORKFLOW_PROCESS_MODEL_DEPLOY_FAILED_TASK_RULE_NOT_CONFIG);
        }
    }

    /**
     * 根据模型id获取流程规则, 并转换为最新的流程定义id对应的任务规则
     *
     * @param fromModelId
     * @param newProcessDefinitionId
     */
    public void copyTaskAssignRuleByProcessDefinitionId(String fromModelId, String newProcessDefinitionId) {
        List<WorkflowTaskAssignRule> workflowTaskAssignRuleList = workflowTaskAssignRuleService.list(
                new QueryWrapper<WorkflowTaskAssignRule>().lambda()
                        .eq(WorkflowTaskAssignRule::getModelId, fromModelId)
                        .eq(WorkflowTaskAssignRule::getDeleted, DeletedEnum.NO.value)
        );
        if (CollectionUtils.isEmpty(workflowTaskAssignRuleList)) {
            throw new BizException(WorkflowErrorCodeEnum.WORKFLOW_PROCESS_MODEL_HAVE_NOT_TASK_RULE);
        }
        List<WorkflowTaskAssignRule> copyNewWorkflowTaskAssignRuleList =  WorkflowTaskAssignRuleHelper.convertTaskRuleByNewProcessDefinitionId(workflowTaskAssignRuleList, newProcessDefinitionId);
        workflowTaskAssignRuleService.saveBatch(copyNewWorkflowTaskAssignRuleList);
    }

    /**
     * 根据流程定义id和任务定义key获取任务处理人规则
     *
     * @param processDefinitionId
     * @param taskDefinitionKey
     * @return
     */
    public WorkflowTaskAssignRule findOneTaskAssignRulesByProcessDefinitionIdAndTaskKey(String processDefinitionId, String taskDefinitionKey) {
       return workflowTaskAssignRuleService.getOne(
                new QueryWrapper<WorkflowTaskAssignRule>().lambda()
                        .eq(WorkflowTaskAssignRule::getProcessDefinitionId, processDefinitionId)
                        .eq(WorkflowTaskAssignRule::getTaskDefinitionKey, taskDefinitionKey)
                        .eq(WorkflowTaskAssignRule::getDeleted, DeletedEnum.NO.value)
        );
    }


    /**
     * 获取选择配置
     * @param ruleTypeEnum
     * @return
     */
    public List<SelectAssignRuleOptionsVo> listSelectAssignRuleOptions(TaskAssignRuleTypeEnum ruleTypeEnum) {
        TaskCandidateService taskCandidateService = taskCandidateServiceSelector.select(ruleTypeEnum);
        return taskCandidateService.listSelectOptions();
    }
}
