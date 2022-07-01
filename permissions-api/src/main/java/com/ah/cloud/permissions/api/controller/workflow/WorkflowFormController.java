package com.ah.cloud.permissions.api.controller.workflow;

import com.ah.cloud.permissions.domain.common.PageResult;
import com.ah.cloud.permissions.domain.common.ResponseResult;
import com.ah.cloud.permissions.workflow.application.manager.WorkflowFormManager;
import com.ah.cloud.permissions.workflow.domain.form.form.AddForm;
import com.ah.cloud.permissions.workflow.domain.form.form.UpdateForm;
import com.ah.cloud.permissions.workflow.domain.form.query.FormModelQuery;
import com.ah.cloud.permissions.workflow.domain.form.vo.FormModelVo;
import com.ah.cloud.permissions.workflow.domain.form.vo.SelectFormModelVo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-06-29 16:04
 **/
@RestController
@RequestMapping("/workflow/form")
public class WorkflowFormController {
    @Resource
    private WorkflowFormManager workflowFormManager;

    /**
     * 添加表单
     * @param form
     * @return
     */
    @PostMapping("/add")
    public ResponseResult<Void> add(@RequestBody @Valid AddForm form) {
        workflowFormManager.addWorkflowForm(form);
        return ResponseResult.ok();
    }

    /**
     * 更新表单
     * @param form
     * @return
     */
    @PostMapping("/update")
    public ResponseResult<Void> update(@RequestBody @Valid UpdateForm form) {
        workflowFormManager.updateWorkflowForm(form);
        return ResponseResult.ok();
    }

    /**
     * 删除表单
     * @param id
     * @return
     */
    @PostMapping("/delete/{id}")
    public ResponseResult<Void> delete(@PathVariable(value = "id") Long id) {
        workflowFormManager.deleteFormModel(id);
        return ResponseResult.ok();
    }

    /**
     * 根据id查询表单
     * @param id
     * @return
     */
    @GetMapping("/findById/{id}")
    public ResponseResult<FormModelVo> findById(@PathVariable(value = "id") Long id) {
        return ResponseResult.ok(workflowFormManager.findById(id));
    }

    /**
     * 分页查询表单
     * @param query
     * @return
     */
    @GetMapping("/page")
    public ResponseResult<PageResult<FormModelVo>> add(FormModelQuery query) {
        return ResponseResult.ok(workflowFormManager.pageFormModelList(query));
    }

    /**
     * 查询表单列表集合
     *
     * @return
     */
    @GetMapping("/selectFormModelList")
    public ResponseResult<List<SelectFormModelVo>> selectFormModelList() {
        return ResponseResult.ok(workflowFormManager.selectFormModelVoList());
    }
}
