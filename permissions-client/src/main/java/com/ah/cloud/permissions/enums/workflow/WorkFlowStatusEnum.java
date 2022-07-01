package com.ah.cloud.permissions.enums.workflow;

import java.util.Objects;

/**
 * @program: permissions-center
 * @description: 流程状态
 * @author: YuKai Fan
 * @create: 2022-06-17 10:11
 **/
public enum WorkFlowStatusEnum {

    //启动  撤回  驳回  审批中  审批通过  审批异常

    /**
     * 未知
     */
    UNKNOWN(-1, "未知"),

    /**
     * 启动
     */
    STARTED(10, "启动"),

    /**
     * 撤回
     */
    WITHDRAW(20, "撤回"),

    /**
     * 驳回
     */
    REJECTED(30, "驳回"),

    /**
     * 审批中
     */
    BEING_APPROVAL(40, "审批中"),

    /**
     * 审批通过
     */
    APPROVAL(50, "审批通过"),

    /**
     * 审批异常
     */
    APPROVAL_ERROR(60, "审批异常"),

    /**
     * 完成
     */
    COMPLETE(99, "完成"),
    ;

    private final int status;

    private final String desc;

    WorkFlowStatusEnum(int status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    public static boolean isValid(Integer flowStatus) {
        WorkFlowStatusEnum workFlowStatusEnum = getByStatus(flowStatus);
        return Objects.equals(workFlowStatusEnum, UNKNOWN);
    }

    public static WorkFlowStatusEnum getByStatus(Integer status) {
        WorkFlowStatusEnum[] workFlowStatusEnums = values();
        for (WorkFlowStatusEnum workFlowStatusEnum : workFlowStatusEnums) {
            if (Objects.equals(workFlowStatusEnum.getStatus(), status)) {
                return workFlowStatusEnum;
            }
        }
        return UNKNOWN;
    }

    public int getStatus() {
        return status;
    }

    public String getDesc() {
        return desc;
    }
}
