package com.ah.cloud.permissions.workflow.application.strategy;

import com.ah.cloud.permissions.biz.application.manager.SysRoleManager;
import com.ah.cloud.permissions.biz.application.manager.SysUserManager;
import com.ah.cloud.permissions.biz.application.manager.UserAuthManager;
import com.ah.cloud.permissions.biz.domain.role.dto.SelectSysRoleDTO;
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
 * @create: 2022-06-30 18:13
 **/
@Slf4j
@Component
public class RoleTaskCandidateService extends AbstractTaskCandidateService {
    private final static String LOG_MARK = "RoleTaskCandidateService";
    @Resource
    private SysRoleManager sysRoleManager;
    @Resource
    private SysUserManager sysUserManager;
    @Resource
    private UserAuthManager userAuthManager;

    @Override
    protected TaskCandidateDTO doGet(GetTaskCandidateDTO getTaskCandidateDTO) {
        UserTaskAssignRuleDTO userTaskAssignRuleDTO = getTaskCandidateDTO.getUserTaskAssignRuleDTO();
        Set<Long> userIds = userAuthManager.listUserIdByRoleCodes(userTaskAssignRuleDTO.getOptions());
        List<SysUser> sysUserList = sysUserManager.listSysUserByUserIds(userIds);
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
        List<SelectSysRoleDTO> selectSysRoleList = sysRoleManager.selectSysRoleDTOList();
        return convertToSelectOptions(selectSysRoleList);
    }

    @Override
    public TaskAssignRuleTypeEnum getTaskAssignRuleTypeEnum() {
        return TaskAssignRuleTypeEnum.ROLE;
    }
}
