package com.ah.cloud.permissions.biz.infrastructure.repository.bean;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 系统接口表
 * </p>
 *
 * @author auto_generation
 * @since 2022-04-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_api")
public class SysApi implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 接口编码
     */
    private String apiCode;

    /**
     * 接口名称
     */
    private String apiName;

    /**
     * 读写类型
     */
    private Integer readOrWrite;

    /**
     * 接口类型
     */
    private Integer apiType;

    /**
     * 接口描述
     */
    private String apiDesc;

    /**
     * 请求路径
     */
    private String apiUrl;

    /**
     * 账号状态(1:启用,2:停用)
     */
    private Integer status;

    /**
     * 是否认证
     */
    @TableField("is_auth")
    private Integer auth;

    /**
     * 是否公开
     */
    @TableField("is_open")
    private Integer open;

    /**
     * 备注
     */
    private String remark;

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
     * 是否可变
     */
    private Integer changeable;


}
