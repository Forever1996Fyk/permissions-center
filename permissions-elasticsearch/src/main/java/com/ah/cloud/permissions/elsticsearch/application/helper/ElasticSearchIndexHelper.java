package com.ah.cloud.permissions.elsticsearch.application.helper;

import com.ah.cloud.permissions.biz.infrastructure.constant.PermissionsConstants;
import com.ah.cloud.permissions.biz.infrastructure.util.CollectionUtils;
import com.ah.cloud.permissions.elsticsearch.domain.index.dto.CreateIndexDTO;
import com.ah.cloud.permissions.elsticsearch.domain.index.dto.DeleteIndexDTO;
import com.ah.cloud.permissions.elsticsearch.domain.index.dto.GetIndexRequestDTO;
import com.ah.cloud.permissions.elsticsearch.domain.index.dto.GetIndexResponseDTO;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.admin.indices.alias.Alias;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;
import org.elasticsearch.common.unit.TimeValue;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-08-25 15:13
 **/
@Component
public class ElasticSearchIndexHelper {

    /**
     * 默认超时时间
     */
    private final static Long DEFAULT_INDEX_TIMEOUT_SECONDS = 2L;

    /**
     * 默认主节点超时时间
     */
    private final static Long DEFAULT_INDEX_MASTER_TIMEOUT_SECONDS = 2L;

    /**
     * 构建创建索引对象
     *
     * @param createIndexDTO
     * @return
     */
    public CreateIndexRequest buildCreateIndexRequest(CreateIndexDTO createIndexDTO) {
        CreateIndexRequest request = new CreateIndexRequest(createIndexDTO.getIndexName());
        Map<String, String> mapping = createIndexDTO.getMapping();
        if (CollectionUtils.isNotEmpty(mapping)) {
            request.settings(mapping);
        }
        if (StringUtils.isNotBlank(createIndexDTO.getIndexAlias())) {
            request.alias(new Alias(createIndexDTO.getIndexAlias()));
        }
        Long timeout = createIndexDTO.getTimeout();
        if (timeout == null || Objects.equals(timeout, PermissionsConstants.ZERO)) {
            timeout = DEFAULT_INDEX_TIMEOUT_SECONDS;
        }
        request.setTimeout(TimeValue.timeValueSeconds(timeout));

        Long masterTimeout = createIndexDTO.getMasterTimeout();
        if (masterTimeout == null || Objects.equals(masterTimeout, PermissionsConstants.ZERO)) {
            masterTimeout = DEFAULT_INDEX_MASTER_TIMEOUT_SECONDS;
        }
        request.setMasterTimeout(TimeValue.timeValueSeconds(masterTimeout));

        return request;
    }

    /**
     * 构建获取索引对象
     *
     * @param getIndexRequestDTO
     * @return
     */
    public GetIndexRequest buildGetIndexRequest(GetIndexRequestDTO getIndexRequestDTO) {
        GetIndexRequest request = new GetIndexRequest(getIndexRequestDTO.getIndexName());
        request.local(getIndexRequestDTO.getLocal());
        request.humanReadable(getIndexRequestDTO.getHumanReadable());
        request.includeDefaults(getIndexRequestDTO.getIncludeDefaults());
        return request;
    }

    /**
     * 转换获取索引对象
     *
     * @param response
     * @return
     */
    public GetIndexResponseDTO convertGetIndexResponseDTO(GetIndexResponse response) {
        return GetIndexResponseDTO.builder()
                .dataStreams(response.getDataStreams())
                .aliases(response.getAliases())
                .mappings(response.getMappings())
                .indices(response.getIndices())
                .build();
    }

    /**
     * 构建删除索引对象
     * @param deleteIndexDTO
     * @return
     */
    public DeleteIndexRequest buildDeleteIndexRequest(DeleteIndexDTO deleteIndexDTO) {
        return new DeleteIndexRequest(deleteIndexDTO.getIndexName());
    }
}
