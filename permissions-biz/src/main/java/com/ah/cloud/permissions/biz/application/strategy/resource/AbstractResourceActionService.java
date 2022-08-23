package com.ah.cloud.permissions.biz.application.strategy.resource;

import com.ah.cloud.permissions.biz.application.helper.ResourceHelper;
import com.ah.cloud.permissions.biz.application.service.ResourceFileService;
import com.ah.cloud.permissions.biz.application.service.ResourceMetaDataService;
import com.ah.cloud.permissions.biz.domain.resource.dto.*;
import com.ah.cloud.permissions.biz.domain.resource.meta.vo.ResourceMetaDataVo;
import com.ah.cloud.permissions.biz.domain.resource.vo.ResourceFileVo;
import com.ah.cloud.permissions.biz.infrastructure.exception.BizException;
import com.ah.cloud.permissions.biz.infrastructure.repository.bean.ResourceFile;
import com.ah.cloud.permissions.biz.infrastructure.repository.bean.ResourceMetaData;
import com.ah.cloud.permissions.biz.infrastructure.util.FileUtils;
import com.ah.cloud.permissions.biz.infrastructure.util.JsonUtils;
import com.ah.cloud.permissions.enums.PositionTypeEnum;
import com.ah.cloud.permissions.enums.common.DeletedEnum;
import com.ah.cloud.permissions.enums.common.FileErrorCodeEnum;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.OutputStream;
import java.util.Objects;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-05-06 16:27
 **/
@Slf4j
@Component
public abstract class AbstractResourceActionService implements ResourceActionService {
    @Resource
    private ResourceHelper resourceHelper;
    @Resource
    private ResourceFileService resourceFileService;
    @Resource
    private ResourceMetaDataService resourceMetaDataService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long uploadFile(UploadFileDTO dto) {
        return doUpload(dto).getResId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String uploadFileUrl(UploadFileDTO dto) {
        return doUpload(dto).getResourceUrl();
    }

    @Override
    public void downloadFile(Long resId, OutputStream outputStream) {
        if (resId == null) {
            log.error("{}[downloadFile] file download error resId is empty", getLogMark());
            return;
        }
        ResourceFile resourceFile = resourceFileService.getOne(
                new QueryWrapper<ResourceFile>().lambda()
                        .eq(ResourceFile::getResId, resId)
                        .eq(ResourceFile::getDeleted, DeletedEnum.NO.value)
        );
        if (Objects.isNull(resourceFile)) {
            log.error("{}[downloadFile] file download error resourceFile is empty, resId is {}", getLogMark(), resId);
            return;
        }
        DownloadFileDTO dto = resourceHelper.convertToDownloadDTO(resourceFile);
        dto.setOutputStream(outputStream);
        download(dto);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteFile(Long resId) {
        if (resId == null) {
            log.error("{}[deleteFile] file delete error resId is empty", getLogMark());
            return;
        }
        ResourceFile resourceFile = resourceFileService.getOne(
                new QueryWrapper<ResourceFile>().lambda()
                        .eq(ResourceFile::getResId, resId)
                        .eq(ResourceFile::getDeleted, DeletedEnum.NO.value)
        );
        if (Objects.isNull(resourceFile)) {
            log.error("{}[deleteFile] file delete error resourceFile is empty, resId is {}", getLogMark(), resId);
            return;
        }
        ResourceFile deleteResourceFile = new ResourceFile();
        deleteResourceFile.setResId(resId);
        deleteResourceFile.setDeleted(resId);
        deleteResourceFile.setVersion(resourceFile.getVersion() + 1);
        boolean deleteResourceFileResult = resourceFileService.update(
                deleteResourceFile,
                new UpdateWrapper<ResourceFile>().lambda()
                        .eq(ResourceFile::getResId, resId)
                        .eq(ResourceFile::getVersion, resourceFile.getVersion())
        );
        ResourceMetaData deleteResourceMetaData = new ResourceMetaData();
        deleteResourceMetaData.setResId(resId);
        deleteResourceFile.setDeleted(resId);
        boolean deleteResourceMetaDataResult = resourceMetaDataService.update(
                deleteResourceMetaData,
                new UpdateWrapper<ResourceMetaData>().lambda()
                        .eq(ResourceMetaData::getResId, resId)
        );
        if (deleteResourceFileResult && deleteResourceMetaDataResult) {
            delete(resourceHelper.convertToDeleteDTO(resourceFile));
        }
    }

    @Override
    public String generateUrl(GetUrlDTO dto) {
        return null;
    }

    @Override
    public ResourceFileVo getResourceFile(GetResourceFileDTO dto) {
        return null;
    }

    @Override
    public ResourceMetaDataVo getResourceMetaData(GetResourceMetaDataDTO dto) {
        return null;
    }

    private ResourceFile doUpload(UploadFileDTO dto) {
        PositionTypeEnum positionTypeEnum = getPositionTypeEnum();
        String fileMd5 = FileUtils.getFileMD5(dto.getInputStream());
        String fileSha1 = FileUtils.getFileSHA1(dto.getInputStream());
        ResourceMetaData existResourceMetaData = resourceMetaDataService.getOne(
                new QueryWrapper<ResourceMetaData>().lambda()
                        .eq(ResourceMetaData::getFileMd5, fileMd5)
                        .eq(ResourceMetaData::getFileSha1, fileSha1)
                        .eq(ResourceMetaData::getDeleted, DeletedEnum.NO.value)
        );
        if (!Objects.isNull(existResourceMetaData)) {
            log.info("{}[upload] file upload existed, uploadType is {},  params is {}", getLogMark(), positionTypeEnum, JsonUtils.toJSONString(dto));
            return resourceFileService.getOne(
                    new QueryWrapper<ResourceFile>().lambda()
                            .eq(ResourceFile::getResId, existResourceMetaData.getResId())
            );
        }
        // 构建资源文件信息
        ResourceFile resourceFile = resourceHelper.convertToEntity(dto);
        // 上传文件
        UploadResultDTO resultDTO = upload(resourceFile.getResId(), dto);
        if (Objects.isNull(resultDTO) || resultDTO.isFailed() || Objects.isNull(resultDTO.getData())) {
            log.error("{}[upload] file upload error, uploadType is {},  params is {}, reason is {}"
                    , getLogMark()
                    , positionTypeEnum
                    , JsonUtils.toJSONString(dto)
                    , Objects.isNull(resultDTO) ? "上传返回结果为空" : resultDTO.getMessage());
            throw new BizException(FileErrorCodeEnum.FILE_UPLOAD_ERROR, dto.getFileName());
        }
        UploadResultDTO.ResultData data = resultDTO.getData();
        resourceFile.setResourceName(data.getResourceName());
        resourceFile.setResourceUrl(data.getResourceUrl());
        resourceFile.setResourcePath(data.getResourcePath());
        resourceFile.setPositionType(positionTypeEnum.getType());
        resourceFileService.save(resourceFile);

        ResourceMetaData resourceMetaData = resourceHelper.convertToMetaEntity(dto, resourceFile);
        resourceMetaData.setFileMd5(fileMd5);
        resourceMetaData.setFileSha1(fileSha1);
        resourceMetaDataService.save(resourceMetaData);
        return resourceFile;
    }

    /**
     * 删除文件
     *
     * @param dto
     */
    protected abstract void delete(DeleteFileDTO dto);

    /**
     * 下载文件
     *
     * @param dto
     */
    protected abstract void download(DownloadFileDTO dto);

    /**
     * 上传文件
     *
     * @param resId
     * @param dto
     * @return
     */
    protected abstract UploadResultDTO upload(Long resId, UploadFileDTO dto);

    /**
     * 日志标记
     *
     * @return
     */
    protected abstract String getLogMark();
}
