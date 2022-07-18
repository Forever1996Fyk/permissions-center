package com.ah.cloud.permissions.task.domain.enums;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-13 17:49
 **/
public enum ImportExportTaskStatusEnum {

    /**
     * 未知
     */
    UNKNOWN(-1, "未知"),

    /**
     * 待处理
     */
    WAIT(10, "待处理"),

    /**
     * 处理中
     */
    PROCESSING(20, "处理中"),

    /**
     * 处理成功
     */
    SUCCESS(30, "处理成功"),

    /**
     * 处理失败
     */
    FAILED(40, "处理失败"),

    /**
     * 部分成功
     */
    PART_SUCCESS(50, "部分成功"),

    ;


    private final Integer status;

    private final String desc;

    ImportExportTaskStatusEnum(Integer status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    public Integer getStatus() {
        return status;
    }

    public String getDesc() {
        return desc;
    }
}
