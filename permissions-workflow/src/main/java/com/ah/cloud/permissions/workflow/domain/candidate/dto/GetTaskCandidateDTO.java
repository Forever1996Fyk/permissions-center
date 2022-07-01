package com.ah.cloud.permissions.workflow.domain.candidate.dto;

import com.ah.cloud.permissions.workflow.domain.behavior.dto.UserTaskAssignRuleDTO;
import lombok.*;
import org.flowable.task.service.impl.persistence.entity.TaskEntity;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-06-30 17:47
 **/
@Getter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class GetTaskCandidateDTO {

    /**
     * 任务实例
     */
    private TaskEntity task;

    /**
     * 用户任务规则
     */
    private UserTaskAssignRuleDTO userTaskAssignRuleDTO;
}
