package com.ah.cloud.permissions.enums;

import java.util.Objects;

/**
 * @program: permissions-center
 * @description: 菜单类型枚举
 * @author: YuKai Fan
 * @create: 2022-03-28 16:17
 **/
public enum MenuTypeEnum {

    /**
     * 未知
     */
    UNKNOWN(-1, "未知"),

    /**
     * 目录
     */
    DIRECTORY(1, "目录"),

    /**
     * 菜单
     */
    MENU(2, "菜单"),

    /**
     * 按钮
     */
    BUTTON(3, "按钮"),
    ;

    /**
     * 类型
     */
    private final int type;

    /**
     * 描述
     */
    private final String desc;

    public static MenuTypeEnum getByType(Integer type) {
        MenuTypeEnum[] values = values();
        for (MenuTypeEnum value : values) {
            if (Objects.equals(value.getType(), type)) {
                return value;
            }
        }
        return UNKNOWN;
    }

    MenuTypeEnum(int type, String desc) {
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

