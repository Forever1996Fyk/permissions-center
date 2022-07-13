package com.ah.cloud.permissions.edi.domain.record.query;

import com.ah.cloud.permissions.enums.edi.BizRetryStatusEnum;
import com.ah.cloud.permissions.enums.edi.EdiTypeEnum;
import lombok.*;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-11 22:14
 **/
@Getter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class RetryBizRecordCountQuery {

    /**
     * id
     */
    private Long id;

    /**
     * 业务类型
     */
    private Integer bizType;

    /**
     * 状态
     */
    private BizRetryStatusEnum bizRetryStatusEnum;

    /**
     * 业务号
     */
    private String bizNo;

    /**
     * 开始创建时间
     */
    private String createTimeStart;

    /**
     * 结束创建时间
     */
    private String createTimeEnd;

    /**
     * 结束修改时间
     */
    private String modifyTimeEnd;

    /**
     * edi类型
     */
    private EdiTypeEnum ediTypeEnum;
}
