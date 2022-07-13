package com.ah.cloud.permissions.enums.edi;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-05 10:31
 **/
public enum BizRetrySourceEnum {

    /**
     * 权限系统
     */
    PERMISSION(1, "权限系统"),
    ;

    private final int type;

    private final String desc;

    BizRetrySourceEnum(int type, String desc) {
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
