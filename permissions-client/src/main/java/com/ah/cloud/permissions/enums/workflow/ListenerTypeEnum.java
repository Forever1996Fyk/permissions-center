package com.ah.cloud.permissions.enums.workflow;

import java.util.Objects;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-06-16 10:37
 **/
public enum ListenerTypeEnum {

    /**
     * 未知
     */
    UNKNOWN(-1, "未知"),

    /**
     * 任务监听
     */
    TASK_LISTENER(1, "任务监听"),

    /**
     * 执行监听
     */
    EXECUTE_LISTENER(2, "执行监听"),
    ;

    private final int type;

    private final String desc;

    public static boolean isValid(Integer type) {
        ListenerTypeEnum value = getByType(type);
        return !Objects.equals(value.getType(), type);
    }

    public static ListenerTypeEnum getByType(Integer type) {
        ListenerTypeEnum[] values = values();
        for (ListenerTypeEnum value : values) {
            if (Objects.equals(value.getType(), type)) {
                return value;
            }
        }
        return UNKNOWN;
    }

    ListenerTypeEnum(int type, String desc) {
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
