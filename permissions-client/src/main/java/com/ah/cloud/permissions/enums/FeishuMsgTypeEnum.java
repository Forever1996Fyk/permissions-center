package com.ah.cloud.permissions.enums;

/**
 * @program: permissions-center
 * @description: 通知内容类型
 * @author: YuKai Fan
 * @create: 2022-06-02 15:41
 **/
public enum FeishuMsgTypeEnum {

    /**
     * 文本
     */
    TEXT("text", "文本"),

    /**
     * 富文本
     */
    POST("post", "富文本"),

    /**
     * 图片
     */
    IMAGE("image", "图片"),
    ;

    private final String type;

    private final String desc;

    FeishuMsgTypeEnum(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public String getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }
}
