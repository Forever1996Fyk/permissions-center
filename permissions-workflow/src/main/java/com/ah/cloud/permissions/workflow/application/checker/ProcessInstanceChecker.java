package com.ah.cloud.permissions.workflow.application.checker;

import com.ah.cloud.permissions.biz.infrastructure.exception.BizException;
import com.ah.cloud.permissions.enums.common.WorkflowErrorCodeEnum;
import com.ah.cloud.permissions.workflow.domain.instance.dto.StartProcessDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-06-13 13:30
 **/
@Component
public class ProcessInstanceChecker {

    public void checkStartProcessParam(StartProcessDTO dto) {
        if (StringUtils.isBlank(dto.getProcessDefinitionKey()) && StringUtils.isBlank(dto.getProcessDefinitionId())) {
            throw new BizException(WorkflowErrorCodeEnum.WORKFLOW_PROCESS_INSTANCE_START_FAILED_MISS_PARAM, "流程定义key，流程定义id至少存在一个");
        }
        if (StringUtils.isBlank(dto.getBusinessKey())) {
            throw new BizException(WorkflowErrorCodeEnum.WORKFLOW_PROCESS_INSTANCE_START_FAILED_MISS_PARAM, "业务key");
        }
        if (StringUtils.isBlank(dto.getProcessName())) {
            throw new BizException(WorkflowErrorCodeEnum.WORKFLOW_PROCESS_INSTANCE_START_FAILED_MISS_PARAM, "流程标题");
        }
    }
}
