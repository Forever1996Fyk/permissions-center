package com.ah.cloud.permissions.task.infrastructure.handler.selector;

import com.ah.cloud.permissions.enums.common.ErrorCodeEnum;
import com.ah.cloud.permissions.exception.ErrorCode;
import com.ah.cloud.permissions.task.domain.enums.ImportExportBizTypeEnum;
import com.ah.cloud.permissions.task.infrastructure.exception.ImportExportException;
import com.ah.cloud.permissions.task.infrastructure.handler.ImportHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-14 16:04
 **/
@Component
public class ImportHandlerSelector {
    @Resource
    private List<ImportHandler> importHandlerList;

    public ImportHandler select(ImportExportBizTypeEnum bizTypeEnum) {
        for (ImportHandler importHandler : importHandlerList) {
            if (Objects.equals(importHandler.getBizType(), bizTypeEnum)) {
                return importHandler;
            }
        }
        throw new ImportExportException(ErrorCodeEnum.SELECTOR_NOT_EXISTED);
    }
}
