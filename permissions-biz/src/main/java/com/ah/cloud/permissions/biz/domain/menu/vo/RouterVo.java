package com.ah.cloud.permissions.biz.domain.menu.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @program: zbgx_system
 * @description:
 * @author: YuKai Fan
 * @create: 2021-01-23 15:16
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class RouterVo {
    /**
     * 路由名称
     */
    private String name;

    /**
     * 路由地址
     */
    private String path;

    /**
     * 是否隐藏路由 当设置 true 的时候该路由不会再侧边栏出现
     */
    private Boolean hidden;

    /**
     * 重定向地址，当设置 noRedirect 的时候该路由在面包屑导航中不可被点击
     */
    private String redirect;

    /**
     * 组件地址
     */
    private String component;

    /**
     * 其他元素
     */
    private MetaVo meta;

    /**
     * 子路由
     */
    private List<RouterVo> children;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MetaVo {
        /**
         * 设置该路由在侧边栏和面包屑中展示的名字
         */
        private String title;

        /**
         * 设置该路由的图标，对应路径src/icons/svg
         */
        private String icon;

        /**
         * 是否显示
         */
        private boolean hidden;

        /**
         * 是否显示在菜单中显示隐藏一级路由
         */
        private boolean levelHidden;


        /**
         * 激活页面路径
         */
        private String activeMenu;

        /**
         * 动态创建新的tab
         */
        private boolean dynamicNewTab;
    }
}
