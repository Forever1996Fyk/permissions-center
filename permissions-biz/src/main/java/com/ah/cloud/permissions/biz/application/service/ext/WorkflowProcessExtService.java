package com.ah.cloud.permissions.biz.application.service.ext;

import com.ah.cloud.permissions.biz.infrastructure.repository.bean.WorkflowProcess;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 流程设计表 服务类
 * </p>
 *
 * @author auto_generation
 * @since 2022-06-15
 */
public interface WorkflowProcessExtService extends IService<WorkflowProcess> {

    /**
     * 根据流程key获取唯一流程
     * @param processDefinitionId
     * @param tenantId
     * @return
     */
    WorkflowProcess findOneProcessByDefinitionId(String processDefinitionId, String tenantId);
}
