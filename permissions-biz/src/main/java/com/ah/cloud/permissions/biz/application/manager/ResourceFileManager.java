package com.ah.cloud.permissions.biz.application.manager;

import com.ah.cloud.permissions.biz.application.helper.ResourceFileHelper;
import com.ah.cloud.permissions.biz.application.service.ResourceFileService;
import com.ah.cloud.permissions.biz.application.service.ResourceMetaDataService;
import com.ah.cloud.permissions.biz.application.strategy.resource.ResourceActionService;
import com.ah.cloud.permissions.biz.application.strategy.selector.ResourceActionServiceSelector;
import com.ah.cloud.permissions.biz.domain.resource.dto.UploadFileDTO;
import com.ah.cloud.permissions.biz.domain.resource.form.ResourceFileForm;
import com.ah.cloud.permissions.biz.domain.resource.query.ResourceFileQuery;
import com.ah.cloud.permissions.biz.domain.resource.vo.ResourceFileDetailVo;
import com.ah.cloud.permissions.biz.domain.resource.vo.ResourceFileVo;
import com.ah.cloud.permissions.biz.infrastructure.exception.BizException;
import com.ah.cloud.permissions.biz.infrastructure.repository.bean.ResourceFile;
import com.ah.cloud.permissions.biz.infrastructure.repository.bean.ResourceMetaData;
import com.ah.cloud.permissions.domain.common.PageResult;
import com.ah.cloud.permissions.enums.common.DeletedEnum;
import com.ah.cloud.permissions.enums.common.FileErrorCodeEnum;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * @program: permissions-center
 * @description: 文件资源管理器
 * @author: YuKai Fan
 * @create: 2022-05-05 22:33
 **/
@Slf4j
@Component
public class ResourceFileManager {
    @Resource
    private ResourceFileHelper resourceFileHelper;
    @Resource
    private ResourceActionServiceSelector selector;
    @Resource
    private ResourceFileService resourceFileService;
    @Resource
    private ResourceMetaDataService resourceMetaDataService;

    /**
     * 上传文件
     * @param request
     * @param form
     * @return
     */
    public Long upload(HttpServletRequest request, ResourceFileForm form) {
        MultipartFile multipartFile = resourceFileHelper.getMultipartFileFromRequest(request);
        ResourceActionService resourceActionService = selector.select();
        UploadFileDTO uploadFileDTO = resourceFileHelper.buildUploadFileDTO(multipartFile, form);
        return resourceActionService.uploadFile(uploadFileDTO);
    }

    /**
     * 上传文件
     * @param request
     * @param form
     * @return
     */
    public String uploadForUrl(HttpServletRequest request) {
        ResourceFileForm fileForm = resourceFileHelper.getDataByRequest(request);
        MultipartFile multipartFile = resourceFileHelper.getMultipartFileFromRequest(request);
        ResourceActionService resourceActionService = selector.select();
        UploadFileDTO uploadFileDTO = resourceFileHelper.buildUploadFileDTO(multipartFile, fileForm);
        return resourceActionService.uploadFileUrl(uploadFileDTO);
    }

    /**
     * 下载当前文件
     *
     * @param response
     * @param resId
     */
    public void download(HttpServletResponse response, Long resId) {
        ResourceActionService resourceActionService = selector.select();
        response.setContentType("application/octet-stream; charset=UTF-8");
        try {
            resourceActionService.downloadFile(resId, response.getOutputStream());
        } catch (IOException e) {
            log.error("ResourceManager[getResourceFile] get resource file error, resId is {}, reason is {}", resId, Throwables.getStackTraceAsString(e));
            throw new BizException(FileErrorCodeEnum.FILE_RESOURCE_DOWNLOAD_ERROR, String.valueOf(resId));
        }
    }

    /**
     * 根据id查询资源文件详情信息
     * @param id
     * @return
     * @throws BizException
     */
    public ResourceFileDetailVo findById(Long id) throws BizException {
        ResourceFile resourceFile = resourceFileService.getOne(
                new QueryWrapper<ResourceFile>().lambda()
                        .eq(ResourceFile::getId, id)
                        .eq(ResourceFile::getDeleted, DeletedEnum.NO.value)
        );
        if (Objects.isNull(resourceFile)) {
            throw new BizException(FileErrorCodeEnum.FILE_RESOURCE_NOT_EXISTED, String.valueOf(id));
        }
        ResourceMetaData resourceMetaData = resourceMetaDataService.getOne(
                new QueryWrapper<ResourceMetaData>().lambda()
                        .eq(ResourceMetaData::getResId, resourceFile.getResId())
                        .eq(ResourceMetaData::getDeleted, DeletedEnum.NO.value)
        );
        return resourceFileHelper.buildResourceFileDetailVo(resourceFile, resourceMetaData);
    }

    /**
     * 根据id删除资源
     *
     * @param resId
     */
    public void deleteById(Long resId) {
        ResourceActionService resourceActionService = selector.select();
        resourceActionService.deleteFile(resId);
    }

    /**
     * 分页查询资源文件
     *
     * @param query
     * @return
     */
    public PageResult<ResourceFileVo> pageResourceFileList(ResourceFileQuery query) {
        PageInfo<ResourceFile> pageInfo = PageHelper.startPage(query.getPageNo(), query.getPageSize())
                .doSelectPageInfo(
                        () -> resourceFileService.list(
                                new QueryWrapper<ResourceFile>().lambda()
                                        .eq(
                                                query.getOwnerId() != null,
                                                ResourceFile::getOwnerId, query.getOwnerId()
                                        )
                                        .eq(
                                                query.getResId() != null,
                                                ResourceFile::getResId, query.getResId()
                                        )
                        )
                );
        return resourceFileHelper.convertToPageResult(pageInfo);
    }
}
