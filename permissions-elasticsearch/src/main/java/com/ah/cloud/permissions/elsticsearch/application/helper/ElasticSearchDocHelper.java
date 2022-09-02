package com.ah.cloud.permissions.elsticsearch.application.helper;

import com.ah.cloud.permissions.elsticsearch.domain.doc.dto.BulkDocRequestDTO;
import com.ah.cloud.permissions.elsticsearch.domain.doc.dto.CommonDocRequest;
import com.ah.cloud.permissions.elsticsearch.domain.doc.dto.DeleteDocRequestDTO;
import com.ah.cloud.permissions.elsticsearch.domain.doc.dto.UpdateDocRequestDTO;
import com.ah.cloud.permissions.elsticsearch.infrastructure.exception.ElasticSearchDocException;
import com.ah.cloud.permissions.enums.common.ElasticSearchErrorCodeEnum;
import org.elasticsearch.action.DocWriteRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-09-01 16:08
 **/
public class ElasticSearchDocHelper {

    /**
     * 构建更新请求
     *
     * @param update
     * @param <T>
     * @return
     */
    public static <T> UpdateRequest buildUpdateRequest(UpdateDocRequestDTO<T> update) {
        UpdateRequest request = new UpdateRequest(update.getIndexName(), update.getId());

        // 这里只允许传入可以转为json类型的实体
        request.doc(update.getSourceObject())
                .docAsUpsert(update.getDoAsUpset())
                .id(update.getId())
                .retryOnConflict(update.getRetryOnConflict())
                .timeout(TimeValue.timeValueSeconds(update.getTimeout()));

        if (checkIncludesAndExcludes(update.getIncludes(), update.getExcludes())) {
            request.fetchSource(new FetchSourceContext(true, update.getIncludes(), update.getExcludes()));
        }
        if (update.getVersion() != null) {
            request.version(update.getVersion());
        }
        return  request;
    }

    /**
     * 构建删除请求
     * @param delete
     * @return
     */
    public static DeleteRequest buildDeleteRequest(DeleteDocRequestDTO delete) {
        DeleteRequest request = new DeleteRequest(delete.getIndexName(), delete.getId());
        request.setRefreshPolicy(delete.getRefreshPolicy())
                .versionType(delete.getVersionType())
                .timeout(TimeValue.timeValueSeconds(delete.getTimeout()));
        if (delete.getVersion() != null) {
            request.version(delete.getVersion());
        }
        return request;
    }

    /**
     *
     * @param bulk
     * @return
     */
    public static BulkRequest buildBulkDocParam(BulkDocRequestDTO bulk) {
        BulkRequest request = new BulkRequest(bulk.getIndexName());
        request.setRefreshPolicy(bulk.getRefreshPolicy())
                .timeout(TimeValue.timeValueSeconds(bulk.getTimeout()))
                .waitForActiveShards(bulk.getWaitForActiveShards())
                .add(convertDocWriteRequestList(bulk.getDocRequests()));

        return request;
    }

    public static List<DocWriteRequest<?>> convertDocWriteRequestList(Collection<CommonDocRequest> commonDocRequestList) {
        return commonDocRequestList.stream()
                .map(ElasticSearchDocHelper::convertDocWriteRequest)
                .collect(Collectors.toList());
    }

    private static DocWriteRequest<?> convertDocWriteRequest(CommonDocRequest commonDocRequest) {
        Class<? extends CommonDocRequest> requestClass = commonDocRequest.getClass();
        if (UpdateDocRequestDTO.class.isAssignableFrom(requestClass)) {
            return buildUpdateRequest((UpdateDocRequestDTO<?>) commonDocRequest);
        }

        if (DeleteDocRequestDTO.class.isAssignableFrom(requestClass)) {
            return buildDeleteRequest((DeleteDocRequestDTO) commonDocRequest);
        }
        throw new ElasticSearchDocException(ElasticSearchErrorCodeEnum.ES_OPERATE_DOC_NOT_SUPPORT, commonDocRequest.getIndexName());
    }

    public static boolean checkIncludesAndExcludes(String[] includes, String[] excludes) {
        return (includes != null && includes.length > 0) && (excludes != null && excludes.length > 0);
    }
}
