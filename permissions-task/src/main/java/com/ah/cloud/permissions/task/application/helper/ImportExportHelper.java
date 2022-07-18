package com.ah.cloud.permissions.task.application.helper;

import com.ah.cloud.permissions.biz.infrastructure.util.AppUtils;
import com.ah.cloud.permissions.biz.infrastructure.util.DateUtils;
import com.ah.cloud.permissions.biz.infrastructure.util.SecurityUtils;
import com.ah.cloud.permissions.domain.common.PageResult;
import com.ah.cloud.permissions.task.domain.dto.DataImportDTO;
import com.ah.cloud.permissions.task.domain.dto.ImportExportTaskAddDTO;
import com.ah.cloud.permissions.task.domain.enums.ImportExportBizTypeEnum;
import com.ah.cloud.permissions.task.domain.enums.ImportExportTaskStatusEnum;
import com.ah.cloud.permissions.task.domain.enums.ImportExportTaskTypeEnum;
import com.ah.cloud.permissions.task.domain.enums.ImportTemplateStatusEnum;
import com.ah.cloud.permissions.task.domain.form.DataExportForm;
import com.ah.cloud.permissions.task.domain.form.DataImportForm;
import com.ah.cloud.permissions.task.domain.form.ImportTemplateForm;
import com.ah.cloud.permissions.task.domain.form.ImportTemplateUpdateForm;
import com.ah.cloud.permissions.task.domain.vo.ImportExportTaskVo;
import com.ah.cloud.permissions.task.domain.vo.ImportTemplateVo;
import com.ah.cloud.permissions.task.infrastructure.repository.bean.SysImportExportTask;
import com.ah.cloud.permissions.task.infrastructure.repository.bean.SysImportTemplateInfo;
import com.github.pagehelper.PageInfo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-13 16:21
 **/
@Component
public class ImportExportHelper {

    @Value(value = "${spring.profiles.active}")
    private String env;

    public String getFileName(ImportExportBizTypeEnum bizTypeEnum) {
        return bizTypeEnum.getDesc().concat(DateUtils.localDateTime2Str(LocalDateTime.now(), DateUtils.pattern1));
    }

    /**
     * 构建添加任务dto
     * @param form
     * @return
     */
    public ImportExportTaskAddDTO buildTask(DataExportForm form) {
        ImportExportBizTypeEnum bizTypeEnum = ImportExportBizTypeEnum.getByCode(form.getBizType());
        return ImportExportTaskAddDTO.builder()
                .bizTypeEnum(bizTypeEnum)
                .params(form.getParams())
                .taskTypeEnum(ImportExportTaskTypeEnum.EXPORT)
                .fileName(getFileName(bizTypeEnum))
                .creator(SecurityUtils.getUserNameBySession())
                .creatorId(SecurityUtils.getUserIdBySession())
                .build();
    }

    /**
     * 数据转换
     * @param addDTO
     * @return
     */
    public SysImportExportTask convertToEntity(ImportExportTaskAddDTO addDTO) {
        SysImportExportTask task = new SysImportExportTask();
        task.setBizType(addDTO.getBizTypeEnum().getCode());
        task.setParam(addDTO.getParams());
        task.setCreator(addDTO.getCreator());
        task.setCreatorId(addDTO.getCreatorId());
        task.setFileName(addDTO.getFileName());
        task.setFileUrl(addDTO.getFileUrl());
        task.setModifier(addDTO.getCreator());
        task.setTaskType(addDTO.getTaskTypeEnum().getType());
        task.setTaskNo(AppUtils.randomStrId());
        task.setStatus(ImportExportTaskStatusEnum.WAIT.getStatus());
        task.setEnv(env);
        return task;
    }

    /**
     * 数据转换
     * @param pageInfo
     * @return
     */
    public PageResult<ImportExportTaskVo> convertToPageResult(PageInfo<SysImportExportTask> pageInfo) {
        PageResult<ImportExportTaskVo> pageResult = new PageResult<>();
        pageResult.setPageSize(pageResult.getPageSize());
        pageResult.setPageNum(pageResult.getPageNum());
        pageResult.setTotal(pageResult.getTotal());
        pageResult.setRows(Convert.INSTANCE.convertToVoList(pageInfo.getList()));
        return pageResult;
    }

    /**
     * 构建任务
     * @param dto
     * @return
     */
    public ImportExportTaskAddDTO buildTask(DataImportDTO dto) {
        return ImportExportTaskAddDTO.builder()
                .bizTypeEnum(dto.getBizTypeEnum())
                .creator(SecurityUtils.getUserNameBySession())
                .creatorId(SecurityUtils.getUserIdBySession())
                .fileName(dto.getBizTypeEnum().getDesc().concat(DateUtils.localDateTime2Str(LocalDateTime.now(), DateUtils.pattern1)))
                .taskTypeEnum(ImportExportTaskTypeEnum.IMPORT)
                .build();
    }

    /**
     * 构建模板信息
     * @param form
     * @return
     */
    public SysImportTemplateInfo buildImportTemplate(ImportTemplateForm form) {
        SysImportTemplateInfo templateInfo = new SysImportTemplateInfo();
        templateInfo.setTemplateUrl(form.getTemplateUrl());
        templateInfo.setTemplateName(form.getTemplateName());
        templateInfo.setBizType(form.getBizType());
        templateInfo.setStatus(ImportTemplateStatusEnum.ENABLE.getStatus());
        templateInfo.setCreator(SecurityUtils.getUserNameBySession());
        templateInfo.setModifier(SecurityUtils.getUserNameBySession());
        return templateInfo;
    }

    /**
     * 构建模板信息
     * @param form
     * @return
     */
    public SysImportTemplateInfo buildImportTemplate(ImportTemplateUpdateForm form) {
        SysImportTemplateInfo templateInfo = new SysImportTemplateInfo();
        templateInfo.setTemplateUrl(form.getTemplateUrl());
        templateInfo.setTemplateName(form.getTemplateName());
        templateInfo.setBizType(form.getBizType());
        templateInfo.setId(form.getId());
        templateInfo.setModifier(SecurityUtils.getUserNameBySession());
        return templateInfo;
    }

    /**
     * 数据转换
     * @param sysImportTemplateInfo
     * @return
     */
    public ImportTemplateVo convertToVo(SysImportTemplateInfo sysImportTemplateInfo) {
        return Convert.INSTANCE.convertToTemplateVo(sysImportTemplateInfo);
    }

    /**
     * 数据转换
     * @param pageInfo
     * @return
     */
    public PageResult<ImportTemplateVo> convertToPageResult(PageInfo<SysImportTemplateInfo> pageInfo) {
        PageResult<ImportTemplateVo> pageResult = new PageResult<>();
        pageResult.setRows(Convert.INSTANCE.convertToTemplateVoList(pageInfo.getList()));
        pageResult.setPageNum(pageResult.getPageNum());
        pageResult.setPageSize(pageResult.getPageSize());
        return pageResult;
    }

    @Mapper
    public interface Convert {
        ImportExportHelper.Convert INSTANCE = Mappers.getMapper(ImportExportHelper.Convert.class);

        /**
         * 数据转换
         * @param task
         * @return
         */
        ImportExportTaskVo convertToVo(SysImportExportTask task);

        /**
         * 数据转换
         * @param sysImportExportTaskList
         * @return
         */
        List<ImportExportTaskVo> convertToVoList(List<SysImportExportTask> sysImportExportTaskList);

        /**
         * 数据转换
         * @param sysImportTemplateInfo
         * @return
         */
        ImportTemplateVo convertToTemplateVo(SysImportTemplateInfo sysImportTemplateInfo);

        /**
         * 数据转换
         * @param sysImportTemplateInfoList
         * @return
         */
        List<ImportTemplateVo> convertToTemplateVoList(List<SysImportTemplateInfo> sysImportTemplateInfoList);
    }
}
