package com.ah.cloud.permissions.biz.domain.resource.dto;

import com.ah.cloud.permissions.enums.FileTypeEnum;
import com.ah.cloud.permissions.enums.ResourceBizTypeEnum;
import com.ah.cloud.permissions.enums.YesOrNoEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.InputStream;
import java.time.Instant;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-05-06 07:32
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UploadFileDTO {
    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 是否公开
     */
    private YesOrNoEnum isPublic;

    /**
     * 资源类型
     */
    private ResourceBizTypeEnum resourceBizTypeEnum;

    /**
     * 文件类型
     */
    private FileTypeEnum fileTypeEnum;

    /**
     * 过期时间
     */
    private Instant expirationTime;

    /**
     * 文件流
     */
    private InputStream inputStream;

    /**
     * 文件数据
     */
    private byte[] bytes;
}
