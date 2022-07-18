package com.ah.cloud.permissions.task.infrastructure.listener;

import com.ah.cloud.permissions.biz.infrastructure.util.CollectionUtils;
import com.ah.cloud.permissions.biz.infrastructure.util.SpringUtils;
import com.ah.cloud.permissions.task.application.client.ImportTaskClient;
import com.ah.cloud.permissions.task.domain.dto.business.import2.SysUserImportDTO;
import com.ah.cloud.permissions.task.domain.enums.ImportExportTaskStatusEnum;
import com.ah.cloud.permissions.task.infrastructure.constant.ImportExportTaskConstants;
import com.ah.cloud.permissions.task.infrastructure.handler.impl.SysUserImportHandler;
import com.ah.cloud.permissions.task.infrastructure.repository.bean.SysImportExportTask;
import com.alibaba.excel.context.AnalysisContext;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.tuple.ImmutableTriple;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-15 15:24
 **/
public class SysUserImportExcelListener extends AbstractAnalysisEventListener<SysUserImportDTO> {
    private final SysUserImportHandler sysUserImportHandler;

    private final SysImportExportTask sysImportExportTask;

    private AtomicLong errorCount = new AtomicLong(0);

    private List<ImportExportTaskStatusEnum> statusEnumList = Lists.newArrayList();

    private final List<SysUserImportDTO> list = Lists.newArrayList();


    public SysUserImportExcelListener(SysUserImportHandler sysUserImportHandler, SysImportExportTask sysImportExportTask) {
        this.sysUserImportHandler = sysUserImportHandler;
        this.sysImportExportTask = sysImportExportTask;
    }


    @Override
    public void invoke(SysUserImportDTO sysUserImportDTO, AnalysisContext analysisContext) {
        list.add(sysUserImportDTO);
        if (list.size() == ImportExportTaskConstants.IMPORT_TASK_MAX_SIZE) {
            // 保存数据
            saveData();
            list.clear();
        }
    }

    /**
     * 保存导入的数据
     */
    private void saveData() {
        ImmutableTriple<String, Long, ImportExportTaskStatusEnum> triple = sysUserImportHandler.processData(list, sysImportExportTask);
        if (triple != null) {
            statusEnumList.add(triple.getRight());
            errorCount.addAndGet(triple.getMiddle());
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        if (CollectionUtils.isNotEmpty(list)) {
            saveData();
            list.clear();
        }
        // 处理完成后，需要更新任务的状态
        ImportExportTaskStatusEnum taskStatus = getTaskStatus(statusEnumList);
        if (Objects.equals(taskStatus, ImportExportTaskStatusEnum.PART_SUCCESS)) {
            String errorMsg = "部分导入成功, 失败[%s]条";
            sysImportExportTask.setErrorReason(String.format(errorMsg, errorCount.get()));
        }
        if (Objects.equals(taskStatus, ImportExportTaskStatusEnum.FAILED)) {
            String errorMsg = "导入失败";
            sysImportExportTask.setErrorReason(errorMsg);
        }
        ImportTaskClient importTaskClient = SpringUtils.getBean(ImportTaskClient.class);
        importTaskClient.updateImportTaskStatusWithOpLock(sysImportExportTask, ImportExportTaskStatusEnum.PROCESSING, taskStatus);
    }
}
