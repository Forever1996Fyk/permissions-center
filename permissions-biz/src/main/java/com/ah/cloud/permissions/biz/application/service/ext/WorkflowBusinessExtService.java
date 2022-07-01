package com.ah.cloud.permissions.biz.application.service.ext;

import com.ah.cloud.permissions.biz.infrastructure.repository.bean.WorkflowBusiness;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 工作流业务表 服务类
 * </p>
 *
 * @author auto_generation
 * @since 2022-06-17
 */
public interface WorkflowBusinessExtService extends IService<WorkflowBusiness> {

    /**
     * 根据流程实例id获取流程业务
     * @param processInstanceId
     * @param tenantId
     * @return
     */
    WorkflowBusiness findOneByProcessInstanceId(String processInstanceId, String tenantId);
}
