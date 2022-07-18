package com.ah.cloud.permissions.task.infrastructure.handler;

import com.ah.cloud.permissions.task.domain.dto.ExportBaseDTO;
import com.ah.cloud.permissions.task.domain.dto.ImportExportTaskAddDTO;
import com.ah.cloud.permissions.task.domain.enums.ImportExportBizTypeEnum;
import com.ah.cloud.permissions.task.infrastructure.repository.bean.SysImportExportTask;
import org.apache.commons.lang3.tuple.ImmutablePair;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-13 18:20
 **/
public interface ExportHandler {

    /**
     * 执行导出
     * @param task
     * @return left: 文件名称, right: 资源id
     */
    ImmutablePair<String, Long> executeExport(SysImportExportTask task);

    /**
     * 业务类型
     * @return
     */
    ImportExportBizTypeEnum getBizType();

    /**
     * 校验导出参数
     * @param addDTO
     */
    void checkExportParam(ImportExportTaskAddDTO addDTO);
}
