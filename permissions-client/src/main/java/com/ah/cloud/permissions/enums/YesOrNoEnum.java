package com.ah.cloud.permissions.enums;

import java.util.Objects;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-03-29 15:54
 **/
public enum YesOrNoEnum {

    /**
     * 否
     */
    NO(0, "否"),

    /**
     * 是
     */
    YES(1, "是"),

    ;

    /**
     * 类型
     */
    private final int type;

    /**
     * 描述
     */
    private final String desc;

    YesOrNoEnum(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public static YesOrNoEnum getByType(Integer type) {
        YesOrNoEnum[] yesOrNoEnums = values();
        for (YesOrNoEnum yesOrNoEnum : yesOrNoEnums) {
            if (Objects.equals(yesOrNoEnum.getType(), type)) {
                return yesOrNoEnum;
            }
        }
        return YesOrNoEnum.NO;
    }

    public boolean isYes() {
        return Objects.equals(this, YES);
    }

    public boolean isNo() {
        return Objects.equals(this, NO);
    }

    public int getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }
}
