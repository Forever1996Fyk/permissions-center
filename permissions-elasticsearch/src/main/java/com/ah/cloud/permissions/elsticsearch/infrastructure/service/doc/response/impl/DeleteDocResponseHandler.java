package com.ah.cloud.permissions.elsticsearch.infrastructure.service.doc.response.impl;

import com.ah.cloud.permissions.elsticsearch.domain.doc.dto.CommonDocRequest;
import com.ah.cloud.permissions.elsticsearch.domain.doc.dto.CreateDocRequestDTO;
import com.ah.cloud.permissions.elsticsearch.domain.doc.dto.DeleteDocRequestDTO;
import com.ah.cloud.permissions.elsticsearch.infrastructure.service.doc.response.AbstractDocResponseHandler;
import com.ah.cloud.permissions.elsticsearch.infrastructure.service.doc.response.result.DocResponseResultHandler;
import com.ah.cloud.permissions.elsticsearch.infrastructure.service.doc.response.result.DocResponseStatusHandler;
import com.ah.cloud.permissions.elsticsearch.infrastructure.service.doc.response.result.factory.DocResponseProcessFactory;
import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.delete.DeleteResponse;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-09-02 10:36
 **/
@Slf4j
public class DeleteDocResponseHandler extends AbstractDocResponseHandler<DeleteDocRequestDTO, DeleteResponse> {

    @Override
    protected void doHandle(DeleteDocRequestDTO request, DeleteResponse response) {
        // 结果处理器
        DocResponseResultHandler resultHandler = DocResponseProcessFactory.getResultHandler(response.getResult());
        if (resultHandler != null) {
            resultHandler.process(request, response);
        }

        // 状态处理器
        DocResponseStatusHandler statusHandler = DocResponseProcessFactory.getStatusHandler(response.status());
        if (statusHandler != null) {
            statusHandler.process(request, response);
        }
    }

    @Override
    protected void doHandleException(DeleteDocRequestDTO request, ElasticsearchException exception) {
        log.error("DeleteDocResponseHandler[doHandleException] delete doc handle exception, request is {}, reason is {}", request, Throwables.getStackTraceAsString(exception));
    }

    @Override
    public boolean support(CommonDocRequest request) {
        return DeleteDocRequestDTO.class.isAssignableFrom(request.getClass());
    }
}
