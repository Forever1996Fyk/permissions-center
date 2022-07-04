package com.ah.cloud.permissions.enums.jasypt;

import java.util.Objects;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-04 15:15
 **/
public enum AlgorithmTypeEnum {

    /**
     * Basic
     */
    BASIC(1, "");
    ;

    private final int type;

    private final String name;

    public static AlgorithmTypeEnum getByType(Integer type) {
        AlgorithmTypeEnum[] values = values();
        for (AlgorithmTypeEnum value : values) {
            if (Objects.equals(value.getType(), type)) {
                return value;
            }
        }
        return BASIC;
    }


    AlgorithmTypeEnum(int type, String name) {
        this.type = type;
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public String getName() {
        return name;
    }
}
