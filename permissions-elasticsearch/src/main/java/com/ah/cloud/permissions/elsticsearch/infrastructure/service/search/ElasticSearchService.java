package com.ah.cloud.permissions.elsticsearch.infrastructure.service.search;

import com.ah.cloud.permissions.domain.common.PageResult;
import com.ah.cloud.permissions.elsticsearch.domain.doc.search.EsPageResult;
import com.ah.cloud.permissions.elsticsearch.domain.doc.search.EsQueryDTO;
import com.ah.cloud.permissions.elsticsearch.domain.doc.search.Pager;
import org.apache.poi.ss.formula.functions.T;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortBuilder;

import java.util.List;

/**
 * @program: permissions-center
 * @description: es搜索接口
 * @author: YuKai Fan
 * @create: 2022-08-26 09:04
 **/
public interface ElasticSearchService<T, Q> {

    /**
     * 通用分页查询
     *
     * @param <T>
     * @param queryDTO<Q>
     * @return
     */
     EsPageResult<T> commonPageSearch(EsQueryDTO<Q> queryDTO);

    /**
     * es分页查询
     * @param index
     * @param queryBuilder
     * @param pager
     * @param sortBuilderList
     * @param highlightBuilder
     * @param <T>
     * @return
     */
    EsPageResult<T> searchByPage(String index, QueryBuilder queryBuilder, Pager pager, List<SortBuilder<?>> sortBuilderList, HighlightBuilder highlightBuilder);


}
