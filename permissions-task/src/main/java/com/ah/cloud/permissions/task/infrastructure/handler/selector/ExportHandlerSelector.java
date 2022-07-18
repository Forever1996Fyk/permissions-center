package com.ah.cloud.permissions.task.infrastructure.handler.selector;

import com.ah.cloud.permissions.enums.common.ErrorCodeEnum;
import com.ah.cloud.permissions.task.domain.enums.ImportExportBizTypeEnum;
import com.ah.cloud.permissions.task.infrastructure.exception.ImportExportException;
import com.ah.cloud.permissions.task.infrastructure.handler.ExportHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-14 11:52
 **/
@Component
public class ExportHandlerSelector {
    @Resource
    private List<ExportHandler> exportHandlerList;

    public ExportHandler select(ImportExportBizTypeEnum bizTypeEnum) {
        for (ExportHandler exportHandler : exportHandlerList) {
            if (Objects.equals(bizTypeEnum, exportHandler.getBizType())) {
                return exportHandler;
            }
        }
        throw new ImportExportException(ErrorCodeEnum.SELECTOR_NOT_EXISTED, "ExportHandlerSelector");
    }
}
