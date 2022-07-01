package com.ah.cloud.permissions.workflow.application.manager;

import com.ah.cloud.permissions.biz.application.service.ext.*;
import com.ah.cloud.permissions.biz.infrastructure.exception.BizException;
import com.ah.cloud.permissions.biz.infrastructure.repository.bean.*;
import com.ah.cloud.permissions.biz.infrastructure.util.CollectionUtils;
import com.ah.cloud.permissions.biz.infrastructure.util.JsonUtils;
import com.ah.cloud.permissions.biz.infrastructure.util.SecurityUtils;
import com.ah.cloud.permissions.enums.common.DeletedEnum;
import com.ah.cloud.permissions.enums.common.WorkflowErrorCodeEnum;
import com.ah.cloud.permissions.workflow.application.helper.WorkflowTaskFormHelper;
import com.ah.cloud.permissions.workflow.domain.business.vo.BusinessVariablesFormVo;
import com.ah.cloud.permissions.workflow.infrastructure.constant.WorkflowConstants;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.impl.persistence.entity.ExecutionEntity;
import org.flowable.task.service.impl.persistence.entity.TaskEntity;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @program: permissions-center
 * @description: 工作流任务表单管理
 * @author: YuKai Fan
 * @create: 2022-06-26 16:55
 **/
@Slf4j
@Component
public class WorkflowTaskFormManager {
    @Resource
    private WorkflowTaskFormHelper workflowTaskFormHelper;
    @Resource
    private WorkflowProcessExtService workflowProcessExtService;
    @Resource
    private WorkflowBusinessExtService workflowBusinessExtService;
    @Resource
    private WorkflowFormModelExtService workflowFormModelExtService;
    @Resource
    private WorkflowFormProcessExtService workflowFormProcessExtService;
    @Resource
    private WorkflowBusinessTaskFormExtService workflowBusinessTaskFormExtService;

    /**
     * 添加流程任务表单
     *
     * 因为表单可以被修改, 但是已经执行过的任务是无法修改的, 所以任务关联的表单在执行后需要把表单信息冗余到表中
     * @param taskEntity
     */
    public void addWorkflowTaskForm(TaskEntity taskEntity) {
        if (Objects.isNull(taskEntity)) {
            log.warn("WorkflowTaskFormManager[addWorkflowTaskForm] add task form to DB failed, reason is task null");
            return;
        }

        String processDefinitionId = taskEntity.getProcessDefinitionId();
        String tenantId = taskEntity.getTenantId();
        WorkflowProcess workflowProcess = workflowProcessExtService.findOneProcessByDefinitionId(processDefinitionId, tenantId);

        WorkflowFormProcess workflowFormProcess = workflowFormProcessExtService.findOneByProcessIdAndTaskKey(workflowProcess.getId(), taskEntity.getTaskDefinitionKey(), tenantId);
        WorkflowFormModel formModel = workflowFormModelExtService.findOneFormModelByCode(workflowFormProcess.getFormCode(), tenantId);

        WorkflowBusiness workflowBusiness = workflowBusinessExtService.findOneByProcessInstanceId(taskEntity.getProcessInstanceId(), taskEntity.getTenantId());
        WorkflowBusinessTaskForm workflowBusinessTaskForm = workflowTaskFormHelper.buildTaskForm(taskEntity, workflowBusiness, formModel);
        workflowBusinessTaskFormExtService.save(workflowBusinessTaskForm);
    }

    /**
     * 添加流程表单
     * @param entity
     */
    public void addWorkflowProcessForm(ExecutionEntity entity) {
        if (Objects.isNull(entity)) {
            log.warn("WorkflowTaskFormManager[addWorkflowProcessForm] add task form to DB failed, reason is ExecutionEntity nul");
            return;
        }
        String processDefinitionId = entity.getProcessDefinitionId();
        String processInstanceId = entity.getProcessInstanceId();
        String tenantId = entity.getTenantId();
        Long userId = SecurityUtils.getUserIdBySession();
        WorkflowProcess workflowProcess = workflowProcessExtService.findOneProcessByDefinitionId(processDefinitionId, tenantId);

        WorkflowFormProcess workflowFormProcess = workflowFormProcessExtService.findOneByProcessId(workflowProcess.getId(), tenantId);
        WorkflowFormModel formModel = workflowFormModelExtService.findOneFormModelByCode(workflowFormProcess.getFormCode(), tenantId);

        WorkflowBusiness workflowBusiness = workflowBusinessExtService.findOneByProcessInstanceId(processInstanceId, tenantId);
        WorkflowBusinessTaskForm workflowBusinessTaskForm = workflowTaskFormHelper.buildProcessForm(entity, workflowBusiness, formModel);
        workflowBusinessTaskFormExtService.save(workflowBusinessTaskForm);
    }

    /**
     * 获取task form map
     *
     * key : taskDefinitionKey
     * @param processInstanceId
     * @return
     */
    public Map<String, WorkflowBusinessTaskForm> getTaskFormMapByProcessInstanceId(String processInstanceId) {
        List<WorkflowBusinessTaskForm> workflowBusinessTaskFormList = workflowBusinessTaskFormExtService.list(
                new QueryWrapper<WorkflowBusinessTaskForm>().lambda()
                        .eq(WorkflowBusinessTaskForm::getProcessInstanceId, processInstanceId)
                        .eq(WorkflowBusinessTaskForm::getDeleted, DeletedEnum.NO.value)
        );
        return CollectionUtils.convertMap(workflowBusinessTaskFormList, WorkflowBusinessTaskForm::getTaskKey);
    }

    /**
     * 获取业务流程
     * @param businessId
     * @return
     */
    public BusinessVariablesFormVo findBusinessVariablesFormByBusinessId(Long businessId, String tenantId) {
        Long userId = SecurityUtils.getUserIdBySession();
        WorkflowBusiness workflowBusiness = workflowBusinessExtService.getOne(
                new QueryWrapper<WorkflowBusiness>().lambda()
                        .eq(WorkflowBusiness::getId, businessId)
                        .eq(WorkflowBusiness::getProposerId, userId)
                        .eq(WorkflowBusiness::getDeleted, DeletedEnum.NO.value)
        );
        if (Objects.isNull(workflowBusiness)) {
            throw new BizException(WorkflowErrorCodeEnum.WORKFLOW_BUSINESS_NOT_EXISTED);
        }
        WorkflowBusinessTaskForm workflowBusinessTaskForm = workflowBusinessTaskFormExtService.findOneByTaskKeyAndBusinessId(WorkflowConstants.DEFAULT_FORM_TASK_KEY, businessId, tenantId);
        if (Objects.isNull(workflowBusinessTaskForm)) {
            return null;
        }
        return BusinessVariablesFormVo.builder()
                .formCode(workflowBusinessTaskForm.getCode())
                .variables(JsonUtils.stringToMap(workflowBusinessTaskForm.getVariables()))
                .config(workflowBusinessTaskForm.getConfig())
                .options(JsonUtils.jsonToList(workflowBusinessTaskForm.getContent(), String.class))
                .build();
    }

    /**
     * 获取业务流程
     * @param processInstanceId
     * @param tenantId
     * @return
     */
    public BusinessVariablesFormVo findBusinessVariablesFormByInstanceId(String processInstanceId, String tenantId) {
        WorkflowBusiness workflowBusiness = workflowBusinessExtService.findOneByProcessInstanceId(processInstanceId, tenantId);
        if (Objects.isNull(workflowBusiness)) {
            throw new BizException(WorkflowErrorCodeEnum.WORKFLOW_BUSINESS_NOT_EXISTED);
        }
        WorkflowBusinessTaskForm workflowBusinessTaskForm = workflowBusinessTaskFormExtService.findOneByTaskKeyAndBusinessId(WorkflowConstants.DEFAULT_FORM_TASK_KEY, workflowBusiness.getId(), tenantId);
        if (Objects.isNull(workflowBusinessTaskForm)) {
            return null;
        }
        return BusinessVariablesFormVo.builder()
                .formCode(workflowBusinessTaskForm.getCode())
                .variables(JsonUtils.stringToMap(workflowBusinessTaskForm.getVariables()))
                .config(workflowBusinessTaskForm.getConfig())
                .options(JsonUtils.jsonToList(workflowBusinessTaskForm.getContent(), String.class))
                .build();
    }
}
