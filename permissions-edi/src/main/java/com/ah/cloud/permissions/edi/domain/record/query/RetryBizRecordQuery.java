package com.ah.cloud.permissions.edi.domain.record.query;

import com.ah.cloud.permissions.biz.infrastructure.annotation.EnumValid;
import com.ah.cloud.permissions.enums.edi.EdiTypeEnum;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-11 10:34
 **/
@Getter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class RetryBizRecordQuery {

    /**
     * 重试id集合
     */
    private List<Long> idList;

    /**
     * 业务类型
     */
    private Integer bizType;

    /**
     * 开始时间
     */
    private String startTime;

    /**
     * edi类型
     */
    private EdiTypeEnum ediTypeEnum;


}
