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
 * 消息主表
 * </p>
 *
 * @author auto_generation
 * @since 2022-05-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("msg_info")
public class MsgInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 消息编号
     */
    private String msgCode;

    /**
     * 消息标题
     */
    private String msgTitle;

    /**
     * 消息类型
     */
    private Integer msgType;

    /**
     * 推送方式
     */
    private Integer pushType;

    /**
     * 推送时间
     */
    private Date pushTime;

    /**
     * //推送时间类型 1:立即 2：自定义
 
     */
    private Integer pushTimeType;

    /**
     * 消息状态
     */
    private Integer msgStatus;

    /**
     * 是否app置顶
     */
    private Integer topFlag;

    /**
     * 是否推送全部 1否 2是
     */
    private Integer reachAllFlag;

    /**
     * 消息内容
     */
    private String content;

    /**
     * 资源ids
     */
    private String resIds;

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
