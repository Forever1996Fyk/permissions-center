package com.ah.cloud.permissions.workflow.application.helper;

import com.ah.cloud.permissions.biz.infrastructure.repository.bean.WorkflowTaskAssignRule;
import com.ah.cloud.permissions.biz.infrastructure.util.CollectionUtils;
import com.ah.cloud.permissions.biz.infrastructure.util.JsonUtils;
import com.ah.cloud.permissions.workflow.domain.task.form.AssignRuleTaskAddForm;
import com.ah.cloud.permissions.workflow.domain.task.form.AssignRuleTaskUpdateForm;
import com.ah.cloud.permissions.workflow.domain.task.vo.AssignRuleTaskVo;
import com.google.common.collect.Lists;
import org.flowable.bpmn.model.UserTask;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import java.security.PublicKey;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-06-20 20:58
 **/
@Component
public class WorkflowTaskAssignRuleHelper {

    /**
     * 数据转换
     * @param userTaskList
     * @param workflowTaskAssignRuleList
     * @return
     */
    public List<AssignRuleTaskVo> convertRuleTaskVoList(List<UserTask> userTaskList, List<WorkflowTaskAssignRule> workflowTaskAssignRuleList) {
        Map<String, WorkflowTaskAssignRule> workflowTaskAssignRuleMap = CollectionUtils.convertMap(workflowTaskAssignRuleList, WorkflowTaskAssignRule::getProcessDefinitionKey);
        // 以 UserTask 为主维度，原因是：流程图编辑后，一些规则实际就没用了。
        return CollectionUtils.convertList(userTaskList, task -> {
            WorkflowTaskAssignRule workflowTaskAssignRule = workflowTaskAssignRuleMap.get(task.getId());
            AssignRuleTaskVo assignRuleTaskVo = Convert.INSTANCE.convertToVo(workflowTaskAssignRule);
            if (Objects.isNull(assignRuleTaskVo)) {
                assignRuleTaskVo = new AssignRuleTaskVo();
                assignRuleTaskVo.setTaskDefinitionId(task.getId());
                assignRuleTaskVo.setTaskDefinitionKey(task.getId());
                assignRuleTaskVo.setTaskDefinitionName(task.getName());
            }
            return assignRuleTaskVo;
        });
    }

    /**
     * 数据转换
     *
     * @param form
     * @return
     */
    public WorkflowTaskAssignRule convert(AssignRuleTaskAddForm form) {
        return Convert.INSTANCE.convert(form);
    }

    /**
     * 数据转换
     *
     * @param form
     * @return
     */
    public WorkflowTaskAssignRule convert(AssignRuleTaskUpdateForm form) {
        return Convert.INSTANCE.convert(form);
    }

    /**
     * 数据转换
     * @param workflowTaskAssignRuleList
     * @param newProcessDefinitionId
     * @return
     */
    public static List<WorkflowTaskAssignRule> convertTaskRuleByNewProcessDefinitionId(List<WorkflowTaskAssignRule> workflowTaskAssignRuleList, String newProcessDefinitionId) {
        List<WorkflowTaskAssignRule> list = Lists.newArrayList();
        for (WorkflowTaskAssignRule workflowTaskAssignRule : workflowTaskAssignRuleList) {
            WorkflowTaskAssignRule newWorkflowTaskAssignRule = Convert.INSTANCE.convertToNew(workflowTaskAssignRule);
            newWorkflowTaskAssignRule.setProcessDefinitionId(newProcessDefinitionId);
            list.add(newWorkflowTaskAssignRule);
        }
        return list;
    }

    @Mapper
    public interface Convert {
        WorkflowTaskAssignRuleHelper.Convert INSTANCE = Mappers.getMapper(WorkflowTaskAssignRuleHelper.Convert.class);

        /**
         * 数据转换
         *
         * @param form
         * @return
         */
        @Mapping(target = "options", expression = "java(com.ah.cloud.permissions.biz.infrastructure.util.JsonUtils.toJsonString(form.getOptions()))")
        WorkflowTaskAssignRule convert(AssignRuleTaskAddForm form);

        /**
         * 数据转换
         *
         * @param form
         * @return
         */
        @Mapping(target = "options", expression = "java(com.ah.cloud.permissions.biz.infrastructure.util.JsonUtils.toJsonString(form.getOptions()))")
        WorkflowTaskAssignRule convert(AssignRuleTaskUpdateForm form);

        /**
         * 数据转换
         *
         * @param workflowTaskAssignRule
         * @return
         */
        @Mapping(target = "options", expression = "java(com.ah.cloud.permissions.biz.infrastructure.util.JsonUtils.jsonToSet(workflowTaskAssignRule.getOptions(), Long.class))")
        AssignRuleTaskVo convertToVo(WorkflowTaskAssignRule workflowTaskAssignRule);

        /**
         * 数据转换
         * @param workflowTaskAssignRule
         * @return
         */
        WorkflowTaskAssignRule convertToNew(WorkflowTaskAssignRule workflowTaskAssignRule);
    }
}
