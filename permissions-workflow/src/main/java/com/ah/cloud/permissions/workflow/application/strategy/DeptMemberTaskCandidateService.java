package com.ah.cloud.permissions.workflow.application.strategy;

import com.ah.cloud.permissions.biz.application.manager.UserAuthManager;
import com.ah.cloud.permissions.biz.infrastructure.repository.bean.SysUser;
import com.ah.cloud.permissions.enums.workflow.TaskAssignRuleTypeEnum;
import com.ah.cloud.permissions.workflow.domain.behavior.dto.UserTaskAssignRuleDTO;
import com.ah.cloud.permissions.workflow.domain.candidate.dto.GetTaskCandidateDTO;
import com.ah.cloud.permissions.workflow.domain.candidate.dto.TaskCandidateDTO;
import com.ah.cloud.permissions.workflow.domain.task.vo.SelectAssignRuleOptionsVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-01 10:39
 **/
@Slf4j
@Component
public class DeptMemberTaskCandidateService extends AbstractTaskCandidateService {
    private final static String LOG_MARK = "DeptMemberTaskCandidateService";
    @Resource
    private UserAuthManager userAuthManager;

    @Override
    protected TaskCandidateDTO doGet(GetTaskCandidateDTO getTaskCandidateDTO) {
        UserTaskAssignRuleDTO userTaskAssignRuleDTO = getTaskCandidateDTO.getUserTaskAssignRuleDTO();
        Set<String> deptCodes = userTaskAssignRuleDTO.getOptions();
        List<SysUser> sysUserList = userAuthManager.listUserIdByDeptCodes(deptCodes);
        List<TaskCandidateDTO.Candidate> candidateList = convert(sysUserList);
        return TaskCandidateDTO.builder()
                .taskAssignRuleTypeEnum(getTaskAssignRuleTypeEnum())
                .candidateList(candidateList)
                .selectedAssignee(chooseOneTaskAssignee(candidateList))
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
        return TaskAssignRuleTypeEnum.DEPT_MEMBER;
    }
}
