package com.ah.cloud.permissions.workflow.application.manager;

import com.ah.cloud.permissions.biz.application.service.WorkflowFormProcessService;
import com.ah.cloud.permissions.biz.application.service.ext.WorkflowFormModelExtService;
import com.ah.cloud.permissions.biz.infrastructure.exception.BizException;
import com.ah.cloud.permissions.biz.infrastructure.repository.bean.WorkflowFormModel;
import com.ah.cloud.permissions.biz.infrastructure.repository.bean.WorkflowFormProcess;
import com.ah.cloud.permissions.domain.common.PageResult;
import com.ah.cloud.permissions.enums.common.DeletedEnum;
import com.ah.cloud.permissions.enums.workflow.WorkflowErrorCodeEnum;
import com.ah.cloud.permissions.workflow.application.helper.WorkflowConfigurationHelper;
import com.ah.cloud.permissions.workflow.domain.configuration.form.ProcessFormConfigurationAddForm;
import com.ah.cloud.permissions.workflow.domain.configuration.form.ProcessFormConfigurationUpdateForm;
import com.ah.cloud.permissions.workflow.domain.configuration.query.ProcessFormConfigurationQuery;
import com.ah.cloud.permissions.workflow.domain.configuration.vo.ProcessFormConfigurationVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @program: permissions-center
 * @description: 流程配置管理
 * @author: YuKai Fan
 * @create: 2022-06-15 16:55
 **/
@Slf4j
@Component
public class WorkflowConfigurationManager {
    @Resource
    private WorkflowConfigurationHelper workflowConfigurationHelper;
    @Resource
    private WorkflowFormProcessService workflowFormProcessService;
    @Resource
    private WorkflowFormModelExtService workflowFormModelExtService;


    /**
     * 新增流程表单配置
     *
     * @param form
     */
    public void addProcessFormConfiguration(ProcessFormConfigurationAddForm form) {
        WorkflowFormProcess existedWorkflowFormProcess = workflowFormProcessService.getOne(
                new QueryWrapper<WorkflowFormProcess>().lambda()
                        .eq(WorkflowFormProcess::getTaskKey, form.getTaskKey())
                        .eq(WorkflowFormProcess::getDeleted, DeletedEnum.NO.value)
        );
        if (Objects.nonNull(existedWorkflowFormProcess)) {
            throw new BizException(WorkflowErrorCodeEnum.WORKFLOW_CONFIGURATION_TASK_FORM_EXISTED, form.getTaskKey(), form.getFormCode());
        }
        WorkflowFormModel workflowFormModel = workflowFormModelExtService.findOneFormModelByCode(form.getFormCode(), form.getTenantId());
        if (Objects.nonNull(workflowFormModel)) {
            throw new BizException(WorkflowErrorCodeEnum.WORKFLOW_FORM_MODEL_NOT_EXISTED);
        }
        WorkflowFormProcess workflowFormProcess = workflowConfigurationHelper.convert(form, workflowFormModel);
        workflowFormProcessService.save(workflowFormProcess);
    }

    /**
     * 更新流程表单配置
     *
     * @param form
     */
    public void updateProcessFormConfiguration(ProcessFormConfigurationUpdateForm form) {
        WorkflowFormProcess existedWorkflowFormProcess = workflowFormProcessService.getOne(
                new QueryWrapper<WorkflowFormProcess>().lambda()
                        .eq(WorkflowFormProcess::getId, form.getId())
                        .eq(WorkflowFormProcess::getDeleted, DeletedEnum.NO.value)
        );
        if (Objects.isNull(existedWorkflowFormProcess)) {
            throw new BizException(WorkflowErrorCodeEnum.WORKFLOW_CONFIGURATION_PROCESS_FORM_EXISTED);
        }
        WorkflowFormProcess updateWorkflowFormProcess = workflowConfigurationHelper.convert(form);
        updateWorkflowFormProcess.setId(form.getId());
        workflowFormProcessService.updateById(updateWorkflowFormProcess);
    }

    /**
     * 根据id获取流程配置
     *
     * @param id
     * @return
     */
    public ProcessFormConfigurationVo findById(Long id) {
        WorkflowFormProcess workflowFormProcess = workflowFormProcessService.getOne(
                new QueryWrapper<WorkflowFormProcess>().lambda()
                        .eq(WorkflowFormProcess::getId, id)
                        .eq(WorkflowFormProcess::getDeleted, DeletedEnum.NO.value)
        );
        if (Objects.isNull(workflowFormProcess)) {
            throw new BizException(WorkflowErrorCodeEnum.WORKFLOW_CONFIGURATION_PROCESS_FORM_EXISTED);
        }
        return workflowConfigurationHelper.convertToVo(workflowFormProcess);
    }

    /**
     * 根据id删除流程配置
     *
     * @param id
     */
    public void deleteById(Long id) {
        WorkflowFormProcess workflowFormProcess = workflowFormProcessService.getOne(
                new QueryWrapper<WorkflowFormProcess>().lambda()
                        .eq(WorkflowFormProcess::getId, id)
                        .eq(WorkflowFormProcess::getDeleted, DeletedEnum.NO.value)
        );
        if (Objects.isNull(workflowFormProcess)) {
            throw new BizException(WorkflowErrorCodeEnum.WORKFLOW_CONFIGURATION_PROCESS_FORM_EXISTED);
        }

        WorkflowFormProcess deleteWorkflowFormProcess = new WorkflowFormProcess();
        deleteWorkflowFormProcess.setId(workflowFormProcess.getId());
        deleteWorkflowFormProcess.setDeleted(workflowFormProcess.getId());
        workflowFormProcessService.updateById(deleteWorkflowFormProcess);
    }

    /**
     * 分页查询流程配置
     * @param query
     * @return
     */
    public PageResult<ProcessFormConfigurationVo> pageProcessFormConfigurationVoList(ProcessFormConfigurationQuery query) {
        PageInfo<WorkflowFormProcess> pageInfo = PageMethod.startPage(query.getPageNum(), query.getPageSize())
                .doSelectPageInfo(() ->
                        workflowFormProcessService.list(
                                new QueryWrapper<WorkflowFormProcess>().lambda()
                                        .eq(
                                                query.getWorkflowProcessId() != null
                                                , WorkflowFormProcess::getWorkflowProcessId
                                                , query.getWorkflowProcessId()
                                        )
                                        .eq(
                                                StringUtils.isNotBlank(query.getProcessDefinitionId())
                                                , WorkflowFormProcess::getProcessDefinitionId
                                                , query.getProcessDefinitionId()
                                        )
                                        .eq(
                                                StringUtils.isNotBlank(query.getTaskKey())
                                                , WorkflowFormProcess::getTaskKey
                                                , query.getTaskKey()
                                        )
                                        .eq(WorkflowFormProcess::getDeleted, DeletedEnum.NO.value)

                        )
                );
        return workflowConfigurationHelper.convertToPageResult(pageInfo);
    }
}
