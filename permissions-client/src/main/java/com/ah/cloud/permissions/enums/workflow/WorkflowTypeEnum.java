package com.ah.cloud.permissions.enums.workflow;

import java.util.Objects;

/**
 * @program: permissions-center
 * @description: 工作流类型枚举
 * @author: YuKai Fan
 * @create: 2022-06-13 14:13
 **/
public enum WorkflowTypeEnum {

    /**
     * 未知
     */
    UNKNOWN(-1, "未知"),

    /**
     * 请假流程
     */
    LEAVE(1, "请假流程")
    ;

    private final int type;

    private final String name;

    public static boolean isValid(Integer type) {
        WorkflowTypeEnum workflowTypeEnum = getByType(type);
        return !Objects.equals(workflowTypeEnum, UNKNOWN);
    }

    public static WorkflowTypeEnum getByType(Integer type) {
        WorkflowTypeEnum[] values = values();
        for (WorkflowTypeEnum value : values) {
            if (Objects.equals(value.getType(), type)) {
                return value;
            }
        }
        return UNKNOWN;
    }

    WorkflowTypeEnum(int type, String name) {
        this.type = type;
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public String getName() {
        return name;
    }
}
