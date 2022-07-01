package com.ah.cloud.permissions.workflow.infrastructure.config;

import com.ah.cloud.permissions.workflow.application.manager.WorkflowTaskAssignRuleManager;
import com.ah.cloud.permissions.workflow.application.strategy.selector.TaskCandidateServiceSelector;
import com.ah.cloud.permissions.workflow.infrastructure.behavior.WorkflowBehaviorFactory;
import com.google.common.collect.Lists;
import org.flowable.common.engine.api.delegate.event.FlowableEventListener;
import org.flowable.spring.SpringProcessEngineConfiguration;
import org.flowable.spring.boot.EngineConfigurationConfigurer;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-01 10:15
 **/
@Configuration
public class FlowableConfiguration {

    /**
     * bpmn模块的 ProcessEngineConfigurationConfigurer 实现类:
     *
     * 1. 设置监听器
     * 2. 设置自定义 ActivityBehaviorFactory
     *
     * @param listeners
     * @param workflowBehaviorFactory
     * @return
     */
    @Bean
    public EngineConfigurationConfigurer<SpringProcessEngineConfiguration> processEngineConfigurationEngineConfigurationConfigurer(
            ObjectProvider<FlowableEventListener> listeners,
            WorkflowBehaviorFactory workflowBehaviorFactory) {

        return configuration -> {
            // 注册监听器
            configuration.setEventListeners(Lists.newArrayList(listeners.iterator()));
            configuration.setActivityBehaviorFactory(workflowBehaviorFactory);
        };
    }

    /**
     * 自定义行为工厂
     *
     * 配置流程的任务行为
     *
     * @param workflowTaskAssignRuleManager
     * @param taskCandidateServiceSelector
     * @return
     */
    @Bean
    public WorkflowBehaviorFactory workflowBehaviorFactory(WorkflowTaskAssignRuleManager workflowTaskAssignRuleManager, TaskCandidateServiceSelector taskCandidateServiceSelector) {
        return new WorkflowBehaviorFactory(taskCandidateServiceSelector, workflowTaskAssignRuleManager);
    }
}
