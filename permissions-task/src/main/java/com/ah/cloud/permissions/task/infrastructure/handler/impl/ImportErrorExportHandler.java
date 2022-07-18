package com.ah.cloud.permissions.task.infrastructure.handler.impl;

import com.ah.cloud.permissions.task.domain.dto.ExportBaseDTO;
import com.ah.cloud.permissions.task.domain.enums.ImportExportBizTypeEnum;
import com.ah.cloud.permissions.task.infrastructure.handler.AbstractExportHandler;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import org.springframework.stereotype.Component;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-15 17:23
 **/
@Component
public class ImportErrorExportHandler extends AbstractExportHandler<ExportBaseDTO> {

    @Override
    protected Class<ExportBaseDTO> getClazz() {
        return ExportBaseDTO.class;
    }

    @Override
    protected void loadDataAndWriteFile(String param, ExcelWriter excelWriter, WriteSheet writeSheet) {

    }

    @Override
    protected String getLogMark() {
        return null;
    }

    @Override
    public ImportExportBizTypeEnum getBizType() {
        return ImportExportBizTypeEnum.COMM_IMPORT_ERROR_EXPORT;
    }
}
