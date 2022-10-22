package com.ah.cloud.permissions.enums;

import java.util.Objects;

/**
 * @program: permissions-center
 * @description: 资源类型
 * @author: YuKai Fan
 * @create: 2022-05-06 07:36
 **/
public enum ResourceBizTypeEnum {

    /**
     * 默认
     */
    UNKNOWN(-1, "默认", "default"),

    /**
     * 头像
     */
    AVATAR(1, "头像", "avatar"),

    /**
     * 导入
     */
    IMPORTING(2, "导入", "importing"),

    /**
     * 导出
     */
    EXPORTING(3, "导出", "exporting"),
    ;

    /**
     * 类型
     */
    private final int type;


    /**
     * 描述
     */
    private final String desc;

    /**
     * 文件夹
     */
    private final String bucketName;

    ResourceBizTypeEnum(int type, String desc, String bucketName) {
        this.type = type;
        this.desc = desc;
        this.bucketName = bucketName;
    }

    public int getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }

    public String getBucketName() {
        return bucketName;
    }

    public static ResourceBizTypeEnum getByType(Integer type) {
        ResourceBizTypeEnum[] values = values();
        for (ResourceBizTypeEnum value : values) {
            if (Objects.equals(value.getType(), type)) {
                return value;
            }
        }
        return UNKNOWN;
    }

    public static boolean isValid(Integer type) {
        ResourceBizTypeEnum bizTypeEnum = getByType(type);
        return !Objects.equals(bizTypeEnum, UNKNOWN);
    }
}
