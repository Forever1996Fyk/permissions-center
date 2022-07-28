package com.ah.cloud.permissions.enums;

import java.util.Objects;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-19 10:31
 **/
public enum DataScopeEnum {

    /**
     * 默认
     */
    DEFAULT_SCOPE(0, "默认"),

    /**
     * 全部
     */
    DATA_SCOPE_ALL(1,"全部"),

    /**
     * 本级
     */
    DATA_SCOPE_DEPT(2,"本级"),

    /**
     * 下级
     */
    DATA_SCOPE_CHILD(3,"下级"),

    /**
     * 本级与下级
     */
    DATA_SCOPE_DEPT_AND_CHILD(4,"本级及下级"),

    /**
     * 本人
     */
    DATA_SCOPE_SELF(5, "本人"),
    ;

    private final int type;

    private final String desc;

    DataScopeEnum(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public static DataScopeEnum getByType(Integer type) {
        DataScopeEnum[] values = values();
        for (DataScopeEnum value : values) {
            if (Objects.equals(value.getType(), type)) {
                return value;
            }
        }
        return DATA_SCOPE_ALL;
    }

    public int getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }
}
