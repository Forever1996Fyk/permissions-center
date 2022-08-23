package com.ah.cloud.permissions.biz.domain.menu.form;

import com.ah.cloud.permissions.biz.infrastructure.annotation.EnumValid;
import com.ah.cloud.permissions.enums.MenuTypeEnum;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-03-22 13:59
 **/
@Data
public class SysMenuAddForm {
    /**
     * 父菜单id
     */
    private Long parentId;

    /**
     * 菜单编码
     */
    @NotEmpty(message = "菜单编码不能为空")
    private String menuCode;

    /**
     * 菜单名称
     */
    @NotEmpty(message = "菜单名称不能为空")
    private String menuName;

    /**
     * 菜单类型
     */
    @EnumValid(enumClass = MenuTypeEnum.class, enumMethod = "isValid")
    private Integer menuType;

    /**
     * 菜单序号
     */
    private Integer menuOrder;

    /**
     * 组件路径
     */
    private String component;

    /**
     * 菜单路径
     */
    private String menuPath;

    /**
     * 打开方式(1:页面内,2:外链)
     */
    private Integer openType;

    /**
     * 菜单图标
     */
    private String menuIcon;

    /**
     * 激活页面路径
     */
    private String activeMenu;

    /**
     * 动态创建新的tab
     */
    private Integer dynamicNewTab;

    /**
     * 是否隐藏
     */
    private Integer hidden;
}
