package com.ah.cloud.permissions.elsticsearch.domain.index.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.elasticsearch.cluster.metadata.AliasMetadata;
import org.elasticsearch.cluster.metadata.MappingMetadata;
import org.elasticsearch.common.settings.Settings;

import java.util.List;
import java.util.Map;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-08-25 15:01
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetIndexResponseDTO {

    /**
     * 索引映射
     */
    private Map<String, MappingMetadata> mappings;

    /**
     * 索引别名
     */
    private Map<String, List<AliasMetadata>> aliases;

    /**
     * 数据流
     */
    private Map<String, String> dataStreams;

    /**
     * 索引
     */
    private String[] indices;
}
