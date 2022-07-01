package com.ah.cloud.permissions.biz.application.service.ext.impl;

import com.ah.cloud.permissions.biz.application.service.WorkflowProcessService;
import com.ah.cloud.permissions.biz.application.service.ext.WorkflowProcessExtService;
import com.ah.cloud.permissions.biz.infrastructure.repository.bean.WorkflowProcess;
import com.ah.cloud.permissions.biz.infrastructure.repository.mapper.WorkflowProcessMapper;
import com.ah.cloud.permissions.enums.common.DeletedEnum;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 流程设计表 服务实现类
 * </p>
 *
 * @author auto_generation
 * @since 2022-06-15
 */
@Service
public class WorkflowProcessExtServiceImpl extends ServiceImpl<WorkflowProcessMapper, WorkflowProcess> implements WorkflowProcessExtService {

    @Override
    public WorkflowProcess findOneProcessByDefinitionId(String processDefinitionId, String tenantId) {
        return baseMapper.selectOne(
                new QueryWrapper<WorkflowProcess>().lambda()
                        .eq(WorkflowProcess::getProcessDefinitionId, processDefinitionId)
                        .eq(StringUtils.isNotBlank(tenantId), WorkflowProcess::getTenantId, tenantId)
                        .eq(WorkflowProcess::getDeleted, DeletedEnum.NO.value)
        );
    }
}
