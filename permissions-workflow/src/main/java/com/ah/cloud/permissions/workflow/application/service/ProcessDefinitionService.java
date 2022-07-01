package com.ah.cloud.permissions.workflow.application.service;

import com.ah.cloud.permissions.workflow.domain.model.dto.*;
import com.ah.cloud.permissions.workflow.domain.model.vo.ModelVo;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.common.engine.impl.db.SuspensionState;

/**
 * @program: permissions-center
 * @description: 流程定义接口
 * @author: YuKai Fan
 * @create: 2022-06-30 11:22
 **/
public interface ProcessDefinitionService {

    /**
     * 创建新流程模型
     * @param createModelDTO
     * @return 模型id
     */
    String createNewModel(CreateModelDTO createModelDTO);

    /**
     * 更新流程模型
     *
     * @param updateModelDTO
     */
    void updateModel(UpdateModelDTO updateModelDTO);

    /**
     * 根据模型id获取流程模型
     *
     * @param id 模型id
     * @return
     */
    ModelVo findModelById(String id);

    /**
     * 删除模型
     * <p>
     * 根据源码，删除模型的同时会删除模型关联的流程图
     * <p>
     * 注意: 此删除是真删除, 慎重操作
     *
     * @param id 模型id
     */
    void deleteModelById(String id);

    /**
     * 保存 bpmn 文件的xml 字节
     *
     * @param modelEditorSourceDTO
     */
    void saveModelEditorSource(ModelEditorSourceDTO modelEditorSourceDTO);

    /**
     * 部署流程
     *
     * @param deployModelDTO
     * @return
     */
    DeployProcessDTO deployModel(DeployModelDTO deployModelDTO);

    /**
     * 根据模型id获取bpmn模型
     *
     * @param modelId
     * @return
     */
    BpmnModel getBpmnModel(String modelId);

    /**
     * 根据流程定义id获取bpmn模型
     *
     * @param processDefinitionId
     * @return
     */
    BpmnModel getBpmnModelByProcessDefinitionId(String processDefinitionId);

    /**
     * 变更流程定义状态
     *
     * @param processDefinitionId
     * @param state
     * @return
     */
    boolean changeProcessDefinitionState(String processDefinitionId, SuspensionState state);

    /**
     * 校验流程模型是否存在任务规则配置
     *
     * @param modelId
     */
    void checkModelTaskAssignRuleConfig(String modelId);

    /**
     * 根据模型id获取流程规则, 并转换为最新的流程定义id对应的任务规则
     * @param fromModelId
     * @param newProcessDefinitionId
     */
    void copyTaskAssignRuleByProcessDefinitionId(String fromModelId, String newProcessDefinitionId);
}
