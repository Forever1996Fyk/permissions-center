package com.ah.cloud.permissions.workflow.application.manager;

import com.ah.cloud.permissions.biz.infrastructure.exception.BizException;
import com.ah.cloud.permissions.biz.infrastructure.util.JsonUtils;
import com.ah.cloud.permissions.enums.common.WorkflowErrorCodeEnum;
import com.ah.cloud.permissions.workflow.application.helper.ProcessModelHelper;
import com.ah.cloud.permissions.workflow.domain.model.dto.ModelMetaInfoDTO;
import com.ah.cloud.permissions.workflow.domain.model.form.ModelAddForm;
import com.ah.cloud.permissions.workflow.domain.model.form.ModelEditorSourceForm;
import com.ah.cloud.permissions.workflow.domain.model.form.ModelUpdateForm;
import com.ah.cloud.permissions.workflow.domain.model.query.ProcessModelQuery;
import com.ah.cloud.permissions.workflow.domain.model.vo.ModelVo;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.flowable.bpmn.converter.BpmnXMLConverter;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.common.engine.api.io.InputStreamProvider;
import org.flowable.common.engine.impl.util.io.BytesStreamSource;
import org.flowable.editor.language.json.converter.BpmnJsonConverter;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.Model;
import org.flowable.engine.repository.ModelQuery;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;

/**
 * @program: permissions-center
 * @description: 流程模型管理
 * @author: YuKai Fan
 * @create: 2022-06-08 15:15
 **/
@Slf4j
@Component
public class ProcessModelManager {
    @Resource
    private RepositoryService repositoryService;
    @Resource
    private ProcessModelHelper processModelHelper;

    /**
     * 创建新流程模型
     *
     * @param form
     */
    public void createNewModel(ModelAddForm form) {
        // 构建一个new model
        Model model = repositoryService.newModel();

        // 设置模型信息
        model.setName(form.getModelName());
        model.setKey(form.getModelKey());

        ModelMetaInfoDTO metaInfoDTO = ModelMetaInfoDTO.builder().build();
        metaInfoDTO.setModelDesc(form.getModelDesc());
        metaInfoDTO.setVersion(form.getVersion());
        model.setMetaInfo(JsonUtils.toJSONString(metaInfoDTO));
        // 保存模型
        repositoryService.saveModel(model);
    }

    /**
     * 更新流程模型
     * @param form
     */
    public void updateModel(ModelUpdateForm form) {
        Model model = repositoryService.getModel(form.getModelId());
        if (Objects.isNull(model)) {
            throw new BizException(WorkflowErrorCodeEnum.WORKFLOW_PROCESS_MODEL_NOT_EXISTED, form.getModelId());
        }
        // 设置模型信息
        model.setName(form.getModelName());
        model.setKey(form.getModelKey());

        ModelMetaInfoDTO metaInfoDTO = ModelMetaInfoDTO.builder().build();
        metaInfoDTO.setModelDesc(form.getModelDesc());
        metaInfoDTO.setVersion(model.getVersion() + 1);
        model.setMetaInfo(JsonUtils.toJSONString(metaInfoDTO));
        // 更新模型 如果id存在则更新，否则新增
        repositoryService.saveModel(model);
    }

    /**
     * 根据 模型id获取 模型数据
     * @param id
     * @return
     */
    public ModelVo findModelById(String id) {
        Model model = repositoryService.getModel(id);
        if (Objects.isNull(model)) {
            throw new BizException(WorkflowErrorCodeEnum.WORKFLOW_PROCESS_MODEL_NOT_EXISTED, id);
        }
        return processModelHelper.convertModelVo(model);
    }

    /**
     * 删除模型
     *
     * 根据源码，删除模型的同时会删除模型关联的流程图
     *
     * 注意: 此删除是真删除, 慎重操作
     *
     * @param id
     */
    public void deleteModelById(String id) {
        repositoryService.deleteModel(id);
    }

    /**
     * 获取模型数据列表
     * @param query
     * @return
     */
    public List<ModelVo> listModelVo(ProcessModelQuery query) {
        ModelQuery modelQuery = repositoryService.createModelQuery()
                .orderByCreateTime()
                .desc();
        if (StringUtils.isNotEmpty(query.getName())) {
            modelQuery.modelNameLike(query.getName());
        }

        if (StringUtils.isNotEmpty(query.getKey())) {
            modelQuery.modelKey(query.getKey());
        }
        List<Model> list = modelQuery.listPage(query.getPageNum(), query.getPageSize());
        return processModelHelper.convertModelVoList(list);
    }

    /**
     * 保存 bpmn 文件的xml 字节
     * @param form
     */
    public void saveModelEditorSource(ModelEditorSourceForm form) {
        Model model = repositoryService.getModel(form.getModelId());
        if (Objects.isNull(model)) {
            throw new BizException(WorkflowErrorCodeEnum.WORKFLOW_PROCESS_MODEL_NOT_EXISTED, form.getModelId());
        }
        repositoryService.addModelEditorSource(model.getId(), form.getModelBpmnXml().getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 部署流程模型
     * @param id
     */
    @Transactional(rollbackFor = Exception.class)
    public void deployModel(String id) {
        // 根据模型id获取当前 流程模型
        Model model = repositoryService.getModel(id);
        if (Objects.isNull(model)) {
            throw new BizException(WorkflowErrorCodeEnum.WORKFLOW_PROCESS_MODEL_NOT_EXISTED, id);
        }
        byte[] modelEditorSource = repositoryService.getModelEditorSource(id);
        if (modelEditorSource == null || modelEditorSource.length == 0) {
            throw new BizException(WorkflowErrorCodeEnum.WORKFLOW_PROCESS_MODEL_RELATE_RESOURCE_NOT_EXISTED, id);
        }
        JsonNode jsonNode = JsonUtils.byteToReadTree(modelEditorSource);
        if (Objects.isNull(jsonNode)) {
            throw new BizException(WorkflowErrorCodeEnum.WORKFLOW_PROCESS_MODEL_RELATE_RESOURCE_CONVERT_JSON_NODE_FAILED, id);
        }
        // 根据byte字节数据构建BpmnModel
        BpmnModel bpmnModel = new BpmnJsonConverter().convertToBpmnModel(jsonNode);
        // 根据BpmnModel 部署流程
        Deployment deployment = repositoryService.createDeployment()
                .name(model.getName())
                .addBpmnModel(processModelHelper.getDeployKey(model.getKey()), bpmnModel)
                .deploy();
        model.setDeploymentId(deployment.getId());
        repositoryService.saveModel(model);
    }


}
