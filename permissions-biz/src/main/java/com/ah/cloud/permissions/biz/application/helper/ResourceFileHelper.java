package com.ah.cloud.permissions.biz.application.helper;

import com.ah.cloud.permissions.biz.domain.resource.dto.DeleteFileDTO;
import com.ah.cloud.permissions.biz.domain.resource.dto.DownloadFileDTO;
import com.ah.cloud.permissions.biz.domain.resource.dto.UploadFileDTO;
import com.ah.cloud.permissions.biz.domain.resource.form.ResourceFileForm;
import com.ah.cloud.permissions.biz.domain.resource.meta.vo.ResourceMetaDataVo;
import com.ah.cloud.permissions.biz.domain.resource.vo.ResourceFileDetailVo;
import com.ah.cloud.permissions.biz.domain.resource.vo.ResourceFileVo;
import com.ah.cloud.permissions.biz.infrastructure.constant.PermissionsConstants;
import com.ah.cloud.permissions.biz.infrastructure.constant.RequestConstants;
import com.ah.cloud.permissions.biz.infrastructure.exception.BizException;
import com.ah.cloud.permissions.biz.infrastructure.repository.bean.ResourceFile;
import com.ah.cloud.permissions.biz.infrastructure.repository.bean.ResourceMetaData;
import com.ah.cloud.permissions.biz.infrastructure.util.AppUtils;
import com.ah.cloud.permissions.biz.infrastructure.util.DateUtils;
import com.ah.cloud.permissions.biz.infrastructure.util.FileUtils;
import com.ah.cloud.permissions.biz.infrastructure.util.JsonUtils;
import com.ah.cloud.permissions.domain.common.PageResult;
import com.ah.cloud.permissions.enums.*;
import com.ah.cloud.permissions.enums.common.FileErrorCodeEnum;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Throwables;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.Instant;
import java.util.*;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-05-06 16:33
 **/
@Slf4j
@Component
public class ResourceFileHelper {

    /**
     * 获取数据
     * @param request
     * @return
     */
    public ResourceFileForm getDataByRequest(HttpServletRequest request) {
        Map<String, String[]> parameterMap = (Map<String, String[]>) request.getParameterMap();
        if (CollectionUtils.isEmpty(parameterMap)) {
            return ResourceFileForm.defaultForm();
        }
        Map<String, Object> formMap = Maps.newHashMap();
        for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
            formMap.put(entry.getKey(), Objects.isNull(entry.getValue()) ? null : entry.getValue()[0]);
        }
        ResourceFileForm resourceFileForm = JsonUtils.mapToBean(formMap, ResourceFileForm.class);
        resourceFileForm.check();
        return resourceFileForm;
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
     * @return
     */
    public UploadFileDTO buildUploadFileDTO(MultipartFile multipartFile, ResourceFileForm form) {
        try {
            byte[] bytes = FileCopyUtils.copyToByteArray(multipartFile.getInputStream());
            UploadFileDTO uploadFileDTO = UploadFileDTO.builder()
                    .fileName(multipartFile.getOriginalFilename())
                    .isPublic(YesOrNoEnum.getByType(form.getIsPublic()))
                    .fileTypeEnum(FileUtils.getFileType(FileSuffixTypeEnum.getByType(FileUtils.getFileSuffix(multipartFile.getOriginalFilename()))))
                    .resourceBizTypeEnum(ResourceBizTypeEnum.getByType(form.getBizType()))
                    .inputStream(new ByteArrayInputStream(bytes))
                    .build();
            if (!Objects.equals(form.getExpireTime(), PermissionsConstants.NEGATIVE_ONE_LONG)) {
                Instant instant = Instant.now().plus(form.getExpireTime(), TimeTypeEnum.convertToChronoUnit(form.getTimeUnit()));
                uploadFileDTO.setExpirationTime(instant);
            }
            return uploadFileDTO;
        } catch (IOException e) {
            log.error("MultipartFile InputStream error with IOException, reason is {}", Throwables.getStackTraceAsString(e));
            throw new RuntimeException(e);
        }
    }

    /**
     * 构建资源文件详情信息
     * @param resourceFile
     * @param resourceMetaData
     * @return
     */
    public ResourceFileDetailVo buildResourceFileDetailVo(ResourceFile resourceFile, ResourceMetaData resourceMetaData) {
        return ResourceFileDetailVo.builder()
                .fileInfo(Convert.INSTANCE.convertToVo(resourceFile))
                .meta(Convert.INSTANCE.convertToVo(resourceMetaData))
                .build();
    }

    /**
     * 数据转换
     * @param pageInfo
     * @return
     */
    public PageResult<ResourceFileVo> convertToPageResult(PageInfo<ResourceFile> pageInfo) {
        PageResult<ResourceFileVo> result = new PageResult<>();
        result.setTotal(pageInfo.getTotal());
        result.setPageNum(pageInfo.getPageNum());
        result.setRows(Convert.INSTANCE.convertToVo(pageInfo.getList()));
        result.setPageSize(pageInfo.getSize());
        result.setPages(pageInfo.getPages());
        return result;
    }

    @Mapper(uses = {YesOrNoEnum.class, ResourceBizTypeEnum.class, FileTypeEnum.class})
    public interface Convert {
        ResourceFileHelper.Convert INSTANCE = Mappers.getMapper(ResourceFileHelper.Convert.class);
        /**
         * 数据转换
         * @param resourceFile
         * @return
         */
        @Mappings({
                @Mapping(target = "isPublicEnum", source = "isPublic"),
                @Mapping(target = "resourceBizTypeEnum", source = "resourceBizType"),
                @Mapping(target = "fileTypeEnum", source = "fileType"),
        })
        DownloadFileDTO convertToDownloadDTO(ResourceFile resourceFile);

        /**
         * 数据转换
         * @param resourceFile
         * @return
         */
        @Mappings({
                @Mapping(target = "isPublicEnum", source = "isPublic"),
        })
        DeleteFileDTO convertToDeleteDTO(ResourceFile resourceFile);

        /**
         * 数据转换
         * @param resourceFile
         * @return
         */
        ResourceFileVo convertToVo(ResourceFile resourceFile);

        /**
         * 数据转换
         * @param resourceFileList
         * @return
         */
        List<ResourceFileVo> convertToVo(List<ResourceFile> resourceFileList);

        /**
         * 数据转换
         * @param resourceMetaData
         * @return
         */
        ResourceMetaDataVo convertToVo(ResourceMetaData resourceMetaData);
    }
}
