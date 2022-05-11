package com.ah.cloud.permissions.biz.domain.resource.dto;

import com.ah.cloud.permissions.enums.FileTypeEnum;
import com.ah.cloud.permissions.enums.PositionTypeEnum;
import com.ah.cloud.permissions.enums.ResourceBizTypeEnum;
import com.ah.cloud.permissions.enums.YesOrNoEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.OutputStream;
import java.util.Date;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-05-06 16:22
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DownloadFileDTO {

    /**
     * 资源id
     */
    private Long resId;

    /**
     * 是否公开
     */
    private YesOrNoEnum isPublicEnum;

    /**
     * 过期时间
     */
    private Date expireTime;

    /**
     * 资源类型
     */
    private ResourceBizTypeEnum resourceBizTypeEnum;

    /**
     * 文件类型
     */
    private FileTypeEnum fileTypeEnum;

    /**
     * 资源名称
     */
    private String resourceName;

    /**
     * 资源地址
     */
    private String resourceUrl;

    /**
     * 资源路径
     */
    private String resourcePath;

    /**
     * 输出流
     */
    private OutputStream outputStream;
}
