package com.ah.cloud.permissions.biz.domain.role.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2021-12-06 16:37
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SysRoleVO {
    /**
     * 主键id
     */
    private Long id;

    /**
     * 角色类型
     */
    private Integer roleType;

    /**
     * 角色编码
     */
    private String roleCode;

    /**
     * 角色名称
     */
    private String roleName;
}
