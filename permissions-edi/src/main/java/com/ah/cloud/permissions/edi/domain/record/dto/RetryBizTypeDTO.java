package com.ah.cloud.permissions.edi.domain.record.dto;

import lombok.*;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-06 11:35
 **/
@Getter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class RetryBizTypeDTO {

    /**
     * 业务类型
     */
    private Integer type;

    /**
     * 业务类型描述
     */
    private String desc;

    /**
     * 是否并行
     */
    private Boolean parallel;


    private Integer tableIndex;
}
