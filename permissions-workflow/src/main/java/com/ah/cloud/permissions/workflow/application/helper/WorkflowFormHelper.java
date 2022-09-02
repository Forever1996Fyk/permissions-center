package com.ah.cloud.permissions.workflow.application.helper;

import com.ah.cloud.permissions.biz.infrastructure.repository.bean.WorkflowFormModel;
import com.ah.cloud.permissions.biz.infrastructure.util.JsonUtils;
import com.ah.cloud.permissions.domain.common.PageResult;
import com.ah.cloud.permissions.workflow.domain.form.form.AddForm;
import com.ah.cloud.permissions.workflow.domain.form.form.UpdateForm;
import com.ah.cloud.permissions.workflow.domain.form.vo.FormModelVo;
import com.ah.cloud.permissions.workflow.domain.form.vo.SelectFormModelVo;
import com.github.pagehelper.PageInfo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-06-15 11:28
 **/
@Component
public class WorkflowFormHelper {

    /**
     * 数据转换
     *
     * @param form
     * @return
     */
    public WorkflowFormModel convert(AddForm form) {
        WorkflowFormModel workflowFormModel = Convert.INSTANCE.convert(form);
        workflowFormModel.setContent(JsonUtils.toJsonString(form.getFields()));
        return workflowFormModel;
    }

    /**
     * 数据转换
     *
     * @param form
     * @return
     */
    public WorkflowFormModel convert(UpdateForm form) {
        return Convert.INSTANCE.convert(form);
    }

    /**
     * 数据转换
     *
     * @param workflowFormModel
     * @return
     */
    public FormModelVo convertToVo(WorkflowFormModel workflowFormModel) {
        FormModelVo formModelVo = Convert.INSTANCE.convertToVo(workflowFormModel);
        formModelVo.setFields(JsonUtils.jsonToList(workflowFormModel.getContent(), String.class));
        return formModelVo;
    }

    /**
     * 数据转换
     *
     * @param pageInfo
     * @return
     */
    public PageResult<FormModelVo> convertToVoPageResult(PageInfo<WorkflowFormModel> pageInfo) {
        PageResult<FormModelVo> pageResult = new PageResult<>();
        pageResult.setPageNum(pageInfo.getPageNum());
        pageResult.setRows(Convert.INSTANCE.convertToVoList(pageInfo.getList()));
        pageResult.setPages(pageInfo.getPages());
        pageResult.setTotal(pageInfo.getTotal());
        pageResult.setPageSize(pageInfo.getPageSize());
        return pageResult;
    }

    /**
     * 数据转换
     *
     * @param workflowFormModelList
     * @return
     */
    public List<SelectFormModelVo> convertToVoList(List<WorkflowFormModel> workflowFormModelList) {
        return Convert.INSTANCE.convertToSelectVoList(workflowFormModelList);
    }

    @Mapper
    public interface Convert {
        WorkflowFormHelper.Convert INSTANCE = Mappers.getMapper(WorkflowFormHelper.Convert.class);

        /**
         * 数据转换
         *
         * @param form
         * @return
         */
        WorkflowFormModel convert(AddForm form);

        /**
         * 数据转换
         *
         * @param form
         * @return
         */
        WorkflowFormModel convert(UpdateForm form);

        /**
         * 数据转换
         *
         * @param workflowFormModel
         * @return
         */
        FormModelVo convertToVo(WorkflowFormModel workflowFormModel);

        /**
         * 数据转换
         *
         * @param workflowFormModelList
         * @return
         */
        List<FormModelVo> convertToVoList(List<WorkflowFormModel> workflowFormModelList);

        /**
         * 数据转换
         *
         * @param workflowFormModel
         * @return
         */
        SelectFormModelVo convertToSelectVo(WorkflowFormModel workflowFormModel);

        /**
         * 数据转换
         *
         * @param workflowFormModelList
         * @return
         */
        List<SelectFormModelVo> convertToSelectVoList(List<WorkflowFormModel> workflowFormModelList);
    }
}
