package com.ah.cloud.permissions.api.controller.workflow;

import com.ah.cloud.permissions.domain.common.PageResult;
import com.ah.cloud.permissions.domain.common.ResponseResult;
import com.ah.cloud.permissions.workflow.application.manager.WorkflowProcessManager;
import com.ah.cloud.permissions.workflow.domain.model.form.ModelEditorSourceForm;
import com.ah.cloud.permissions.workflow.domain.process.form.WorkflowProcessAddForm;
import com.ah.cloud.permissions.workflow.domain.process.form.WorkflowProcessDeployForm;
import com.ah.cloud.permissions.workflow.domain.process.form.WorkflowProcessUpdateForm;
import com.ah.cloud.permissions.workflow.domain.process.query.WorkflowProcessQuery;
import com.ah.cloud.permissions.workflow.domain.process.vo.BusinessWorkflowVo;
import com.ah.cloud.permissions.workflow.domain.process.vo.WorkflowFormVo;
import com.ah.cloud.permissions.workflow.domain.process.vo.WorkflowProcessVo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-06-28 21:06
 **/
@RestController
@RequestMapping("/workflow/model")
public class WorkflowProcessController {
    @Resource
    private WorkflowProcessManager workflowProcessManager;

    /**
     * 添加工作流模型
     *
     * @param form
     * @return
     */
    @PostMapping("/add")
    public ResponseResult<Long> addWorkflowModel(@RequestBody @Valid WorkflowProcessAddForm form) {
        return ResponseResult.ok(workflowProcessManager.addWorkflowProcess(form));
    }

    /**
     * 保存工作流资源
     * @param form
     * @return
     */
    @PostMapping("/saveWorkflowProcessResource")
    public ResponseResult<Void> saveWorkflowResource(@RequestBody @Valid ModelEditorSourceForm form) {
        workflowProcessManager.saveWorkflowProcessResource(form);
        return ResponseResult.ok();
    }

    /**
     * 更新工作流模型
     *
     * @param form
     * @return
     */
    @PostMapping("/update")
    public ResponseResult<Void> updateWorkflowModel(@RequestBody @Valid WorkflowProcessUpdateForm form) {
        workflowProcessManager.updateWorkflowProcess(form);
        return ResponseResult.ok();
    }

    /**
     * 删除工作流模型
     * @param id
     * @return
     */
    @PostMapping("/delete/{id}")
    public ResponseResult<Void> deleteWorkflowModelById(@PathVariable(value = "id") Long id) {
        workflowProcessManager.deleteWorkflowProcessById(id);
        return ResponseResult.ok();
    }

    /**
     * 根据id获取流程模型
     *
     * @param id
     * @return
     */
    @GetMapping("/findById/{id}")
    public ResponseResult<WorkflowProcessVo> findModelById(@PathVariable(value = "id") Long id) {
        return ResponseResult.ok(workflowProcessManager.findById(id));
    }

    /**
     * 分页查询工作流模型
     *
     * @param query
     * @return
     */
    @GetMapping("/page")
    public ResponseResult<PageResult<WorkflowProcessVo>> pageWorkflowModelList(WorkflowProcessQuery query) {
        return ResponseResult.ok(workflowProcessManager.pageWorkflowProcessVoList(query));
    }

    /**
     * 部署流程
     * @param form
     * @return
     */
    @PostMapping("/deploy")
    public ResponseResult<Void> deploy(@RequestBody @Valid WorkflowProcessDeployForm form) {
        workflowProcessManager.deployProcessById(form);
        return ResponseResult.ok();
    }

    /**
     * 根据流程类别分组 获取流程列表
     * @return
     */
    @GetMapping("/listWorkflowCategoryGroup")
    public ResponseResult<List<BusinessWorkflowVo>> listBusinessWorkflowVoCategoryGroup() {
        return ResponseResult.ok(workflowProcessManager.listBusinessWorkflowVoCategoryGroup());
    }

    /**
     * 根据流程id获取表单信息
     * @param id
     * @return
     */
    @GetMapping("/findWorkflowFormById/{id}")
    public ResponseResult<WorkflowFormVo> findWorkflowFormById(@PathVariable(value = "id") Long id) {
        return ResponseResult.ok(workflowProcessManager.findWorkflowFormVoById(id));
    }
}
