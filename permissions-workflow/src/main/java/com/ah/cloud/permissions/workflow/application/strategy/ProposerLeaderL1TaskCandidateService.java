package com.ah.cloud.permissions.workflow.application.strategy;

import com.ah.cloud.permissions.biz.application.manager.SysUserManager;
import com.ah.cloud.permissions.biz.infrastructure.repository.bean.SysUser;
import com.ah.cloud.permissions.enums.workflow.TaskAssignRuleTypeEnum;
import com.ah.cloud.permissions.workflow.application.manager.WorkflowBusinessManager;
import com.ah.cloud.permissions.workflow.domain.candidate.dto.GetTaskCandidateDTO;
import com.ah.cloud.permissions.workflow.domain.candidate.dto.TaskCandidateDTO;
import com.ah.cloud.permissions.workflow.domain.task.vo.SelectAssignRuleOptionsVo;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.flowable.task.service.impl.persistence.entity.TaskEntity;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @program: permissions-center
 * @description: 当前申请人一级领导人
 * @author: YuKai Fan
 * @create: 2022-07-01 11:28
 **/
@Slf4j
@Component
public class ProposerLeaderL1TaskCandidateService extends AbstractTaskCandidateService {
    private final static String LOG_MARK = "ProposerLeaderL1TaskCandidateService";
    @Resource
    private SysUserManager sysUserManager;
    @Resource
    private WorkflowBusinessManager workflowBusinessManager;

    @Override
    protected TaskCandidateDTO doGet(GetTaskCandidateDTO getTaskCandidateDTO) {
        TaskEntity task = getTaskCandidateDTO.getTask();
        String processInstanceId = task.getProcessInstanceId();
        String tenantId = task.getTenantId();
        Long proposerId = workflowBusinessManager.getProposerIdByProcessInstanceId(processInstanceId, tenantId);
        SysUser sysUser = sysUserManager.findLeaderL1ByUserId(proposerId);
        TaskCandidateDTO.Candidate candidate = convert(sysUser);
        return TaskCandidateDTO.builder()
                .candidateList(Lists.newArrayList(candidate))
                .selectedAssignee(candidate)
                .taskAssignRuleTypeEnum(getTaskAssignRuleTypeEnum())
                .build();
    }

    @Override
    protected String getLogMark() {
        return LOG_MARK;
    }

    @Override
    public List<SelectAssignRuleOptionsVo> listSelectOptions() {
        return null;
    }

    @Override
    public TaskAssignRuleTypeEnum getTaskAssignRuleTypeEnum() {
        return TaskAssignRuleTypeEnum.PROPOSER_LEADER_L1;
    }
}
