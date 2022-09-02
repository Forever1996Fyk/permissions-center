package com.ah.cloud.permissions.task.application.manager;

import com.ah.cloud.permissions.biz.application.manager.ThreadPoolManager;
import com.ah.cloud.permissions.biz.infrastructure.util.JsonUtils;
import com.ah.cloud.permissions.domain.common.PageResult;
import com.ah.cloud.permissions.enums.task.ImportExportErrorCodeEnum;
import com.ah.cloud.permissions.task.application.client.ExportTaskClient;
import com.ah.cloud.permissions.task.application.client.ImportTaskClient;
import com.ah.cloud.permissions.task.application.helper.ImportExportHelper;
import com.ah.cloud.permissions.task.application.service.SysImportExportTaskService;
import com.ah.cloud.permissions.task.domain.dto.DataImportDTO;
import com.ah.cloud.permissions.task.domain.dto.ImportExportTaskAddDTO;
import com.ah.cloud.permissions.task.domain.enums.ImportExportBizTypeEnum;
import com.ah.cloud.permissions.task.domain.form.DataExportForm;
import com.ah.cloud.permissions.task.domain.query.ImportExportTaskQuery;
import com.ah.cloud.permissions.task.domain.vo.ImportExportBizTypeVo;
import com.ah.cloud.permissions.task.domain.vo.ImportExportTaskVo;
import com.ah.cloud.permissions.task.infrastructure.exception.ImportExportException;
import com.ah.cloud.permissions.task.infrastructure.repository.bean.SysImportExportTask;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-13 16:00
 **/
@Slf4j
@Component
public class ImportExportTaskManager {
    @Resource
    private ImportTaskClient importTaskClient;
    @Resource
    private ExportTaskClient exportTaskClient;
    @Resource
    private ImportExportHelper importExportHelper;
    @Resource
    private SysImportExportTaskService sysImportExportTaskService;

    /**
     * 创建导出任务
     *
     * @param form
     */
    public void createExportTask(DataExportForm form) {
        ImportExportTaskAddDTO addDTO = importExportHelper.buildTask(form);
        exportTaskClient.checkExportParam(addDTO);
        Long taskId = this.addTask(addDTO);
        ThreadPoolManager.exportTaskThreadPool.execute(() -> exportTaskClient.exportTask(taskId));
    }

    /**
     * 创建导入任务
     * @param dto
     */
    public void createImportTask(DataImportDTO dto) {
        ImportExportTaskAddDTO addDTO = importExportHelper.buildTask(dto);
        Long taskId = this.addTask(addDTO);
        ThreadPoolManager.importTaskThreadPool.execute(() -> importTaskClient.importWithTask(taskId, dto.getInputStream()));
    }

    /**
     * 分页查询任务列表
     * @param query
     * @return
     */
    public PageResult<ImportExportTaskVo> pageImportExportList(ImportExportTaskQuery query) {
        PageInfo<SysImportExportTask> pageInfo = PageMethod.startPage(query.getPageNo(), query.getPageSize())
                .doSelectPageInfo(
                        () -> sysImportExportTaskService.list(
                                new QueryWrapper<SysImportExportTask>().lambda()
                                        .eq(StringUtils.isNotBlank(query.getBizType()), SysImportExportTask::getBizType, query.getBizType())
                                        .eq(query.getTaskStatus() != null, SysImportExportTask::getStatus, query.getTaskStatus())
                                        .eq(query.getTaskType() != null, SysImportExportTask::getTaskType, query.getTaskType())
                                        .ge(StringUtils.isNotBlank(query.getTaskStartTime()), SysImportExportTask::getBeginTime, query.getTaskStartTime())
                                        .le(StringUtils.isNotBlank(query.getTaskEndTime()), SysImportExportTask::getFinishTime, query.getTaskEndTime())
                        )
                );
        return importExportHelper.convertToPageResult(pageInfo);
    }

    /**
     * 获取业务选择列表
     * @return
     */
    public List<ImportExportBizTypeVo> selectImportExportBizTypeList(Integer taskType) {
        ImportExportBizTypeEnum[] bizTypeEnums = ImportExportBizTypeEnum.values();
        return Arrays.stream(bizTypeEnums)
                .filter(item -> Objects.equals(taskType, item.getTaskTypeEnum().getType()))
                .map(item -> ImportExportBizTypeVo.builder().bizType(item.getCode()).bizTypeName(item.getDesc()).build())
                .collect(Collectors.toList());
    }

    /**
     * 添加任务
     *
     * @param addDTO
     * @return
     */
    public Long addTask(ImportExportTaskAddDTO addDTO) {
        log.info("ImportExportTaskManager[addTask], param is {}", JsonUtils.toJsonString(addDTO));
        SysImportExportTask sysImportExportTask = importExportHelper.convertToEntity(addDTO);
        boolean result = sysImportExportTaskService.save(sysImportExportTask);
        if (!result) {
            throw new ImportExportException(ImportExportErrorCodeEnum.CREATE_TASK_FAILED);
        }
        return sysImportExportTask.getId();
    }
}
