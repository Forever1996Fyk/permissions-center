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
 * 消息对象表
 * </p>
 *
 * @author auto_generation
 * @since 2022-05-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("msg_target")
public class MsgTarget implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 接受人账号
     */
    private String receiverAccount;

    /**
     * 接受人id
     */
    private Long userId;

    /**
     * 消息编码
     */
    private String msgCode;

    /**
     * 读取标识 0未读 1已读
     */
    private Integer readFlag;

    /**
     * 不在提醒 0no 1 yes
     */
    private Integer remind;

    /**
     * 消息类型
     */
    private Integer msgType;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 是否app置顶
     */
    private Integer topFlag;

    /**
     * 消息标题
     */
    private String msgTitle;

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
