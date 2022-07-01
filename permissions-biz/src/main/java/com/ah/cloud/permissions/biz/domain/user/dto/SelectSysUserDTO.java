package com.ah.cloud.permissions.biz.domain.user.dto;

import com.ah.cloud.permissions.domain.dto.SelectEntity;
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
public class SelectSysUserDTO implements SelectEntity {

    /**
     * 用户id
     */
    private String code;

    /**
     * 用户名称
     */
    private String name;
}
