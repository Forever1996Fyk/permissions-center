package com.ah.cloud.permissions.enums.common;

import com.ah.cloud.permissions.exception.ErrorCode;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-06-08 21:14
 **/
public enum WorkflowErrorCodeEnum implements ErrorCode {

    /**
     * 工作流异常码
     */
    WORKFLOW_PROCESS_MODEL_NOT_EXISTED(5_10_0_001, "当前流程模型[%s]不存在"),
    WORKFLOW_PROCESS_MODEL_RELATE_BPMN_NOT_EXISTED(5_10_0_002, "当前流程模型[%s]对应的bpmn模型不存在"),
    WORKFLOW_PROCESS_MODEL_RELATE_RESOURCE_NOT_EXISTED(5_10_0_003, "当前流程模型[%s]对应的bpmn资源不存在"),
    WORKFLOW_PROCESS_MODEL_RELATE_RESOURCE_CONVERT_JSON_NODE_FAILED(5_10_0_004, "当前流程模型[%s]对应的bpmn资源转为JsonNode失败"),
    WORKFLOW_PROCESS_INSTANCE_START_FAILED(5_10_0_005, "当前流程启动失败"),
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
