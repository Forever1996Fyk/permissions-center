package com.ah.cloud.permissions.elsticsearch.infrastructure.service.search;

import com.ah.cloud.permissions.elsticsearch.domain.doc.search.EsQueryDTO;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortBuilder;

import java.util.List;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-08-26 10:18
 **/
public interface ElasticSearchSettingService<Q> {


    /**
     * 构建查询
     * @param queryDTO
     * @param <Q>
     * @return
     */
    QueryBuilder setQueryBuilder(EsQueryDTO<Q> queryDTO);

    /**
     * 构建排序
     * @param queryDTO
     * @param <Q>
     * @return
     */
    List<SortBuilder<?>> setSortBuilder(EsQueryDTO<Q> queryDTO);

    /**
     * 构建高亮
     * @param queryDTO
     * @param <Q>
     * @return
     */
    HighlightBuilder setHighlightBuilder(EsQueryDTO<Q> queryDTO);
}
