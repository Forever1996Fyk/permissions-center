package com.ah.cloud.permissions.edi.domain.config.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.*;

import java.util.Date;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-05 11:55
 **/
@Getter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class CfgBizRetryVo {
    /**
     * 主键
     */
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
}
