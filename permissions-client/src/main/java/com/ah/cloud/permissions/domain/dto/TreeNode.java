package com.ah.cloud.permissions.domain.dto;

import java.util.List;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-18 18:01
 **/
public interface TreeNode<T extends SelectEntity> {

    /**
     * 子节点
     */
    List<T> getChildNodeList();

}
