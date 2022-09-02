package com.ah.cloud.permissions.elsticsearch.infrastructure.service.doc.response.result;

import com.ah.cloud.permissions.elsticsearch.domain.doc.dto.CommonDocRequest;
import org.elasticsearch.action.ActionResponse;
import org.elasticsearch.rest.RestStatus;
import org.springframework.beans.factory.InitializingBean;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-09-02 11:50
 **/
public interface DocResponseStatusHandler extends InitializingBean {

    /**
     * 处理逻辑
     * @param request
     * @param response
     */
    void process(CommonDocRequest request, ActionResponse response);

    /**
     * 响应状态
     * @return
     */
    RestStatus getRestStatus();
}
