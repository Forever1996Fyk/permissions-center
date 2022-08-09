package com.ah.cloud.permissions.biz.domain.user.vo;

import lombok.*;

import java.util.Set;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-08-01 09:43
 **/
@Getter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class GetUserInfoVo {

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 权限apiCode集合
     */
    private Set<String> permissions;
}
