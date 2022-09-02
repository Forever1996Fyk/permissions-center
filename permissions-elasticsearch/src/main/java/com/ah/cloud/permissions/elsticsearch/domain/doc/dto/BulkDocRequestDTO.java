package com.ah.cloud.permissions.elsticsearch.domain.doc.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.poi.ss.formula.functions.T;
import org.elasticsearch.action.support.ActiveShardCount;
import org.elasticsearch.action.support.WriteRequest;

import java.util.Collection;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-08-25 17:22
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BulkDocRequestDTO implements CommonDocRequest {

    /**
     * 索引名称
     */
    private String indexName;

    /**
     * 超时时间(秒)
     */
    private Long timeout;

    /**
     * 设置在批量操作前必须有多少分片处于激活状态
     */
    private Integer waitForActiveShards;

    /**
     * 刷新策略
     */
    private WriteRequest.RefreshPolicy refreshPolicy;

    /**
     * 文档映射
     */
    private Collection<CommonDocRequest> docRequests;


    public Long getTimeout() {
        return (timeout == null || timeout == 0L) ? DEFAULT_TIMEOUT : timeout;
    }

    public WriteRequest.RefreshPolicy getRefreshPolicy() {
        return refreshPolicy == null ? DEFAULT_REFRESH_POLICY : refreshPolicy;
    }

    public Integer getWaitForActiveShards() {
        return (waitForActiveShards == null || waitForActiveShards == 0) ? 1 : waitForActiveShards;
    }

    @Override
    public String getId() {
        return null;
    }
}
