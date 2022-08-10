package com.ah.cloud.permissions.enums;

import java.util.Objects;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-03-28 17:21
 **/
public enum MenuOpenTypeEnum {

    /**
     * 未知
     */
    UNKNOWN(-1, "未知"),

    /**
     * 页面内
     */
    FRAME_TARGET(1, "页面内"),

    /**
     * 外部链接
     */
    NO_FRAME_TARGET(2, "外部链接"),
    ;

    private final int type;

    private final String desc;

    public static MenuOpenTypeEnum getByType(Integer type) {
        MenuOpenTypeEnum[] values = values();
        for (MenuOpenTypeEnum value : values) {
            if (Objects.equals(value.getType(), type)) {
                return value;
            }
        }
        return UNKNOWN;
    }

    MenuOpenTypeEnum(int type, String desc) {
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
