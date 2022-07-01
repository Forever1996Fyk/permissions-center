package com.ah.cloud.permissions.workflow.application.helper;

import com.ah.cloud.permissions.biz.infrastructure.repository.bean.WorkflowBusiness;
import com.ah.cloud.permissions.biz.infrastructure.repository.bean.WorkflowProcess;
import com.ah.cloud.permissions.biz.infrastructure.util.AppUtils;
import com.ah.cloud.permissions.biz.infrastructure.util.JsonUtils;
import com.ah.cloud.permissions.biz.infrastructure.util.SecurityUtils;
import com.ah.cloud.permissions.domain.common.PageResult;
import com.ah.cloud.permissions.enums.workflow.WorkFlowStatusEnum;
import com.ah.cloud.permissions.workflow.domain.business.form.WorkflowBusinessStartForm;
import com.ah.cloud.permissions.workflow.domain.business.vo.UserSubmitProcessVo;
import com.github.pagehelper.PageInfo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-06-17 09:58
 **/
@Component
public class WorkflowBusinessHelper {

    /**
     * 获取业务标题
     * @param processName
     * @return
     */
    public String getBusinessName(String processName) {
        return "【" +
                SecurityUtils.getUserNameBySession() +
                "】" +
                "的" +
                processName;
    }

    /**
     * 构建业务流程
     * @param form
     * @param workflowProcess
     * @return
     */
    public WorkflowBusiness buildWorkflowBusiness(WorkflowBusinessStartForm form, WorkflowProcess workflowProcess) {
        WorkflowBusiness workflowBusiness = new WorkflowBusiness();
        workflowBusiness.setProcessId(workflowProcess.getId());
        workflowBusiness.setProcessDefinitionId(workflowProcess.getProcessDefinitionId());
        workflowBusiness.setProcessDefinitionKey(workflowProcess.getProcessDefinitionKey());
        workflowBusiness.setBusinessKey(AppUtils.simpleUUID());
        workflowBusiness.setFlowStatus(WorkFlowStatusEnum.STARTED.getStatus());
        workflowBusiness.setStartTime(new Date());
        workflowBusiness.setProposerId(SecurityUtils.getUserIdBySession());
        workflowBusiness.setProposer(SecurityUtils.getUserNameBySession());
        workflowBusiness.setCreator(SecurityUtils.getUserNameBySession());
        workflowBusiness.setModifier(SecurityUtils.getUserNameBySession());
        return workflowBusiness;
    }

    public PageResult<UserSubmitProcessVo> convertToPageResult(PageInfo<WorkflowBusiness> pageInfo) {
        PageResult<UserSubmitProcessVo> pageResult = new PageResult<>();
        pageResult.setPageNum(pageInfo.getPageNum());
        pageResult.setPageSize(pageInfo.getPageSize());
        pageResult.setPages(pageInfo.getPages());
        pageResult.setTotal(pageInfo.getTotal());
        pageResult.setRows(Convert.INSTANCE.convertToUserSubmitProcessVoList(pageInfo.getList()));
        return pageResult;
    }

    @Mapper
    public interface Convert {
        WorkflowBusinessHelper.Convert INSTANCE = Mappers.getMapper(WorkflowBusinessHelper.Convert.class);

        /**
         * 数据转换
         * @param workflowBusiness
         * @return
         */
        UserSubmitProcessVo convertToUserSubmitProcessVo(WorkflowBusiness workflowBusiness);
        /**
         * 数据转换
         * @param workflowBusinessList
         * @return
         */
        List<UserSubmitProcessVo> convertToUserSubmitProcessVoList(List<WorkflowBusiness> workflowBusinessList);
    }
}
