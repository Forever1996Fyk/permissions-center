package com.ah.cloud.permissions.workflow.application.helper;

import com.ah.cloud.permissions.biz.infrastructure.repository.bean.WorkflowFormModel;
import com.ah.cloud.permissions.biz.infrastructure.repository.bean.WorkflowProcess;
import com.ah.cloud.permissions.biz.infrastructure.repository.bean.WorkflowProcessListener;
import com.ah.cloud.permissions.biz.infrastructure.util.SecurityUtils;
import com.ah.cloud.permissions.domain.common.PageResult;
import com.ah.cloud.permissions.workflow.domain.form.form.UpdateForm;
import com.ah.cloud.permissions.workflow.domain.form.vo.FormModelVo;
import com.ah.cloud.permissions.workflow.domain.listener.form.ProcessListenerAddForm;
import com.ah.cloud.permissions.workflow.domain.listener.form.ProcessListenerUpdateForm;
import com.ah.cloud.permissions.workflow.domain.listener.vo.ProcessListenerVo;
import com.ah.cloud.permissions.workflow.domain.process.form.WorkflowProcessAddForm;
import com.github.pagehelper.PageInfo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-06-16 11:07
 **/
@Component
public class WorkflowListenerHelper {

    /**
     * 数据转换
     * @param form
     * @return
     */
    public WorkflowProcessListener convert(ProcessListenerAddForm form) {
        WorkflowProcessListener workflowProcessListener = Convert.INSTANCE.convert(form);
        workflowProcessListener.setCreator(SecurityUtils.getUserNameBySession());
        workflowProcessListener.setModifier(SecurityUtils.getUserNameBySession());
        return workflowProcessListener;
    }

    /**
     * 数据转换
     * @param form
     * @return
     */
    public WorkflowProcessListener convert(ProcessListenerUpdateForm form) {
        WorkflowProcessListener workflowProcessListener = Convert.INSTANCE.convert(form);
        workflowProcessListener.setModifier(SecurityUtils.getUserNameBySession());
        return workflowProcessListener;
    }

    /**
     * 数据转换
     * @param pageInfo
     * @return
     */
    public PageResult<ProcessListenerVo> convertToPageResult(PageInfo<WorkflowProcessListener> pageInfo) {
        PageResult<ProcessListenerVo> pageResult = new PageResult<>();
        pageResult.setPageNum(pageInfo.getPageNum());
        pageResult.setPageSize(pageInfo.getPageSize());
        pageResult.setTotal(pageInfo.getTotal());
        pageResult.setRows(Convert.INSTANCE.convertToVoList(pageInfo.getList()));
        return pageResult;
    }

    /**
     * 数据转换
     * @param workflowProcessListener
     * @return
     */
    public ProcessListenerVo convertToVo(WorkflowProcessListener workflowProcessListener) {
        return Convert.INSTANCE.convertToVo(workflowProcessListener);
    }

    @Mapper
    public interface Convert {
        WorkflowListenerHelper.Convert INSTANCE = Mappers.getMapper(WorkflowListenerHelper.Convert.class);

        /**
         * 数据转换
         * @param form
         * @return
         */
        WorkflowProcessListener convert(ProcessListenerAddForm form);

        /**
         * 数据转换
         * @param form
         * @return
         */
        WorkflowProcessListener convert(ProcessListenerUpdateForm form);

        /**
         * 数据转换
         * @param workflowProcessListener
         * @return
         */
        ProcessListenerVo convertToVo(WorkflowProcessListener workflowProcessListener);

        /**
         * 数据转换
         * @param workflowProcessListenerList
         * @return
         */
        List<ProcessListenerVo> convertToVoList(List<WorkflowProcessListener> workflowProcessListenerList);
    }
}
