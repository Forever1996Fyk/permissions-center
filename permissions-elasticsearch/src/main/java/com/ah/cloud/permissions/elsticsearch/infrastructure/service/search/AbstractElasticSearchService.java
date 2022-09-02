package com.ah.cloud.permissions.elsticsearch.infrastructure.service.search;

import com.ah.cloud.permissions.biz.infrastructure.exception.BizException;
import com.ah.cloud.permissions.biz.infrastructure.util.CollectionUtils;
import com.ah.cloud.permissions.biz.infrastructure.util.JsonUtils;
import com.ah.cloud.permissions.elsticsearch.domain.doc.search.EsPageResult;
import com.ah.cloud.permissions.elsticsearch.domain.doc.search.EsQueryDTO;
import com.ah.cloud.permissions.elsticsearch.domain.doc.search.Pager;
import com.ah.cloud.permissions.enums.common.ElasticSearchErrorCodeEnum;
import com.google.common.base.Throwables;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.HttpAsyncResponseConsumerFactory;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-08-26 09:51
 **/
@Slf4j
@Component
public abstract class AbstractElasticSearchService<T, Q> implements ElasticSearchService<T, Q>, ElasticSearchSettingService<Q> {
    private final RestHighLevelClient restHighLevelClient;
    private static final RequestOptions COMMON_OPTIONS;

    static {
        RequestOptions.Builder builder = RequestOptions.DEFAULT.toBuilder();
        builder.setHttpAsyncResponseConsumerFactory(
                new HttpAsyncResponseConsumerFactory
                        .HeapBufferedResponseConsumerFactory(100 * 1024 * 1024));
        COMMON_OPTIONS = builder.build();
    }

    protected AbstractElasticSearchService(RestHighLevelClient restHighLevelClient) {
        this.restHighLevelClient = restHighLevelClient;
    }

    @Override
    public EsPageResult<T> commonPageSearch(EsQueryDTO<Q> queryDTO) {
        log.info("{}[commonPageSearch] es page search, queryDTO is {}", getLogMark(), queryDTO);
        if (Objects.isNull(queryDTO)) {
            log.error("{}[commonPageSearch] es page search failed reason is queryDTO empty", getLogMark());
            return EsPageResult.failed(ElasticSearchErrorCodeEnum.ES_SEARCH_PARAM_IS_NULL);
        }
        Pager pager = queryDTO.getPager();
        if (Objects.isNull(pager)) {
            pager = Pager.newPager();
        }
        return searchByPage(queryDTO.getIndex(), setQueryBuilder(queryDTO), pager, setSortBuilder(queryDTO), setHighlightBuilder(queryDTO));
    }

    @Override
    public EsPageResult<T> searchByPage(String index, QueryBuilder queryBuilder, Pager pager, List<SortBuilder<?>> sortBuilderList, HighlightBuilder highlightBuilder) {
        SearchRequest searchRequest = new SearchRequest(index);
        int fromIndex = (pager.getPage() - 1) * pager.getPageSize();
        SearchSourceBuilder searchSourceBuilder = SearchSourceBuilder.searchSource()
                .query(queryBuilder)
                .from(fromIndex)
                .size(pager.getPageSize());
        if (CollectionUtils.isNotEmpty(sortBuilderList)) {
            for (SortBuilder<?> sortBuilder : sortBuilderList) {
                searchSourceBuilder.sort(sortBuilder);
            }
        }
        if (Objects.nonNull(highlightBuilder)) {
            searchSourceBuilder.highlighter(highlightBuilder);
        }
        searchRequest.source(searchSourceBuilder);
        try {
            SearchResponse searchResponse = restHighLevelClient.search(searchRequest, COMMON_OPTIONS);
            log.info("{}[searchByPage] request is {}, response is {}", getLogMark(), searchRequest, searchResponse);
            if (!Objects.equals(searchResponse.status(), RestStatus.OK)) {
                throw new BizException(ElasticSearchErrorCodeEnum.ES_SEARCH_RESPONSE_STATUS_ERROR, searchResponse.status().name());
            }
            List<T> resultList = convertSearchResponse(searchResponse);
            long total = searchResponse.getHits().getTotalHits().value;
            // 判断当前分页查询是否结束
            boolean isEnd = total <= pager.getCurrentPageSizeTotal();
            pager.setEnd(isEnd);
            pager.setTotal(total);
            return EsPageResult.success(resultList, pager);
        } catch (Exception e) {
            log.error("{}[searchByPage] es page search failed, request is {}, reason is {}", getLogMark(), JsonUtils.toJsonString(searchRequest), Throwables.getStackTraceAsString(e));
            return EsPageResult.failed(ElasticSearchErrorCodeEnum.ES_SEARCH_PAGE_FAILED);
        }
    }

    @Override
    public List<SortBuilder<?>> setSortBuilder(EsQueryDTO<Q> queryDTO) {
        List<SortBuilder<?>> sortBuilderList = new ArrayList<>();
        if (queryDTO.noNeedSort()) {
            return sortBuilderList;
        }
        return buildSortBuilder(queryDTO);
    }

    @Override
    public HighlightBuilder setHighlightBuilder(EsQueryDTO<Q> queryDTO) {
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        if (queryDTO.noNeedHighlight()) {
            return highlightBuilder;
        }
        return buildHighlightBuilder(queryDTO);
    }

    /**
     * 构建排序
     *
     * @param queryDTO
     * @param <Q>
     * @return
     */
    public List<SortBuilder<?>> buildSortBuilder(EsQueryDTO<Q> queryDTO) {
        return null;
    }

    /**
     * 构建高亮
     *
     * @param queryDTO
     * @param <Q>
     * @return
     */
    public HighlightBuilder buildHighlightBuilder(EsQueryDTO<Q> queryDTO) {
        return null;
    }

    /**
     * 获取查询结果的class
     *
     * @param <T>
     * @return
     */
    public abstract Class<T> getClazz();

    /**
     * 获取日志标识
     *
     * @return
     */
    public abstract String getLogMark();

    /**
     * 根据结果转换成业务实体 子类可根据实际情况重写
     * @param searchResponse
     * @param <T>
     * @return
     */
    public List<T> convertSearchResponse(SearchResponse searchResponse) {
        List<T> list = Lists.newArrayList();
        SearchHit[] hits = searchResponse.getHits().getHits();
        for (SearchHit hit : hits) {
            T t = JsonUtils.mapToBean(hit.getSourceAsMap(), getClazz());
            list.add(t);
        }
        return list;
    }
}
