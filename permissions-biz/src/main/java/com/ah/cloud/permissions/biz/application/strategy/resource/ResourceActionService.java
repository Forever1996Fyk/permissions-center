package com.ah.cloud.permissions.biz.application.strategy.resource;

import com.ah.cloud.permissions.biz.domain.resource.dto.GetResourceFileDTO;
import com.ah.cloud.permissions.biz.domain.resource.dto.GetResourceMetaDataDTO;
import com.ah.cloud.permissions.biz.domain.resource.dto.GetUrlDTO;
import com.ah.cloud.permissions.biz.domain.resource.dto.UploadFileDTO;
import com.ah.cloud.permissions.biz.domain.resource.meta.vo.ResourceMetaDataVo;
import com.ah.cloud.permissions.biz.domain.resource.vo.ResourceFileVo;
import com.ah.cloud.permissions.enums.PositionTypeEnum;

import java.io.OutputStream;

/**
 * @program: permissions-center
 * @description: 资源操作接口
 * @author: YuKai Fan
 * @create: 2022-05-05 23:06
 **/
public interface ResourceActionService {

    /**
     * 上传文件
     * @param dto
     * @return
     */
    Long uploadFile(UploadFileDTO dto);

    /**
     * 上传文件
     * @param dto
     * @return
     */
    String uploadFileUrl(UploadFileDTO dto);

    /**
     * 获取资源url
     * @param dto
     * @return
     */
    String generateUrl(GetUrlDTO dto);

    /**
     * 获取资源文件
     * @param dto
     * @return
     */
    ResourceFileVo getResourceFile(GetResourceFileDTO dto);

    /**
     * 下载文件
     * @param resId
     * @param outputStream
     * @return
     */
    void downloadFile(Long resId, OutputStream outputStream);

    /**
     * 删除文件
     * @param resId
     */
    void deleteFile(Long resId);

    /**
     * 获取资源元数据
     * @param dto
     * @return
     */
    ResourceMetaDataVo getResourceMetaData(GetResourceMetaDataDTO dto);

    /**
     * 上传方式
     * @return
     */
    PositionTypeEnum getPositionTypeEnum();
}
