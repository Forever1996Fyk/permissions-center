package com.ah.cloud.permissions.workflow.application.helper;

import com.ah.cloud.permissions.biz.infrastructure.util.DateUtils;
import com.ah.cloud.permissions.biz.infrastructure.util.JsonUtils;
import com.ah.cloud.permissions.workflow.domain.model.dto.ModelMetaInfoDTO;
import com.ah.cloud.permissions.workflow.domain.model.vo.ModelVo;
import com.ah.cloud.permissions.workflow.domain.model.vo.SelectProcessVo;
import org.apache.commons.lang3.StringUtils;
import org.flowable.engine.repository.Model;
import org.flowable.engine.repository.ProcessDefinition;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-06-08 21:36
 **/
@Component
public class ProcessModelHelper {

    /**
     * 构建模型
     *
     * @param model
     * @return
     */
    public ModelVo convertModelVo(Model model) {
        ModelVo modelVo = ModelVo.builder()
                .id(model.getId())
                .createTime(DateUtils.format(model.getCreateTime()))
                .lastUpdateTime(DateUtils.format(model.getLastUpdateTime()))
                .build();
        String metaInfo = model.getMetaInfo();
        if (StringUtils.isNotBlank(metaInfo)) {
            ModelMetaInfoDTO metaInfoDTO = JsonUtils.stringToBean(metaInfo, ModelMetaInfoDTO.class);
            modelVo.setDesc(metaInfoDTO.getModelDesc());
        }
        return modelVo;
    }

    /**
     * 数据转换
     *
     * @param list
     * @return
     */
    public List<ModelVo> convertModelVoList(List<Model> list) {
        return list.stream().map(this::convertModelVo).collect(Collectors.toList());
    }

    /**
     * 获取流程部署可以
     * @param key
     * @return
     */
    public String getDeployKey(String key) {
        return key + ".bpmn";
    }

    /**
     * 数据转换
     * @param processDefinitionList
     * @return
     */
    public List<SelectProcessVo> convertToVoList(List<ProcessDefinition> processDefinitionList) {
        return processDefinitionList.stream()
                .map(processDefinition ->
                        SelectProcessVo.builder()
                                .name(processDefinition.getName())
                                .key(processDefinition.getKey())
                                .processDefinitionId(processDefinition.getId())
                                .build()
                )
                .collect(Collectors.toList());
    }
}
