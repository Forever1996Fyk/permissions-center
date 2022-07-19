package com.ah.cloud.permissions.biz.domain.dept.vo;

import com.ah.cloud.permissions.domain.dto.SelectEntity;
import com.ah.cloud.permissions.domain.dto.TreeNode;
import lombok.Data;

import java.util.List;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-18 17:59
 **/
@Data
public class SelectDeptTreeNodeVo implements SelectEntity {

    /**
     * 编码
     */
    private String code;

    /**
     * 名称
     */
    private String name;

    /**
     * 子节点
     */
    private List<SelectDeptTreeNodeVo> childNodeList;
}
