package com.ah.cloud.permissions.workflow.application.strategy;

import com.ah.cloud.permissions.enums.workflow.TaskAssignRuleTypeEnum;
import com.ah.cloud.permissions.workflow.domain.candidate.dto.GetTaskCandidateDTO;
import com.ah.cloud.permissions.workflow.domain.candidate.dto.TaskCandidateDTO;
import com.ah.cloud.permissions.workflow.domain.task.vo.SelectAssignRuleOptionsVo;

import java.util.List;

/**
 * @program: permissions-center
 * @description: 任务候选人策略
 * @author: YuKai Fan
 * @create: 2022-06-30 17:08
 **/
public interface TaskCandidateService {

    /**
     * 获取任务候选人
     * @param getTaskCandidateDTO
     * @return
     */
    TaskCandidateDTO getTaskCandidate(GetTaskCandidateDTO getTaskCandidateDTO);

    /**
     * 获取对应的选择配置
     * @return
     */
    List<SelectAssignRuleOptionsVo> listSelectOptions();

    /**
     * 获取任务规则类型
     * @return
     */
    TaskAssignRuleTypeEnum getTaskAssignRuleTypeEnum();
}
