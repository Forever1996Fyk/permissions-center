package com.ah.cloud.permissions.elsticsearch.infrastructure.service.index;

import com.ah.cloud.permissions.elsticsearch.domain.index.dto.CreateIndexDTO;
import com.ah.cloud.permissions.elsticsearch.domain.index.dto.DeleteIndexDTO;
import com.ah.cloud.permissions.elsticsearch.domain.index.dto.GetIndexRequestDTO;
import com.ah.cloud.permissions.elsticsearch.domain.index.dto.GetIndexResponseDTO;

/**
 * @program: permissions-center
 * @description: 索引操作接口
 * @author: YuKai Fan
 * @create: 2022-08-25 14:16
 **/
public interface ElasticSearchIndexService {

    /**
     * 创建es索引
     * @param createIndexDTO
     * @return
     */
    boolean createEsIndex(CreateIndexDTO createIndexDTO);

    /**
     * 获取es索引
     * @param requestDTO
     * @return
     */
    GetIndexResponseDTO getEsIndex(GetIndexRequestDTO requestDTO);

    /**
     * 删除es索引
     * @param deleteIndexDTO
     * @return
     */
    boolean deleteEsIndex(DeleteIndexDTO deleteIndexDTO);

    /**
     * 索引是否存在
     * @param indexName
     * @return
     */
    boolean existsEsIndex(String indexName);
}
