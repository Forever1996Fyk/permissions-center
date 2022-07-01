package com.ah.cloud.permissions.api.controller.workflow;

import com.ah.cloud.permissions.domain.common.PageResult;
import com.ah.cloud.permissions.domain.common.ResponseResult;
import com.ah.cloud.permissions.workflow.application.manager.WorkflowListenerManager;
import com.ah.cloud.permissions.workflow.domain.listener.form.ProcessListenerAddForm;
import com.ah.cloud.permissions.workflow.domain.listener.form.ProcessListenerUpdateForm;
import com.ah.cloud.permissions.workflow.domain.listener.query.ProcessListenerQuery;
import com.ah.cloud.permissions.workflow.domain.listener.vo.ProcessListenerVo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-06-29 15:36
 **/
@RestController
@RequestMapping("/workflow/listener")
public class WorkflowListenerController {
    @Resource
    private WorkflowListenerManager workflowListenerManager;

    /**
     * 添加监听器
     *
     * @param form
     * @return
     */
    @PostMapping("/add")
    public ResponseResult<Void> add(@RequestBody @Valid ProcessListenerAddForm form) {
        workflowListenerManager.addWorkflowListener(form);
        return ResponseResult.ok();
    }

    /**
     * 更新监听器
     * @param form
     * @return
     */
    @PostMapping("/update")
    public ResponseResult<Void> update(@RequestBody @Valid ProcessListenerUpdateForm form) {
        workflowListenerManager.updateWorkflowListener(form);
        return ResponseResult.ok();
    }

    /**
     * 删除监听器
     * @param id
     * @return
     */
    @PostMapping("/delete/{id}")
    public ResponseResult<Void> delete(@PathVariable(value = "id") Long id) {
        workflowListenerManager.deleteById(id);
        return ResponseResult.ok();
    }

    /**
     * 获取监听器
     * @param id
     * @return
     */
    @GetMapping("/findById/{id}")
    public ResponseResult<ProcessListenerVo> findById(@PathVariable(value = "id") Long id) {
        return ResponseResult.ok(workflowListenerManager.findById(id));
    }

    /**
     * 分页查询监听器
     * @param id
     * @return
     */
    @GetMapping("/page")
    public ResponseResult<PageResult<ProcessListenerVo>> findById(ProcessListenerQuery query) {
        return ResponseResult.ok(workflowListenerManager.pageProcessListenerVoList(query));
    }
}
