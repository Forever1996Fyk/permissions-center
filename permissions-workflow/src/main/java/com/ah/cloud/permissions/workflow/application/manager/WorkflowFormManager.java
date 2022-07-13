package com.ah.cloud.permissions.workflow.application.manager;

import com.ah.cloud.permissions.biz.application.service.ext.WorkflowFormModelExtService;
import com.ah.cloud.permissions.biz.infrastructure.exception.BizException;
import com.ah.cloud.permissions.biz.infrastructure.repository.bean.WorkflowFormModel;
import com.ah.cloud.permissions.biz.infrastructure.util.SecurityUtils;
import com.ah.cloud.permissions.domain.common.PageResult;
import com.ah.cloud.permissions.enums.common.DeletedEnum;
import com.ah.cloud.permissions.enums.workflow.WorkflowErrorCodeEnum;
import com.ah.cloud.permissions.workflow.application.helper.WorkflowFormHelper;
import com.ah.cloud.permissions.workflow.domain.form.form.AddForm;
import com.ah.cloud.permissions.workflow.domain.form.form.FormModelDesignForm;
import com.ah.cloud.permissions.workflow.domain.form.form.UpdateForm;
import com.ah.cloud.permissions.workflow.domain.form.query.FormModelQuery;
import com.ah.cloud.permissions.workflow.domain.form.vo.FormModelVo;
import com.ah.cloud.permissions.workflow.domain.form.vo.SelectFormModelVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * @program: permissions-center
 * @description: 表单管理
 * @author: YuKai Fan
 * @create: 2022-06-13 17:20
 **/
@Slf4j
@Component
public class WorkflowFormManager {
    @Resource
    private WorkflowFormHelper workflowFormHelper;
    @Resource
    private WorkflowFormModelExtService workflowFormModelExtService;

    /**
     * 添加新表单模型
     *
     * @param form
     */
    public void addWorkflowForm(AddForm form) {
        // 保证code唯一
        WorkflowFormModel existFormModel = workflowFormModelExtService.findOneFormModelByCode(form.getCode(), null);
        if (Objects.nonNull(existFormModel)) {
            throw new BizException(WorkflowErrorCodeEnum.WORKFLOW_FORM_CODE_EXISTED, form.getCode());
        }
        WorkflowFormModel workflowFormModel = workflowFormHelper.convert(form);
        workflowFormModelExtService.save(workflowFormModel);
    }

    /**
     * 更新新表单模型
     *
     * @param form
     */
    public void updateWorkflowForm(UpdateForm form) {
        WorkflowFormModel existedFormModel = workflowFormModelExtService.getOne(
                new QueryWrapper<WorkflowFormModel>().lambda()
                        .eq(WorkflowFormModel::getId, form.getId())
                        .eq(WorkflowFormModel::getDeleted, DeletedEnum.NO.value)
        );
        if (Objects.isNull(existedFormModel)) {
            throw new BizException(WorkflowErrorCodeEnum.WORKFLOW_FORM_MODEL_NOT_EXISTED);
        }

        WorkflowFormModel updateWorkflowFormModel = workflowFormHelper.convert(form);
        updateWorkflowFormModel.setId(existedFormModel.getId());
        updateWorkflowFormModel.setModifier(SecurityUtils.getUserNameBySession());
        workflowFormModelExtService.updateById(updateWorkflowFormModel);
    }

    /**
     * 表单设计保存
     *
     * @param form
     */
    public void saveFormModelDesign(FormModelDesignForm form) {
        WorkflowFormModel existedFormModel = workflowFormModelExtService.getOne(
                new QueryWrapper<WorkflowFormModel>().lambda()
                        .eq(WorkflowFormModel::getId, form.getId())
                        .eq(WorkflowFormModel::getDeleted, DeletedEnum.NO.value)
        );
        if (Objects.isNull(existedFormModel)) {
            throw new BizException(WorkflowErrorCodeEnum.WORKFLOW_FORM_MODEL_NOT_EXISTED);
        }
        WorkflowFormModel updateWorkflowFormModel = new WorkflowFormModel();
        updateWorkflowFormModel.setId(existedFormModel.getId());
        updateWorkflowFormModel.setContent(form.getContent());
        updateWorkflowFormModel.setModifier(SecurityUtils.getUserNameBySession());
        workflowFormModelExtService.updateById(updateWorkflowFormModel);
    }

    /**
     * 删除表单模型
     *
     * @param id
     */
    public void deleteFormModel(Long id) {
        WorkflowFormModel existedFormModel = workflowFormModelExtService.getOne(
                new QueryWrapper<WorkflowFormModel>().lambda()
                        .eq(WorkflowFormModel::getId, id)
                        .eq(WorkflowFormModel::getDeleted, DeletedEnum.NO.value)
        );
        if (Objects.isNull(existedFormModel)) {
            throw new BizException(WorkflowErrorCodeEnum.WORKFLOW_FORM_MODEL_NOT_EXISTED);
        }
        WorkflowFormModel deleteWorkflowFormModel = new WorkflowFormModel();
        deleteWorkflowFormModel.setId(existedFormModel.getId());
        deleteWorkflowFormModel.setDeleted(existedFormModel.getId());
        deleteWorkflowFormModel.setModifier(SecurityUtils.getUserNameBySession());
        workflowFormModelExtService.updateById(deleteWorkflowFormModel);
    }

    /**
     * 根据id查询表单模型
     *
     * @param id
     * @return
     */
    public FormModelVo findById(Long id) {
        WorkflowFormModel existedFormModel = workflowFormModelExtService.getOne(
                new QueryWrapper<WorkflowFormModel>().lambda()
                        .eq(WorkflowFormModel::getId, id)
                        .eq(WorkflowFormModel::getDeleted, DeletedEnum.NO.value)
        );
        if (Objects.isNull(existedFormModel)) {
            throw new BizException(WorkflowErrorCodeEnum.WORKFLOW_FORM_MODEL_NOT_EXISTED);
        }
        return workflowFormHelper.convertToVo(existedFormModel);
    }

    /**
     * 分页查询表单模型
     *
     * @param query
     * @return
     */
    public PageResult<FormModelVo> pageFormModelList(FormModelQuery query) {
        PageInfo<WorkflowFormModel> pageInfo = PageMethod.startPage(query.getPageNum(), query.getPageSize())
                .doSelectPageInfo(() -> workflowFormModelExtService.list(
                        new QueryWrapper<WorkflowFormModel>().lambda()
                                .eq(StringUtils.isNotBlank(query.getCode()), WorkflowFormModel::getCode, query.getCode())
                                .like(StringUtils.isNotBlank(query.getName()), WorkflowFormModel::getName, query.getName())
                ));
        return workflowFormHelper.convertToVoPageResult(pageInfo);
    }

    /**
     * 查询表单列表集合
     * @return
     */
    public List<SelectFormModelVo> selectFormModelVoList() {
        List<WorkflowFormModel> workflowFormModelList = workflowFormModelExtService.list(
                new QueryWrapper<WorkflowFormModel>().lambda()
                        .eq(WorkflowFormModel::getDeleted, DeletedEnum.NO.value)
        );
        return workflowFormHelper.convertToVoList(workflowFormModelList);
    }
}
