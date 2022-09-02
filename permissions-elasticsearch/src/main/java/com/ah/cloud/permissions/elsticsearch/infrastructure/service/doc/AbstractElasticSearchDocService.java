package com.ah.cloud.permissions.elsticsearch.infrastructure.service.doc;

import com.ah.cloud.permissions.biz.infrastructure.util.JsonUtils;
import com.ah.cloud.permissions.elsticsearch.application.checker.ElasticSearchDocChecker;
import com.ah.cloud.permissions.elsticsearch.application.helper.ElasticSearchDocHelper;
import com.ah.cloud.permissions.elsticsearch.application.manager.DocResponseManager;
import com.ah.cloud.permissions.elsticsearch.domain.doc.dto.BulkDocRequestDTO;
import com.ah.cloud.permissions.elsticsearch.domain.doc.dto.CreateDocRequestDTO;
import com.ah.cloud.permissions.elsticsearch.domain.doc.dto.DeleteDocRequestDTO;
import com.ah.cloud.permissions.elsticsearch.domain.doc.dto.UpdateDocRequestDTO;
import com.ah.cloud.permissions.elsticsearch.infrastructure.exception.ElasticSearchDocException;
import com.ah.cloud.permissions.enums.common.ElasticSearchErrorCodeEnum;
import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;

import java.io.IOException;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-09-01 15:14
 **/
@Slf4j
public abstract class AbstractElasticSearchDocService implements ElasticSearchDocService {
    private final RestHighLevelClient restHighLevelClient;
    private final DocResponseManager docResponseManager;

    public AbstractElasticSearchDocService(RestHighLevelClient restHighLevelClient) {
        this(restHighLevelClient, DocResponseManager.buildDefaultManager());
    }

    public AbstractElasticSearchDocService(RestHighLevelClient restHighLevelClient, DocResponseManager docResponseManager) {
        this.restHighLevelClient = restHighLevelClient;
        this.docResponseManager = docResponseManager;
    }

    @Override
    public <T> void createDoc(CreateDocRequestDTO<T> createDocRequestDTO) {
        ElasticSearchDocChecker.checkCommonDocParam(createDocRequestDTO);
        IndexRequest request = new IndexRequest(createDocRequestDTO.getIndexName());
        request.id(createDocRequestDTO.getId())
                .source(JsonUtils.toJsonString(createDocRequestDTO.getSourceObject()), XContentType.JSON);
        try {
            IndexResponse response = restHighLevelClient.index(request, RequestOptions.DEFAULT);
            log.info("{}[createDoc] create doc, request is {}, response is {}", getLogMark(), request, response);
            docResponseManager.handleResponse(createDocRequestDTO, response);
        } catch (IOException e) {
            log.error("{}[createDoc] create doc error with IOException, request is {}, reason is {}", getLogMark(), request, Throwables.getStackTraceAsString(e));
            throw new ElasticSearchDocException(ElasticSearchErrorCodeEnum.ES_OPERATE_DOC_FAILED, "create");
        } catch (ElasticsearchException es) {
            log.error("{}[createDoc] create doc error with ElasticsearchException, request is {}, reason is {}", getLogMark(), request, Throwables.getStackTraceAsString(es));
            docResponseManager.handleException(createDocRequestDTO, es);
        }
    }

    @Override
    public <T> void updateDoc(UpdateDocRequestDTO<T> updateDocRequestDTO) {
        ElasticSearchDocChecker.checkCommonDocParam(updateDocRequestDTO);
        UpdateRequest request = ElasticSearchDocHelper.buildUpdateRequest(updateDocRequestDTO);
        try {
            UpdateResponse response = restHighLevelClient.update(request, RequestOptions.DEFAULT);
            log.info("{}[updateDoc] update doc, request is {}, response is {}", getLogMark(), request, response);
            docResponseManager.handleResponse(updateDocRequestDTO, response);
        } catch (IOException e) {
            log.error("{}[updateDoc] update doc error with IOException, request is {}, reason is {}", getLogMark(), request, Throwables.getStackTraceAsString(e));
            throw new ElasticSearchDocException(ElasticSearchErrorCodeEnum.ES_OPERATE_DOC_FAILED, "update");
        } catch (ElasticsearchException es) {
            log.error("{}[createDoc] update doc error with ElasticsearchException, request is {}, reason is {}", getLogMark(), request, Throwables.getStackTraceAsString(es));
            docResponseManager.handleException(updateDocRequestDTO, es);
        }
    }

    @Override
    public void deleteDoc(DeleteDocRequestDTO deleteDocRequestDTO) {
        ElasticSearchDocChecker.checkCommonDocParam(deleteDocRequestDTO);
        DeleteRequest request = ElasticSearchDocHelper.buildDeleteRequest(deleteDocRequestDTO);
        try {
            DeleteResponse response = restHighLevelClient.delete(request, RequestOptions.DEFAULT);
            log.info("{}[updateDoc] delete doc, request is {}, response is {}", getLogMark(), request, response);
            docResponseManager.handleResponse(deleteDocRequestDTO, response);
        } catch (IOException e) {
            log.error("{}[updateDoc] delete doc error with IOException, request is {}, reason is {}", getLogMark(), request, Throwables.getStackTraceAsString(e));
            throw new ElasticSearchDocException(ElasticSearchErrorCodeEnum.ES_OPERATE_DOC_FAILED, "delete");
        } catch (ElasticsearchException es) {
            log.error("{}[createDoc] delete doc error with ElasticsearchException, request is {}, reason is {}", getLogMark(), request, Throwables.getStackTraceAsString(es));
            docResponseManager.handleException(deleteDocRequestDTO, es);
        }
    }

    @Override
    public void bulkDoc(BulkDocRequestDTO bulkDocRequestDTO) {
        ElasticSearchDocChecker.checkBulkDocParam(bulkDocRequestDTO);
        BulkRequest request = ElasticSearchDocHelper.buildBulkDocParam(bulkDocRequestDTO);
        try {
            BulkResponse response = restHighLevelClient.bulk(request, RequestOptions.DEFAULT);
            log.info("{}[bulkDoc] bulk doc, request is {}, response is {}", getLogMark(), request, response);
            docResponseManager.handleResponse(bulkDocRequestDTO, response);
        } catch (IOException e) {
            log.error("{}[bulkDoc] bulk doc error with IOException, request is {}, reason is {}", getLogMark(), request, Throwables.getStackTraceAsString(e));
            throw new ElasticSearchDocException(ElasticSearchErrorCodeEnum.ES_OPERATE_DOC_FAILED, "bulk");
        } catch (ElasticsearchException es) {
            log.error("{}[bulkDoc] bulk doc error with ElasticsearchException, request is {}, reason is {}", getLogMark(), request, Throwables.getStackTraceAsString(es));
            docResponseManager.handleException(bulkDocRequestDTO, es);
        }
    }

    /**
     * 日志标记
     * @return
     */
    public abstract String getLogMark();
}
