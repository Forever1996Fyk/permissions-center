package com.ah.cloud.permissions.task.infrastructure.handler.impl;

import com.ah.cloud.permissions.biz.application.manager.SysUserManager;
import com.ah.cloud.permissions.biz.infrastructure.constant.PermissionsConstants;
import com.ah.cloud.permissions.biz.infrastructure.repository.bean.SysUser;
import com.ah.cloud.permissions.biz.infrastructure.util.CollectionUtils;
import com.ah.cloud.permissions.biz.infrastructure.util.JsonUtils;
import com.ah.cloud.permissions.enums.SexEnum;
import com.ah.cloud.permissions.enums.task.ImportExportErrorCodeEnum;
import com.ah.cloud.permissions.task.application.helper.SysUserImportExportHelper;
import com.ah.cloud.permissions.task.domain.dto.business.ImportBo;
import com.ah.cloud.permissions.task.domain.dto.business.import2.SysUserImportDTO;
import com.ah.cloud.permissions.task.domain.enums.ImportExportBizTypeEnum;
import com.ah.cloud.permissions.task.domain.enums.ImportExportTaskStatusEnum;
import com.ah.cloud.permissions.task.infrastructure.exception.ImportExportException;
import com.ah.cloud.permissions.task.infrastructure.handler.AbstractImportHandler;
import com.ah.cloud.permissions.task.infrastructure.listener.SysUserImportExcelListener;
import com.ah.cloud.permissions.task.infrastructure.repository.bean.SysImportExportTask;
import com.alibaba.excel.event.AnalysisEventListener;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-15 15:13
 **/
@Slf4j
@Component
public class SysUserImportHandler extends AbstractImportHandler<SysUserImportDTO> {
    private final static String LOG_MARK = "SysUserImportHandler";
    @Value(value = "${permission.import.maxRow:100000}")
    private Integer importCount;

    @Resource
    private SysUserManager sysUserManager;
    @Resource
    private SysUserImportExportHelper sysUserImportExportHelper;


    @Override
    protected boolean needBatchImport() {
        return true;
    }

    @Override
    protected AnalysisEventListener<SysUserImportDTO> getListener(SysImportExportTask task) {
        return new SysUserImportExcelListener(this, task);
    }

    @Override
    protected String getLogMark() {
        return LOG_MARK;
    }

    @Override
    protected Class<SysUserImportDTO> getClazz() {
        return SysUserImportDTO.class;
    }

    @Override
    public ImmutableTriple<String, Long, ImportExportTaskStatusEnum> processData(List<SysUserImportDTO> dataList, SysImportExportTask task) {
        // 校验数据
        ImportBo<SysUserImportDTO> importBo = this.checkImportIsAllow(dataList, task);
        List<SysUserImportDTO> sysUserImportDTOList = importBo.successValidData();
        if (CollectionUtils.isNotEmpty(sysUserImportDTOList)) {
            doHandle(sysUserImportDTOList);
        }
        return ImmutableTriple.of(JsonUtils.toJsonString(importBo.getErrorMsgMap()), importBo.getErrorCount(), importBo.getProcessStatus());
    }

    private void doHandle(List<SysUserImportDTO> dataList) {
        List<SysUser> sysUserList = dataList.stream().map(data -> sysUserImportExportHelper.convert(data)).collect(Collectors.toList());
        sysUserManager.batchImportSysUser(sysUserList);
    }

    private ImportBo<SysUserImportDTO> checkImportIsAllow(List<SysUserImportDTO> dataList, SysImportExportTask task) {
        // 导入数量限制
        if (CollectionUtils.isEmpty(dataList)) {
            throw new ImportExportException(ImportExportErrorCodeEnum.IMPORT_TASK_DATA_IS_EMPTY);
        }
        if (dataList.size() > importCount) {
            log.error("[批量建单导入处理] 导入数量超过限定数量！导入:{} 条,限定:{} 条", dataList.size(), importCount);
            throw new ImportExportException(ImportExportErrorCodeEnum.IMPORT_TASK_LIMIT_OVER_MAX_ALLOW, String.valueOf(dataList.size()), String.valueOf(importCount));
        }

        ImportBo<SysUserImportDTO> importBo = ImportBo.<SysUserImportDTO>builder()
                .dataList(dataList)
                .task(task)
                .build();
        String template = "第%s行";
        Map<String, String> errorMsgMap = importBo.getErrorMsgMap();
        for (int i = 0; i < dataList.size(); i++) {
            SysUserImportDTO sysUserImportDTO = dataList.get(i);
            String errorKey = String.format(template, i);
            String errorMsg = null;
            if (StringUtils.isBlank(sysUserImportDTO.getAccount())) {
                errorMsg = "账号信息为空".concat(PermissionsConstants.COMMA_SEPARATOR);
            }
            if (StringUtils.isBlank(sysUserImportDTO.getNickName())) {
                errorMsg = "用户名称为空".concat(PermissionsConstants.COMMA_SEPARATOR);
            }
            if (StringUtils.isBlank(sysUserImportDTO.getEmail())) {
                errorMsg = "邮箱为空".concat(PermissionsConstants.COMMA_SEPARATOR);
            }
            if (StringUtils.isBlank(sysUserImportDTO.getPhone())) {
                errorMsg = "手机号为空".concat(PermissionsConstants.COMMA_SEPARATOR);
            }
            if (StringUtils.isBlank(sysUserImportDTO.getDeptCode())) {
                errorMsg = "部门编码为空".concat(PermissionsConstants.COMMA_SEPARATOR);
            }
            if (StringUtils.isBlank(sysUserImportDTO.getPostCode())) {
                errorMsg = "岗位编码为空".concat(PermissionsConstants.COMMA_SEPARATOR);
            }
            String sexName = sysUserImportDTO.getSexName();
            if (StringUtils.isBlank(sexName)) {
                errorMsg = "性别为空".concat(PermissionsConstants.COMMA_SEPARATOR);
            }
            SexEnum sexEnum = SexEnum.getByDesc(sexName);
            if (sexEnum == null) {
                errorMsg = "性别只能填写 [男/女] ";
            }

            if (StringUtils.isBlank(errorMsg)) {
                sysUserImportDTO.setSuccess(true);
            } else {
                sysUserImportDTO.setSuccess(false);
                errorMsgMap.put(errorKey, errorMsg);
                importBo.addOne();
            }
        }
        return importBo;
    }

    @Override
    public ImportExportBizTypeEnum getBizType() {
        return ImportExportBizTypeEnum.SYS_USER_IMPORT;
    }
}
