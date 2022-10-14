package com.ah.cloud.permissions.enums;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-05-07 08:01
 **/
public enum PositionTypeEnum {

    /**
     * 本地上传
     */
    LOCAL(1, "本地"),

    /**
     * 阿里云oss上传
     */
    ALIYUN_OSS(2, "阿里云OSS"),


    /**
     * minio oss文件上传
     */
    MINIO_OSS(3, "MINIO OSS"),
    ;

    /**
     * 类型
     */
    private final int type;


    /**
     * 描述
     */
    private final String desc;

    PositionTypeEnum(int type, String desc) {
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
