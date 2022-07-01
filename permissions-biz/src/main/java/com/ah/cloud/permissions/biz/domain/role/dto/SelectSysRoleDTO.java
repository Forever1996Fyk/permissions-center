package com.ah.cloud.permissions.biz.domain.role.dto;

import com.ah.cloud.permissions.domain.dto.SelectEntity;
import lombok.*;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-01 14:13
 **/
@Getter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class SelectSysRoleDTO implements SelectEntity {
    /**
     * 角色编码
     */
    private String code;

    /**
     * 角色名称
     */
    private String name;
}
