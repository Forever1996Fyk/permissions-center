package com.ah.cloud.permissions.biz.domain.menu.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @program: permissions-center
 * @description: 设置用户角色form
 * @author: YuKai Fan
 * @create: 2022-03-21 22:11
 **/
@Data
public class SysMenuApiAddForm {

    /**
     * 菜单id
     */
    @NotNull(message = "菜单id不能为空")
    private Long menuId;

    /**
     * 菜单编码不能为空
     */
    @NotEmpty(message = "菜单编码不能为空")
    private String menuCode;

    /**
     * apiCode列表
     */
    private List<String> apiCodeList;
}
