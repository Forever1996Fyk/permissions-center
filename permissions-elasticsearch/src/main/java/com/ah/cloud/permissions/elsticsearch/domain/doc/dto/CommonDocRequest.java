package com.ah.cloud.permissions.elsticsearch.domain.doc.dto;

import org.elasticsearch.action.support.WriteRequest;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-09-01 14:47
 **/
public interface CommonDocRequest {


    /**
     * 默认超时时间
     */
     Long DEFAULT_TIMEOUT = 60L;

    /**
     * 默认冲突重试次数
     */
     Integer DEFAULT_RETRY_ON_CONFLICT = 3;

    /**
     * 默认策略
     */
    WriteRequest.RefreshPolicy DEFAULT_REFRESH_POLICY = WriteRequest.RefreshPolicy.WAIT_UNTIL;

    /**
     * 获取索引名称
     * @return
     */
    String getIndexName();

    /**
     * 文档id
     * @return
     */
    String getId();
}
