package com.ah.cloud.permissions.elsticsearch.domain.doc.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.elasticsearch.action.support.WriteRequest;
import org.elasticsearch.common.xcontent.XContent;
import org.elasticsearch.common.xcontent.XContentType;

import java.util.Date;

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
public class UpdateDocRequestDTO<T> implements CommonDocRequest {


    /**
     * 索引名称
     */
    private String indexName;

    /**
     * 文档主键
     */
    private String id;

    /**
     * 文档映射
     */
    private T sourceObject;

    /**
     * true: 存在即更新，不存在即创建
     *
     * false: 存在即更新，不存在则返回无操作
     */
    private Boolean doAsUpset;

    /**
     * 超时时间(秒)
     */
    private Long timeout;

    /**
     * 刷新策略
     */
    private WriteRequest.RefreshPolicy refreshPolicy;

    /**
     * 冲突后重试次数
     */
    private Integer retryOnConflict;

    /**
     * 包含字段
     */
    private String[] includes;

    /**
     * 排除字段
     */
    private String[] excludes;

    /**
     * 指定版本
     */
    private Integer version;

    public Boolean getDoAsUpset() {
        return doAsUpset == null || doAsUpset;
    }

    public Long getTimeout() {
        return (timeout == null || timeout == 0L) ? DEFAULT_TIMEOUT : timeout;
    }

    public WriteRequest.RefreshPolicy getRefreshPolicy() {
        return refreshPolicy == null ? DEFAULT_REFRESH_POLICY : refreshPolicy;
    }

    public Integer getRetryOnConflict() {
        return (retryOnConflict == null || retryOnConflict == 0) ? DEFAULT_RETRY_ON_CONFLICT : retryOnConflict;
    }
}
