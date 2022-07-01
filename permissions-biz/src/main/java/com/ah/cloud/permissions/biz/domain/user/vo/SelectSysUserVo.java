package com.ah.cloud.permissions.biz.domain.user.vo;

import lombok.*;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-01 14:44
 **/
@Getter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class SelectSysUserVo {

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 用户名称
     */
    private String name;
}
