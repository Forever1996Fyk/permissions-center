package com.ah.cloud.permissions.workflow.application.manager;

import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.ProcessEngine;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.repository.Deployment;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @program: permissions-center
 * @description: 流程管理
 * @author: YuKai Fan
 * @create: 2022-06-08 14:54
 **/
@Slf4j
@Component
public class ProcessManager {
    @Resource
    private ProcessEngine processEngine;

    public void deploy() {
        RepositoryService repositoryService = processEngine.getRepositoryService();
        Deployment deploy = repositoryService.createDeployment()
                .addClasspathResource("leave_v1.bpmn20.xml")
                .deploy();
    }
}
