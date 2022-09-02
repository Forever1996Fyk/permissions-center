package com.ah.cloud.permissions.elsticsearch.infrastructure.service.doc.response.result;

import com.ah.cloud.permissions.elsticsearch.domain.doc.dto.CommonDocRequest;
import org.elasticsearch.action.DocWriteResponse;
import org.springframework.beans.factory.InitializingBean;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-09-02 11:50
 **/
public interface DocResponseResultHandler extends InitializingBean {

    /**
     * 处理逻辑
     * @param request
     * @param response
     */
    void process(CommonDocRequest request, DocWriteResponse response);

    /**
     * 执行结果
     * @return
     */
    DocWriteResponse.Result getResult();
}
