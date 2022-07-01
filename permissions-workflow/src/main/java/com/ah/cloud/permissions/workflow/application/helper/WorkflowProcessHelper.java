package com.ah.cloud.permissions.workflow.application.helper;

import com.ah.cloud.permissions.biz.infrastructure.repository.bean.WorkflowFormModel;
import com.ah.cloud.permissions.biz.infrastructure.repository.bean.WorkflowProcess;
import com.ah.cloud.permissions.biz.infrastructure.util.SecurityUtils;
import com.ah.cloud.permissions.domain.common.PageResult;
import com.ah.cloud.permissions.enums.YesOrNoEnum;
import com.ah.cloud.permissions.enums.workflow.BusinessCategoryEnum;
import com.ah.cloud.permissions.workflow.domain.form.form.AddForm;
import com.ah.cloud.permissions.workflow.domain.form.form.UpdateForm;
import com.ah.cloud.permissions.workflow.domain.form.vo.FormModelVo;
import com.ah.cloud.permissions.workflow.domain.model.dto.CreateModelDTO;
import com.ah.cloud.permissions.workflow.domain.model.dto.UpdateModelDTO;
import com.ah.cloud.permissions.workflow.domain.process.form.WorkflowProcessAddForm;
import com.ah.cloud.permissions.workflow.domain.process.form.WorkflowProcessUpdateForm;
import com.ah.cloud.permissions.workflow.domain.process.vo.BusinessWorkflowDetailVo;
import com.ah.cloud.permissions.workflow.domain.process.vo.WorkflowProcessVo;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-06-15 14:12
 **/
@Component
public class WorkflowProcessHelper {

    /**
     * 数据转换
     * @param form
     * @return
     */
    public WorkflowProcess convert(WorkflowProcessAddForm form) {
        WorkflowProcess workflowProcess = Convert.INSTANCE.convert(form);
        workflowProcess.setStatus(YesOrNoEnum.NO.getType());
        workflowProcess.setCreator(SecurityUtils.getUserNameBySession());
        workflowProcess.setModifier(SecurityUtils.getUserNameBySession());
        return workflowProcess;
    }

    /**
     * 数据转换
     * @param form
     * @return
     */
    public WorkflowProcess convert(WorkflowProcessUpdateForm form) {
        WorkflowProcess workflowProcess = Convert.INSTANCE.convert(form);
        workflowProcess.setModifier(SecurityUtils.getUserNameBySession());
        return workflowProcess;
    }

    /**
     * 数据转换
     * @param form
     * @return
     */
    public CreateModelDTO convertToDTO(WorkflowProcessAddForm form) {
        return CreateModelDTO.builder()
                .modelDesc(form.getDescription())
                .modelKey(form.getKey())
                .modelName(form.getName())
                .processCategory(BusinessCategoryEnum.getByCategory(form.getProcessCategory()))
                .build();
    }

    /**
     * 数据转换
     * @param form
     * @return
     */
    public UpdateModelDTO convertToDTO(WorkflowProcessUpdateForm form) {
        return UpdateModelDTO.builder()
                .modelDesc(form.getDescription())
                .modelKey(form.getKey())
                .modelName(form.getName())
                .processCategory(BusinessCategoryEnum.getByCategory(form.getProcessCategory()))
                .build();
    }

    /**
     * 数据转换
     * @param workflowProcess
     * @return
     */
    public WorkflowProcessVo convertToVo(WorkflowProcess workflowProcess) {
        return Convert.INSTANCE.convertToVo(workflowProcess);
    }

    /**
     * 数据转换
     * @param pageInfo
     * @return
     */
    public PageResult<WorkflowProcessVo> convertToPageResult(PageInfo<WorkflowProcess> pageInfo) {
        PageResult<WorkflowProcessVo> pageResult = new PageResult<>();
        pageResult.setPageSize(pageInfo.getPageSize());
        pageResult.setPageNum(pageInfo.getPageNum());
        pageResult.setTotal(pageInfo.getTotal());
        pageResult.setPages(pageInfo.getPages());
        pageResult.setRows(Convert.INSTANCE.convertToVoList(pageInfo.getList()));
        return pageResult;
    }

    /**
     * 数据转换
     * @param workflowProcessList
     * @return
     */
    public List<BusinessWorkflowDetailVo> convertToBusinessVoList(List<WorkflowProcess> workflowProcessList) {
        return Convert.INSTANCE.convertToBusinessVoList(workflowProcessList);
    }

    @Mapper
    public interface Convert {
        WorkflowProcessHelper.Convert INSTANCE = Mappers.getMapper(WorkflowProcessHelper.Convert.class);

        /**
         * 数据转换
         * @param form
         * @return
         */
        WorkflowProcess convert(WorkflowProcessAddForm form);

        /**
         * 数据转换
         * @param form
         * @return
         */
        WorkflowProcess convert(WorkflowProcessUpdateForm form);

        /**
         * 数据转换
         * @param workflowProcess
         * @return
         */
        WorkflowProcessVo convertToVo(WorkflowProcess workflowProcess);

        /**
         * 数据转换
         * @param workflowProcessList
         * @return
         */
        List<WorkflowProcessVo> convertToVoList(List<WorkflowProcess> workflowProcessList);

        /**
         * 数据转换
         * @param workflowProcess
         * @return
         */
        @Mappings({
                @Mapping(source = "name", target = "processName"),
                @Mapping(source = "id", target = "processId"),
        })
        BusinessWorkflowDetailVo convertToBusinessVo(WorkflowProcess workflowProcess);

        /**
         * 数据转换
         * @param workflowProcessList
         * @return
         */
        List<BusinessWorkflowDetailVo> convertToBusinessVoList(List<WorkflowProcess> workflowProcessList);
    }
}
