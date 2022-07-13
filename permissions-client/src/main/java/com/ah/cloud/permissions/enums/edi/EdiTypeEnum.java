package com.ah.cloud.permissions.enums.edi;

import java.util.Objects;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-05 14:34
 **/
public enum EdiTypeEnum {

    /**
     * 未知
     */
    UNKNOWN(-1, "未知"),

    /**
     * edi
     */
    EDI(1, "EDI"),

    /**
     * tech-edi
     */
    TECH_EDI(2, "TECH-EDI")
    ;

    private final int type;

    private final String desc;

    EdiTypeEnum(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public static EdiTypeEnum getByType(Integer type) {
        EdiTypeEnum[] values = values();
        for (EdiTypeEnum value : values) {
            if (Objects.equals(value.getType(), type)) {
                return value;
            }
        }
        return UNKNOWN;
    }

    public static boolean isValid(Integer type) {
        EdiTypeEnum ediTypeEnum = getByType(type);
        return !Objects.equals(ediTypeEnum, UNKNOWN);
    }

    public int getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }

    public static EdiTypeEnum get(boolean isTech) {
        return isTech ? TECH_EDI : EDI;
    }

    public boolean isTech() {
        return Objects.equals(this, TECH_EDI);
    }
}
