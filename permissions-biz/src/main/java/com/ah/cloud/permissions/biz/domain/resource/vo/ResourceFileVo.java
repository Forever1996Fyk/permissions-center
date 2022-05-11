package com.ah.cloud.permissions.biz.domain.resource.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: permissions-center
 * @description: 资源文件vo
 * @author: YuKai Fan
 * @create: 2022-05-06 16:05
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResourceFileVo {

    /**
     * 主键id
     */
    private Long id;

    /**
     * 所属id
     */
    private Long ownerId;

    /**
     * 资源位置类型
     */
    private Integer positionType;

    /**
     * 是否公开
     */
    private Integer isPublic;

    /**
     * 资源类型
     */
    private Integer resourceBizType;

    /**
     * 文件类型
     */
    private Integer fileType;

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
}
