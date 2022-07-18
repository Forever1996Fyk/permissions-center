package com.ah.cloud.permissions.task.infrastructure.handler;

import com.ah.cloud.permissions.task.domain.enums.ImportExportBizTypeEnum;
import com.ah.cloud.permissions.task.domain.enums.ImportExportTaskStatusEnum;
import com.ah.cloud.permissions.task.infrastructure.repository.bean.SysImportExportTask;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.ImmutableTriple;

import java.io.IOException;
import java.io.InputStream;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-14 15:45
 **/
public interface ImportHandler {


    /**
     * 获取对应的导入类型
     *
     * @return 导入类型枚举
     */
    ImportExportBizTypeEnum getBizType();

    /**
     * 执行导入
     *
     * @param task 导入任务
     * @param inputStream 文件流
     * @return
     */
    ImmutableTriple<String, Long, ImportExportTaskStatusEnum> executeImport(SysImportExportTask task, InputStream inputStream);
}
