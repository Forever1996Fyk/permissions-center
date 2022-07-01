package com.ah.cloud.permissions.workflow.application.service;

import com.ah.cloud.permissions.domain.common.PageResult;
import com.ah.cloud.permissions.workflow.domain.instance.dto.StartProcessDTO;
import com.ah.cloud.permissions.workflow.domain.instance.form.DeleteProcessForm;
import com.ah.cloud.permissions.workflow.domain.instance.form.ProcessInstanceStateForm;
import com.ah.cloud.permissions.workflow.domain.instance.query.InstanceQuery;
import com.ah.cloud.permissions.workflow.domain.instance.vo.ProcessInstanceVo;
import org.flowable.engine.runtime.ProcessInstance;

import java.util.List;

/**
 * @program: permissions-center
 * @description: 流程实例接口
 * @author: YuKai Fan
 * @create: 2022-06-30 11:08
 **/
public interface ProcessInstanceService {

    /**
     * 启动流程实例
     *
     * @param startProcessDTO
     * @return
     */
    ProcessInstance startProcess(StartProcessDTO startProcessDTO);

    /**
     * 变更流程实例状态
     *
     * @param form
     */
    void changeProcessInstanceState(ProcessInstanceStateForm form);

    /**
     * 删除流程实例
     *
     * @param form
     */
    void deleteProcessInstance(DeleteProcessForm form);

    /**
     * 分页查询流程实例
     * @param query
     * @return
     */
    PageResult<ProcessInstanceVo> pageProcessInstanceList(InstanceQuery query);
}
