package com.ah.cloud.permissions.elsticsearch.infrastructure.service.search.impl;

import com.ah.cloud.permissions.elsticsearch.domain.doc.search.EsQueryDTO;
import com.ah.cloud.permissions.elsticsearch.domain.example.ItemDoc;
import com.ah.cloud.permissions.elsticsearch.domain.example.ItemQuery;
import com.ah.cloud.permissions.elsticsearch.infrastructure.service.search.AbstractElasticSearchService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @program: permissions-center
 * @description: es查询例子
 * @author: YuKai Fan
 * @create: 2022-08-27 11:18
 **/
@Slf4j
@Component
public class ExampleElasticSearchServiceImpl extends AbstractElasticSearchService<ItemDoc, ItemQuery> {

    private final static String LOG_MARK = "ExampleElasticSearchServiceImpl";

    @Autowired
    protected ExampleElasticSearchServiceImpl(RestHighLevelClient restHighLevelClient) {
        super(restHighLevelClient);
    }

    @Override
    public Class<ItemDoc> getClazz() {
        return ItemDoc.class;
    }

    @Override
    public String getLogMark() {
        return LOG_MARK;
    }

    @Override
    public QueryBuilder setQueryBuilder(EsQueryDTO<ItemQuery> queryDTO) {
        ItemQuery itemQuery = queryDTO.getQuery();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

        if (StringUtils.isNotBlank(itemQuery.getName())) {
            boolQueryBuilder.must(
                    // 构建 match query
                    QueryBuilders
                            // name: 查询es文档的字段, text: 需要搜索的内容
                            .matchQuery("name", itemQuery.getName())
                            // 模糊查询
                            .fuzziness(Fuzziness.AUTO)
                            // 前缀查询长度
                            .prefixLength(3)
                            // 控制模式查询的最大长度
                            .maxExpansions(10)
            );
        }

        if (StringUtils.isNotBlank(itemQuery.getItemId())) {
            boolQueryBuilder.filter(
                    QueryBuilders.termQuery("itemId", itemQuery.getItemId())
            );
        }

        if (StringUtils.isNotBlank(itemQuery.getFrontCategoryId())) {
            BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
            queryBuilder.filter(
                    QueryBuilders.termQuery("frontCateInfo.frontCateId", itemQuery.getFrontCategoryId())
            );
            /*
            设置 nested query
             */
            boolQueryBuilder.filter(QueryBuilders.nestedQuery("frontCateInfo", queryBuilder, ScoreMode.Avg));
        }
        return boolQueryBuilder;
    }
}
