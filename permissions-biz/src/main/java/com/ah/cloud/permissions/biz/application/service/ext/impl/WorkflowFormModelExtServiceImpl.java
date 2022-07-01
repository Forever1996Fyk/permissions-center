package com.ah.cloud.permissions.biz.application.service.ext.impl;

import com.ah.cloud.permissions.biz.application.service.WorkflowFormModelService;
import com.ah.cloud.permissions.biz.application.service.ext.WorkflowFormModelExtService;
import com.ah.cloud.permissions.biz.infrastructure.repository.bean.WorkflowFormModel;
import com.ah.cloud.permissions.biz.infrastructure.repository.mapper.WorkflowFormModelMapper;
import com.ah.cloud.permissions.enums.common.DeletedEnum;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-06-15 11:32
 **/
@Service
public class WorkflowFormModelExtServiceImpl extends ServiceImpl<WorkflowFormModelMapper, WorkflowFormModel> implements WorkflowFormModelExtService {

    @Override
    public WorkflowFormModel findOneFormModelByCode(String code, String tenantId) {
        return baseMapper.selectOne(
                new QueryWrapper<WorkflowFormModel>().lambda()
                        .eq(WorkflowFormModel::getCode, code)
                        .eq(StringUtils.isNotBlank(tenantId), WorkflowFormModel::getTenantId, tenantId)
                        .eq(WorkflowFormModel::getDeleted, DeletedEnum.NO.value)
        );
    }

    @Override
    public List<WorkflowFormModel> listFormModelByCode(Collection<String> codes, String tenantId) {
        return baseMapper.selectList(
                new QueryWrapper<WorkflowFormModel>().lambda()
                        .in(WorkflowFormModel::getCode, codes)
                        .eq(StringUtils.isNotBlank(tenantId), WorkflowFormModel::getTenantId, tenantId)
                        .eq(WorkflowFormModel::getDeleted, DeletedEnum.NO.value)
        );
    }
}
