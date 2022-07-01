package com.ah.cloud.permissions.workflow.application.strategy.selector;

import com.ah.cloud.permissions.biz.infrastructure.exception.BizException;
import com.ah.cloud.permissions.enums.common.ErrorCodeEnum;
import com.ah.cloud.permissions.enums.workflow.TaskAssignRuleTypeEnum;
import com.ah.cloud.permissions.workflow.application.strategy.TaskCandidateService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-06-30 18:28
 **/
@Component
public class TaskCandidateServiceSelector {
    @Resource
    private List<TaskCandidateService> taskCandidateServiceList;

    public TaskCandidateService select(TaskAssignRuleTypeEnum ruleTypeEnum) {
        for (TaskCandidateService taskCandidateService : taskCandidateServiceList) {
            if (Objects.equals(taskCandidateService.getTaskAssignRuleTypeEnum(), ruleTypeEnum)) {
                return taskCandidateService;
            }
        }
        throw new BizException(ErrorCodeEnum.SELECTOR_NOT_EXISTED, "TaskCandidateServiceSelector");
    }
}
