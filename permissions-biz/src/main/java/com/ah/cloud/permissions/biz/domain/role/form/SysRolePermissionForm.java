package com.ah.cloud.permissions.biz.domain.role.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-03-21 23:42
 **/
@Data
public class SysRolePermissionForm {

    /**
     * 角色id
     */
    @NotNull(message = "角色id不能为空")
    private Long roleId;

    /**
     * 菜单编码集合
     */
    private List<String> menuCodeList;

    /**
     * api编码集合
     */
    private List<String> apiCodeList;
}
