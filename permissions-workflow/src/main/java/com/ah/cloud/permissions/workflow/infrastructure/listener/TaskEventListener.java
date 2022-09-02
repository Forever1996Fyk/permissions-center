package com.ah.cloud.permissions.workflow.infrastructure.listener;

import com.ah.cloud.permissions.biz.infrastructure.util.JsonUtils;
import com.ah.cloud.permissions.workflow.application.manager.WorkflowBusinessManager;
import com.ah.cloud.permissions.workflow.application.manager.WorkflowTaskFormManager;
import lombok.extern.slf4j.Slf4j;
import org.flowable.common.engine.api.delegate.event.FlowableEngineEntityEvent;
import org.flowable.engine.delegate.event.AbstractFlowableEngineEventListener;
import org.flowable.task.service.impl.persistence.entity.TaskEntity;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-06-26 16:34
 **/
@Slf4j
@Component
public class TaskEventListener extends AbstractFlowableEngineEventListener {
    @Resource
    private WorkflowTaskFormManager workflowTaskFormManager;
    @Resource
    private WorkflowBusinessManager workflowBusinessManager;


    @Override
    protected void taskCompleted(FlowableEngineEntityEvent event) {
        log.info("TaskEventListener[taskCompleted] task completed run event start, event is {}", JsonUtils.toJsonString(event));
        workflowTaskFormManager.addWorkflowTaskForm((TaskEntity) event.getEntity());

        // 更新流程
//        boolean result = workflowBusinessManager.updateWorkflowProcess();
//        if (!result) {
//            throw new BizException(WorkflowErrorCodeEnum.WORKFLOW_TASK_MAYBE_HANDLED);
//        }
        log.info("TaskEventListener[taskCompleted] task completed run event end");
    }


}
