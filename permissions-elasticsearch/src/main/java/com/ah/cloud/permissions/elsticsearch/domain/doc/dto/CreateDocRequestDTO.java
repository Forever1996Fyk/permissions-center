package com.ah.cloud.permissions.elsticsearch.domain.doc.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
public class CreateDocRequestDTO<T> implements CommonDocRequest {

    /**
     * 索引名称
     */
    private String indexName;

    /**
     * 文档主键
     */
    private String id;

    /**
     * 文档实体
     */
    private T sourceObject;
}
