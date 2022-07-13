package com.ah.cloud.permissions.edi.domain.record.query;

import com.ah.cloud.permissions.domain.common.PageQuery;
import com.ah.cloud.permissions.enums.edi.EdiTypeEnum;
import lombok.*;

import java.util.Date;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-07 14:10
 **/
@Getter
@Builder
@ToString
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class RetryBizRecordScanQuery extends PageQuery {

    /**
     * 最小id
     */
    private Long minId;

    /**
     * 最大id
     */
    private Long maxId;

    /**
     * 开始时间
     */
    private Date startTime;

    /**
     * 结束时间
     */
    private Date endTime;

    /**
     * 环境隔离
     */
    private String env;

    /**
     * edi类型
     */
    private EdiTypeEnum ediTypeEnum;

    /**
     * 最大查询大小
     */
    private Integer maxQuerySize;

    /**
     * 业务类型
     */
    private Integer bizType;

    /**
     * 状态
     */
    private Integer recordStatus;

    /**
     * 业务单号
     */
    private String bizNo;
}
