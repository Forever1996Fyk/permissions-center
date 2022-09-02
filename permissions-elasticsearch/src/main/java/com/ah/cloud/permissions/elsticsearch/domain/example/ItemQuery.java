package com.ah.cloud.permissions.elsticsearch.domain.example;

import lombok.*;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-08-29 11:18
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemQuery {

    /**
     * 商品名称
     */
    private String name;

    /**
     * 商品id
     */
    private String itemId;

    /**
     * 是否按照id升序
     */
    private boolean isIdAsc;

    /**
     * 是否按照分数 _score 降序
     */
    private boolean isScoreDesc;

    /**
     * 需要查出的字段
     */
    private String[] includeFields;

    /**
     * 需要过滤的字段
     */
    private String[] excludeField;

    /**
     * 前台类目ID
     */
    private String frontCategoryId;

}
