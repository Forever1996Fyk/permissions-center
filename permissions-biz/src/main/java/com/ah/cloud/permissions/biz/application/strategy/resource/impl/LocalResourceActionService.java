package com.ah.cloud.permissions.biz.application.strategy.resource.impl;

import com.ah.cloud.permissions.biz.application.strategy.resource.AbstractResourceActionService;
import com.ah.cloud.permissions.biz.domain.resource.dto.DeleteFileDTO;
import com.ah.cloud.permissions.biz.domain.resource.dto.DownloadFileDTO;
import com.ah.cloud.permissions.biz.domain.resource.dto.UploadFileDTO;
import com.ah.cloud.permissions.biz.domain.resource.dto.UploadResultDTO;
import com.ah.cloud.permissions.biz.infrastructure.constant.PermissionsConstants;
import com.ah.cloud.permissions.biz.infrastructure.exception.BizException;
import com.ah.cloud.permissions.biz.infrastructure.util.AppUtils;
import com.ah.cloud.permissions.biz.infrastructure.util.DateUtils;
import com.ah.cloud.permissions.biz.infrastructure.util.FileUtils;
import com.ah.cloud.permissions.biz.infrastructure.util.JsonUtils;
import com.ah.cloud.permissions.enums.PositionTypeEnum;
import com.ah.cloud.permissions.enums.ResourceBizTypeEnum;
import com.ah.cloud.permissions.enums.common.FileErrorCodeEnum;
import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-05-07 09:34
 **/
@Slf4j
@Component
public class LocalResourceActionService extends AbstractResourceActionService {
    private final static String LOG_MARK = "LocalResourceActionService";

    /**
     * 本地上传路径
     */
    @Value("${system.upload.path}")
    private String localUploadPath;

    @Value("${system.local.file.uri}")
    private String localFileUri;

    @Override
    protected UploadResultDTO upload(Long resId, UploadFileDTO dto) {
        UploadResultDTO resultDTO = UploadResultDTO.builder().build();
        ResourceBizTypeEnum resourceBizTypeEnum = dto.getResourceBizTypeEnum();
        String path = localUploadPath + "/" + resourceBizTypeEnum.getBucketName();
        String url =  localFileUri + "/" + resId;
        // 生成资源名称, 即存储在服务器上的文件名
        String resourceName = AppUtils.simpleUUID() + PermissionsConstants.DOT_SEPARATOR + FileUtils.getFileSuffix(dto.getFileName());
        try {
            FileUtils.uploadLocal(dto.getInputStream(), path, resourceName);
            resultDTO.setSuccess(true);
            resultDTO.setData(
                    UploadResultDTO.ResultData.builder()
                            .resourceName(resourceName)
                            .resourcePath(path)
                            .resourceUrl(url)
                            .build()
            );
        } catch (IOException e) {
            log.error("LocalResourceActionService[upload] local file upload error, params is {}, reason is {}", JsonUtils.toJSONString(dto), Throwables.getStackTraceAsString(e));
            resultDTO.setSuccess(false);
            resultDTO.setMessage(Throwables.getStackTraceAsString(e));
        }
        return resultDTO;
    }

    @Override
    protected void delete(DeleteFileDTO dto) {
        // 如果不是公开的
        if (dto.getIsPublicEnum().isNo()) {
            // TODO
        }
        // 当前文件已过期
        if (DateUtils.afterDate(dto.getExpireTime())) {
            throw new BizException(FileErrorCodeEnum.FILE_RESOURCE_IS_EXPIRED, String.valueOf(dto.getResId()));
        }
        boolean result = FileUtils.deleteFile(dto.getResourcePath() + "/" + dto.getResourceName());
        if (!result) {
            throw new BizException(FileErrorCodeEnum.FILE_RESOURCE_REAL_DELETE_FAILED, String.valueOf(dto.getResId()));
        }
    }

    @Override
    protected void download(DownloadFileDTO dto) {
        // 如果不是公开的
        if (dto.getIsPublicEnum().isNo()) {
            // TODO
        }
        // 当前文件已过期
        if (DateUtils.afterDate(dto.getExpireTime())) {
            throw new BizException(FileErrorCodeEnum.FILE_RESOURCE_IS_EXPIRED, String.valueOf(dto.getResId()));
        }
        File file = new File(dto.getResourcePath() + "/" + dto.getResourceName());
        try {
            FileInputStream inputStream = new FileInputStream(file);
            IOUtils.copy(inputStream, dto.getOutputStream());
        } catch (IOException e) {
            log.error("LocalResourceActionService[download] resource download error, params is {}, reason is {}", JsonUtils.toJSONString(dto), Throwables.getStackTraceAsString(e));
            throw new BizException(FileErrorCodeEnum.FILE_RESOURCE_DOWNLOAD_ERROR, String.valueOf(dto.getResId()));
        }
    }

    @Override
    protected String getLogMark() {
        return LOG_MARK;
    }

    @Override
    public PositionTypeEnum getPositionTypeEnum() {
        return PositionTypeEnum.LOCAL;
    }
}
