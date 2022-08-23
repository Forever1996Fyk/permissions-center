package com.ah.cloud.permissions.biz.application.manager;

import com.ah.cloud.permissions.biz.application.helper.ResourceHelper;
import com.ah.cloud.permissions.biz.application.strategy.resource.ResourceActionService;
import com.ah.cloud.permissions.biz.application.strategy.selector.ResourceActionServiceSelector;
import com.ah.cloud.permissions.biz.domain.resource.dto.UploadFileDTO;
import com.ah.cloud.permissions.biz.domain.resource.form.ResourceFileForm;
import com.ah.cloud.permissions.biz.infrastructure.exception.BizException;
import com.ah.cloud.permissions.enums.common.FileErrorCodeEnum;
import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @program: permissions-center
 * @description: 文件资源管理器
 * @author: YuKai Fan
 * @create: 2022-05-05 22:33
 **/
@Slf4j
@Component
public class ResourceManager {
    @Resource
    private ResourceHelper resourceHelper;
    @Resource
    private ResourceActionServiceSelector selector;

    /**
     * 上传文件
     * 返回资源id
     * @param request
     * @param ownerId
     * @return
     */
    public Long upload(HttpServletRequest request, Long ownerId) {
        ResourceFileForm form = resourceHelper.getDataByRequest(request);
        MultipartFile multipartFile = resourceHelper.getMultipartFileFromRequest(request);
        ResourceActionService resourceActionService = selector.select();
        UploadFileDTO uploadFileDTO = resourceHelper.buildUploadFileDTO(multipartFile, form, ownerId);
        return resourceActionService.uploadFile(uploadFileDTO);
    }


    /**
     * 上传文件
     * 返回文件地址
     * @param request
     * @param ownerId
     * @return
     */
    public String uploadUrl(HttpServletRequest request, Long ownerId) {
        ResourceFileForm form = resourceHelper.getDataByRequest(request);
        MultipartFile multipartFile = resourceHelper.getMultipartFileFromRequest(request);
        ResourceActionService resourceActionService = selector.select();
        UploadFileDTO uploadFileDTO = resourceHelper.buildUploadFileDTO(multipartFile, form, ownerId);
        return resourceActionService.uploadFileUrl(uploadFileDTO);
    }

    /**
     * 获取当前资源文件
     *
     * @param response
     * @param resId
     */
    public void getResourceFile(HttpServletResponse response, Long resId) {
        ResourceActionService resourceActionService = selector.select();
        response.setContentType("application/octet-stream; charset=UTF-8");
        try {
            resourceActionService.downloadFile(resId, response.getOutputStream());
        } catch (IOException e) {
            log.error("ResourceManager[getResourceFile] get resource file error, resId is {}, reason is {}", resId, Throwables.getStackTraceAsString(e));
            throw new BizException(FileErrorCodeEnum.FILE_RESOURCE_DOWNLOAD_ERROR, String.valueOf(resId));
        }
    }
}
