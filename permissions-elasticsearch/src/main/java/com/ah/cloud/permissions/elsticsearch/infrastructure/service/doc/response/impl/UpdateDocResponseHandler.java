package com.ah.cloud.permissions.elsticsearch.infrastructure.service.doc.response.impl;

import com.ah.cloud.permissions.elsticsearch.domain.doc.dto.CommonDocRequest;
import com.ah.cloud.permissions.elsticsearch.domain.doc.dto.CreateDocRequestDTO;
import com.ah.cloud.permissions.elsticsearch.domain.doc.dto.DeleteDocRequestDTO;
import com.ah.cloud.permissions.elsticsearch.domain.doc.dto.UpdateDocRequestDTO;
import com.ah.cloud.permissions.elsticsearch.infrastructure.service.doc.response.AbstractDocResponseHandler;
import com.ah.cloud.permissions.elsticsearch.infrastructure.service.doc.response.result.DocResponseResultHandler;
import com.ah.cloud.permissions.elsticsearch.infrastructure.service.doc.response.result.DocResponseStatusHandler;
import com.ah.cloud.permissions.elsticsearch.infrastructure.service.doc.response.result.factory.DocResponseProcessFactory;
import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.update.UpdateResponse;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-09-02 10:36
 **/
@Slf4j
public class UpdateDocResponseHandler<T> extends AbstractDocResponseHandler<UpdateDocRequestDTO<T>, UpdateResponse> {

    @Override
    protected void doHandle(UpdateDocRequestDTO<T> request, UpdateResponse response) {
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
    protected void doHandleException(UpdateDocRequestDTO<T> request, ElasticsearchException exception) {
        log.error("CreateDocResponseHandler[doHandleException] create doc handle exception, request is {}, reason is {}", request, Throwables.getStackTraceAsString(exception));
    }

    @Override
    public boolean support(CommonDocRequest request) {
        return UpdateDocRequestDTO.class.isAssignableFrom(request.getClass());
    }
}
