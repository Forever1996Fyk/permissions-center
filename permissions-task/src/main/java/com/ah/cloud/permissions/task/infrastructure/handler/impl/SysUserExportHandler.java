package com.ah.cloud.permissions.task.infrastructure.handler.impl;

import com.ah.cloud.permissions.biz.application.manager.SysUserManager;
import com.ah.cloud.permissions.biz.domain.user.query.SysUserExportQuery;
import com.ah.cloud.permissions.biz.infrastructure.repository.bean.SysUser;
import com.ah.cloud.permissions.biz.infrastructure.util.CollectionUtils;
import com.ah.cloud.permissions.biz.infrastructure.util.JsonUtils;
import com.ah.cloud.permissions.enums.SexEnum;
import com.ah.cloud.permissions.enums.UserStatusEnum;
import com.ah.cloud.permissions.task.application.helper.SysUserImportExportHelper;
import com.ah.cloud.permissions.task.domain.dto.business.export.SysUserExportDTO;
import com.ah.cloud.permissions.task.domain.dto.business.param.SysUserExportParamDTO;
import com.ah.cloud.permissions.task.domain.enums.ImportExportBizTypeEnum;
import com.ah.cloud.permissions.task.infrastructure.constant.ImportExportTaskConstants;
import com.ah.cloud.permissions.task.infrastructure.handler.AbstractExportHandler;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-15 13:45
 **/
@Component
public class SysUserExportHandler extends AbstractExportHandler<SysUserExportDTO> {
    private final static String LOG_MARK = "SysUserExportHandler";
    @Resource
    private SysUserImportExportHelper sysUserImportExportHelper;
    @Resource
    private SysUserManager sysUserManager;

    @Override
    protected Class<SysUserExportDTO> getClazz() {
        return SysUserExportDTO.class;
    }

    @Override
    protected void loadDataAndWriteFile(String param, ExcelWriter excelWriter, WriteSheet writeSheet) {
        SysUserExportParamDTO sysUserExportParamDTO = JsonUtils.stringToBean(param, SysUserExportParamDTO.class);
        SysUserExportQuery sysUserExportQuery = sysUserImportExportHelper.convert(sysUserExportParamDTO);
        List<SysUser> result = Lists.newArrayList();
        sysUserManager.listSysUserForExport(sysUserExportQuery, resultContext -> {
            result.add(resultContext.getResultObject());
            if (result.size() == ImportExportTaskConstants.EXPORT_TASK_MAX_SIZE) {
                excelWriter.write(result.stream().map(this::convertResult).collect(Collectors.toList()), writeSheet);
                result.clear();
            }
        });
        if (CollectionUtils.isNotEmpty(result)) {
            excelWriter.write(result.stream().map(this::convertResult).collect(Collectors.toList()), writeSheet);
            result.clear();
        } else {
            excelWriter.write(Lists.newArrayList(), writeSheet);
        }
        excelWriter.finish();
    }

    private SysUserExportDTO convertResult(SysUser sysUser) {
        return SysUserExportDTO.builder()
                .account(sysUser.getAccount())
                .email(sysUser.getEmail())
                .phone(sysUser.getPhone())
                .userStatusName(UserStatusEnum.valueOf(sysUser.getStatus()).getDesc())
                .sexName(SexEnum.getByType(sysUser.getSex()).getDesc())
                .build();
    }


    @Override
    protected String getLogMark() {
        return LOG_MARK;
    }

    @Override
    public ImportExportBizTypeEnum getBizType() {
        return ImportExportBizTypeEnum.SYS_USER_EXPORT;
    }
}
