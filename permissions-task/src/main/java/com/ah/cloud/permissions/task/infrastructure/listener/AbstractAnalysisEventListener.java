package com.ah.cloud.permissions.task.infrastructure.listener;

import com.ah.cloud.permissions.task.domain.dto.ImportBaseDTO;
import com.ah.cloud.permissions.task.domain.enums.ImportExportTaskStatusEnum;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.List;
import java.util.Objects;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-17 10:25
 **/
public abstract class AbstractAnalysisEventListener<T extends ImportBaseDTO> extends AnalysisEventListener<T> {

    protected ImportExportTaskStatusEnum getTaskStatus(List<ImportExportTaskStatusEnum> statusEnumList) {
        boolean flag = false;
        for (ImportExportTaskStatusEnum statusEnum : statusEnumList) {
            if (Objects.equals(statusEnum, ImportExportTaskStatusEnum.FAILED)) {
                return ImportExportTaskStatusEnum.FAILED;
            }
            if (Objects.equals(statusEnum, ImportExportTaskStatusEnum.PART_SUCCESS)) {
                flag = true;
            }
        }
        return flag ? ImportExportTaskStatusEnum.SUCCESS : ImportExportTaskStatusEnum.PART_SUCCESS;
    }
}
