package com.ah.cloud.permissions.biz.application.service.ext.impl;

import com.ah.cloud.permissions.biz.application.service.ext.WorkflowFormProcessExtService;
import com.ah.cloud.permissions.biz.infrastructure.repository.bean.WorkflowFormProcess;
import com.ah.cloud.permissions.biz.infrastructure.repository.mapper.WorkflowFormProcessMapper;
import com.ah.cloud.permissions.enums.common.DeletedEnum;
import com.ah.cloud.permissions.enums.workflow.FormProcessTypeEnum;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-06-26 17:24
 **/
@Service
public class WorkflowFormProcessExtServiceImpl extends ServiceImpl<WorkflowFormProcessMapper, WorkflowFormProcess> implements WorkflowFormProcessExtService {

    @Override
    public WorkflowFormProcess findOneByProcessIdAndTaskKey(Long processId, String taskKey, String tenantId) {
        return baseMapper.selectOne(
                new QueryWrapper<WorkflowFormProcess>().lambda()
                        .eq(WorkflowFormProcess::getWorkflowProcessId, processId)
                        .eq(WorkflowFormProcess::getTaskKey, taskKey)
                        .eq(WorkflowFormProcess::getType, FormProcessTypeEnum.TASK_FORM.getType())
                        .eq(StringUtils.isNotBlank(tenantId), WorkflowFormProcess::getTenantId, tenantId)
                        .eq(WorkflowFormProcess::getDeleted, DeletedEnum.NO.value)
        );
    }

    @Override
    public WorkflowFormProcess findOneByProcessId(Long processId, String tenantId) {
        return baseMapper.selectOne(
                new QueryWrapper<WorkflowFormProcess>().lambda()
                        .eq(WorkflowFormProcess::getWorkflowProcessId, processId)
                        .eq(WorkflowFormProcess::getType, FormProcessTypeEnum.PROCESS_FORM.getType())
                        .eq(StringUtils.isNotBlank(tenantId), WorkflowFormProcess::getTenantId, tenantId)
                        .eq(WorkflowFormProcess::getDeleted, DeletedEnum.NO.value)
        );
    }
}
