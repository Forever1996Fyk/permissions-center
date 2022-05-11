package com.ah.cloud.permissions.enums;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-05-11 11:31
 **/
public enum QuartzJobChangeTypeEnum {
    /**
     * 添加job
     */
    ADD(1, "添加job"),

    /**
     * 更新job
     */
    UPDATE(2, "更新job"),

    /**
     * 删除job
     */
    DELETED(3, "删除job"),

    /**
     * 执行一次
     */
    RUN(4, "执行一次"),

    /**
     * 变更状态
     */
    CHANGE_STATUS(5, "变更状态"),
    ;

    private final int type;

    private final String desc;

    QuartzJobChangeTypeEnum(int type, String desc) {
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
