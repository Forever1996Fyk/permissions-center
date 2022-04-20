package com.ah.cloud.permissions.enums;

import java.util.Objects;

/**
 * @program: permissions-center
 * @description: 系统api类型枚举
 * @author: YuKai Fan
 * @create: 2022-04-03 14:53
 **/
public enum SysApiTypeEnum {

    /**
     * 未知
     */
    UNKNOWN(-1, "未知"),

    /**
     * WEB端 系统类型
     */
    WEB_SYSTEM(1, "web端系统类型"),

    /**
     * WEB端 业务类型
     */
    WEB_BUSINESS(2, "web端业务类型"),

    /**
     * 移动端 系统类型
     */
    MT_SYSTEM(3, "移动端系统类型"),

    /**
     * 移动端 业务类型
     */
    MT_BUSINESS(4, "移动端业务类型"),
    ;


    private final int type;

    private final String desc;

    SysApiTypeEnum(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public int getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }

    public static boolean isValid(Integer type) {
        SysApiTypeEnum sysApiTypeEnum = getByType(type);
        return !Objects.equals(sysApiTypeEnum, UNKNOWN);
    }

    public static SysApiTypeEnum getByType(Integer type) {
        SysApiTypeEnum[] values = values();
        for (SysApiTypeEnum sysApiTypeEnum : values) {
            if (Objects.equals(sysApiTypeEnum.getType(), type)) {
                return sysApiTypeEnum;
            }
        }
        return UNKNOWN;
    }
}
