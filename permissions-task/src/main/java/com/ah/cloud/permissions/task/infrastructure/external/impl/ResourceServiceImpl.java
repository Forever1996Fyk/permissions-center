package com.ah.cloud.permissions.task.infrastructure.external.impl;

import com.ah.cloud.permissions.biz.application.strategy.resource.ResourceActionService;
import com.ah.cloud.permissions.biz.application.strategy.selector.ResourceActionServiceSelector;
import com.ah.cloud.permissions.biz.domain.resource.dto.UploadFileDTO;
import com.ah.cloud.permissions.biz.infrastructure.util.DateUtils;
import com.ah.cloud.permissions.biz.infrastructure.util.FileUtils;
import com.ah.cloud.permissions.enums.FileTypeEnum;
import com.ah.cloud.permissions.enums.PositionTypeEnum;
import com.ah.cloud.permissions.enums.ResourceBizTypeEnum;
import com.ah.cloud.permissions.enums.YesOrNoEnum;
import com.ah.cloud.permissions.enums.task.ImportExportErrorCodeEnum;
import com.ah.cloud.permissions.task.infrastructure.exception.ImportExportException;
import com.ah.cloud.permissions.task.infrastructure.external.ResourceService;
import com.ah.cloud.permissions.task.infrastructure.repository.bean.SysImportExportTask;
import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.InputStream;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-14 11:17
 **/
@Slf4j
@Service
public class ResourceServiceImpl implements ResourceService {
    @Resource
    private ResourceActionServiceSelector selector;

    @Override
    public Long uploadFile(String fileName, String filePath, SysImportExportTask task) {
        ResourceActionService actionService = selector.select(PositionTypeEnum.MINIO_OSS);
        try (InputStream inputStream = FileUtils.getInputStream(fileName, filePath)) {
            UploadFileDTO uploadFileDTO = UploadFileDTO.builder()
                    .fileName(fileName)
                    .expirationTime(DateUtils.plusDayLocalDateToInstant(7))
                    .fileTypeEnum(FileTypeEnum.EXCEL)
                    .isPublic(YesOrNoEnum.YES)
                    .resourceBizTypeEnum(ResourceBizTypeEnum.EXPORTING)
                    .inputStream(inputStream)
                    .creator(task.getCreator())
                    .modifier(task.getModifier())
                    .build();
            return actionService.uploadFile(uploadFileDTO);

        } catch (Exception e){
            log.error("ResourceServiceImpl[uploadFile] upload file failed, reason is {}, filePath is {}, fileName is {}", Throwables.getStackTraceAsString(e), filePath, fileName);
            throw new ImportExportException(ImportExportErrorCodeEnum.FILE_UPLOAD_FAILED);
        }
    }

}
