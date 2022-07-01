package com.ah.cloud.permissions.biz.application.service.ext.impl;

import com.ah.cloud.permissions.biz.application.service.WorkflowBusinessTaskFormService;
import com.ah.cloud.permissions.biz.application.service.ext.WorkflowBusinessTaskFormExtService;
import com.ah.cloud.permissions.biz.infrastructure.repository.bean.WorkflowBusinessTaskForm;
import com.ah.cloud.permissions.biz.infrastructure.repository.mapper.WorkflowBusinessTaskFormMapper;
import com.ah.cloud.permissions.enums.common.DeletedEnum;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 工作流业务表单任务关联表 服务实现类
 * </p>
 *
 * @author auto_generation
 * @since 2022-06-26
 */
@Service
public class WorkflowBusinessTaskFormExtServiceImpl extends ServiceImpl<WorkflowBusinessTaskFormMapper, WorkflowBusinessTaskForm> implements WorkflowBusinessTaskFormExtService {

    @Override
    public WorkflowBusinessTaskForm findOneByTaskKeyAndBusinessId(String taskKey, Long businessId, String tenantId) {
        return baseMapper.selectOne(
                new QueryWrapper<WorkflowBusinessTaskForm>().lambda()
                        .eq(WorkflowBusinessTaskForm::getTaskKey, taskKey)
                        .eq(WorkflowBusinessTaskForm::getWorkflowBusinessId, businessId)
                        .eq(StringUtils.isNotBlank(tenantId), WorkflowBusinessTaskForm::getTenantId, tenantId)
                        .eq(WorkflowBusinessTaskForm::getDeleted, DeletedEnum.NO.value)
        );
    }
}
