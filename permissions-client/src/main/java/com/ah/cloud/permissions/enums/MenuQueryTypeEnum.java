package com.ah.cloud.permissions.enums;

import java.util.Objects;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-03-30 11:39
 **/
public enum MenuQueryTypeEnum {

    /**
     * 未知
     */
    UNKNOWN(-1, "未知"),
    /**
     * 全量查询
     */
    ALL(1, "全量查询"),

    /**
     * 用户菜单
     */
    USER_MENU(3, "用户菜单"),

    /**
     * 角色菜单
     */
    ROLE_MENU(2, "角色菜单"),

    ;

    private final int type;

    private final String desc;


    public static MenuQueryTypeEnum getByType(Integer type) {
        MenuQueryTypeEnum[] values = values();
        for (MenuQueryTypeEnum menuQueryTypeEnum : values) {
            if (Objects.equals(menuQueryTypeEnum.getType(), type)) {
                return menuQueryTypeEnum;
            }
        }
        return UNKNOWN;
    }

    public boolean isRoleMenu() {
        return Objects.equals(this, ROLE_MENU);
    }

    public boolean isUserMenu() {
        return Objects.equals(this, USER_MENU);
    }

    MenuQueryTypeEnum(int type, String desc) {
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
