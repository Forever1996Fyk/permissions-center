package com.ah.cloud.permissions.enums.netty;

import java.util.Objects;

/**
 * @program: permissions-center
 * @description: 消息来源
 * @author: YuKai Fan
 * @create: 2022-05-12 14:10
 **/
public enum MsgSourceEnum {

    /**
     * 未知
     */
    UNKNOWN(-1, "未知", "unknown"),

    /**
     * web浏览器
     */
    WEB(1, "web浏览器", "web"),

    /**
     * iOS
     */
    IOS(2, "iOS", "mobile"),

    /**
     * 安卓
     */
    ANDROID(3, "安卓", "mobile"),

    /**
     * 安卓pad
     */
    PAD(4, "安卓pad", "mobile_pad"),

    /**
     * ipad
     */
    IPAD(5, "ipad", "mobile_pad"),

    /**
     * 服务端连接
     */
    SERVER(6, "server", "server"),

    ;

    private final int type;

    private final String desc;

    private final String platform;

    MsgSourceEnum(int type, String desc, String platform) {
        this.type = type;
        this.desc = desc;
        this.platform = platform;
    }

    public int getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }

    public String getPlatform() {
        return platform;
    }

    public static MsgSourceEnum getByType(Integer type) {
        MsgSourceEnum[] msgSourceEnums = values();
        for (MsgSourceEnum msgSourceEnum : msgSourceEnums) {
            if (Objects.equals(msgSourceEnum.getType(), type)) {
                return msgSourceEnum;
            }
        }
        return UNKNOWN;
    }
}
