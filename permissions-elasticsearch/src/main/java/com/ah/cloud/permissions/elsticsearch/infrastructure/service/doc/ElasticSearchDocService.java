package com.ah.cloud.permissions.elsticsearch.infrastructure.service.doc;

import com.ah.cloud.permissions.elsticsearch.domain.doc.dto.*;
import com.ah.cloud.permissions.elsticsearch.domain.index.dto.GetIndexRequestDTO;
import org.apache.poi.ss.formula.functions.T;

import java.util.Collection;

/**
 * @program: permissions-center
 * @description: es 文档操作接口
 * @author: YuKai Fan
 * @create: 2022-08-25 17:21
 **/
public interface ElasticSearchDocService {

    /**
     * 创建文档
     * @param createDocRequestDTO
     * @param <T>
     */
    <T> void createDoc(CreateDocRequestDTO<T> createDocRequestDTO);

    /**
     * 修改文档
     * @param updateDocRequestDTO
     * @param <T>
     */
    <T> void updateDoc(UpdateDocRequestDTO<T> updateDocRequestDTO);

    /**
     * 删除文档
     * @param deleteDocRequestDTO
     */
    void deleteDoc(DeleteDocRequestDTO deleteDocRequestDTO);

    /**
     * 批量处理文档
     * @param indexName
     * @param bulkDocRequestDTO
     * @param <T>
     */
    void bulkDoc(BulkDocRequestDTO bulkDocRequestDTO);
}
