package com.ah.cloud.permissions.elsticsearch.domain.doc.search;

import lombok.*;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-08-26 09:47
 **/
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class EsQueryDTO<Q> {

    /**
     * 索引
     */
    private String index;

    /**
     * 分页参数
     */
    private Pager pager;

    /**
     * 是否需要排序
     */
    private Boolean sort;

    /**
     * 是否需要高亮
     */
    private Boolean highlight;

    /**
     * 查询实体
     */
    private Q query;

    /**
     * 不需要排序
     * @return
     */
    public boolean noNeedSort() {
        return !sort;
    }

    /**
     * 不需要排序
     * @return
     */
    public boolean noNeedHighlight() {
        return !highlight;
    }
}
