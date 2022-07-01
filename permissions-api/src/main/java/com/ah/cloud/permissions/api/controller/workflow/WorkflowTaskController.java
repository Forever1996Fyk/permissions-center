package com.ah.cloud.permissions.api.controller.workflow;

import com.ah.cloud.permissions.domain.common.PageResult;
import com.ah.cloud.permissions.domain.common.ResponseResult;
import com.ah.cloud.permissions.workflow.application.manager.WorkflowTaskManager;
import com.ah.cloud.permissions.workflow.application.manager.WorkflowResourceManager;
import com.ah.cloud.permissions.workflow.application.manager.WorkflowTaskFormManager;
import com.ah.cloud.permissions.workflow.domain.business.vo.BusinessVariablesFormVo;
import com.ah.cloud.permissions.workflow.domain.task.form.CompleteTaskForm;
import com.ah.cloud.permissions.workflow.domain.task.query.MyTaskQuery;
import com.ah.cloud.permissions.workflow.domain.task.vo.DoneTaskVo;
import com.ah.cloud.permissions.workflow.domain.task.vo.TaskFormVo;
import com.ah.cloud.permissions.workflow.domain.task.vo.ToDoTaskVo;
import com.ah.cloud.permissions.workflow.domain.task.vo.detail.TaskInstanceDetailVo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-06-29 14:57
 **/
@RestController
@RequestMapping("/workflow/task")
public class WorkflowTaskController {
    @Resource
    private WorkflowTaskManager workflowTaskManager;
    @Resource
    private WorkflowResourceManager workflowResourceManager;
    @Resource
    private WorkflowTaskFormManager workflowTaskFormManager;

    /**
     * 分页待办任务列表
     * @param query
     * @return
     */
    @GetMapping("/todo/page")
    public ResponseResult<PageResult<ToDoTaskVo>> pageCurrentUserToDoTaskList(MyTaskQuery query) {
        return ResponseResult.ok(workflowTaskManager.pageCurrentUserToDoTaskList(query));
    }

    /**
     * 分页已办任务列表
     * @param query
     * @return
     */
    @GetMapping("/done/page")
    public ResponseResult<PageResult<DoneTaskVo>> pageCurrentUserDoneTaskList(MyTaskQuery query) {
        return ResponseResult.ok(workflowTaskManager.pageCurrentUserDoneTaskList(query));
    }

    /**
     * 根据任务key获取关联的任务表单
     *
     * @param processDefinitionId
     * @param taskKey
     * @return
     */
    @GetMapping("/findTaskFormByTaskKey")
    public ResponseResult<TaskFormVo> findTaskFormByTaskKey(String processDefinitionId, String taskKey) {
        return ResponseResult.ok(workflowTaskManager.findTaskFormByTaskKey(processDefinitionId, taskKey));
    }

    /**
     * 完成任务
     * @param form
     * @return
     */
    @PostMapping("/completeTask")
    public ResponseResult<Void> completeTask(@RequestBody @Valid CompleteTaskForm form) {
        workflowTaskManager.completeTask(form);
        return ResponseResult.ok();
    }

    /**
     * 获取任务详情列表
     * @param processInstanceId
     * @return
     */
    @GetMapping("/listTaskDetail/{processInstanceId}")
    public ResponseResult<List<TaskInstanceDetailVo>> listProcessDetailTask(@PathVariable(value = "processInstanceId") String processInstanceId) {
        return ResponseResult.ok(workflowTaskManager.listProcessDetailTaskByProcessInstanceId(processInstanceId));
    }

    /**
     * 根据流程实例id获取当前申请人的流程表单
     * @param processInstanceId
     * @return
     */
    @GetMapping("/findBusinessVariablesFormByInstanceId/{processInstanceId}")
    public ResponseResult<BusinessVariablesFormVo> findBusinessVariablesFormByInstanceId(@PathVariable(value = "processInstanceId") String processInstanceId) {
        return ResponseResult.ok(workflowTaskFormManager.findBusinessVariablesFormByInstanceId(processInstanceId, null));
    }

    /**
     * 根据流程定义id获取流程定义xml
     *
     * @param processDefinitionId
     * @return
     */
    @GetMapping("/findBpmnXmlByProcessDefinitionId/{processDefinitionId}")
    public ResponseResult<String> findBpmnXmlByProcessDefinitionId(@PathVariable(value = "processDefinitionId") String processDefinitionId) {
        return ResponseResult.ok(workflowResourceManager.getProcessDefinitionBpmnXml(processDefinitionId));
    }
}
