package com.ah.cloud.permissions.enums.workflow;

import com.ah.cloud.permissions.exception.ErrorCode;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-06-08 21:14
 **/
public enum WorkflowErrorCodeEnum implements ErrorCode {

    /**
     * 流程模型
     */
    WORKFLOW_PROCESS_MODEL_NOT_EXISTED(5_10_0_001, "当前流程模型[%s]不存在"),
    WORKFLOW_PROCESS_MODEL_RELATE_BPMN_NOT_EXISTED(5_10_0_002, "当前流程模型[%s]对应的bpmn模型不存在"),
    WORKFLOW_PROCESS_MODEL_RELATE_RESOURCE_NOT_EXISTED(5_10_0_003, "当前流程模型[%s]对应的bpmn资源不存在"),
    WORKFLOW_PROCESS_MODEL_BPMN_NOT_EXISTED(5_10_0_004, "当前流程模型bpmn资源不存在"),
    WORKFLOW_PROCESS_KEY_EXISTED(5_10_0_005, "当前流程key[%s]已存在"),
    WORKFLOW_PROCESS_MODEL_HAVE_NOT_TASK_RULE(5_10_0_006, "当前流程模型不存在任务规则"),
    WORKFLOW_PROCESS_MODEL_DEPLOY_FAILED_TASK_RULE_NOT_CONFIG(5_10_0_007, "部署流程失败原因：用户任务({})未配置分配规则，请点击【修改流程】按钮进行配置"),

    /**
     * 流程实例
     */
    WORKFLOW_PROCESS_INSTANCE_START_FAILED_MISS_PARAM(5_10_1_006, "当前流程启动失败, 缺少必要的参数[%s]"),
    WORKFLOW_PROCESS_INSTANCE_START_FAILED_PROCESS_DEFINITION_KEY_ERROR(5_10_1_007, "当前流程启动失败, 流程定义key[%s]错误"),

    /**
     * 流程配置
     */
    WORKFLOW_CONFIGURATION_TASK_FORM_EXISTED(5_10_2_001, "当前任务节点[%s]流程表单[%s]配置已存在"),
    WORKFLOW_CONFIGURATION_PROCESS_FORM_EXISTED(5_10_0_002, "当前流程配置不存在"),

    /**
     * 表单
     */
    WORKFLOW_FORM_CODE_EXISTED(5_11_0_001, "当前表单编码[%s]已存在"),
    WORKFLOW_FORM_MODEL_NOT_EXISTED(5_11_0_002, "当前表单模型不存在"),

    /**
     * 监听器
     */
    WORKFLOW_LISTENER_NOT_EXISTED(5_12_0_001, "当前监听器不存在"),

    /**
     * 流程
     */
    WORKFLOW_PROCESS_NOT_EXISTED(5_13_0_001, "当前流程[%s]不存在"),
    WORKFLOW_PROCESS_DEPLOY_FAILED(5_13_0_002, "当前流程[%s]部署失败"),

    /**
     * 业务流程
     */
    WORKFLOW_BUSINESS_BUILD_FAILED(5_14_0_001, "构建流程失败，请联系管理员"),
    WORKFLOW_BUSINESS_NOT_EXISTED(5_14_0_002, "当前业务流程不存在"),

    /**
     * 任务
     */
    WORKFLOW_TASK_NOT_EXISTED(5_15_0_001, "当前任务不存在"),
    WORKFLOW_TASK_MAYBE_HANDLED(5_15_0_002, "当前任务可能已经被处理"),
    WORKFLOW_TASK_ASSIGN_RULE_REPEAT(5_15_0_003, "当前任务规则已重复"),
    WORKFLOW_TASK_ASSIGN_RULE_NOT_EXISTED(5_15_0_004, "当前任务规则不存在"),

    WORKFLOW_TASK_FORM_NOT_EXITED(5_15_1_001, "当前任务关联的表单不存在"),


    /**
     * 候选人
     */
    WORKFLOW_TASK_FAILED_USER_TASK(5_16_0_001, "用户任务失败"),
    WORKFLOW_TASK_FAILED_NO_GOOD_CANDIDATE(5_16_0_002, "用户任务失败, 没有合适的候选人"),
    ;

    private final int code;

    private final String msg;

    WorkflowErrorCodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }
}
