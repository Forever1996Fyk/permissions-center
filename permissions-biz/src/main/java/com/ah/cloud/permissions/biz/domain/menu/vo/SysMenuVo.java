package com.ah.cloud.permissions.biz.domain.menu.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-03-22 15:13
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SysMenuVo {

    /**
     * 主键id
     */
    private Long id;

    /**
     * 父菜单id
     */
    private Long parentId;

    /**
     * 菜单编码
     */
    private String menuCode;

    /**
     * 菜单名称
     */
    private String menuName;

    /**
     * 菜单类型
     */
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
     * 菜单url
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
