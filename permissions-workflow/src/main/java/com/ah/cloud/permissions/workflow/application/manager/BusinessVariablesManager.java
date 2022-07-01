package com.ah.cloud.permissions.workflow.application.manager;

import com.ah.cloud.permissions.biz.infrastructure.util.JsonUtils;
import com.ah.cloud.permissions.workflow.domain.business.dto.BusinessVariablesDTO;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.flowable.task.api.Task;

import java.util.List;
import java.util.Map;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-06-26 15:23
 **/
public class BusinessVariablesManager {

    /**
     * 构建业务变量参数
     * @param currentActiveTaskNode
     * @param variables
     * @return
     */
    public static List<BusinessVariablesDTO> buildVariables(Task currentActiveTaskNode, Map<String, Object> variables) {
        BusinessVariablesDTO businessVariablesDTO = BusinessVariablesDTO.builder()
                .taskDefinitionId(currentActiveTaskNode.getTaskDefinitionId())
                .taskDefinitionKey(currentActiveTaskNode.getTaskDefinitionKey())
                .taskName(currentActiveTaskNode.getName())
                .variables(variables)
                .build();
        List<BusinessVariablesDTO> list = Lists.newArrayList();
        list.add(businessVariablesDTO);
        return list;
    }

    /**
     * 添加参数变量
     * @param businessParamJson
     * @param currentActiveTaskNode
     * @param variables
     * @return
     */
    public static String addVariables(String businessParamJson, Task currentActiveTaskNode, Map<String, Object> variables) {
        if (StringUtils.isBlank(businessParamJson)) {
            return JsonUtils.toJSONString(buildVariables(currentActiveTaskNode, variables));
        }
        List<BusinessVariablesDTO> businessVariablesList = JsonUtils.jsonToList(businessParamJson, BusinessVariablesDTO.class);
        BusinessVariablesDTO businessVariablesDTO = BusinessVariablesDTO.builder()
                .taskDefinitionId(currentActiveTaskNode.getTaskDefinitionId())
                .taskDefinitionKey(currentActiveTaskNode.getTaskDefinitionKey())
                .taskName(currentActiveTaskNode.getName())
                .variables(variables)
                .build();
        businessVariablesList.add(businessVariablesDTO);
        return JsonUtils.toJSONString(businessVariablesDTO);
    }
}
