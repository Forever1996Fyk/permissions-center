package com.ah.cloud.permissions.biz.domain.resource.meta.vo;

import com.ah.cloud.permissions.biz.domain.resource.meta.dto.ResourceAttrDTO;
import lombok.Builder;
import lombok.Data;

/**
 * @program: permissions-center
 * @description: 资源元数据
 * @author: YuKai Fan
 * @create: 2022-05-06 07:56
 **/
@Data
@Builder
public class ResourceMetaDataVo {

    /**
     * 元数据id
     */
    private Long id;

    /**
     * 所属id
     */
    private Long ownerId;

    /**
     * 文件md5
     */
    private String fileMd5;

    /**
     * 文件sha1
     */
    private String fileSha1;

    /**
     * 文件大小
     */
    private Long fileSize;

    /**
     * 文件后缀
     */
    private String fileSuffix;

    /**
     * 资源原标题
     */
    private String originTitle;
}
