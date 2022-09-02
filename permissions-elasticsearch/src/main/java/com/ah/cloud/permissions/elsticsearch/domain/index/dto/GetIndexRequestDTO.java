package com.ah.cloud.permissions.elsticsearch.domain.index.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-08-25 15:02
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetIndexRequestDTO {

    /**
     * 索引名称
     */
    private String indexName;

    /**
     * 是否从主节点返回本地索引信息状态
     */
    private Boolean local;

    /**
     * 以适合人类的格式返回
     */
    private Boolean humanReadable;

    /**
     * 是否返回每个索引的所有默认配置
     */
    private Boolean includeDefaults;
}
