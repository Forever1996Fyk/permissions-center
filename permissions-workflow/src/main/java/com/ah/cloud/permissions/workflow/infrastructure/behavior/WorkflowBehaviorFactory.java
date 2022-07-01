package com.ah.cloud.permissions.workflow.infrastructure.behavior;

import com.ah.cloud.permissions.workflow.application.manager.WorkflowTaskAssignRuleManager;
import com.ah.cloud.permissions.workflow.application.strategy.selector.TaskCandidateServiceSelector;
import com.ah.cloud.permissions.workflow.infrastructure.behavior.task.WorkflowUserTaskActivityBehavior;
import lombok.AllArgsConstructor;
import org.flowable.bpmn.model.UserTask;
import org.flowable.engine.impl.bpmn.behavior.UserTaskActivityBehavior;
import org.flowable.engine.impl.bpmn.parser.factory.DefaultActivityBehaviorFactory;

/**
 * @program: permissions-center
 * @description: 行为工厂, 如果有自定义的行为, 必须要用行为工厂, 否则flowable会用默认的DefaultActivityBehaviorFactory, 不会加载自定义行为
 * @author: YuKai Fan
 * @create: 2022-06-30 16:01
 **/
@AllArgsConstructor
public class WorkflowBehaviorFactory extends DefaultActivityBehaviorFactory {
    private TaskCandidateServiceSelector taskCandidateServiceSelector;
    private WorkflowTaskAssignRuleManager workflowTaskAssignRuleManager;

    @Override
    public UserTaskActivityBehavior createUserTaskActivityBehavior(UserTask userTask) {
        return new WorkflowUserTaskActivityBehavior(userTask, taskCandidateServiceSelector, workflowTaskAssignRuleManager);
    }
}
