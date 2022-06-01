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
 * 消息目标表
 * </p>
 *
 * @author auto_generation
 * @since 2022-05-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("msg_reach")
public class MsgReach implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 目标类型
     */
    private Integer reachType;

    /**
     * 目标值
     */
    private String reachValue;

    /**
     * 来源编号来源id(msg_info，mgs_event)
     */
    private Long sourceId;

    /**
     * 来源id类型 1 msg_info 2 msg_event
     */
    private Integer sourceIdType;

    /**
     * 对象名称：角色名，用户名等
     */
    private String reachName;

    /**
     * 范围类型id 对应租户id 运营组id 角色id等
     */
    private Long groupId;

    /**
     * 上级是否全选 1否 2是
     */
    private Integer superiorCheckAll;

    /**
     * 范围名称
     */
    private String groupName;

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
