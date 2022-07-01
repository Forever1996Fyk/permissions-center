package com.ah.cloud.permissions.api.controller.workflow;

import com.ah.cloud.permissions.domain.common.ResponseResult;
import com.ah.cloud.permissions.workflow.application.manager.WorkflowTaskAssignRuleManager;
import com.ah.cloud.permissions.workflow.domain.task.form.AssignRuleTaskAddForm;
import com.ah.cloud.permissions.workflow.domain.task.form.AssignRuleTaskUpdateForm;
import com.ah.cloud.permissions.workflow.domain.task.vo.AssignRuleTaskVo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-06-29 16:15
 **/
@RestController
@RequestMapping("/workflow/taskRule")
public class WorkflowTaskRuleController {
    @Resource
    private WorkflowTaskAssignRuleManager workflowTaskAssignRuleManager;

    /**
     * 创建任务规则
     * @param form
     * @return
     */
    @PostMapping("/add")
    public ResponseResult<Void> add(@RequestBody @Valid AssignRuleTaskAddForm form) {
        workflowTaskAssignRuleManager.createAssignRuleTask(form);
        return ResponseResult.ok();
    }

    /**
     * 更新任务规则
     * @param form
     * @return
     */
    @PostMapping("/update")
    public ResponseResult<Void> update(@RequestBody @Valid AssignRuleTaskUpdateForm form) {
        workflowTaskAssignRuleManager.updateAssignRuleTask(form);
        return ResponseResult.ok();
    }

    /**
     * 获取处理人规则列表
     * @param modelId
     * @param processDefinitionId
     * @return
     */
    @GetMapping("/listAssignRuleTask")
    public ResponseResult<List<AssignRuleTaskVo>> listAssignRuleTask(String modelId, String processDefinitionId) {
        return ResponseResult.ok(workflowTaskAssignRuleManager.listAssignRuleTaskVos(modelId, processDefinitionId));
    }
}
