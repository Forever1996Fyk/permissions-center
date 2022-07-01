package com.ah.cloud.permissions.enums.workflow;

import java.util.Objects;

/**
 * @program: permissions-center
 * @description: 表单类型
 * @author: YuKai Fan
 * @create: 2022-06-15 11:21
 **/
public enum FormTypeEnum {

    /**
     * 未知
     */
    UNKNOWN(-1, "未知"),

    /**
     * 自定义表单
     */
    CUSTOM_FORM(1, "自定义表单"),

    /**
     * online表单
     */
    ONLINE_FORM(2, "online表单"),
    ;

    private final int type;

    private final String desc;

    FormTypeEnum(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public int getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }

    public static FormTypeEnum getByType(Integer type) {
        FormTypeEnum[] formTypeEnums = values();
        for (FormTypeEnum formTypeEnum : formTypeEnums) {
            if (Objects.equals(formTypeEnum.getType(), type)) {
                return formTypeEnum;
            }
        }
        return UNKNOWN;
    }

    public static boolean isValid(Integer type) {
        FormTypeEnum formTypeEnum = getByType(type);
        return !Objects.equals(formTypeEnum, UNKNOWN);
    }
}
