package com.ah.cloud.permissions.biz.application.service.ext;

import com.ah.cloud.permissions.biz.infrastructure.repository.bean.WorkflowFormProcess;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-06-26 17:23
 **/
public interface WorkflowFormProcessExtService extends IService<WorkflowFormProcess> {

    /**
     * 根据流程id，任务key获取唯一的流程表单信息
     *
     * @param processId
     * @param taskKey
     * @param tenantId
     * @return
     */
    WorkflowFormProcess findOneByProcessIdAndTaskKey(Long processId, String taskKey, String tenantId);

    /**
     * 根据流程id，任务key获取唯一的流程表单信息
     *
     * @param processId
     * @param taskKey
     * @param tenantId
     * @return
     */
    WorkflowFormProcess findOneByProcessId(Long processId, String tenantId);
}
