package com.ah.cloud.permissions.enums;

/**
 * @program: permissions-center
 * @description: 设备类型
 * @author: YuKai Fan
 * @create: 2022-05-31 14:50
 **/
public enum DeviceTypeEnum {

    /**
     * ios
     */
    IOS(1, "ios"),

    /**
     * android
     */
    ANDROID(2, "android"),
    ;

    private final int type;

    private final String desc;

    DeviceTypeEnum(int type, String desc) {
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
