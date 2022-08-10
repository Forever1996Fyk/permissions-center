package com.ah.cloud.permissions.workflow.application.manager;

import com.ah.cloud.permissions.biz.application.service.ext.WorkflowBusinessExtService;
import com.ah.cloud.permissions.biz.application.service.ext.WorkflowProcessExtService;
import com.ah.cloud.permissions.biz.infrastructure.exception.BizException;
import com.ah.cloud.permissions.biz.infrastructure.repository.bean.WorkflowBusiness;
import com.ah.cloud.permissions.biz.infrastructure.repository.bean.WorkflowProcess;
import com.ah.cloud.permissions.biz.infrastructure.util.JsonUtils;
import com.ah.cloud.permissions.biz.infrastructure.util.SecurityUtils;
import com.ah.cloud.permissions.domain.common.PageResult;
import com.ah.cloud.permissions.enums.common.DeletedEnum;
import com.ah.cloud.permissions.enums.workflow.WorkflowErrorCodeEnum;
import com.ah.cloud.permissions.enums.workflow.WorkFlowStatusEnum;
import com.ah.cloud.permissions.workflow.application.helper.WorkflowBusinessHelper;
import com.ah.cloud.permissions.workflow.application.service.ProcessInstanceService;
import com.ah.cloud.permissions.workflow.domain.business.form.WorkflowBusinessStartForm;
import com.ah.cloud.permissions.workflow.domain.business.query.UserSubmitProcessQuery;
import com.ah.cloud.permissions.workflow.domain.business.vo.UserSubmitProcessVo;
import com.ah.cloud.permissions.workflow.domain.instance.dto.StartProcessDTO;
import com.ah.cloud.permissions.workflow.infrastructure.constant.ProcessVariableConstants;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Objects;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-06-17 09:53
 **/
@Slf4j
@Component
public class WorkflowBusinessManager {
    @Resource
    private WorkflowTaskManager workflowTaskManager;
    @Resource
    private WorkflowBusinessHelper workflowBusinessHelper;
    @Resource
    private ProcessInstanceService processInstanceService;
    @Resource
    private WorkflowProcessExtService workflowProcessExtService;
    @Resource
    private WorkflowBusinessExtService workflowBusinessExtService;

    /**
     * 分页查询用户申请的流程
     *
     * @param query
     * @return
     */
    public PageResult<UserSubmitProcessVo> pageUserSubmitProcessVoList(UserSubmitProcessQuery query) {
        PageInfo<WorkflowBusiness> pageInfo = PageMethod.startPage(query.getPageNo(), query.getPageSize())
                .doSelectPageInfo(
                        () -> workflowBusinessExtService.list(
                                new QueryWrapper<WorkflowBusiness>().lambda()
                                        .eq(WorkflowBusiness::getProposerId, SecurityUtils.getUserIdBySession())
                                        .like(
                                                StringUtils.isNotBlank(query.getName()),
                                                WorkflowBusiness::getBusinessName,
                                                query.getName()
                                        )
                                        .eq(
                                                query.getFlowStatus() != null,
                                                WorkflowBusiness::getFlowStatus
                                                , query.getFlowStatus()
                                        )
                                        .ge(
                                                StringUtils.isNotBlank(query.getStartTime()),
                                                WorkflowBusiness::getStartTime,
                                                query.getStartTime()
                                        )
                                        .le(
                                                StringUtils.isNotBlank(query.getEndTime()),
                                                WorkflowBusiness::getEndTime,
                                                query.getEndTime()
                                        )

                        )
                );
        return workflowBusinessHelper.convertToPageResult(pageInfo);
    }


    /**
     * 提交业务流程
     *
     * @param form
     */
    @Transactional(rollbackFor = Exception.class)
    public void submitWorkflowBusiness(WorkflowBusinessStartForm form) {
        WorkflowProcess workflowProcess = workflowProcessExtService.getOne(
                new QueryWrapper<WorkflowProcess>().lambda()
                        .eq(WorkflowProcess::getId, form.getProcessId())
                        .eq(WorkflowProcess::getDeleted, DeletedEnum.NO.value)
        );
        if (Objects.isNull(workflowProcess)) {
            throw new BizException(WorkflowErrorCodeEnum.WORKFLOW_PROCESS_NOT_EXISTED, String.valueOf(form.getProcessId()));
        }
        // 构建业务流程
        String businessName = workflowBusinessHelper.getBusinessName(workflowProcess.getName());
        WorkflowBusiness workflowBusiness = workflowBusinessHelper.buildWorkflowBusiness(form, workflowProcess);
        // 启动流程实例
        Map<String, Object> variables = Maps.newHashMap();
        variables.putAll(form.getVariables());
        variables.put(ProcessVariableConstants.PROCESS_NAME, businessName);
        variables.put(ProcessVariableConstants.PROPOSER, workflowBusiness.getProposer());
        variables.put(ProcessVariableConstants.PROPOSER_ID, workflowBusiness.getProposerId());
        StartProcessDTO startProcessDTO = StartProcessDTO.builder()
                .processDefinitionId(workflowProcess.getProcessDefinitionId())
                .processDefinitionKey(workflowProcess.getProcessDefinitionKey())
                .variables(variables)
                .processName(businessName)
                .businessKey(workflowBusiness.getBusinessKey())
                .build();
        ProcessInstance processInstance = processInstanceService.startProcess(startProcessDTO);
        Task currentActiveTaskNode = workflowTaskManager.getCurrentActiveTaskNode(processInstance.getProcessInstanceId());
        if (Objects.nonNull(currentActiveTaskNode)) {
            workflowBusiness.setTaskId(currentActiveTaskNode.getId());
            workflowBusiness.setTaskName(currentActiveTaskNode.getName());
        }
        // 保存业务流程
        workflowBusiness.setProcessInstanceId(processInstance.getProcessInstanceId());
        workflowBusiness.setBusinessParamJson(JsonUtils.toJSONString(BusinessVariablesManager.buildVariables(currentActiveTaskNode, form.getVariables())));
        boolean saveResult = workflowBusinessExtService.save(workflowBusiness);
        if (!saveResult) {
            throw new BizException(WorkflowErrorCodeEnum.WORKFLOW_BUSINESS_BUILD_FAILED);
        }
    }

    /**
     * 更新流程
     *
     * @param processId
     * @param currentActiveTaskNode
     * @param variables
     * @param flowStatusEnum
     * @return
     */
    public boolean updateWorkflowProcess(Long processId, Task currentActiveTaskNode, Map<String, Object> variables, WorkFlowStatusEnum flowStatusEnum) {
        WorkflowBusiness workflowBusiness = workflowBusinessExtService.getOne(
                new QueryWrapper<WorkflowBusiness>().lambda()
                        .eq(WorkflowBusiness::getId, processId)
                        .eq(WorkflowBusiness::getDeleted, DeletedEnum.NO.value)
        );
        if (Objects.isNull(workflowBusiness)) {
            log.error("WorkflowBusinessManager[updateWorkflowProcess] update workflow process failed, reason is WorkflowBusiness not existed, params is {}", JsonUtils.toJSONString(processId));
            throw new BizException(WorkflowErrorCodeEnum.WORKFLOW_BUSINESS_NOT_EXISTED);
        }
        WorkflowBusiness updateWorkflowBusiness = new WorkflowBusiness();
        updateWorkflowBusiness.setTaskId(currentActiveTaskNode.getId());
        updateWorkflowBusiness.setTaskName(currentActiveTaskNode.getName());
        if (isUpdateFlowStatus(flowStatusEnum, workflowBusiness)) {
            updateWorkflowBusiness.setFlowStatus(flowStatusEnum.getStatus());
        }
        updateWorkflowBusiness.setVersion(workflowBusiness.getVersion() + 1);
        return workflowBusinessExtService.update(
                updateWorkflowBusiness,
                new UpdateWrapper<WorkflowBusiness>().lambda()
                        .eq(WorkflowBusiness::getId, workflowBusiness.getId())
                        .eq(WorkflowBusiness::getVersion, workflowBusiness.getVersion())
                        .ne(WorkflowBusiness::getFlowStatus, WorkFlowStatusEnum.COMPLETE.getStatus())
                        .eq(WorkflowBusiness::getDeleted, DeletedEnum.NO.value)
        );
    }

    /**
     * 根据流程实例id获取当前申请人id
     * @param processInstanceId
     * @param tenantId
     * @return
     */
    public Long getProposerIdByProcessInstanceId(String processInstanceId, String tenantId) {
        WorkflowBusiness workflowBusiness = workflowBusinessExtService.findOneByProcessInstanceId(processInstanceId, tenantId);
        if (Objects.isNull(workflowBusiness)) {
            log.error("WorkflowBusinessManager[getProposerIdByProcessInstanceId] get proposer failed, reason is WorkflowBusiness not existed, processInstanceId is {}, tenantId is {}"
                    , processInstanceId
                    , tenantId);
            throw new BizException(WorkflowErrorCodeEnum.WORKFLOW_BUSINESS_NOT_EXISTED);
        }
        return workflowBusiness.getProposerId();
    }

    /**
     * 是否需要更新流程状态
     *
     * @param updateNewStatusEnum
     * @param workflowBusiness
     * @return
     */
    private boolean isUpdateFlowStatus(WorkFlowStatusEnum updateNewStatusEnum, WorkflowBusiness workflowBusiness) {
        return !(Objects.isNull(updateNewStatusEnum)
                || !Objects.equals(updateNewStatusEnum, WorkFlowStatusEnum.UNKNOWN)
                || Objects.equals(workflowBusiness.getFlowStatus(), updateNewStatusEnum.getStatus()));
    }
}
