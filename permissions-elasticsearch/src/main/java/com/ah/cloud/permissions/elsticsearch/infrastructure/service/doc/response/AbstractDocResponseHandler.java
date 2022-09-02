package com.ah.cloud.permissions.elsticsearch.infrastructure.service.doc.response;

import com.ah.cloud.permissions.elsticsearch.domain.doc.dto.CommonDocRequest;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.ActionResponse;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-09-02 09:58
 **/
@Slf4j
public abstract class AbstractDocResponseHandler<T extends CommonDocRequest, R extends ActionResponse> implements DocResponseHandler {

    @Override
    public void handleResponse(CommonDocRequest request, ActionResponse response) {
        doHandle((T) request, (R) response);
    }

    @Override
    public void handleElasticsearchException(CommonDocRequest request, ElasticsearchException exception) {
        doHandleException((T) request, exception);
    }

    /**
     * 执行处理响应结果
     * @param request
     * @param response
     */
    protected abstract void doHandle(T request, R response);


    /**
     * 执行处理响应异常
     * @param request
     * @param exception
     */
    protected abstract void doHandleException(T request, ElasticsearchException exception);
}
