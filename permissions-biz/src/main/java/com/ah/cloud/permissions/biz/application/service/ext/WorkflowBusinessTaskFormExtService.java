package com.ah.cloud.permissions.biz.application.service.ext;

import com.ah.cloud.permissions.biz.infrastructure.repository.bean.WorkflowBusinessTaskForm;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 工作流业务表单任务关联表 服务类
 * </p>
 *
 * @author auto_generation
 * @since 2022-06-26
 */
public interface WorkflowBusinessTaskFormExtService extends IService<WorkflowBusinessTaskForm> {

    /**
     * 根据任务key，业务id获取唯一的业务流程任务表单
     * @param taskKey
     * @param businessId
     * @param tenantId
     * @return
     */
    WorkflowBusinessTaskForm findOneByTaskKeyAndBusinessId(String taskKey, Long businessId, String tenantId);

}
