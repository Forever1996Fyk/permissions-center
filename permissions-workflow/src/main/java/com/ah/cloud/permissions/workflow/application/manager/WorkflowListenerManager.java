package com.ah.cloud.permissions.workflow.application.manager;

import com.ah.cloud.permissions.biz.application.service.WorkflowProcessListenerService;
import com.ah.cloud.permissions.biz.infrastructure.exception.BizException;
import com.ah.cloud.permissions.biz.infrastructure.repository.bean.WorkflowProcessListener;
import com.ah.cloud.permissions.domain.common.PageResult;
import com.ah.cloud.permissions.enums.common.DeletedEnum;
import com.ah.cloud.permissions.enums.workflow.WorkflowErrorCodeEnum;
import com.ah.cloud.permissions.workflow.application.helper.WorkflowListenerHelper;
import com.ah.cloud.permissions.workflow.domain.listener.form.ProcessListenerAddForm;
import com.ah.cloud.permissions.workflow.domain.listener.form.ProcessListenerUpdateForm;
import com.ah.cloud.permissions.workflow.domain.listener.query.ProcessListenerQuery;
import com.ah.cloud.permissions.workflow.domain.listener.vo.ProcessListenerVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @program: permissions-center
 * @description: 流程监听器管理
 * @author: YuKai Fan
 * @create: 2022-06-15 16:56
 **/
@Slf4j
@Component
public class WorkflowListenerManager {
    @Resource
    private WorkflowListenerHelper workflowListenerHelper;
    @Resource
    private WorkflowProcessListenerService workflowProcessListenerService;

    /**
     * 添加新的监听器
     * @param form
     */
    public void addWorkflowListener(ProcessListenerAddForm form) {
        WorkflowProcessListener workflowProcessListener = workflowListenerHelper.convert(form);
        workflowProcessListenerService.save(workflowProcessListener);
    }

    /**
     * 更新监听器
     * @param form
     */
    public void updateWorkflowListener(ProcessListenerUpdateForm form) {
        WorkflowProcessListener existedWorkflowListener = workflowProcessListenerService.getOne(
                new QueryWrapper<WorkflowProcessListener>().lambda()
                        .eq(WorkflowProcessListener::getId, form.getId())
                        .eq(WorkflowProcessListener::getDeleted, DeletedEnum.NO.value)
        );
        if (Objects.isNull(existedWorkflowListener)) {
            throw new BizException(WorkflowErrorCodeEnum.WORKFLOW_LISTENER_NOT_EXISTED);
        }
        WorkflowProcessListener workflowProcessListener = workflowListenerHelper.convert(form);
        workflowProcessListenerService.updateById(workflowProcessListener);
    }

    /**
     * 根据id删除监听器
     * @param id
     */
    public void deleteById(Long id) {
        WorkflowProcessListener existedWorkflowListener = workflowProcessListenerService.getOne(
                new QueryWrapper<WorkflowProcessListener>().lambda()
                        .eq(WorkflowProcessListener::getId, id)
                        .eq(WorkflowProcessListener::getDeleted, DeletedEnum.NO.value)
        );
        if (Objects.isNull(existedWorkflowListener)) {
            throw new BizException(WorkflowErrorCodeEnum.WORKFLOW_LISTENER_NOT_EXISTED);
        }
        WorkflowProcessListener deleteWorkflowProcessListener = new WorkflowProcessListener();
        deleteWorkflowProcessListener.setId(id);
        deleteWorkflowProcessListener.setDeleted(id);
        workflowProcessListenerService.updateById(deleteWorkflowProcessListener);
    }

    /**
     * 根据id获取监听器
     * @param id
     * @return
     */
    public ProcessListenerVo findById(Long id) {
        WorkflowProcessListener existedWorkflowListener = workflowProcessListenerService.getOne(
                new QueryWrapper<WorkflowProcessListener>().lambda()
                        .eq(WorkflowProcessListener::getId, id)
                        .eq(WorkflowProcessListener::getDeleted, DeletedEnum.NO.value)
        );
        if (Objects.isNull(existedWorkflowListener)) {
            throw new BizException(WorkflowErrorCodeEnum.WORKFLOW_LISTENER_NOT_EXISTED);
        }
        return workflowListenerHelper.convertToVo(existedWorkflowListener);
    }

    /**
     * 分页查询监听器
     * @param query
     * @return
     */
    public PageResult<ProcessListenerVo> pageProcessListenerVoList(ProcessListenerQuery query) {
        PageInfo<WorkflowProcessListener> pageInfo = PageMethod.startPage(query.getPageNum(), query.getPageSize())
                .doSelectPageInfo(
                        () -> workflowProcessListenerService.list(
                                new QueryWrapper<WorkflowProcessListener>().lambda()
                                        .like(StringUtils.isNotBlank(query.getName()), WorkflowProcessListener::getListenerName, query.getName())
                        )
                );
        return workflowListenerHelper.convertToPageResult(pageInfo);
    }
}
