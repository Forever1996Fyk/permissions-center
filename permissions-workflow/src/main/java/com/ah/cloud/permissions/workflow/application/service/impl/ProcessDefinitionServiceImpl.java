package com.ah.cloud.permissions.workflow.application.service.impl;

import com.ah.cloud.permissions.biz.application.service.WorkflowTaskAssignRuleService;
import com.ah.cloud.permissions.biz.infrastructure.exception.BizException;
import com.ah.cloud.permissions.biz.infrastructure.repository.bean.WorkflowTaskAssignRule;
import com.ah.cloud.permissions.biz.infrastructure.util.DateUtils;
import com.ah.cloud.permissions.biz.infrastructure.util.JsonUtils;
import com.ah.cloud.permissions.enums.common.DeletedEnum;
import com.ah.cloud.permissions.enums.workflow.WorkflowErrorCodeEnum;
import com.ah.cloud.permissions.workflow.application.helper.ProcessModelHelper;
import com.ah.cloud.permissions.workflow.application.helper.WorkflowTaskAssignRuleHelper;
import com.ah.cloud.permissions.workflow.application.service.ProcessDefinitionService;
import com.ah.cloud.permissions.workflow.domain.model.dto.*;
import com.ah.cloud.permissions.workflow.domain.model.vo.ModelVo;
import com.ah.cloud.permissions.workflow.infrastructure.util.FlowableUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.flowable.bpmn.converter.BpmnXMLConverter;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.bpmn.model.UserTask;
import org.flowable.common.engine.impl.db.SuspensionState;
import org.flowable.common.engine.impl.util.io.BytesStreamSource;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.Model;
import org.flowable.engine.repository.ProcessDefinition;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-06-30 11:52
 **/
@Slf4j
@Service
public class ProcessDefinitionServiceImpl implements ProcessDefinitionService {
    @Resource
    private RepositoryService repositoryService;
    @Resource
    private ProcessModelHelper processModelHelper;
    @Resource
    private WorkflowTaskAssignRuleService workflowTaskAssignRuleService;

    /**
     * 创建新流程模型
     *
     * @param dto
     * @return 流程模型id
     */
    @Override
    public String createNewModel(CreateModelDTO dto) {
        // 构建一个new model
        Model model = repositoryService.newModel();

        // 设置模型信息
        model.setName(dto.getModelName());
        model.setKey(dto.getModelKey());
        model.setCategory(dto.getProcessCategory().getCategory());

        ModelMetaInfoDTO metaInfoDTO = ModelMetaInfoDTO.builder().build();
        metaInfoDTO.setModelDesc(dto.getModelDesc());
        model.setMetaInfo(JsonUtils.toJsonString(metaInfoDTO));
        // 保存模型
        repositoryService.saveModel(model);
        return model.getId();
    }

    /**
     * 更新流程模型
     *
     * @param dto
     */
    @Override
    public void updateModel(UpdateModelDTO dto) {
        Model model = repositoryService.getModel(dto.getModelId());
        if (Objects.isNull(model)) {
            throw new BizException(WorkflowErrorCodeEnum.WORKFLOW_PROCESS_MODEL_NOT_EXISTED, dto.getModelId());
        }
        // 设置模型信息
        model.setName(dto.getModelName());
        model.setKey(dto.getModelKey());

        ModelMetaInfoDTO metaInfoDTO = ModelMetaInfoDTO.builder().build();
        metaInfoDTO.setModelDesc(dto.getModelDesc());
        metaInfoDTO.setVersion(model.getVersion() + 1);
        model.setMetaInfo(JsonUtils.toJsonString(metaInfoDTO));
        // 更新模型 如果id存在则更新，否则新增
        repositoryService.saveModel(model);
    }

    /**
     * 根据 模型id获取 模型数据
     *
     * @param id
     * @return
     */
    @Override
    public ModelVo findModelById(String id) {
        Model model = repositoryService.getModel(id);
        if (Objects.isNull(model)) {
            throw new BizException(WorkflowErrorCodeEnum.WORKFLOW_PROCESS_MODEL_NOT_EXISTED, id);
        }
        byte[] modelEditorSource = repositoryService.getModelEditorSource(id);
        if (modelEditorSource == null || modelEditorSource.length == 0) {
            throw new BizException(WorkflowErrorCodeEnum.WORKFLOW_PROCESS_MODEL_RELATE_RESOURCE_NOT_EXISTED, id);
        }
        ModelVo modelVo = processModelHelper.convertModelVo(model);
        modelVo.setBpmnXml(new String(modelEditorSource, StandardCharsets.UTF_8));
        return modelVo;
    }

    /**
     * 删除模型
     * <p>
     * 根据源码，删除模型的同时会删除模型关联的流程图
     * <p>
     * 注意: 此删除是真删除, 慎重操作
     *
     * @param id
     */
    @Override
    public void deleteModelById(String id) {
        repositoryService.deleteModel(id);
    }

    /**
     * 保存 bpmn 文件的xml 字节
     *
     * @param dto
     */
    @Override
    public void saveModelEditorSource(ModelEditorSourceDTO dto) {
        Model model = repositoryService.getModel(dto.getModelId());
        if (Objects.isNull(model)) {
            throw new BizException(WorkflowErrorCodeEnum.WORKFLOW_PROCESS_MODEL_NOT_EXISTED, dto.getModelId());
        }
        repositoryService.addModelEditorSource(model.getId(), dto.getModelBpmnXml().getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 部署流程模型
     *
     * @param dto
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public DeployProcessDTO deployModel(DeployModelDTO dto) {
        String modelId = dto.getModelId();
        // 根据模型id获取当前 流程模型
        Model model = repositoryService.getModel(modelId);
        if (Objects.isNull(model)) {
            throw new BizException(WorkflowErrorCodeEnum.WORKFLOW_PROCESS_MODEL_NOT_EXISTED, modelId);
        }
        /*
         校验流程模型的规则配置：
         1. 当前流程没有用户任务, 则无需校验
         2. 当前流程存在用户任务, 判断任务关联的规则配置是否为空
         */
        this.checkModelTaskAssignRuleConfig(modelId);

        byte[] modelEditorSource = repositoryService.getModelEditorSource(modelId);
        if (modelEditorSource == null || modelEditorSource.length == 0) {
            log.error("ProcessModelManager[deployModel] deploy model failed, reason is modelEditorSource is empty, params is {}", JsonUtils.toJsonString(dto));
            throw new BizException(WorkflowErrorCodeEnum.WORKFLOW_PROCESS_MODEL_RELATE_RESOURCE_NOT_EXISTED, modelId);
        }
        ByteArrayInputStream inputStream = new ByteArrayInputStream(modelEditorSource);
        // 根据BpmnModel 部署流程
        String deployName = model.getName();
        if (StringUtils.isNotEmpty(dto.getDeployName())) {
            deployName = dto.getDeployName();
        }

        /*
        将老的流程定义进行挂起。也就是说，只有最新部署的流程定义，才可以发起任务。
        因为每次部署都会产生一个deploymentId, 并且保存在model中, 所以如果model中已经存在了deploymentId, 说明之前已经部署过, 去出来挂起即可
         */
        String oldDeploymentId = model.getDeploymentId();
        if (StringUtils.isNotBlank(oldDeploymentId)) {
            // 流程部署后, 获取流程定义
            ProcessDefinition oldProcessDefinition = repositoryService.createProcessDefinitionQuery().deploymentId(oldDeploymentId).latestVersion().singleResult();
            if (Objects.nonNull(oldProcessDefinition)) {
                this.changeProcessDefinitionState(oldProcessDefinition.getId(), SuspensionState.SUSPENDED);
            }
        }

        // 流程部署
        Deployment deployment = repositoryService.createDeployment()
                .name(deployName)
                .addInputStream(processModelHelper.getDeployKey(model.getKey()), inputStream)
                .category(dto.getCategory())
                .deploy();

        // 更新流程模型关联流程定义id
        model.setDeploymentId(deployment.getId());
        repositoryService.saveModel(model);

        // 流程部署后, 获取流程定义
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().deploymentId(deployment.getId()).latestVersion().singleResult();
        // 复制新的任务规则
        this.copyTaskAssignRuleByProcessDefinitionId(modelId, processDefinition.getId());

        return DeployProcessDTO.builder()
                .processDefinitionId(processDefinition.getId())
                .processDefinitionKey(processDefinition.getKey())
                .processDeployId(deployment.getId())
                .build();
    }

    /**
     * 根据模型id获取BPMN模型
     *
     * @param modelId
     * @return
     */
    @Override
    public BpmnModel getBpmnModel(String modelId) {
        byte[] modelEditorSource = repositoryService.getModelEditorSource(modelId);
        if (ArrayUtils.isEmpty(modelEditorSource)) {
            return null;
        }
        BpmnXMLConverter converter = new BpmnXMLConverter();
        return converter.convertToBpmnModel(new BytesStreamSource(modelEditorSource), true, true);
    }

    /**
     * 根据流程定义id获取BPMN模型
     *
     * @param processDefinitionId
     * @return
     */
    @Override
    public BpmnModel getBpmnModelByProcessDefinitionId(String processDefinitionId) {
        return repositoryService.getBpmnModel(processDefinitionId);
    }

    /**
     * 更新流程定义状态
     *
     * @param processDefinitionId
     * @param state
     */
    @Override
    public boolean changeProcessDefinitionState(String processDefinitionId, SuspensionState state) {
        if (StringUtils.isBlank(processDefinitionId) || Objects.isNull(state)) {
            return false;
        }
        try {
            log.info("ProcessModelManager[changeProcessDefinitionState] change process state, processDefinitionId is {}, changeState is {}", processDefinitionId, state);
            if (Objects.equals(state, SuspensionState.SUSPENDED)) {
                repositoryService.suspendProcessDefinitionById(processDefinitionId, false, DateUtils.getCurrentDateTime());
            }

            if (Objects.equals(state, SuspensionState.ACTIVE)) {
                repositoryService.activateProcessDefinitionById(processDefinitionId, false, DateUtils.getCurrentDateTime());
            }
            return true;
        } catch (Exception e) {
            log.error("ProcessModelManager[changeProcessDefinitionState] change process state failed" +
                            ", processDefinitionId is {}, changeState is {}, reason is {}"
                    , Throwables.getStackTraceAsString(e)
                    , processDefinitionId
                    , state);
            return false;
        }
    }

    @Override
    public void checkModelTaskAssignRuleConfig(String modelId) {
        BpmnModel bpmnModel = this.getBpmnModel(modelId);
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

    @Override
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
}
