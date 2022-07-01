package com.ah.cloud.permissions.api.controller.workflow;

import com.ah.cloud.permissions.domain.common.PageResult;
import com.ah.cloud.permissions.domain.common.ResponseResult;
import com.ah.cloud.permissions.workflow.application.manager.WorkflowBusinessManager;
import com.ah.cloud.permissions.workflow.application.manager.WorkflowResourceManager;
import com.ah.cloud.permissions.workflow.application.manager.WorkflowTaskFormManager;
import com.ah.cloud.permissions.workflow.domain.business.form.WorkflowBusinessStartForm;
import com.ah.cloud.permissions.workflow.domain.business.query.UserSubmitProcessQuery;
import com.ah.cloud.permissions.workflow.domain.business.vo.BusinessVariablesFormVo;
import com.ah.cloud.permissions.workflow.domain.business.vo.UserSubmitProcessVo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-06-28 21:32
 **/
@RestController
@RequestMapping("/workflow/business")
public class WorkflowBusinessController {
    @Resource
    private WorkflowBusinessManager workflowBusinessManager;
    @Resource
    private WorkflowTaskFormManager workflowTaskFormManager;
    @Resource
    private WorkflowResourceManager workflowResourceManager;

    /**
     * 分页查询用户申请的流程
     *
     * @param query
     * @return
     */
    @GetMapping("/page")
    public ResponseResult<PageResult<UserSubmitProcessVo>> pageUserSubmitProcessList(UserSubmitProcessQuery query) {
        return ResponseResult.ok(workflowBusinessManager.pageUserSubmitProcessVoList(query));
    }

    /**
     * 提交流程
     * @param form
     * @return
     */
    @PostMapping("/submit")
    public ResponseResult<Void> submitWorkflowBusiness(@RequestBody @Valid WorkflowBusinessStartForm form) {
        workflowBusinessManager.submitWorkflowBusiness(form);
        return ResponseResult.ok();
    }

    /**
     * 根据流程业务id获取当前申请人的流程表单
     * @param id
     * @return
     */
    @GetMapping("/findBusinessVariablesFormByBusinessId/{id}")
    public ResponseResult<BusinessVariablesFormVo> findBusinessVariablesFormByBusinessId(@PathVariable(value = "id") Long id) {
        return ResponseResult.ok(workflowTaskFormManager.findBusinessVariablesFormByBusinessId(id, null));
    }

    /**
     * 根据业务id获取流程定义xml
     * @param id
     * @return
     */
    @GetMapping("/findBpmnXmlById/{id}")
    public ResponseResult<String> findBpmnXmlById(@PathVariable(value = "id") Long id) {
        return ResponseResult.ok(workflowResourceManager.getProcessDefinitionBpmnXmlByBusinessId(id));
    }
}
