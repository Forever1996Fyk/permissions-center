package com.ah.cloud.permissions.enums;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-05-31 18:17
 **/
public enum PushMsgModeEnum {

    /**
     * 单播
     */
    SINGLE_PUSH(1, "单推"),

    /**
     * 广播
     */
    BROAD_PUSH(2, "广播"),
    ;
    private final int type;

    private final String desc;

    PushMsgModeEnum(int type, String desc) {
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
