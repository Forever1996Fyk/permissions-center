package com.ah.cloud.permissions.elsticsearch.infrastructure.service.index.impl;

import com.ah.cloud.permissions.biz.infrastructure.exception.BizException;
import com.ah.cloud.permissions.biz.infrastructure.util.JsonUtils;
import com.ah.cloud.permissions.elsticsearch.application.checker.ElasticSearchIndexChecker;
import com.ah.cloud.permissions.elsticsearch.domain.index.dto.CreateIndexDTO;
import com.ah.cloud.permissions.elsticsearch.domain.index.dto.DeleteIndexDTO;
import com.ah.cloud.permissions.elsticsearch.domain.index.dto.GetIndexRequestDTO;
import com.ah.cloud.permissions.elsticsearch.domain.index.dto.GetIndexResponseDTO;
import com.ah.cloud.permissions.elsticsearch.application.helper.ElasticSearchIndexHelper;
import com.ah.cloud.permissions.elsticsearch.infrastructure.service.index.ElasticSearchIndexService;
import com.ah.cloud.permissions.enums.common.ElasticSearchErrorCodeEnum;
import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Objects;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-08-25 15:11
 **/
@Slf4j
@Service
public class ElasticSearchIndexServiceImpl implements ElasticSearchIndexService {
    @Resource
    private RestHighLevelClient restHighLevelClient;
    @Resource
    private ElasticSearchIndexHelper elasticSearchIndexHelper;


    @Override
    public boolean createEsIndex(CreateIndexDTO createIndexDTO) {
        ElasticSearchIndexChecker.checkCreateIndexParam(createIndexDTO);
        CreateIndexRequest request = elasticSearchIndexHelper.buildCreateIndexRequest(createIndexDTO);
        try {
            CreateIndexResponse response = restHighLevelClient.indices().create(request, RequestOptions.DEFAULT);
            log.info("ElasticSearchIndexServiceImpl[createEsIndex] create index request is {}, response is {}", JsonUtils.toJsonString(request), JsonUtils.toJsonString(response));
            return Objects.isNull(response) ? Boolean.FALSE : response.isAcknowledged();
        } catch (IOException e) {
            log.error("ElasticSearchIndexServiceImpl[createEsIndex] create index failed, request is {}, reason is {}", JsonUtils.toJsonString(request), Throwables.getStackTraceAsString(e));
            throw new BizException(ElasticSearchErrorCodeEnum.ES_OPERATION_EXCEPTION, createIndexDTO.getIndexName());
        }
    }

    @Override
    public GetIndexResponseDTO getEsIndex(GetIndexRequestDTO getIndexRequestDTO) {
        ElasticSearchIndexChecker.checkGetIndexParam(getIndexRequestDTO);
        GetIndexRequest request = elasticSearchIndexHelper.buildGetIndexRequest(getIndexRequestDTO);
        try {
            GetIndexResponse response = restHighLevelClient.indices().get(request, RequestOptions.DEFAULT);
            log.info("ElasticSearchIndexServiceImpl[getEsIndex] get index request is {}, response is {}", JsonUtils.toJsonString(request), JsonUtils.toJsonString(response));
            return Objects.isNull(response) ? null : elasticSearchIndexHelper.convertGetIndexResponseDTO(response);
        } catch (IOException e) {
            log.error("ElasticSearchIndexServiceImpl[getEsIndex] get index failed, request is {}, reason is {}", JsonUtils.toJsonString(request), Throwables.getStackTraceAsString(e));
            throw new BizException(ElasticSearchErrorCodeEnum.ES_OPERATION_EXCEPTION, getIndexRequestDTO.getIndexName());
        }
    }

    @Override
    public boolean deleteEsIndex(DeleteIndexDTO deleteIndexDTO) {
        ElasticSearchIndexChecker.checkDeleteIndexParam(deleteIndexDTO);
        DeleteIndexRequest request = elasticSearchIndexHelper.buildDeleteIndexRequest(deleteIndexDTO);
        try {
            AcknowledgedResponse response = restHighLevelClient.indices().delete(request, RequestOptions.DEFAULT);
            log.info("ElasticSearchIndexServiceImpl[deleteEsIndex] delete index request is {}, response is {}", JsonUtils.toJsonString(request), JsonUtils.toJsonString(response));
            return Objects.isNull(response) ? Boolean.FALSE : response.isAcknowledged();
        } catch (IOException e) {
            log.error("ElasticSearchIndexServiceImpl[deleteEsIndex] delete index failed, request is {}, reason is {}", JsonUtils.toJsonString(request), Throwables.getStackTraceAsString(e));
            throw new BizException(ElasticSearchErrorCodeEnum.ES_OPERATION_EXCEPTION, deleteIndexDTO.getIndexName());
        }
    }

    @Override
    public boolean existsEsIndex(String indexName) {
        GetIndexRequestDTO getIndexRequestDTO = GetIndexRequestDTO.builder()
                .indexName(indexName)
                .local(false)
                .humanReadable(true)
                .includeDefaults(false)
                .build();
        GetIndexRequest request = elasticSearchIndexHelper.buildGetIndexRequest(getIndexRequestDTO);
        try {
            return restHighLevelClient.indices().exists(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            log.error("ElasticSearchIndexServiceImpl[existsEsIndex] exists index failed, request is {}, reason is {}", JsonUtils.toJsonString(request), Throwables.getStackTraceAsString(e));
            throw new BizException(ElasticSearchErrorCodeEnum.ES_OPERATION_EXCEPTION, indexName);
        }
    }
}
