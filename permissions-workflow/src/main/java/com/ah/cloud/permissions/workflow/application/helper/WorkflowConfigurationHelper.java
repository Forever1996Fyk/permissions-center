package com.ah.cloud.permissions.workflow.application.helper;

import com.ah.cloud.permissions.biz.infrastructure.repository.bean.WorkflowFormModel;
import com.ah.cloud.permissions.biz.infrastructure.repository.bean.WorkflowFormProcess;
import com.ah.cloud.permissions.biz.infrastructure.util.SecurityUtils;
import com.ah.cloud.permissions.domain.common.PageResult;
import com.ah.cloud.permissions.workflow.domain.configuration.form.ProcessFormConfigurationAddForm;
import com.ah.cloud.permissions.workflow.domain.configuration.form.ProcessFormConfigurationUpdateForm;
import com.ah.cloud.permissions.workflow.domain.configuration.vo.ProcessFormConfigurationVo;
import com.github.pagehelper.PageInfo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-06-15 17:15
 **/
@Component
public class WorkflowConfigurationHelper {

    /**
     * 数据转换
     * @param form
     * @param workflowFormModel
     * @return
     */
    public WorkflowFormProcess convert(ProcessFormConfigurationAddForm form, WorkflowFormModel workflowFormModel) {
        WorkflowFormProcess workflowFormProcess = Convert.INSTANCE.convert(form);
        workflowFormProcess.setFormCode(workflowFormModel.getCode());
        workflowFormProcess.setCreator(SecurityUtils.getUserNameBySession());
        workflowFormProcess.setModifier(SecurityUtils.getUserNameBySession());
        return workflowFormProcess;
    }

    /**
     * 数据转换
     * @param form
     * @return
     */
    public WorkflowFormProcess convert(ProcessFormConfigurationUpdateForm form) {
        WorkflowFormProcess workflowFormProcess = Convert.INSTANCE.convert(form);
        workflowFormProcess.setModifier(SecurityUtils.getUserNameBySession());
        return workflowFormProcess;
    }

    /**
     * 数据转换
     * @param workflowFormProcess
     * @return
     */
    public ProcessFormConfigurationVo convertToVo(WorkflowFormProcess workflowFormProcess) {
        return Convert.INSTANCE.convertToVo(workflowFormProcess);
    }

    /**
     * 数据转换
     * @param pageInfo
     * @return
     */
    public PageResult<ProcessFormConfigurationVo> convertToPageResult(PageInfo<WorkflowFormProcess> pageInfo) {
        PageResult<ProcessFormConfigurationVo> pageResult = new PageResult<>();
        pageResult.setPageSize(pageInfo.getPageSize());
        pageResult.setPages(pageInfo.getPages());
        pageResult.setPageNum(pageInfo.getPageNum());
        pageResult.setTotal(pageInfo.getTotal());
        pageResult.setRows(Convert.INSTANCE.convertToVoList(pageInfo.getList()));
        return pageResult;
    }

    @Mapper
    public interface Convert {
        WorkflowConfigurationHelper.Convert INSTANCE = Mappers.getMapper(WorkflowConfigurationHelper.Convert.class);

        /**
         * 数据转换
         * @param form
         * @return
         */
        WorkflowFormProcess convert(ProcessFormConfigurationAddForm form);

        /**
         * 数据转换
         * @param form
         * @return
         */
        WorkflowFormProcess convert(ProcessFormConfigurationUpdateForm form);

        /**
         * 数据转换
         * @param workflowFormModel
         * @return
         */
        ProcessFormConfigurationVo convertToVo(WorkflowFormProcess workflowFormModel);

        /**
         * 数据转换
         * @param workflowFormModelList
         * @return
         */
        List<ProcessFormConfigurationVo> convertToVoList(List<WorkflowFormProcess> workflowFormModelList);
    }
}
