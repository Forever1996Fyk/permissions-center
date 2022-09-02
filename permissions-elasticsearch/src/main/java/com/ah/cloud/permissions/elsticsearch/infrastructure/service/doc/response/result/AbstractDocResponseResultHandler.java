package com.ah.cloud.permissions.elsticsearch.infrastructure.service.doc.response.result;

import com.ah.cloud.permissions.elsticsearch.infrastructure.service.doc.response.result.factory.DocResponseProcessFactory;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-09-02 13:45
 **/
public abstract class AbstractDocResponseResultHandler implements DocResponseResultHandler {

    @Override
    public void afterPropertiesSet() throws Exception {
        DocResponseProcessFactory.resultRegister(getResult(), getCurrentHandler());
    }

    /**
     * 获取当前的处理器
     * @return
     */
    public abstract DocResponseResultHandler getCurrentHandler();
}
