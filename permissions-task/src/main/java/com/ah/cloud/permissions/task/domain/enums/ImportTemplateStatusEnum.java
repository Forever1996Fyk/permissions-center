package com.ah.cloud.permissions.task.domain.enums;

import java.util.Arrays;
import java.util.Objects;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-14 18:09
 **/
public enum ImportTemplateStatusEnum {

    /**
     * 未知
     */
    UNKNOWN(-1, "未知"),

    /**
     * 启用
     */
    ENABLE(1, "启用"),

    /**
     * 禁用
     */
    DISABLE(2, "禁用"),
    ;

    private final int status;

    private final String desc;

    ImportTemplateStatusEnum(int status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    public static ImportTemplateStatusEnum getByStatus(Integer status) {
        return Arrays.stream(values())
                .filter(importTemplateStatusEnum -> Objects.equals(importTemplateStatusEnum.getStatus(), status))
                .findFirst()
                .orElse(UNKNOWN);
    }

    public static boolean isValid(Integer status) {
        ImportTemplateStatusEnum statusEnum = getByStatus(status);
        return !Objects.equals(statusEnum, UNKNOWN);
    }

    public int getStatus() {
        return status;
    }

    public String getDesc() {
        return desc;
    }
}
