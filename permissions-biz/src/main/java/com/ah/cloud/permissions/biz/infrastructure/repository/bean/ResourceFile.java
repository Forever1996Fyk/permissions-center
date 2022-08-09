package com.ah.cloud.permissions.biz.infrastructure.repository.bean;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 资源文件表
 * </p>
 *
 * @author auto_generation
 * @since 2022-05-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("resource_file")
public class ResourceFile implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 资源id
     */
    private Long resId;

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
     * 过期时间
     */
    private Date expireTime;

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

    /**
     * 行记录创建者
     */
    private String creator;

    /**
     * 行记录最近更新人
     */
    private String modifier;

    /**
     * 行记录创建时间
     */
    private Date createdTime;

    /**
     * 行记录最近修改时间
     */
    private Date modifiedTime;

    /**
     * 行版本号
     */
    @Version
    private Integer version;

    /**
     * 拓展字段
     */
    private String extension;

    /**
     * 是否删除
     */
    private Long deleted;


}
