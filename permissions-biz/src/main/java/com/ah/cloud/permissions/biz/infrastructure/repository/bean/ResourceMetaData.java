package com.ah.cloud.permissions.biz.infrastructure.repository.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 资源元数据表
 * </p>
 *
 * @author auto_generation
 * @since 2022-05-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("resource_meta_data")
public class ResourceMetaData implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
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

    /**
     * 资源属性
     */
    private String resourceAttr;

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

    /**
     * 资源id
     */
    private Long resId;


}
