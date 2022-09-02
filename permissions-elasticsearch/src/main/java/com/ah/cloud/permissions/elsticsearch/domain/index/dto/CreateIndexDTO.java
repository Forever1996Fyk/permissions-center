package com.ah.cloud.permissions.elsticsearch.domain.index.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-08-25 14:59
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateIndexDTO {

    /**
     * 索引名称
     */
    private String indexName;

    /**
     * 索引映射
     */
    private Map<String, String> mapping;

    /**
     * 索引别名
     */
    private String indexAlias;

    /**
     * 超时时间(秒)
     */
    private Long timeout;

    /**
     * 主节点超时时间(秒)
     */
    private Long masterTimeout;

}
