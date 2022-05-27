package com.ah.cloud.permissions.enums.netty;

import java.util.Objects;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-05-11 22:48
 **/
public enum FormatEnum {

    /**
     * 未知
     */
    UNKNOWN(-1, "未知"),
    /**
     * 文字
     */
    TEXT(1, "文字"),

    /**
     * 图片
     */
    PICTURE(2, "图片"),

    /**
     * 视频
     */
    VIDEO(3, "视频"),

    /**
     * 音频
     */
    AUDIO(4, "音频"),
    ;

    private final int type;

    private final String desc;

    FormatEnum(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public int getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }

    public static FormatEnum getByType(Integer type) {
        FormatEnum[] formatEnums = values();
        for (FormatEnum formatEnum : formatEnums) {
            if(Objects.equals(formatEnum.getType(), type)) {
                return formatEnum;
            }
        }
        return UNKNOWN;
    }
}
