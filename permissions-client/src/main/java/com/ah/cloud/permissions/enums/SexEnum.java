package com.ah.cloud.permissions.enums;

import java.util.Objects;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-15 14:59
 **/
public enum SexEnum {

    /**
     * 未知
     */
    UNKNOWN(-1, "未知"),

    /**
     * 男
     */
    MALE(1, "男"),

    /**
     * 女
     */
    FEMALE(2, "女"),
    ;

    private final int type;

    private final String desc;

    public static SexEnum getByType(Integer type) {
        SexEnum[] values = values();
        for (SexEnum value : values) {
            if (Objects.equals(value.getType(), type)) {
                return value;
            }
        }
        return UNKNOWN;
    }

    public static SexEnum getByDesc(String desc) {
        SexEnum[] values = values();
        for (SexEnum value : values) {
            if (Objects.equals(value.getDesc(), desc)) {
                return value;
            }
        }
        return null;
    }

    public static boolean isValid(Integer type) {
        SexEnum sexEnum = getByType(type);
        return !Objects.equals(sexEnum, UNKNOWN);
    }

    SexEnum(int type, String desc) {
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
