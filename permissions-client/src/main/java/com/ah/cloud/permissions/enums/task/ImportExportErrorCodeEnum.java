package com.ah.cloud.permissions.enums.task;

import com.ah.cloud.permissions.exception.ErrorCode;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-13 17:23
 **/
public enum ImportExportErrorCodeEnum implements ErrorCode {

    /**
     * 任务错误码
     */
    CREATE_TASK_FAILED(8_10_0_001, "创建任务失败"),
    CURRENT_TASK_STATUS_ERROR(8_10_0_002, "当前任务状态异常"),

    /**
     * 导出
     */
    EXPORT_TASK_NOT_EXISTED(8_10_1_001, "导出任务不存在"),
    EXPORT_EXECUTE_FAILED(8_10_1_002, "【%s】执行导出任务失败"),

    /**
     * 导入
     */
    IMPORT_TASK_NOT_EXISTED(8_10_2_001, "导入任务不存在"),
    IMPORT_TASK_DATA_IS_EMPTY(8_10_2_002, "导入数据为空"),
    IMPORT_TASK_LIMIT_OVER_MAX_ALLOW(8_10_2_003, "导入数量超过限定数量！导入:[%s] 条,限定:[%s] 条"),

    /**
     * 文件
     */
    FILE_UPLOAD_FAILED(8_10_3_001, "文件上传失败"),

    /**
     * 模板
     */
    IMPORT_TEMPLATE_NOT_EXITED(8_10_4_001, "导入模板不存在"),
    ;

    private final int code;

    private final String msg;

    ImportExportErrorCodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public int getCode() {
        return 0;
    }

    @Override
    public String getMsg() {
        return null;
    }
}
