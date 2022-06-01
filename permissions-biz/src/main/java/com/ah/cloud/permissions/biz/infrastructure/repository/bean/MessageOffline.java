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
 * 离线消息表
 * </p>
 *
 * @author auto_generation
 * @since 2022-05-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("message_offline")
public class MessageOffline implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 消息id
     */
    private Long msgId;

    /**
     * 发送id
     */
    private Long fromId;

    /**
     * 接收id
     */
    private Long toId;

    /**
     * 消息类型
     */
    private Integer msgType;

    /**
     * 消息来源
     */
    private Integer msgSource;

    /**
     * 格式
     */
    private Integer format;

    /**
     * 消息内容
     */
    private String content;

    /**
     * 消息时间
     */
    private Date msgTime;

    /**
     * 是否已读
     */
    private Integer hasRead;

    /**
     * 扩展字段
     */
    private String extension;

    /**
     * 描述
     */
    private String remark;

    /**
     * 版本号
     */
    @Version
    private Integer version;

    /**
     * 创建人
     */
    private String creator;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新人
     */
    private String modifier;

    /**
     * 修改时间
     */
    private Date modifyTime;

    /**
     * 是否删除
     */
    private Long deleted;


}
