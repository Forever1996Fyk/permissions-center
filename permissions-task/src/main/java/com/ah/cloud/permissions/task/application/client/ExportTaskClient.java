package com.ah.cloud.permissions.task.application.client;

import cn.hutool.core.date.StopWatch;
import com.ah.cloud.permissions.biz.infrastructure.util.JsonUtils;
import com.ah.cloud.permissions.enums.task.ImportExportErrorCodeEnum;
import com.ah.cloud.permissions.task.application.service.SysImportExportTaskService;
import com.ah.cloud.permissions.task.domain.dto.ImportExportTaskAddDTO;
import com.ah.cloud.permissions.task.domain.dto.business.export.SysUserImportErrorExportDTO;
import com.ah.cloud.permissions.task.domain.enums.ImportExportBizTypeEnum;
import com.ah.cloud.permissions.task.domain.enums.ImportExportTaskStatusEnum;
import com.ah.cloud.permissions.task.infrastructure.constant.ImportExportTaskConstants;
import com.ah.cloud.permissions.task.infrastructure.exception.ImportExportException;
import com.ah.cloud.permissions.task.infrastructure.handler.AbstractExportHandler;
import com.ah.cloud.permissions.task.infrastructure.handler.ExportHandler;
import com.ah.cloud.permissions.task.infrastructure.handler.selector.ExportHandlerSelector;
import com.ah.cloud.permissions.task.infrastructure.repository.bean.SysImportExportTask;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-13 17:38
 **/
@Slf4j
@Component
public class ExportTaskClient {
    @Resource
    private ExportHandlerSelector selector;
    @Resource
    private SysImportExportTaskService sysImportExportTaskService;

    /**
     * 校验参数
     * @param addDTO
     */
    public void checkExportParam(ImportExportTaskAddDTO addDTO) {
        ExportHandler exportHandler = selector.select(addDTO.getBizTypeEnum());
        exportHandler.checkExportParam(addDTO);
    }

    /**
     * 导出任务
     * @param taskId
     */
    public void exportTask(Long taskId) {
        log.info("ExportTaskClient[exportTask] export task taskId is {}", taskId);
        StopWatch stopWatch = new StopWatch(ImportExportTaskConstants.EXPORT_PREFIX.concat("执行耗时统计"));
        Pair<String, Long> filePair = null;
        ImportExportTaskStatusEnum statusEnum = ImportExportTaskStatusEnum.SUCCESS;
        String errorMsg = null;
        // 校验任务数据
        SysImportExportTask task = loadAndCheckTask(taskId);
        log.info("ExportTaskClient[exportTask] export task, taskNo is {} change to processing", task.getTaskNo());
        if (!updateTaskStatusToProcessing(task, ImportExportTaskStatusEnum.PROCESSING)) {
            log.error("ExportTaskClient[exportTask] export task change to processing failed");
            return;
        }
        task.setVersion(task.getVersion() + 1);
        try {
            ExportHandler exportHandler = selector.select(ImportExportBizTypeEnum.getByCode(task.getBizType()));
            //【4.执行导出】
            stopWatch.start("执行导出");
            filePair = exportHandler.executeExport(task);
            stopWatch.stop();
            if (Objects.isNull(filePair)) {
                statusEnum =ImportExportTaskStatusEnum.FAILED;
            }
        } catch (Exception e) {
            errorMsg = Throwables.getStackTraceAsString(e);
            log.error("ExportTaskClient[exportTask] export task failed, reason is {}, task is {}", errorMsg, JsonUtils.toJSONString(task));
            statusEnum = ImportExportTaskStatusEnum.FAILED;
        }
        updateTaskStatusToFinished(filePair, statusEnum, errorMsg, task);
        log.info(stopWatch.prettyPrint());
    }

    /**
     * 更新任务状态
     *
     * @param task
     * @param statusEnum
     * @return
     */
    private boolean updateTaskStatusToProcessing(SysImportExportTask task, ImportExportTaskStatusEnum statusEnum) {
        SysImportExportTask updateImportExportTask = new SysImportExportTask();
        updateImportExportTask.setBeginTime(new Date());
        updateImportExportTask.setStatus(statusEnum.getStatus());
        updateImportExportTask.setVersion(task.getVersion());
        return sysImportExportTaskService.update(
                updateImportExportTask,
                new UpdateWrapper<SysImportExportTask>().lambda()
                        .eq(SysImportExportTask::getId, task.getId())
                        .eq(SysImportExportTask::getVersion, task.getVersion())
        );
    }

    private void updateTaskStatusToFinished(Pair<String, Long> filePair, ImportExportTaskStatusEnum statusEnum, String errorMsg, SysImportExportTask task) {
        SysImportExportTask updateImportExportTask = new SysImportExportTask();
        updateImportExportTask.setStatus(statusEnum.getStatus());
        if (Objects.nonNull(filePair)) {
            updateImportExportTask.setFileName(filePair.getLeft());
            updateImportExportTask.setFileUrl(String.valueOf(filePair.getRight()));
        }
        updateImportExportTask.setErrorReason(errorMsg);
        updateImportExportTask.setVersion(task.getVersion());
        updateImportExportTask.setFinishTime(new Date());
        sysImportExportTaskService.update(
                updateImportExportTask,
                new UpdateWrapper<SysImportExportTask>().lambda()
                        .eq(SysImportExportTask::getId, task.getId())
                        .eq(SysImportExportTask::getVersion, task.getVersion())
        );
    }

    /**
     * 校验任务
     *
     * @param taskId
     */
    private SysImportExportTask loadAndCheckTask(Long taskId) {
        SysImportExportTask task = sysImportExportTaskService.getById(taskId);
        if (Objects.isNull(task)) {
            throw new ImportExportException(ImportExportErrorCodeEnum.EXPORT_TASK_NOT_EXISTED);
        }
        if (!Objects.equals(task.getStatus(), ImportExportTaskStatusEnum.WAIT.getStatus())) {
            throw new ImportExportException(ImportExportErrorCodeEnum.CURRENT_TASK_STATUS_ERROR);
        }
        return task;
    }

    public Long exportWithFile(List<SysUserImportErrorExportDTO> errorExportDTOList) {
        SysImportExportTask exportTask = new SysImportExportTask();
        exportTask.setParam(JsonUtils.toJSONString(errorExportDTOList));
        exportTask.setTaskNo(ImportExportBizTypeEnum.COMM_IMPORT_ERROR_EXPORT.getDesc());
        try {
            ExportHandler exportHandler = selector.select(ImportExportBizTypeEnum.COMM_IMPORT_ERROR_EXPORT);
            return exportHandler.executeExport(exportTask).getRight();
        } catch (Exception exception) {
            log.error("ExportTaskClient[exportWithTask] is error, caused by {}", Throwables.getStackTraceAsString(exception));
        }
        return null;
    }
}
