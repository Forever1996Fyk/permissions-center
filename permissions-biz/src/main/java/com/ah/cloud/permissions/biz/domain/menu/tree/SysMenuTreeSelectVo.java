package com.ah.cloud.permissions.biz.domain.menu.tree;

import com.ah.cloud.permissions.domain.common.TreeSelect;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @program: permissions-center
 * @description: 菜单选择树
 * @author: YuKai Fan
 * @create: 2022-03-29 17:14
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SysMenuTreeSelectVo extends TreeSelect {

    /**
     * 节点id
     */
    private String id;

    /**
     * 节点值
     */
    private String value;

    /**
     * 节点名称
     */
    private String label;

    /**
     * 子节点
     */
    private List<SysMenuTreeSelectVo> children;

    /**
     * 是否被禁用
     */
    private boolean disabled;

    /**
     * 是否已选中
     */
    private boolean selected;

    /**
     * value就是id, 这里主要是为了适配前端的树形选择器 数据结构
     * @return
     */
    public String getValue() {
        return id;
    }
}
