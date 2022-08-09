package com.ah.cloud.permissions.task.application.manager;

import com.ah.cloud.permissions.biz.infrastructure.util.SecurityUtils;
import com.ah.cloud.permissions.domain.common.PageResult;
import com.ah.cloud.permissions.enums.common.DeletedEnum;
import com.ah.cloud.permissions.enums.common.ErrorCodeEnum;
import com.ah.cloud.permissions.enums.task.ImportExportErrorCodeEnum;
import com.ah.cloud.permissions.task.application.helper.ImportExportHelper;
import com.ah.cloud.permissions.task.application.service.SysImportTemplateInfoService;
import com.ah.cloud.permissions.task.domain.enums.ImportTemplateStatusEnum;
import com.ah.cloud.permissions.task.domain.form.ImportTemplateForm;
import com.ah.cloud.permissions.task.domain.form.ImportTemplateUpdateForm;
import com.ah.cloud.permissions.task.domain.query.ImportTemplateQuery;
import com.ah.cloud.permissions.task.domain.vo.ImportTemplateDownloadVo;
import com.ah.cloud.permissions.task.domain.vo.ImportTemplateVo;
import com.ah.cloud.permissions.task.infrastructure.exception.ImportExportException;
import com.ah.cloud.permissions.task.infrastructure.repository.bean.SysImportTemplateInfo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-15 09:42
 **/
@Slf4j
@Component
public class ImportTemplateManager {
    @Resource
    private ImportExportHelper importExportHelper;
    @Resource
    private SysImportTemplateInfoService sysImportTemplateInfoService;

    /**
     * 创建模板
     * @param form
     */
    public void createImportTemplate(ImportTemplateForm form) {
        SysImportTemplateInfo sysImportTemplateInfo = importExportHelper.buildImportTemplate(form);
        sysImportTemplateInfoService.save(sysImportTemplateInfo);
    }

    /**
     * 更新模板
     * @param form
     */
    public void updateImportTemplate(ImportTemplateUpdateForm form) {
        SysImportTemplateInfo sysImportTemplateInfo = importExportHelper.buildImportTemplate(form);
        sysImportTemplateInfoService.updateById(sysImportTemplateInfo);
    }

    /**
     * 删除模板
     * @param id
     */
    public void deleteImportTemplate(Long id) {
        SysImportTemplateInfo sysImportTemplateInfo = sysImportTemplateInfoService.getById(id);
        if (Objects.isNull(sysImportTemplateInfo)) {
            throw new ImportExportException(ImportExportErrorCodeEnum.IMPORT_TEMPLATE_NOT_EXITED);
        }
        SysImportTemplateInfo deleteSysImportTemplateInfo = new SysImportTemplateInfo();
        deleteSysImportTemplateInfo.setId(id);
        deleteSysImportTemplateInfo.setDeleted(sysImportTemplateInfo.getId());
        deleteSysImportTemplateInfo.setModifier(SecurityUtils.getUserNameBySession());
        boolean result = sysImportTemplateInfoService.updateById(deleteSysImportTemplateInfo);
        if (!result) {
            throw new ImportExportException(ErrorCodeEnum.UNKNOWN_ERROR);
        }
    }

    /**
     * 根据id查询模板
     * @param id
     * @return
     */
    public ImportTemplateVo findById(Long id) {
        SysImportTemplateInfo sysImportTemplateInfo = sysImportTemplateInfoService.getById(id);
        return importExportHelper.convertToVo(sysImportTemplateInfo);
    }

    /**
     * 分页查询模板
     * @param query
     * @return
     */
    public PageResult<ImportTemplateVo> pageImportTemplateVoList(ImportTemplateQuery query) {
        PageInfo<SysImportTemplateInfo> pageInfo = PageMethod.startPage(query.getPageNo(), query.getPageSize())
                .doSelectPageInfo(
                        () -> sysImportTemplateInfoService.list(
                                new QueryWrapper<SysImportTemplateInfo>().lambda()
                                        .eq(StringUtils.isNotBlank(query.getBizType()), SysImportTemplateInfo::getBizType, query.getBizType())
                                        .like(StringUtils.isNotBlank(query.getTemplateName()), SysImportTemplateInfo::getTemplateName, query.getTemplateName())
                                        .eq(query.getStatus() != null, SysImportTemplateInfo::getStatus, query.getStatus())
                        )
                );

        return importExportHelper.convertToTemplatePageResult(pageInfo);
    }

    /**
     * 根据业务类型查询导入模板下载信息
     * @param bizType
     * @return
     */
    public ImportTemplateDownloadVo findImportTemplateDownloadInfoByBizType(String bizType) {
        SysImportTemplateInfo sysImportTemplateInfo = sysImportTemplateInfoService.getOne(
                new QueryWrapper<SysImportTemplateInfo>().lambda()
                        .eq(SysImportTemplateInfo::getBizType, bizType)
                        .eq(SysImportTemplateInfo::getStatus, ImportTemplateStatusEnum.ENABLE.getStatus())
                        .eq(SysImportTemplateInfo::getDeleted, DeletedEnum.NO.value)
        );
        if (Objects.isNull(sysImportTemplateInfo)) {
            throw new ImportExportException(ImportExportErrorCodeEnum.IMPORT_TEMPLATE_NOT_EXITED);
        }
        return ImportTemplateDownloadVo.builder()
                .templateUrl(sysImportTemplateInfo.getTemplateUrl())
                .templateName(sysImportTemplateInfo.getTemplateName())
                .build();
    }
}
