package com.ah.cloud.permissions.enums;

import java.util.Objects;

/**
 * @program: permissions-center
 * @description: 定时任务状态
 * @author: YuKai Fan
 * @create: 2022-05-01 09:32
 **/
public enum JobStatusEnum {

    /**
     * 正常
     */
    NORMAL(1, "正常"),

    /**
     * 暂停
     */
    PAUSE(2, "暂停"),
    ;

    private final int status;

    private final String desc;

    JobStatusEnum(int status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    public int getStatus() {
        return status;
    }

    public String getDesc() {
        return desc;
    }

    public boolean isValid(Integer status) {
        JobStatusEnum[] values = values();
        for (JobStatusEnum value : values) {
            if (Objects.equals(status, value.getStatus())) {
               return true;
            }
        }
        return false;
    }
}
