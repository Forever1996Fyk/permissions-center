package com.ah.cloud.permissions.biz.application.service.ext;

import com.ah.cloud.permissions.biz.infrastructure.repository.bean.WorkflowFormModel;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-06-15 11:32
 **/
public interface WorkflowFormModelExtService extends IService<WorkflowFormModel> {

    /**
     * 根据code获取唯一的表单模型
     * @param code
     * @return
     */
    WorkflowFormModel findOneFormModelByCode(String code, String tenantId);

    /**
     * 查询表单集合
     * @param codes
     * @param tenantId
     * @return
     */
    List<WorkflowFormModel> listFormModelByCode(Collection<String> codes, String tenantId);
}
