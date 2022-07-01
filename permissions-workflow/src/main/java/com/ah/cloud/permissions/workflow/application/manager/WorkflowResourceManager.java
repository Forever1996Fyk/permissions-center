package com.ah.cloud.permissions.workflow.application.manager;

import com.ah.cloud.permissions.biz.application.service.ext.WorkflowBusinessExtService;
import com.ah.cloud.permissions.biz.infrastructure.constant.PermissionsConstants;
import com.ah.cloud.permissions.biz.infrastructure.exception.BizException;
import com.ah.cloud.permissions.biz.infrastructure.repository.bean.WorkflowBusiness;
import com.ah.cloud.permissions.enums.common.DeletedEnum;
import com.ah.cloud.permissions.enums.common.WorkflowErrorCodeEnum;
import com.ah.cloud.permissions.workflow.application.service.ProcessDefinitionService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.flowable.bpmn.converter.BpmnXMLConverter;
import org.flowable.bpmn.model.BpmnModel;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * @program: permissions-center
 * @description: 流程资源管理
 * @author: YuKai Fan
 * @create: 2022-06-17 14:46
 **/
@Slf4j
@Component
public class WorkflowResourceManager {
    @Resource
    private ProcessDefinitionService processDefinitionService;
    @Resource
    private WorkflowBusinessExtService workflowBusinessExtService;

    /**
     * 根据业务id获取当前流程图
     * @param businessId
     * @return
     */
    public String getProcessDefinitionBpmnXmlByBusinessId(Long businessId) {
        WorkflowBusiness workflowBusiness = workflowBusinessExtService.getOne(
                new QueryWrapper<WorkflowBusiness>().lambda()
                        .eq(WorkflowBusiness::getId, businessId)
                        .eq(WorkflowBusiness::getDeleted, DeletedEnum.NO.value)
        );
        if (Objects.isNull(workflowBusiness)) {
            throw new BizException(WorkflowErrorCodeEnum.WORKFLOW_BUSINESS_NOT_EXISTED);
        }
        return this.getProcessDefinitionBpmnXml(workflowBusiness.getProcessDefinitionId());
    }

    /**
     * 根据流程定义id获取当前流程图
     * @param processDefinitionId
     * @return
     */
    public String getProcessDefinitionBpmnXml(String processDefinitionId) {
        BpmnModel bpmnModel = processDefinitionService.getBpmnModelByProcessDefinitionId(processDefinitionId);
        if (Objects.isNull(bpmnModel)) {
            return PermissionsConstants.STR_EMPTY;
        }
        BpmnXMLConverter converter = new BpmnXMLConverter();
        return new String(converter.convertToXML(bpmnModel), StandardCharsets.UTF_8);

    }
}
