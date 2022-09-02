package com.ah.cloud.permissions.elsticsearch.domain.doc.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.elasticsearch.action.support.WriteRequest;
import org.elasticsearch.index.VersionType;

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
public class DeleteDocRequestDTO implements CommonDocRequest {

    /**
     * 索引名称
     */
    private String indexName;

    /**
     * 文档主键
     */
    private String id;

    /**
     * 超时时间(秒)
     */
    private Long timeout;

    /**
     * 刷新策略
     */
    private WriteRequest.RefreshPolicy refreshPolicy;

    /**
     * 版本类型
     */
    private VersionType versionType;

    /**
     * 版本号
     */
    private Integer version;

    public VersionType getVersionType() {
        return versionType == null ? VersionType.EXTERNAL : versionType;
    }

    public Long getTimeout() {
        return (timeout == null || timeout == 0L) ? DEFAULT_TIMEOUT : timeout;
    }

    public WriteRequest.RefreshPolicy getRefreshPolicy() {
        return refreshPolicy == null ? DEFAULT_REFRESH_POLICY : refreshPolicy;
    }
}
