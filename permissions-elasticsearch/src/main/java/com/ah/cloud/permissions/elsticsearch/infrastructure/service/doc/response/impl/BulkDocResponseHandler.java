package com.ah.cloud.permissions.elsticsearch.infrastructure.service.doc.response.impl;

import com.ah.cloud.permissions.elsticsearch.domain.doc.dto.BulkDocRequestDTO;
import com.ah.cloud.permissions.elsticsearch.domain.doc.dto.CommonDocRequest;
import com.ah.cloud.permissions.elsticsearch.domain.doc.dto.UpdateDocRequestDTO;
import com.ah.cloud.permissions.elsticsearch.infrastructure.service.doc.response.AbstractDocResponseHandler;
import com.ah.cloud.permissions.elsticsearch.infrastructure.service.doc.response.result.DocResponseStatusHandler;
import com.ah.cloud.permissions.elsticsearch.infrastructure.service.doc.response.result.factory.DocResponseProcessFactory;
import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.DocWriteRequest;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.rest.RestStatus;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-09-02 10:36
 **/
@Slf4j
public class BulkDocResponseHandler extends AbstractDocResponseHandler<BulkDocRequestDTO, BulkResponse> {

    @Override
    protected void doHandle(BulkDocRequestDTO request, BulkResponse response) {
        if (response.hasFailures()) {
            for (BulkItemResponse bulkItemResponse : response) {
                if (bulkItemResponse.isFailed()) {
                    BulkItemResponse.Failure failure = bulkItemResponse.getFailure();
                    log.error("BulkDocResponseHandler[doHandle] bulk failed, request is {}, failure is {}", request, failure);
                }
            }
        }

        // 状态处理器
        DocResponseStatusHandler statusHandler = DocResponseProcessFactory.getStatusHandler(response.status());
        statusHandler.process(request, response);
    }

    @Override
    protected void doHandleException(BulkDocRequestDTO request, ElasticsearchException exception) {
        log.error("BulkDocResponseHandler[doHandleException] bulk doc handle exception, request is {}, reason is {}", request, Throwables.getStackTraceAsString(exception));
    }

    @Override
    public boolean support(CommonDocRequest request) {
        return BulkDocRequestDTO.class.isAssignableFrom(request.getClass());
    }
}
