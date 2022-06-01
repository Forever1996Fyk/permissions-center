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
 * 消息事件表
 * </p>
 *
 * @author auto_generation
 * @since 2022-05-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("msg_event")
public class MsgEvent implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 事件编号
     */
    private String eventCode;

    /**
     * 事件名称
     */
    private String eventName;

    /**
     * 所属系统
     */
    private String sourceSystem;

    /**
     * 跳转页面
     */
    private String jumpUrl;

    /**
     * 推送类型
     */
    private Integer pushType;

    /**
     * 是否app置顶
     */
    private Integer topFlag;

    /**
     * 密钥
     */
    private String secretKey;

    /**
     * 是否推送全部 1否 2是
     */
    private Integer reachAllFlag;

    /**
     * 推送语音code
     */
    private String voiceCode;

    /**
     * 删除标识 0:否 其他的删除
     */
    private Long deleted;

    /**
     * 修改时间
     */
    private Date modifyTime;

    /**
     * 创建人
     */
    private String creator;

    /**
     * 修改人
     */
    private String modifier;

    /**
     * 版本号
     */
    @Version
    private Integer version;

    /**
     * 创建时间
     */
    private Date createTime;


}
