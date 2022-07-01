package com.ah.cloud.permissions.workflow.domain.task.vo;

import lombok.*;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-01 13:57
 **/
@Getter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class SelectAssignRuleOptionsVo {

    /**
     * 配置编码
     */
    private String code;

    /**
     * 配送名称
     */
    private String name;
}
