package com.ah.cloud.permissions.enums;

/**
 * @program: permissions-center
 * @description: 菜单类型枚举
 * @author: YuKai Fan
 * @create: 2022-03-28 16:17
 **/
public enum MenuTypeEnum {

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

