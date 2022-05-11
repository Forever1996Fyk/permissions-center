package com.ah.cloud.permissions.enums;

/**
 * @program: permissions-center
 * @description: 文件类型
 * @author: YuKai Fan
 * @create: 2022-05-06 07:36
 **/
public enum FileTypeEnum {

    /**
     * 其他
     */
    OTHER(-1, "其他"),
    /**
     * 图片
     */
    IMAGE(1, "图片"),

    /**
     * Excel表格
     */
    EXCEL(2, "Excel表格"),

    /**
     * 视频
     */
    VIDEO(3, "视频"),

    /**
     * 音频
     */
    AUDIO(4, "音频"),

    /**
     * 文档
     */
    DOC(5, "文档"),


    /**
     * 文档
     */
    TOTTENTS(6, "种子"),
    ;
    /**
     * 类型
     */
    private final int type;


    /**
     * 描述
     */
    private final String desc;

    FileTypeEnum(int type, String desc) {
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
