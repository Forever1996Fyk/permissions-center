package com.ah.cloud.permissions.edi.domain.record.vo;

import lombok.*;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-07 15:35
 **/
@Getter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class RetryBizTypeVo {

    /**
     * 类型
     */
    private Integer type;

    /**
     * 名称
     */
    private String name;
}
