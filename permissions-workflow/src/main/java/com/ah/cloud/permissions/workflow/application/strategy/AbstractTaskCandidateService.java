package com.ah.cloud.permissions.workflow.application.strategy;

import cn.hutool.core.util.RandomUtil;
import com.ah.cloud.permissions.biz.infrastructure.exception.BizException;
import com.ah.cloud.permissions.biz.infrastructure.repository.bean.SysUser;
import com.ah.cloud.permissions.biz.infrastructure.util.CollectionUtils;
import com.ah.cloud.permissions.biz.infrastructure.util.JsonUtils;
import com.ah.cloud.permissions.domain.dto.SelectEntity;
import com.ah.cloud.permissions.enums.workflow.WorkflowErrorCodeEnum;
import com.ah.cloud.permissions.workflow.domain.candidate.dto.GetTaskCandidateDTO;
import com.ah.cloud.permissions.workflow.domain.candidate.dto.TaskCandidateDTO;
import com.ah.cloud.permissions.workflow.domain.task.vo.SelectAssignRuleOptionsVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-06-30 18:11
 **/
@Slf4j
@Component
public abstract class AbstractTaskCandidateService implements TaskCandidateService {

    @Override
    public TaskCandidateDTO getTaskCandidate(GetTaskCandidateDTO getTaskCandidateDTO) {
        log.info("{}[getTaskCandidate] get task candidate, param is {}", getLogMark(), JsonUtils.toJSONString(getTaskCandidateDTO));
        TaskCandidateDTO taskCandidateDTO = doGet(getTaskCandidateDTO);
        if (Objects.isNull(taskCandidateDTO)) {
            log.error("{}[getTaskCandidate] get task candidate failed, reason is taskCandidateDTO empty, params is {}", getLogMark(), JsonUtils.toJSONString(getTaskCandidateDTO));
            throw new BizException(WorkflowErrorCodeEnum.WORKFLOW_TASK_FAILED_USER_TASK);
        }
        List<TaskCandidateDTO.Candidate> candidateList = taskCandidateDTO.getCandidateList();
        if (CollectionUtils.isEmpty(candidateList)) {
            handleNoGoodCandidates(getTaskCandidateDTO, taskCandidateDTO);
        }
        return taskCandidateDTO;
    }

    /**
     * 处理没有合适的候选人
     * <p>
     * 默认抛出异常, 业务逻辑子类可以重写实现
     * 1、挂起流程
     * 2、直接结束流程
     * 3、强制选一个候选人
     *
     * @param getTaskCandidateDTO
     * @param taskCandidateDTO
     */
    protected void handleNoGoodCandidates(GetTaskCandidateDTO getTaskCandidateDTO, TaskCandidateDTO taskCandidateDTO) {
        log.error("{}[getTaskCandidate] get task candidate failed, reason is no good candidate, params is {}", getLogMark(), JsonUtils.toJSONString(getTaskCandidateDTO));
        throw new BizException(WorkflowErrorCodeEnum.WORKFLOW_TASK_FAILED_NO_GOOD_CANDIDATE);
    }

    /**
     * 获取候选人逻辑
     *
     * @param getTaskCandidateDTO
     * @return
     */
    protected abstract TaskCandidateDTO doGet(GetTaskCandidateDTO getTaskCandidateDTO);

    /**
     * 获取日志标识
     *
     * @return
     */
    protected abstract String getLogMark();


    /**
     * 选择一个任务处理人
     *
     * @param candidateList
     * @return
     */
    protected TaskCandidateDTO.Candidate chooseOneTaskAssignee(List<TaskCandidateDTO.Candidate> candidateList) {
        int index = RandomUtil.randomInt(candidateList.size());
        return candidateList.get(index);
    }

    /**
     * 数据转换
     *
     * @param sysUserList
     * @return
     */
    protected List<TaskCandidateDTO.Candidate> convert(List<SysUser> sysUserList) {
        return sysUserList.stream()
                .map(this::convert)
                .collect(Collectors.toList());
    }

    /**
     * 转换选择集合
     * @param list
     * @param <T>
     * @return
     */
    protected <T extends SelectEntity> List<SelectAssignRuleOptionsVo> convertToSelectOptions(List<T> list) {
        return list.stream()
                .map(item -> SelectAssignRuleOptionsVo.builder().code(item.getCode()).name(item.getName()).build())
                .collect(Collectors.toList());
    }

    /**
     * 数据转换
     * @param sysUser
     * @return
     */
    protected TaskCandidateDTO.Candidate convert(SysUser sysUser) {
        return TaskCandidateDTO.Candidate.builder()
                .userId(sysUser.getUserId())
                .name(sysUser.getNickName())
                .build();
    }
}
