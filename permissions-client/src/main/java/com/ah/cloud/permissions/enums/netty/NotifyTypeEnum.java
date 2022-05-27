package com.ah.cloud.permissions.enums.netty;

/**
 * @program: permissions-center
 * @description: 通知类型
 * @author: YuKai Fan
 * @create: 2022-05-20 10:31
 **/
public enum NotifyTypeEnum {

    /**
     * 系统通知
     */
    SYSTEM(1, "系统通知"),

    /**
     * 下线通知
     */
    OFFLINE(2, "下线通知"),
    ;

    private final int type;

    private final String desc;

    NotifyTypeEnum(int type, String desc) {
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
