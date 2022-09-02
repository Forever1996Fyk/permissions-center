package com.ah.cloud.permissions.elsticsearch.application.manager;

import com.ah.cloud.permissions.elsticsearch.domain.doc.dto.CommonDocRequest;
import com.ah.cloud.permissions.elsticsearch.infrastructure.exception.ElasticSearchDocException;
import com.ah.cloud.permissions.elsticsearch.infrastructure.service.doc.response.*;
import com.ah.cloud.permissions.elsticsearch.infrastructure.service.doc.response.impl.BulkDocResponseHandler;
import com.ah.cloud.permissions.elsticsearch.infrastructure.service.doc.response.impl.CreateDocResponseHandler;
import com.ah.cloud.permissions.elsticsearch.infrastructure.service.doc.response.impl.DeleteDocResponseHandler;
import com.ah.cloud.permissions.elsticsearch.infrastructure.service.doc.response.impl.UpdateDocResponseHandler;
import com.ah.cloud.permissions.enums.common.ErrorCodeEnum;
import com.google.common.collect.Lists;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.ActionResponse;

import java.util.List;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-09-02 10:01
 **/
public class DocResponseManager {
    private List<DocResponseHandler> serviceList = Lists.newArrayList();

    private static final List<DocResponseHandler> DEFAULT_SERVICE_LIST;

    static {
        DEFAULT_SERVICE_LIST = Lists.newArrayList(
                new CreateDocResponseHandler<>(),
                new DeleteDocResponseHandler(),
                new UpdateDocResponseHandler<>(),
                new BulkDocResponseHandler()
        );
    }

    public DocResponseManager() {
    }

    public DocResponseManager(List<DocResponseHandler> serviceList) {
        this.serviceList = serviceList;
    }


    /**
     * 默认管理器
     *
     * @return
     */
    public static DocResponseManager buildDefaultManager() {
        return new DocResponseManager(DEFAULT_SERVICE_LIST);
    }

    /**
     * 添加处理器
     *
     * @param handler
     * @return
     */
    public DocResponseManager add(DocResponseHandler handler) {
        serviceList.add(handler);
        return this;
    }

    /**
     * 获取 响应处理器
     *
     * @param request
     * @return
     */
    private DocResponseHandler select(CommonDocRequest request) {
        for (DocResponseHandler docResponseHandler : serviceList) {
            if (docResponseHandler.support(request)) {
                return docResponseHandler;
            }
        }
        throw new ElasticSearchDocException(ErrorCodeEnum.SELECTOR_NOT_EXISTED, "DocResponseHandler");
    }

    /**
     * 处理响应
     *
     * @param request
     * @param response
     */
    public void handleResponse(CommonDocRequest request, ActionResponse response) {
        DocResponseHandler handler = this.select(request);
        handler.handleResponse(request, response);
    }

    /**
     * 处理es异常
     *
     * @param request
     * @param exception
     */
    public void handleException(CommonDocRequest request, ElasticsearchException exception) {
        DocResponseHandler handler = this.select(request);
        handler.handleElasticsearchException(request, exception);
    }


}
