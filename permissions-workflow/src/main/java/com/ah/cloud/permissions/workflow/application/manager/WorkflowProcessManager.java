package com.ah.cloud.permissions.workflow.application.manager;

import com.ah.cloud.permissions.biz.application.service.ext.WorkflowFormModelExtService;
import com.ah.cloud.permissions.biz.application.service.ext.WorkflowProcessExtService;
import com.ah.cloud.permissions.biz.infrastructure.exception.BizException;
import com.ah.cloud.permissions.biz.infrastructure.repository.bean.WorkflowFormModel;
import com.ah.cloud.permissions.biz.infrastructure.repository.bean.WorkflowProcess;
import com.ah.cloud.permissions.domain.common.PageResult;
import com.ah.cloud.permissions.enums.YesOrNoEnum;
import com.ah.cloud.permissions.enums.common.DeletedEnum;
import com.ah.cloud.permissions.enums.common.ErrorCodeEnum;
import com.ah.cloud.permissions.enums.workflow.WorkflowErrorCodeEnum;
import com.ah.cloud.permissions.workflow.application.helper.WorkflowProcessHelper;
import com.ah.cloud.permissions.workflow.application.service.ProcessDefinitionService;
import com.ah.cloud.permissions.workflow.domain.model.dto.*;
import com.ah.cloud.permissions.workflow.domain.model.form.ModelEditorSourceForm;
import com.ah.cloud.permissions.workflow.domain.process.form.WorkflowProcessAddForm;
import com.ah.cloud.permissions.workflow.domain.process.form.WorkflowProcessDeployForm;
import com.ah.cloud.permissions.workflow.domain.process.form.WorkflowProcessUpdateForm;
import com.ah.cloud.permissions.workflow.domain.process.query.WorkflowProcessQuery;
import com.ah.cloud.permissions.workflow.domain.process.vo.BusinessWorkflowVo;
import com.ah.cloud.permissions.workflow.domain.process.vo.WorkflowFormVo;
import com.ah.cloud.permissions.workflow.domain.process.vo.WorkflowProcessVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-06-15 13:37
 **/
@Slf4j
@Component
public class WorkflowProcessManager {
    @Resource
    private WorkflowProcessHelper workflowProcessHelper;
    @Resource
    private ProcessDefinitionService processDefinitionService;
    @Resource
    private WorkflowProcessExtService workflowProcessExtService;
    @Resource
    private WorkflowFormModelExtService workflowFormModelExtService;

    /**
     * 新增流程
     *
     * @param form
     */
    @Transactional(rollbackFor = Exception.class)
    public Long addWorkflowProcess(WorkflowProcessAddForm form) {
        // 保存流程模型
        CreateModelDTO createModelDTO = workflowProcessHelper.convertToDTO(form);
        String modelId = processDefinitionService.createNewModel(createModelDTO);

        WorkflowProcess workflowProcess = workflowProcessHelper.convert(form);
        workflowProcess.setModelId(modelId);
        workflowProcessExtService.save(workflowProcess);
        return workflowProcess.getId();
    }

    /**
     * 保存工作流资源xml
     * @param form
     */
    public void saveWorkflowProcessResource(ModelEditorSourceForm form) {
        ModelEditorSourceDTO modelEditorSourceDTO = ModelEditorSourceDTO.builder().modelId(form.getModelId()).modelBpmnXml(form.getModelBpmnXml()).build();
        processDefinitionService.saveModelEditorSource(modelEditorSourceDTO);
    }

    /**
     * 更新流程
     *
     * @param form
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateWorkflowProcess(WorkflowProcessUpdateForm form) {
        WorkflowProcess existedWorkflowProcess = workflowProcessExtService.getOne(
                new QueryWrapper<WorkflowProcess>().lambda()
                        .eq(WorkflowProcess::getId, form.getId())
                        .eq(WorkflowProcess::getDeleted, DeletedEnum.NO.value)
        );
        if (Objects.isNull(existedWorkflowProcess)) {
            throw new BizException(WorkflowErrorCodeEnum.WORKFLOW_PROCESS_NOT_EXISTED, form.getName());
        }
        // 保存流程模型
        UpdateModelDTO updateModelDTO = workflowProcessHelper.convertToDTO(form);
        processDefinitionService.updateModel(updateModelDTO);

        WorkflowProcess updateWorkflowProcess = workflowProcessHelper.convert(form);
        updateWorkflowProcess.setId(form.getId());
        workflowProcessExtService.updateById(updateWorkflowProcess);
    }

    /**
     * 删除流程
     *
     * @param id
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteWorkflowProcessById(Long id) {
        WorkflowProcess existedWorkflowProcess = workflowProcessExtService.getOne(
                new QueryWrapper<WorkflowProcess>().lambda()
                        .eq(WorkflowProcess::getId, id)
                        .eq(WorkflowProcess::getDeleted, DeletedEnum.NO.value)
        );
        if (Objects.isNull(existedWorkflowProcess)) {
            throw new BizException(WorkflowErrorCodeEnum.WORKFLOW_PROCESS_NOT_EXISTED, String.valueOf(id));
        }
        processDefinitionService.deleteModelById(existedWorkflowProcess.getModelId());

        WorkflowProcess deleteWorkflowProcess = new WorkflowProcess();
        deleteWorkflowProcess.setId(id);
        deleteWorkflowProcess.setDeleted(id);
        workflowProcessExtService.updateById(deleteWorkflowProcess);
    }

    /**
     * 根据id查询流程
     * @param id
     * @return
     */
    public WorkflowProcessVo findById(Long id) {
        WorkflowProcess existedWorkflowProcess = workflowProcessExtService.getOne(
                new QueryWrapper<WorkflowProcess>().lambda()
                        .eq(WorkflowProcess::getId, id)
                        .eq(WorkflowProcess::getDeleted, DeletedEnum.NO.value)
        );
        if (Objects.isNull(existedWorkflowProcess)) {
            throw new BizException(WorkflowErrorCodeEnum.WORKFLOW_PROCESS_NOT_EXISTED, String.valueOf(id));
        }
        return workflowProcessHelper.convertToVo(existedWorkflowProcess);
    }

    /**
     * 分页查询流程
     * @param query
     * @return
     */
    public PageResult<WorkflowProcessVo> pageWorkflowProcessVoList(WorkflowProcessQuery query) {
        PageInfo<WorkflowProcess> pageInfo = PageMethod.startPage(query.getPageNum(), query.getPageSize())
                .doSelectPageInfo(
                        () -> workflowProcessExtService.list(
                                new QueryWrapper<WorkflowProcess>().lambda()
                                        .like(StringUtils.isNotBlank(query.getName()), WorkflowProcess::getName, query.getName())
                                        .eq(StringUtils.isNotBlank(query.getKey()), WorkflowProcess::getProcessDefinitionKey, query.getKey())
                        )
                );

        return workflowProcessHelper.convertToPageResult(pageInfo);
    }

    /**
     * 根据id部署流程
     * @param form
     */
    @Transactional(rollbackFor = Exception.class)
    public void deployProcessById(WorkflowProcessDeployForm form) {
        WorkflowProcess workflowProcess = workflowProcessExtService.getOne(
                new QueryWrapper<WorkflowProcess>().lambda()
                        .eq(WorkflowProcess::getId, form.getId())
                        .eq(WorkflowProcess::getDeleted, DeletedEnum.NO.value)
        );
        if (Objects.isNull(workflowProcess)) {
            throw new BizException(WorkflowErrorCodeEnum.WORKFLOW_PROCESS_NOT_EXISTED, String.valueOf(form.getId()));
        }
        String modelId = workflowProcess.getModelId();
        if (StringUtils.isEmpty(modelId)) {
            log.error("WorkflowProcessManager[deployProcessById] process deploy failed, reason is modelId is empty, processId is {}", form.getId());
            throw new BizException(WorkflowErrorCodeEnum.WORKFLOW_PROCESS_DEPLOY_FAILED, workflowProcess.getName());
        }
        // 流程部署
        DeployProcessDTO deployProcessDTO = processDefinitionService.deployModel(
                DeployModelDTO.builder().deployName(form.getDeployName()).modelId(modelId).category(workflowProcess.getProcessCategory()).build()
        );
        // 更新流程信息
        WorkflowProcess updateWorkflowProcess = new WorkflowProcess();
        updateWorkflowProcess.setId(workflowProcess.getId());
        updateWorkflowProcess.setProcessDeployId(deployProcessDTO.getProcessDeployId());
        updateWorkflowProcess.setProcessDefinitionKey(deployProcessDTO.getProcessDefinitionKey());
        updateWorkflowProcess.setProcessDefinitionId(deployProcessDTO.getProcessDefinitionId());
        updateWorkflowProcess.setStatus(YesOrNoEnum.YES.getType());
        updateWorkflowProcess.setVersion(workflowProcess.getVersion() + 1);
        boolean updateResult = workflowProcessExtService.update(
                updateWorkflowProcess,
                new UpdateWrapper<WorkflowProcess>().lambda()
                        .eq(WorkflowProcess::getId, workflowProcess.getId())
                        .eq(WorkflowProcess::getVersion, workflowProcess.getVersion())
        );
        if (!updateResult) {
            throw new BizException(ErrorCodeEnum.VERSION_ERROR);
        }
    }

    /**
     * 根据流程类别分组 获取流程列表
     * @return
     */
    public List<BusinessWorkflowVo> listBusinessWorkflowVoCategoryGroup() {
        List<WorkflowProcess> workflowProcessList = workflowProcessExtService.list(
                new QueryWrapper<WorkflowProcess>().lambda()
                        .eq(WorkflowProcess::getStatus, YesOrNoEnum.YES.getType())
                        .eq(WorkflowProcess::getDeleted, DeletedEnum.NO.value)
        );
        Map<String, List<WorkflowProcess>> businessWorkflowVoCategoryGroupMap = workflowProcessList.stream().collect(Collectors.groupingBy(WorkflowProcess::getProcessCategory));
        List<BusinessWorkflowVo> businessWorkflowVoList = Lists.newArrayList();
        for (Map.Entry<String, List<WorkflowProcess>> entry : businessWorkflowVoCategoryGroupMap.entrySet()) {
            businessWorkflowVoList.add(
                    BusinessWorkflowVo.builder()
                            .processCategory(entry.getKey())
                            .detailVoList(workflowProcessHelper.convertToBusinessVoList(entry.getValue()))
                            .build()
            );
        }
        return businessWorkflowVoList;
    }

    /**
     * 根据流程id获取表单信息
     * @param id
     * @return
     */
    public WorkflowFormVo findWorkflowFormVoById(Long id) {
        WorkflowProcess workflowProcess = workflowProcessExtService.getOne(
                new QueryWrapper<WorkflowProcess>().lambda()
                        .eq(WorkflowProcess::getId, id)
                        .eq(WorkflowProcess::getDeleted, DeletedEnum.NO.value)
        );
        if (Objects.isNull(workflowProcess)) {
            throw new BizException(WorkflowErrorCodeEnum.WORKFLOW_PROCESS_NOT_EXISTED, String.valueOf(id));
        }
        WorkflowFormVo workflowFormVo = WorkflowFormVo.builder()
                .processId(id)
                .build();
        String formCode = workflowProcess.getFormCode();
        if (StringUtils.isNotBlank(formCode)) {
            WorkflowFormModel workflowFormModel = workflowFormModelExtService.findOneFormModelByCode(formCode, null);
            workflowFormVo.setContent(workflowFormModel.getContent());
        }
        return workflowFormVo;
    }
}
