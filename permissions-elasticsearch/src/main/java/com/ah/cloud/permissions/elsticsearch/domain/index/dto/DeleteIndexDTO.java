package com.ah.cloud.permissions.elsticsearch.domain.index.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
public class DeleteIndexDTO {

    /**
     * 索引名称
     */
    private String indexName;

}
