package com.ah.cloud.permissions.task.infrastructure.constant;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-13 17:40
 **/
public class ImportExportTaskConstants {

    /**
     * 导出前缀
     */
    public final static String EXPORT_PREFIX = "Export Service";

    /**
     * 导入前缀
     */
    public final static String IMPORT_PREFIX = "Import Service";

    /**
     * 业务类型
     */
    public final static String REQUEST_PARAM_BIZ_TYPE_KEY = "bizType";

    /**
     * 导出最大数据量
     */
    public static final Integer EXPORT_TASK_MAX_SIZE = 1000;
    /**
     * 导入文件最大数据量
     */
    public static final Integer IMPORT_TASK_MAX_SIZE = 1000;

}
