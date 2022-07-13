package com.ah.cloud.permissions.edi.infrastructure.repository.bean;

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
 * 重试配置表
 * </p>
 *
 * @author auto_generation
 * @since 2022-07-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("edi_cfg_biz_retry")
public class EdiCfgBizRetry implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 业务类型
     */
    private Integer bizType;

    /**
     * 业务名称
     */
    private String bizName;

    /**
     * 最大重试次数。0表示不重试，-1无限重试
     */
    private Integer maxRetryTimes;

    /**
     * 执行间隔，毫秒为单位
     */
    private Integer retryInterval;

    /**
     * 0系统自动重试 1手动重试
     */
    private Integer retryModel;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建人
     */
    private String creator;

    /**
     * 变更人
     */
    private String modifier;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date modifyTime;

    /**
     * 停用启用
     */
    private Integer status;

    /**
     * 删除标志位
     */
    private Long deleted;


}
