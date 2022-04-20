package com.ah.cloud.permissions.enums;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-03-28 17:21
 **/
public enum MenuOpenType {

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

    MenuOpenType(int type, String desc) {
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
