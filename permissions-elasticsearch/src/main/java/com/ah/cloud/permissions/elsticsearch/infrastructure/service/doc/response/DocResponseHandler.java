package com.ah.cloud.permissions.elsticsearch.infrastructure.service.doc.response;

import com.ah.cloud.permissions.elsticsearch.domain.doc.dto.CommonDocRequest;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.ActionResponse;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-09-02 09:51
 **/
public interface DocResponseHandler {

    /**
     * 处理文档写入返回结果
     * @param request
     * @param response
     */
    void handleResponse(CommonDocRequest request, ActionResponse response);

    /**
     * 处理返回状态异常
     * @param request
     * @param exception
     * @param <T>
     */
    void handleElasticsearchException(CommonDocRequest request, ElasticsearchException exception);

    /**
     * 是否支持
     * @param request
     * @return
     */
    boolean support(CommonDocRequest request);
}
