package com.ah.cloud.permissions.biz.application.service.ext.impl;

import com.ah.cloud.permissions.biz.application.service.WorkflowBusinessService;
import com.ah.cloud.permissions.biz.application.service.ext.WorkflowBusinessExtService;
import com.ah.cloud.permissions.biz.infrastructure.repository.bean.WorkflowBusiness;
import com.ah.cloud.permissions.biz.infrastructure.repository.mapper.WorkflowBusinessMapper;
import com.ah.cloud.permissions.biz.infrastructure.util.SecurityUtils;
import com.ah.cloud.permissions.enums.common.DeletedEnum;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 工作流业务表 服务实现类
 * </p>
 *
 * @author auto_generation
 * @since 2022-06-17
 */
@Service
public class WorkflowBusinessExtServiceImpl extends ServiceImpl<WorkflowBusinessMapper, WorkflowBusiness> implements WorkflowBusinessExtService {

    @Override
    public WorkflowBusiness findOneByProcessInstanceId(String processInstanceId, String tenantId) {
        return baseMapper.selectOne(
                new QueryWrapper<WorkflowBusiness>().lambda()
                        .eq(WorkflowBusiness::getProcessInstanceId, processInstanceId)
                        .eq(StringUtils.isNotBlank(tenantId), WorkflowBusiness::getTenantId, tenantId)
                        .eq(WorkflowBusiness::getDeleted, DeletedEnum.NO.value)
        );
    }
}
