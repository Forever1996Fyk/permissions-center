package com.ah.cloud.permissions.task.application.client;

import com.ah.cloud.permissions.biz.infrastructure.util.JsonUtils;
import com.ah.cloud.permissions.enums.task.ImportExportErrorCodeEnum;
import com.ah.cloud.permissions.task.application.service.SysImportExportTaskService;
import com.ah.cloud.permissions.task.domain.enums.ImportExportBizTypeEnum;
import com.ah.cloud.permissions.task.domain.enums.ImportExportTaskStatusEnum;
import com.ah.cloud.permissions.task.infrastructure.exception.ImportExportException;
import com.ah.cloud.permissions.task.infrastructure.handler.ImportHandler;
import com.ah.cloud.permissions.task.infrastructure.handler.selector.ImportHandlerSelector;
import com.ah.cloud.permissions.task.infrastructure.repository.bean.SysImportExportTask;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.InputStream;
import java.util.Date;
import java.util.Objects;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-14 15:56
 **/
@Slf4j
@Component
public class ImportTaskClient {
    @Resource
    private ImportHandlerSelector selector;
    @Resource
    private SysImportExportTaskService sysImportExportTaskService;
    /**
     * 执行导入任务
     *
     * @param taskId 任务id
     */
    public void importWithTask(Long taskId, InputStream inputStream) {
        log.info("ImportTaskClient[importWithTask] taskId={}, 开始执行", taskId);
        //【0.加载任务和校验任务状态】
        SysImportExportTask importTask = loadAndCheckTask(taskId);
        log.info("ImportTaskClient[importWithTask] taskId={}, importTask={}, 开始执行", taskId, JsonUtils.toJsonString(importTask));
        try {
             /*
            1 更新导出任务为导出中, 增加version确保内容没有被篡改, 通过状态位做乐观锁防止定时任务重复执行同样的任务
            */
            updateImportTaskStatusWithOpLock(importTask, ImportExportTaskStatusEnum.WAIT, ImportExportTaskStatusEnum.PROCESSING);
            /*
            2 获取导入执行器
             */
            ImportHandler importHandler = selector.select(ImportExportBizTypeEnum.getByCode(importTask.getBizType()));
            /*
            3 执行导入
             */
            ImmutableTriple<String, Long, ImportExportTaskStatusEnum> triple = importHandler.executeImport(importTask, inputStream);
            if (Objects.isNull(triple)) {
                return;
            }
            importTask.setErrorReason(triple.getLeft());
            /*
            4 更新导入结果为导入成功
             */
            updateImportTaskStatusWithOpLock(importTask, ImportExportTaskStatusEnum.PROCESSING, triple.getRight());
        } catch (Exception exception) {
            log.error("ImportTaskClient[importWithTask] 导入任务执行出错, task is {}, caused by {}", JsonUtils.toJsonString(importTask), Throwables.getStackTraceAsString(exception));
            updateImportTaskStatusWithOpLock(importTask, ImportExportTaskStatusEnum.PROCESSING, ImportExportTaskStatusEnum.FAILED);
        }
    }

    /**
     * 更新导出任务的状态
     *
     * @param importTask     导出任务
     * @param fromStatusEnum 来源状态
     * @param toStatusEnum   更新后状态
     */
    public void updateImportTaskStatusWithOpLock(SysImportExportTask importTask, ImportExportTaskStatusEnum fromStatusEnum, ImportExportTaskStatusEnum toStatusEnum) {
        SysImportExportTask update = new SysImportExportTask();
        update.setVersion(importTask.getVersion());
        if (ImportExportTaskStatusEnum.WAIT.equals(fromStatusEnum) && ImportExportTaskStatusEnum.PROCESSING.equals(toStatusEnum)) {
            update.setBeginTime(new Date());
        }
        if (ImportExportTaskStatusEnum.PROCESSING.equals(fromStatusEnum) && ImportExportTaskStatusEnum.SUCCESS.equals(toStatusEnum)) {
            update.setFinishTime(new Date());
        }
        if (ImportExportTaskStatusEnum.PROCESSING.equals(fromStatusEnum) && ImportExportTaskStatusEnum.FAILED.equals(toStatusEnum)) {
            update.setErrorReason(importTask.getErrorReason());
        }
        update.setStatus(toStatusEnum.getStatus());
        final boolean update2ProcessingResult = sysImportExportTaskService.update(
                update,
                new UpdateWrapper<SysImportExportTask>().lambda()
                        .eq(SysImportExportTask::getId, importTask.getId())
                        .eq(SysImportExportTask::getStatus, fromStatusEnum.getStatus())
                        .eq(SysImportExportTask::getVersion, importTask.getVersion())
        );
        if (!update2ProcessingResult) {
            log.warn("ImportTaskClient[importWithTask] update importTask {} status fail, from status is {}, to status is {}",
                    JsonUtils.toJsonString(importTask), fromStatusEnum, toStatusEnum);
            throw new ImportExportException(ImportExportErrorCodeEnum.CURRENT_TASK_STATUS_ERROR);
        }
        log.info("ImportTaskClient[importWithTask] update importTask {} status success, from status is {}, to status is {}",
                JsonUtils.toJsonString(importTask), fromStatusEnum, toStatusEnum);
        //执行更新成功后更新版本号
        importTask.setVersion(update.getVersion());
    }

    /**
     * 加载和校验任务信息
     *
     * @param taskId 任务ID
     */
    private SysImportExportTask loadAndCheckTask(Long taskId) {
        SysImportExportTask task = sysImportExportTaskService.getById(taskId);
        if (Objects.isNull(task)) {
            throw new ImportExportException(ImportExportErrorCodeEnum.IMPORT_TASK_NOT_EXISTED);
        }
        if (!Objects.equals(ImportExportTaskStatusEnum.WAIT.getStatus(), task.getStatus())) {
            throw new ImportExportException(ImportExportErrorCodeEnum.CURRENT_TASK_STATUS_ERROR);
        }
        return task;
    }
}
