package com.ah.cloud.permissions.task.domain.enums;

import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-13 16:36
 **/
public enum ImportExportBizTypeEnum {

    /**
     * 业务枚举
     */
    UNKNOWN("UNKNOWN", "未知", ImportExportTaskTypeEnum.UNKNOWN, false),
    COMM_IMPORT_ERROR_EXPORT("COMM_IMPORT_ERROR_EXPORT", "导入错误日志", ImportExportTaskTypeEnum.EXPORT,  Boolean.FALSE),

    /**
     * 用户导出
     */
    SYS_USER_EXPORT("SYS_USER_EXPORT", "系统用户导出", ImportExportTaskTypeEnum.EXPORT, true),

    /**
     * 用户导入
     */
    SYS_USER_IMPORT("SYS_USER_IMPORT", "系统用户导入", ImportExportTaskTypeEnum.IMPORT, false);

    ;

    /**
     * 业务标识
     */
    private final String code;

    /**
     * 描述
     */
    private final String desc;

    /**
     * 任务类型
     */
    private final ImportExportTaskTypeEnum taskTypeEnum;

    /**
     * 前端是否展示
     */
    private final boolean isShow;

    public static ImportExportBizTypeEnum getByCode(String code) {
        ImportExportBizTypeEnum[] values = values();
        for (ImportExportBizTypeEnum value : values) {
            if (StringUtils.equals(code, value.getCode())) {
                return value;
            }
        }
        return UNKNOWN;
    }

    public static boolean isValid(String code) {
        ImportExportBizTypeEnum importExportBizTypeEnum = getByCode(code);
        return !Objects.equals(importExportBizTypeEnum, UNKNOWN);
    }

    public static boolean isImportValid(String code) {
        ImportExportBizTypeEnum bizTypeEnum = getByCode(code);
        return bizTypeEnum.getTaskTypeEnum().isImport();
    }

    public static boolean isExportValid(String code) {
        ImportExportBizTypeEnum bizTypeEnum = getByCode(code);
        return !bizTypeEnum.getTaskTypeEnum().isImport();
    }

    ImportExportBizTypeEnum(String code, String desc, ImportExportTaskTypeEnum taskTypeEnum, boolean isShow) {
        this.code = code;
        this.desc = desc;
        this.taskTypeEnum = taskTypeEnum;
        this.isShow = isShow;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public ImportExportTaskTypeEnum getTaskTypeEnum() {
        return taskTypeEnum;
    }

    public boolean isShow() {
        return isShow;
    }
}
