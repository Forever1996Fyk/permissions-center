package com.ah.cloud.permissions.elsticsearch.domain.example;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-09-01 11:50
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemDoc {

    /**
     * 文档主键
     */
    private String id;

    /**
     * 商品id
     */
    private String itemId;

    /**
     * 操作类型
     */
    private String type;

    /**
     * 商品名称
     */
    private String name;

    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * 描述
     */
    private String desc;

    /**
     * 类目信息
     */
    private List<FrontCategory> frontCateInfo;
}
