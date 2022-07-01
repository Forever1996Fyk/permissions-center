package com.ah.cloud.permissions.workflow.application.service.impl;

import com.ah.cloud.permissions.biz.infrastructure.exception.BizException;
import com.ah.cloud.permissions.biz.infrastructure.util.JsonUtils;
import com.ah.cloud.permissions.domain.common.PageResult;
import com.ah.cloud.permissions.enums.common.WorkflowErrorCodeEnum;
import com.ah.cloud.permissions.enums.workflow.ProcessInstanceStateEnum;
import com.ah.cloud.permissions.workflow.application.checker.ProcessInstanceChecker;
import com.ah.cloud.permissions.workflow.application.helper.ProcessInstanceHelper;
import com.ah.cloud.permissions.workflow.application.service.ProcessInstanceService;
import com.ah.cloud.permissions.workflow.domain.instance.dto.StartProcessDTO;
import com.ah.cloud.permissions.workflow.domain.instance.form.DeleteProcessForm;
import com.ah.cloud.permissions.workflow.domain.instance.form.ProcessInstanceStateForm;
import com.ah.cloud.permissions.workflow.domain.instance.query.InstanceQuery;
import com.ah.cloud.permissions.workflow.domain.instance.vo.ProcessInstanceVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.repository.ProcessDefinitionQuery;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.engine.runtime.ProcessInstanceQuery;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-06-30 11:14
 **/
@Slf4j
@Service
public class ProcessInstanceServiceImpl implements ProcessInstanceService {
    @Resource
    private RuntimeService runtimeService;
    @Resource
    private RepositoryService repositoryService;
    @Resource
    private ProcessInstanceHelper processInstanceHelper;
    @Resource
    private ProcessInstanceChecker processInstanceChecker;

    /**
     * 启动流程实例
     *
     * @param startProcessDTO
     * @return
     */
    @Override
    public ProcessInstance startProcess(StartProcessDTO startProcessDTO) {
        // 参数校验
        processInstanceChecker.checkStartProcessParam(startProcessDTO);
        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();
        ProcessDefinition processDefinition;
        if (StringUtils.isBlank(startProcessDTO.getProcessDefinitionKey())) {
            processDefinition = processDefinitionQuery.processDefinitionId(startProcessDTO.getProcessDefinitionId()).singleResult();
        } else {
            processDefinition = processDefinitionQuery.processDefinitionKey(startProcessDTO.getProcessDefinitionKey())
                    .latestVersion()
                    .singleResult();
        }
        if (Objects.isNull(processDefinition)) {
            throw new BizException(WorkflowErrorCodeEnum.WORKFLOW_PROCESS_INSTANCE_START_FAILED_MISS_PARAM, startProcessDTO.getProcessDefinitionKey());
        }
        // 构建流程实例实体，并启动
        ProcessInstance processInstance = runtimeService.createProcessInstanceBuilder()
                .businessKey(startProcessDTO.getBusinessKey())
                .name(startProcessDTO.getProcessName())
                .processDefinitionKey(processDefinition.getKey())
                .processDefinitionId(processDefinition.getId())
                .variables(startProcessDTO.getVariables())
                .start();
        log.info("ProcessInstanceManager[startProcess] start process get instance is {}", JsonUtils.toJSONString(processInstance));
        return processInstance;
    }

    /**
     * 挂起流程
     *
     * @param form
     */
    @Override
    public void changeProcessInstanceState(ProcessInstanceStateForm form) {
        if (Objects.equals(form.getState(), ProcessInstanceStateEnum.ACTIVATE.getState())) {
            runtimeService.activateProcessInstanceById(form.getProcessInstanceId());
        } else if (Objects.equals(form.getState(), ProcessInstanceStateEnum.SUSPEND.getState())) {
            runtimeService.suspendProcessInstanceById(form.getProcessInstanceId());
        }
    }

    /**
     * 删除流程实例
     *
     * @param form
     */
    @Override
    public void deleteProcessInstance(DeleteProcessForm form) {
        runtimeService.deleteProcessInstance(form.getProcessInstanceId(), form.getReason());
    }

    /**
     * 分页查询流程实例
     *
     * @param query
     * @return
     */
    @Override
    public PageResult<ProcessInstanceVo> pageProcessInstanceList(InstanceQuery query) {
        ProcessInstanceQuery processInstanceQuery = runtimeService.createProcessInstanceQuery();
        processInstanceHelper.buildProcessInstanceQuery(processInstanceQuery, query);
        List<ProcessInstance> processInstanceList = processInstanceQuery
                .orderByStartTime().desc()
                .listPage(query.getPageNum(), query.getPageSize());
        long count = processInstanceQuery.count();
        return processInstanceHelper.convertToVoPageResult(processInstanceList, count, query);
    }
}
