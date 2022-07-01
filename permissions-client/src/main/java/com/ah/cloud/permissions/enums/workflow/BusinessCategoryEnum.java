package com.ah.cloud.permissions.enums.workflow;

import java.util.Objects;

/**
 * @program: permissions-center
 * @description: 业务类别
 * @author: YuKai Fan
 * @create: 2022-06-13 17:37
 **/
public enum BusinessCategoryEnum {


    /**
     * 未知
     */
    UNKNOWN("unknown", "未知"),
    /**
     * 流程OA
     */
    OA("oa", "流程OA"),

    /**
     * 业务办理
     */
    BUSINESS_HANDLE("business_handle", "业务办理"),

    ;

    private final String category;

    private final String name;

    BusinessCategoryEnum(String category, String name) {
        this.category = category;
        this.name = name;
    }

    public static boolean isValid(String category) {
        BusinessCategoryEnum businessCategoryEnum = getByCategory(category);
        return !Objects.equals(businessCategoryEnum, UNKNOWN);
    }

    public static BusinessCategoryEnum getByCategory(String category) {
        BusinessCategoryEnum[] values = values();
        for (BusinessCategoryEnum value : values) {
            if (Objects.equals(value.getCategory(), category)) {
                return value;
            }
        }
        return UNKNOWN;
    }

    public String getCategory() {
        return category;
    }

    public String getName() {
        return name;
    }
}
