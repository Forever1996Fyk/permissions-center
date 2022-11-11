package com.ah.cloud.permissions.biz.application.helper;

import com.ah.cloud.permissions.biz.domain.resource.dto.DeleteFileDTO;
import com.ah.cloud.permissions.biz.domain.resource.dto.DownloadFileDTO;
import com.ah.cloud.permissions.biz.domain.resource.dto.UploadFileDTO;
import com.ah.cloud.permissions.biz.domain.resource.form.ResourceFileForm;
import com.ah.cloud.permissions.biz.infrastructure.constant.PermissionsConstants;
import com.ah.cloud.permissions.biz.infrastructure.constant.RequestConstants;
import com.ah.cloud.permissions.biz.infrastructure.exception.BizException;
import com.ah.cloud.permissions.biz.infrastructure.repository.bean.ResourceFile;
import com.ah.cloud.permissions.biz.infrastructure.repository.bean.ResourceMetaData;
import com.ah.cloud.permissions.biz.infrastructure.util.AppUtils;
import com.ah.cloud.permissions.biz.infrastructure.util.DateUtils;
import com.ah.cloud.permissions.biz.infrastructure.util.FileUtils;
import com.ah.cloud.permissions.biz.infrastructure.util.JsonUtils;
import com.ah.cloud.permissions.enums.*;
import com.ah.cloud.permissions.enums.common.FileErrorCodeEnum;
import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.Instant;
import java.util.Map;
import java.util.Objects;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-05-06 16:33
 **/
@Slf4j
@Component
public class ResourceHelper {

    /**
     * 获取数据
     * @param request
     * @return
     */
    public ResourceFileForm getDataByRequest(HttpServletRequest request) {
        Map parameterMap = request.getParameterMap();
        if (CollectionUtils.isEmpty(parameterMap)) {
            return ResourceFileForm.defaultForm();
        }
        Object o = parameterMap.get(RequestConstants.FILE_DATA_KEY);
        if (Objects.isNull(o)) {
            return ResourceFileForm.defaultForm();
        }
        return (ResourceFileForm) o;
    }

    /**
     * 从Http request 获取当前文件信息
     * @param request
     * @return
     */
    public MultipartFile getMultipartFileFromRequest(HttpServletRequest request) {
        if (request instanceof MultipartHttpServletRequest) {
            Map<String, MultipartFile> fileMap = ((MultipartHttpServletRequest) request).getFileMap();
            if (CollectionUtils.isEmpty(fileMap) || CollectionUtils.isEmpty(fileMap.keySet())) {
                return null;
            }
            for (Map.Entry<String, MultipartFile> entry : fileMap.entrySet()) {
                if (StringUtils.isBlank(entry.getKey())) {
                    continue;
                }

                if (!entry.getValue().isEmpty()) {
                    return entry.getValue();
                }
            }
        }
        return null;
    }

    /**
     * 数据转换
     * @param dto
     * @return
     */
    public ResourceFile convertToEntity(UploadFileDTO dto) {
        ResourceFile resourceFile = new ResourceFile();
        resourceFile.setIsPublic(dto.getIsPublic().getType());
        resourceFile.setResId(AppUtils.randomLongId());
        resourceFile.setFileType(dto.getFileTypeEnum().getType());
        resourceFile.setResourceBizType(dto.getResourceBizTypeEnum().getType());
        if (!Objects.isNull(dto.getExpirationTime())) {
            resourceFile.setExpireTime(DateUtils.instantToDate(dto.getExpirationTime()));
        }
        return resourceFile;
    }

    /**
     * 数据转换
     * @param dto
     * @param resourceFile
     * @return
     */
    public ResourceMetaData convertToMetaEntity(UploadFileDTO dto, ResourceFile resourceFile) {
        ResourceMetaData resourceMetaData = new ResourceMetaData();
        resourceMetaData.setOriginTitle(FileUtils.splitFullFileName(dto.getFileName())[0]);
        resourceMetaData.setFileSuffix(FileUtils.getFileSuffix(dto.getFileName()));
        try {
            resourceMetaData.setFileSize((long) dto.getInputStream().available());
        } catch (IOException e) {
            log.error("ResourceHelper[convertToMetaEntity] file size error, dto:{}, reason:{}", JsonUtils.toJsonString(dto), Throwables.getStackTraceAsString(e));
            throw new BizException(FileErrorCodeEnum.FILE_INPUT_STREAM_ERROR);
        }
        resourceMetaData.setResId(resourceFile.getResId());
        resourceMetaData.setOwnerId(resourceFile.getOwnerId());
        return resourceMetaData;
    }

    /**
     * 数据转换
     * @param resourceFile
     * @return
     */
    public DownloadFileDTO convertToDownloadDTO(ResourceFile resourceFile) {
        return Convert.INSTANCE.convertToDownloadDTO(resourceFile);
    }

    /**
     * 数据转换
     * @param resourceFile
     * @return
     */
    public DeleteFileDTO convertToDeleteDTO(ResourceFile resourceFile) {
        return Convert.INSTANCE.convertToDeleteDTO(resourceFile);
    }

    /**
     * 构建上传文件dto
     * @param multipartFile
     * @param form
     * @param ownerId
     * @return
     */
    public UploadFileDTO buildUploadFileDTO(MultipartFile multipartFile, ResourceFileForm form, Long ownerId) {
        UploadFileDTO uploadFileDTO = UploadFileDTO.builder()
                .fileName(multipartFile.getOriginalFilename())
                .isPublic(YesOrNoEnum.getByType(form.getIsPublic()))
                .fileTypeEnum(FileUtils.getFileType(FileSuffixTypeEnum.getByType(FileUtils.getFileSuffix(multipartFile.getOriginalFilename()))))
                .resourceBizTypeEnum(ResourceBizTypeEnum.getByType(form.getBizType()))
                .build();
        if (!Objects.equals(form.getExpireTime(), PermissionsConstants.NEGATIVE_ONE_LONG)) {
            Instant instant = Instant.now().plus(form.getExpireTime(), TimeTypeEnum.convertToChronoUnit(form.getTimeUnit()));
            uploadFileDTO.setExpirationTime(instant);
        }
        return uploadFileDTO;
    }

    @Mapper(uses = {YesOrNoEnum.class, ResourceBizTypeEnum.class, FileTypeEnum.class})
    public interface Convert {
        ResourceHelper.Convert INSTANCE = Mappers.getMapper(ResourceHelper.Convert.class);
        /**
         * 数据转换
         * @param resourceFile
         * @return
         */
        @Mappings({
                @Mapping(target = "isPublicEnum", source = "isPublic"),
                @Mapping(target = "resourceBizTypeEnum", source = "resourceBizType"),
                @Mapping(target = "fileTypeEnum", expression = "java(com.ah.cloud.permissions.enums.FileTypeEnum.getByType(resourceFile.getFileType()))"),
        })
        DownloadFileDTO convertToDownloadDTO(ResourceFile resourceFile);

        /**
         * 数据转换
         *
         * @param resourceFile
         * @return
         */
        @Mappings({
                @Mapping(target = "isPublicEnum", source = "isPublic"),
        })
        DeleteFileDTO convertToDeleteDTO(ResourceFile resourceFile);
    }
}
