package com.ah.cloud.permissions.workflow.application.manager;

import com.ah.cloud.permissions.biz.infrastructure.exception.BizException;
import com.ah.cloud.permissions.enums.common.WorkflowErrorCodeEnum;
import com.ah.cloud.permissions.enums.workflow.ProcessInstanceStateEnum;
import com.ah.cloud.permissions.workflow.application.helper.ProcessInstanceHelper;
import com.ah.cloud.permissions.workflow.domain.instance.form.DeleteProcessForm;
import com.ah.cloud.permissions.workflow.domain.instance.form.ProcessInstanceStateForm;
import com.ah.cloud.permissions.workflow.domain.instance.form.StartProcessForm;
import com.ah.cloud.permissions.workflow.domain.instance.query.InstanceQuery;
import com.ah.cloud.permissions.workflow.domain.instance.vo.ProcessInstanceVo;
import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.engine.runtime.ProcessInstanceQuery;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-06-10 10:13
 **/
@Slf4j
@Component
public class ProcessInstanceManager {
    @Resource
    private RuntimeService runtimeService;
    @Resource
    private RepositoryService repositoryService;
    @Resource
    private ProcessInstanceHelper processInstanceHelper;

    /**
     * 启动流程
     *
     * @param form 流程启动入参
     */
    public void startProcess(StartProcessForm form) {
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().deploymentId(form.getDeploymentId()).singleResult();
        if (Objects.isNull(processDefinition)) {
            throw new BizException(WorkflowErrorCodeEnum.WORKFLOW_PROCESS_INSTANCE_START_FAILED);
        }
        runtimeService.startProcessInstanceById(processDefinition.getId());
    }

    /**
     * 挂起流程
     * @param form
     */
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
    public void deleteProcessInstance(DeleteProcessForm form) {
        runtimeService.deleteProcessInstance(form.getProcessInstanceId(), form.getReason());
    }

    /**
     * 分页查询流程实例
     *
     * @param query
     * @return
     */
    public List<ProcessInstanceVo> pageProcessInstanceList(InstanceQuery query) {
        ProcessInstanceQuery processInstanceQuery = runtimeService.createProcessInstanceQuery();
        processInstanceHelper.buildProcessInstanceQuery(processInstanceQuery, query);
        List<ProcessInstance> processInstanceList = processInstanceQuery.listPage(query.getPageNum(), query.getPageSize());
        return processInstanceHelper.convertToVoList(processInstanceList);
    }

}
