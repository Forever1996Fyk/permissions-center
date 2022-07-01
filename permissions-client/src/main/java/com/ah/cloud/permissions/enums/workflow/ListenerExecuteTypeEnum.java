package com.ah.cloud.permissions.enums.workflow;

import java.util.Objects;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-06-16 10:38
 **/
public enum ListenerExecuteTypeEnum {

    /**
     * 未知
     */
    UNKNOWN("unknown", "未知"),

    /**
     * JAVA类
     */
    JAVA_CLASS("class", "JAVA类"),

    /**
     * Spring表达式
     */
    SPRING_EXPRESSION("delegateExpression", "Spring表达式"),

    /**
     * 表达式
     */
    EXPRESSION("expression", "表达式"),
    ;

    private final String type;

    private final String desc;

    public static boolean isValid(String type) {
        ListenerExecuteTypeEnum value = getByType(type);
        return !Objects.equals(value.getType(), type);
    }

    public static ListenerExecuteTypeEnum getByType(String type) {
        ListenerExecuteTypeEnum[] values = values();
        for (ListenerExecuteTypeEnum value : values) {
            if (Objects.equals(value.getType(), type)) {
                return value;
            }
        }
        return UNKNOWN;
    }

    ListenerExecuteTypeEnum(String type, String desc) {
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
