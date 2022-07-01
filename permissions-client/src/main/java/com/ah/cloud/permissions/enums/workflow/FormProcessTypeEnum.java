package com.ah.cloud.permissions.enums.workflow;

import java.util.Objects;

/**
 * @program: permissions-center
 * @description: 表单类型
 * @author: YuKai Fan
 * @create: 2022-06-29 18:04
 **/
public enum FormProcessTypeEnum {

    /**
     * 未知
     */
    UNKNOWN(-1, "未知"),

    /**
     * 流程表单
     */
    PROCESS_FORM(1, "流程表单"),

    /**
     * 任务表单
     */
    TASK_FORM(2, "任务表单"),
    ;

    private final int type;

    private final String desc;

    public static FormProcessTypeEnum getByType(Integer type) {
        FormProcessTypeEnum[] values = values();
        for (FormProcessTypeEnum value : values) {
            if (Objects.equals(value.getType(), type)) {
                return value;
            }
        }
        return UNKNOWN;
    }

    public static boolean isValid(Integer type) {
        FormProcessTypeEnum processTypeEnum = getByType(type);
        return Objects.equals(processTypeEnum, UNKNOWN);
    }

    FormProcessTypeEnum(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public int getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }
}
