package com.ah.cloud.permissions.biz.application.strategy.resource;

import com.ah.cloud.permissions.biz.application.helper.ResourceHelper;
import com.ah.cloud.permissions.biz.application.service.ResourceFileService;
import com.ah.cloud.permissions.biz.application.service.ResourceMetaDataService;
import com.ah.cloud.permissions.biz.domain.resource.dto.*;
import com.ah.cloud.permissions.biz.domain.resource.meta.vo.ResourceMetaDataVo;
import com.ah.cloud.permissions.biz.domain.resource.vo.ResourceFileVo;
import com.ah.cloud.permissions.biz.infrastructure.constant.PermissionsConstants;
import com.ah.cloud.permissions.biz.infrastructure.exception.BizException;
import com.ah.cloud.permissions.biz.infrastructure.repository.bean.ResourceFile;
import com.ah.cloud.permissions.biz.infrastructure.repository.bean.ResourceMetaData;
import com.ah.cloud.permissions.biz.infrastructure.util.FileUtils;
import com.ah.cloud.permissions.biz.infrastructure.util.JsonUtils;
import com.ah.cloud.permissions.biz.infrastructure.util.SecurityUtils;
import com.ah.cloud.permissions.enums.PositionTypeEnum;
import com.ah.cloud.permissions.enums.common.DeletedEnum;
import com.ah.cloud.permissions.enums.common.ErrorCodeEnum;
import com.ah.cloud.permissions.enums.common.FileErrorCodeEnum;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import sun.misc.BASE64Encoder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
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
    public void downloadFile(Long resId, HttpServletRequest request, HttpServletResponse response) throws IOException {
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
        response.setCharacterEncoding("utf-8");
        // 设置强制下载不打开
        response.setContentType("application/force-download");
        response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
        // filenameEncoding 方法兼容不同浏览器编码
        response.addHeader("Content-Disposition", "attachment;fileName=" + filenameEncoding(resourceFile.getResourceName(), request));
        DownloadFileDTO dto = resourceHelper.convertToDownloadDTO(resourceFile);
        dto.setOutputStream(response.getOutputStream());
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
        ResourceFile resourceFile = resourceFileService.getOne(
                new QueryWrapper<ResourceFile>().lambda()
                        .eq(ResourceFile::getResId, dto.getResId())
                        .eq(ResourceFile::getDeleted, DeletedEnum.NO.value)
        );
        if (Objects.isNull(resourceFile)) {
            log.error("{}[generateUrl] get resource url resourceFile is empty, resId is {}", getLogMark(), dto.getResId());
            throw new BizException(FileErrorCodeEnum.FILE_RESOURCE_NOT_EXISTED, dto.getResId().toString());
        }
        return doGetUrl(resourceFile);
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
        // 构建资源文件信息
        ResourceFile resourceFile = resourceHelper.convertToEntity(dto);
        // 上传文件
        UploadResultDTO resultDTO = upload(resourceFile.getResId(), dto);
        if (Objects.isNull(resultDTO) || resultDTO.isFailed() || Objects.isNull(resultDTO.getData())) {
            log.error("{}[upload] file upload error, uploadType is {},  params is {}, reason is {}"
                    , getLogMark()
                    , positionTypeEnum
                    , JsonUtils.toJsonString(dto)
                    , Objects.isNull(resultDTO) ? "上传返回结果为空" : resultDTO.getMessage());
            throw new BizException(FileErrorCodeEnum.FILE_UPLOAD_ERROR, dto.getFileName());
        }
        String creator = dto.getCreator();
        String modifier = dto.getModifier();
        if (StringUtils.isBlank(creator)) {
            creator = SecurityUtils.getUserNameBySession();
        }
        if (StringUtils.isBlank(modifier)) {
            modifier = SecurityUtils.getUserNameBySession();
        }
        UploadResultDTO.ResultData data = resultDTO.getData();
        resourceFile.setResourceName(data.getResourceName());
        resourceFile.setResourceUrl(data.getResourceUrl());
        resourceFile.setResourcePath(data.getResourcePath());
        resourceFile.setPositionType(positionTypeEnum.getType());
        resourceFile.setCreator(creator);
        resourceFile.setModifier(modifier);
        resourceFileService.save(resourceFile);

        ResourceMetaData resourceMetaData = resourceHelper.convertToMetaEntity(dto, resourceFile);
        resourceMetaData.setFileMd5(PermissionsConstants.STR_EMPTY);
        resourceMetaData.setFileSha1(PermissionsConstants.STR_EMPTY);
        resourceMetaData.setCreator(creator);
        resourceMetaData.setModifier(modifier);
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

    /**
     * 获取url
     * @param resourceFile
     * @return
     */
    protected abstract String doGetUrl(ResourceFile resourceFile);

    /**
     *  设置不同浏览器编码
     * @param filename 文件名称
     * @param request 请求对象
     */
    public static String filenameEncoding(String filename, HttpServletRequest request) throws UnsupportedEncodingException {
        // 获得请求头中的User-Agent
        String agent = request.getHeader("User-Agent");
        // 根据不同的客户端进行不同的编码

        if (agent.contains("MSIE")) {
            // IE浏览器
            filename = URLEncoder.encode(filename, "utf-8");
        } else if (agent.contains("Firefox")) {
            // 火狐浏览器
            BASE64Encoder base64Encoder = new BASE64Encoder();
            filename = "=?utf-8?B?" + base64Encoder.encode(filename.getBytes("utf-8")) + "?=";
        } else {
            // 其它浏览器
            filename = URLEncoder.encode(filename, "utf-8");
        }
        return filename;
    }
}
