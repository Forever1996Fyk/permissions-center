package com.ah.cloud.permissions.biz.application.strategy.resource.impl;

import com.ah.cloud.permissions.biz.application.strategy.resource.AbstractResourceActionService;
import com.ah.cloud.permissions.biz.domain.minio.MinioResult;
import com.ah.cloud.permissions.biz.domain.resource.dto.DeleteFileDTO;
import com.ah.cloud.permissions.biz.domain.resource.dto.DownloadFileDTO;
import com.ah.cloud.permissions.biz.domain.resource.dto.UploadFileDTO;
import com.ah.cloud.permissions.biz.domain.resource.dto.UploadResultDTO;
import com.ah.cloud.permissions.biz.infrastructure.constant.PermissionsConstants;
import com.ah.cloud.permissions.biz.infrastructure.minio.MinioService;
import com.ah.cloud.permissions.biz.infrastructure.repository.bean.ResourceFile;
import com.ah.cloud.permissions.biz.infrastructure.util.AppUtils;
import com.ah.cloud.permissions.biz.infrastructure.util.FileUtils;
import com.ah.cloud.permissions.enums.PositionTypeEnum;
import com.ah.cloud.permissions.enums.ResourceBizTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022/10/14 23:16
 **/
@Slf4j
@Component
public class MinioResourceActionServiceImpl extends AbstractResourceActionService {
    @Resource
    private MinioService minioService;

    private final static String LOG_MARK = "MinioResourceActionServiceImpl";

    @Override
    protected void delete(DeleteFileDTO dto) {

    }

    @Override
    protected void download(DownloadFileDTO dto) {
        minioService.download(dto.getResourceBizTypeEnum().getBucketName(), dto.getResourceName(), dto.getOutputStream());
    }

    @Override
    protected UploadResultDTO upload(Long resId, UploadFileDTO dto) {
        ResourceBizTypeEnum resourceBizTypeEnum = dto.getResourceBizTypeEnum();
        // 生成资源名称, 即存储在服务器上的文件名
        String resourceName = AppUtils.simpleUUID() + PermissionsConstants.DOT_SEPARATOR + FileUtils.getFileSuffix(dto.getFileName());
        MinioResult<String> result = minioService.upload(resourceBizTypeEnum.getBucketName(), resourceName, dto.getIsPublic(), dto.getInputStream());
        if (result.isSuccess() && StringUtils.isNotBlank(result.getData())) {
            return UploadResultDTO.ofSuccess(
                    UploadResultDTO.ResultData.builder()
                            .resourceName(resourceName)
                            .resourceUrl(result.getData())
                            .build()
            );
        }
        return UploadResultDTO.ofFailed(result.getMessage());
    }

    @Override
    protected String getLogMark() {
        return LOG_MARK;
    }

    @Override
    protected String doGetUrl(ResourceFile resourceFile) {
        return resourceFile.getResourceUrl();
    }

    @Override
    public PositionTypeEnum getPositionTypeEnum() {
        return PositionTypeEnum.MINIO_OSS;
    }
}
