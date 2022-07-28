package com.ah.cloud.permissions.task.domain.enums;

import java.util.Objects;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-13 17:00
 **/
public enum ImportExportTaskTypeEnum {

    /**
     * 未知
     */
    UNKNOWN(-1, "未知"),

    /**
     * 导入
     */
    IMPORT(1, "导入"),

    /**
     * 导出
     */
    EXPORT(2, "导出"),
    ;

    private final Integer type;

    private final String desc;

    ImportExportTaskTypeEnum(Integer type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public boolean isImport() {
        return Objects.equals(this, IMPORT);
    }

    public Integer getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }
}
