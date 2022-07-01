package com.ah.cloud.permissions.api.controller.workflow;

import com.ah.cloud.permissions.domain.common.PageResult;
import com.ah.cloud.permissions.domain.common.ResponseResult;
import com.ah.cloud.permissions.workflow.application.manager.WorkflowConfigurationManager;
import com.ah.cloud.permissions.workflow.domain.configuration.form.ProcessFormConfigurationAddForm;
import com.ah.cloud.permissions.workflow.domain.configuration.form.ProcessFormConfigurationUpdateForm;
import com.ah.cloud.permissions.workflow.domain.configuration.query.ProcessFormConfigurationQuery;
import com.ah.cloud.permissions.workflow.domain.configuration.vo.ProcessFormConfigurationVo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-06-29 16:29
 **/
@RestController
@RequestMapping("/workflow/configuration")
public class WorkflowConfigurationController {
    @Resource
    private WorkflowConfigurationManager workflowConfigurationManager;

    /**
     * 新增流程表单配置
     * @param form
     * @return
     */
    @PostMapping("/add")
    public ResponseResult<Void> add(@RequestBody @Valid ProcessFormConfigurationAddForm form) {
        workflowConfigurationManager.addProcessFormConfiguration(form);
        return ResponseResult.ok();
    }

    /**
     * 更新流程表单配置
     * @param form
     * @return
     */
    @PostMapping("/update")
    public ResponseResult<Void> update(@RequestBody @Valid ProcessFormConfigurationUpdateForm form) {
        workflowConfigurationManager.updateProcessFormConfiguration(form);
        return ResponseResult.ok();
    }

    /**
     * 删除流程表单配置
     * @param id
     * @return
     */
    @PostMapping("/delete/{id}")
    public ResponseResult<Void> delete(@PathVariable(value = "id") Long id) {
        workflowConfigurationManager.deleteById(id);
        return ResponseResult.ok();
    }

    /**
     * 根据id获取流程表单配置
     * @param id
     * @return
     */
    @GetMapping("/findById/{id}")
    public ResponseResult<ProcessFormConfigurationVo> findById(@PathVariable(value = "id") Long id) {
        return ResponseResult.ok(workflowConfigurationManager.findById(id));
    }

    /**
     * 分页获取流程表单配置
     * @param query
     * @return
     */
    @GetMapping("/page")
    public ResponseResult<PageResult<ProcessFormConfigurationVo>> pageProcessFormConfigurationList(ProcessFormConfigurationQuery query) {
        return ResponseResult.ok(workflowConfigurationManager.pageProcessFormConfigurationVoList(query));
    }


}
