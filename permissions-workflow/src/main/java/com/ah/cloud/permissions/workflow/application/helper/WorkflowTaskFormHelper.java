package com.ah.cloud.permissions.workflow.application.helper;

import com.ah.cloud.permissions.biz.infrastructure.repository.bean.WorkflowBusiness;
import com.ah.cloud.permissions.biz.infrastructure.repository.bean.WorkflowBusinessTaskForm;
import com.ah.cloud.permissions.biz.infrastructure.repository.bean.WorkflowFormModel;
import com.ah.cloud.permissions.biz.infrastructure.util.JsonUtils;
import com.ah.cloud.permissions.biz.infrastructure.util.SecurityUtils;
import com.ah.cloud.permissions.workflow.infrastructure.constant.WorkflowConstants;
import org.flowable.engine.impl.persistence.entity.ExecutionEntity;
import org.flowable.task.api.Task;
import org.flowable.task.service.impl.persistence.entity.TaskEntity;
import org.springframework.stereotype.Component;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-06-26 17:28
 **/
@Component
public class WorkflowTaskFormHelper {

    /**
     * 构建任务表单
     * @param taskEntity
     * @param workflowBusiness
     * @param formModel
     * @return
     */
    public WorkflowBusinessTaskForm buildTaskForm(TaskEntity taskEntity, WorkflowBusiness workflowBusiness, WorkflowFormModel formModel) {
        WorkflowBusinessTaskForm workflowBusinessTaskForm = new WorkflowBusinessTaskForm();
        workflowBusinessTaskForm.setWorkflowBusinessId(workflowBusiness.getId());
        workflowBusinessTaskForm.setProcessInstanceId(workflowBusiness.getProcessInstanceId());
        workflowBusinessTaskForm.setFormType(formModel.getFormType());
        workflowBusinessTaskForm.setTaskKey(taskEntity.getTaskDefinitionKey());
        workflowBusinessTaskForm.setCode(formModel.getCode());
        workflowBusinessTaskForm.setContent(formModel.getContent());
        workflowBusinessTaskForm.setConfig(formModel.getConfig());
        workflowBusinessTaskForm.setVariables(JsonUtils.toJSONString(taskEntity.getVariables()));
        workflowBusinessTaskForm.setCreator(SecurityUtils.getUserNameBySession());
        workflowBusinessTaskForm.setModifier(SecurityUtils.getUserNameBySession());
        return workflowBusinessTaskForm;
    }

    /**
     * 构建流程表单
     * @param entity
     * @param workflowBusiness
     * @param formModel
     * @return
     */
    public WorkflowBusinessTaskForm buildProcessForm(ExecutionEntity entity, WorkflowBusiness workflowBusiness, WorkflowFormModel formModel) {
        WorkflowBusinessTaskForm workflowBusinessTaskForm = new WorkflowBusinessTaskForm();
        workflowBusinessTaskForm.setWorkflowBusinessId(workflowBusiness.getId());
        workflowBusinessTaskForm.setProcessInstanceId(workflowBusiness.getProcessInstanceId());
        workflowBusinessTaskForm.setFormType(formModel.getFormType());
        workflowBusinessTaskForm.setCode(formModel.getCode());
        workflowBusinessTaskForm.setContent(formModel.getContent());
        workflowBusinessTaskForm.setConfig(formModel.getConfig());
        workflowBusinessTaskForm.setTaskKey(WorkflowConstants.DEFAULT_FORM_TASK_KEY);
        workflowBusinessTaskForm.setVariables(JsonUtils.toJSONString(entity.getVariables()));
        workflowBusinessTaskForm.setCreator(SecurityUtils.getUserNameBySession());
        workflowBusinessTaskForm.setModifier(SecurityUtils.getUserNameBySession());
        return workflowBusinessTaskForm;
    }
}
