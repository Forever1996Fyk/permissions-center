package com.ah.cloud.permissions.edi.domain.record.vo;

import com.ah.cloud.permissions.biz.infrastructure.util.DateUtils;
import com.ah.cloud.permissions.edi.domain.config.dto.RetryBizConfigCacheDTO;
import com.ah.cloud.permissions.edi.infrastructure.constant.EdiConstants;
import com.ah.cloud.permissions.edi.infrastructure.exceprion.BizSuccessException;
import com.ah.cloud.permissions.edi.infrastructure.exceprion.EdiException;
import com.ah.cloud.permissions.edi.infrastructure.service.RetryHandle;
import com.ah.cloud.permissions.enums.YesOrNoEnum;
import com.ah.cloud.permissions.enums.edi.BizRetryStatusEnum;
import com.ah.cloud.permissions.enums.edi.EdiErrorCodeEnum;
import com.google.common.base.Throwables;
import lombok.*;
import org.apache.commons.lang3.StringUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-07 15:51
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RetryBizRecordVo {

    /**
     * 主键
     */
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
     * 下次执行时间
     */
    private Date nextOpTime;

    /**
     * 失败 成功 重试中
     */
    private Integer recordStatus;

    /**
     * 重试次数
     */
    private Integer retryTimes;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 分片key
     */
    private String shardingKey;
}
