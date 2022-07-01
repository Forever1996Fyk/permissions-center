package com.ah.cloud.permissions.enums.workflow;

import java.util.Objects;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-06-16 10:37
 **/
public enum ListenerEventEnum {

    /**
     * 未知
     */
    UNKNOWN("unknown", "未知"),

    /**
     * 执行监听-开始事件
     */
    START("start", "执行监听-开始事件"),

    /**
     * 执行监听-结束事件
     */
    END("end", "执行监听-结束事件"),

    /**
     * 任务监听-删除事件
     */
    DELETE("delete", "任务监听-删除事件"),

    /**
     * 任务监听-创建事件
     */
    CREATE("create", "任务监听-创建事件"),

    /**
     * 任务监听-指派人事件
     */
    ASSIGNMENT("assignment", "任务监听-指派人事件"),

    /**
     * 任务监听-完成事件
     */
    COMPLETE("complete", "任务监听-完成事件"),
    ;

    private final String type;

    private final String desc;

    public static boolean isValid(String type) {
        ListenerEventEnum value = getByType(type);
        return !Objects.equals(value.getType(), type);
    }

    public static ListenerEventEnum getByType(String type) {
        ListenerEventEnum[] values = values();
        for (ListenerEventEnum value : values) {
            if (Objects.equals(value.getType(), type)) {
                return value;
            }
        }
        return UNKNOWN;
    }

    ListenerEventEnum(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public String getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }
}
