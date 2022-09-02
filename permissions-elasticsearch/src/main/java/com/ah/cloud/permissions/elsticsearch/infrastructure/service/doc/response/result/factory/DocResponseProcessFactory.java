package com.ah.cloud.permissions.elsticsearch.infrastructure.service.doc.response.result.factory;

import com.ah.cloud.permissions.elsticsearch.infrastructure.exception.DocResponseProcessException;
import com.ah.cloud.permissions.elsticsearch.infrastructure.exception.ElasticSearchException;
import com.ah.cloud.permissions.elsticsearch.infrastructure.service.doc.response.result.DocResponseResultHandler;
import com.ah.cloud.permissions.elsticsearch.infrastructure.service.doc.response.result.DocResponseStatusHandler;
import com.ah.cloud.permissions.enums.common.ElasticSearchErrorCodeEnum;
import com.google.common.collect.Maps;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.rest.RestStatus;

import java.util.Map;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-09-02 13:45
 **/
public class DocResponseProcessFactory {
    /**
     * 状态处理map
     */
    private final static Map<RestStatus, DocResponseStatusHandler> STATUS_HANDLER_MAP = Maps.newConcurrentMap();

    /**
     * 结果处理map
     */
    private final static Map<DocWriteResponse.Result, DocResponseResultHandler> RESULT_HANDLER_MAP = Maps.newConcurrentMap();

    public static void statusRegister(RestStatus restStatus, DocResponseStatusHandler handler) {
        if (restStatus == null) {
            throw new DocResponseProcessException(ElasticSearchErrorCodeEnum.ES_DOC_RESPONSE_STATUS_IS_NULL);
        }
        if (handler == null) {
            throw new DocResponseProcessException(ElasticSearchErrorCodeEnum.ES_DOC_RESPONSE_STATUS_HANDLER_IS_NULL);
        }
        STATUS_HANDLER_MAP.put(restStatus, handler);
    }

    public static DocResponseStatusHandler getStatusHandler(RestStatus restStatus) {
        if (restStatus == null) {
            throw new DocResponseProcessException(ElasticSearchErrorCodeEnum.ES_DOC_RESPONSE_STATUS_IS_NULL);
        }
        return STATUS_HANDLER_MAP.get(restStatus);
    }

    public static void resultRegister(DocWriteResponse.Result result, DocResponseResultHandler handler) {
        if (result == null) {
            throw new DocResponseProcessException(ElasticSearchErrorCodeEnum.ES_DOC_RESPONSE_RESULT_IS_NULL);
        }
        if (handler == null) {
            throw new DocResponseProcessException(ElasticSearchErrorCodeEnum.ES_DOC_RESPONSE_RESULT_HANDLER_IS_NULL);
        }
        RESULT_HANDLER_MAP.put(result, handler);
    }

    public static DocResponseResultHandler getResultHandler(DocWriteResponse.Result result) {
        if (result == null) {
            throw new DocResponseProcessException(ElasticSearchErrorCodeEnum.ES_DOC_RESPONSE_RESULT_IS_NULL);
        }
        return RESULT_HANDLER_MAP.get(result);
    }
}
