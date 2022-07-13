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
 * 重试记录表
 * </p>
 *
 * @author auto_generation
 * @since 2022-07-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("edi_tech_biz_retry_record")
public class EdiTechBizRetryRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 业务单据号
     */
    private String bizNo;

    /**
     * 业务id
     */
    private Long bizId;

    /**
     * 业务类型
     */
    private Integer bizType;

    /**
     * 系统来源
     */
    private String bizSource;

    /**
     * 异常信息
     */
    private String errorMessage;

    /**
     * 上次执行时间
     */
    private Date lastOpTime;

    /**
     * 失败 成功 重试中
     */
    private Integer recordStatus;

    /**
     * 执行参数
     */
    private String bizParams;

    /**
     * 重试次数
     */
    private Integer retryTimes;

    /**
     * 乐观锁版本号
     */
    @Version
    private Integer version;

    /**
     * 扩展信息
     */
    private String ext;

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
     * 删除标志位
     */
    private Long deleted;

    private String shardingKey;

    /**
     * 环境标字段
     */
    private String env;


}
