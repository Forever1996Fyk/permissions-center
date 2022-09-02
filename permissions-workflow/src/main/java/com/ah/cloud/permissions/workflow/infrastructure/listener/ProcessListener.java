package com.ah.cloud.permissions.workflow.infrastructure.listener;

import com.ah.cloud.permissions.biz.infrastructure.util.JsonUtils;
import com.ah.cloud.permissions.workflow.application.manager.WorkflowTaskFormManager;
import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.delegate.event.AbstractFlowableEngineEventListener;
import org.flowable.engine.delegate.event.FlowableProcessStartedEvent;
import org.flowable.engine.impl.persistence.entity.ExecutionEntity;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @program: permissions-center
 * @description: 流程监听器
 * @author: YuKai Fan
 * @create: 2022-06-29 17:41
 **/
@Slf4j
@Component
public class ProcessListener extends AbstractFlowableEngineEventListener {
    @Resource
    private WorkflowTaskFormManager workflowTaskFormManager;

    @Override
    protected void processStarted(FlowableProcessStartedEvent event) {
        log.info("ProcessListener[processStarted] processStarted event start, event is {}", JsonUtils.toJsonString(event));
        workflowTaskFormManager.addWorkflowProcessForm((ExecutionEntity) event.getEntity());
        log.info("ProcessListener[processStarted] process started end");
    }
}
