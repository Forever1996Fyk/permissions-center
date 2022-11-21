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
 * IM用户session表
 * </p>
 *
 * @author auto_generation
 * @since 2022-10-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("im_session")
public class ImSession implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 当前机器的channelId
     */
    private String nid;

    /**
     * 设备id
     */
    private String deviceId;

    /**
     * 当前设备类型
     */
    private Integer channel;

    /**
     * 设备名称
     */
    private String deviceName;

    /**
     * app版本
     */
    private String appVersion;

    /**
     * 系统版本
     */
    private String osVersion;

    /**
     * 绑定时间
     */
    private Date bindTime;

    /**
     * 经度
     */
    private String longitude;

    /**
     * 纬度
     */
    private String latitude;

    /**
     * 位置
     */
    private String location;

    /**
     * 连接状态
     */
    private Integer status;

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
