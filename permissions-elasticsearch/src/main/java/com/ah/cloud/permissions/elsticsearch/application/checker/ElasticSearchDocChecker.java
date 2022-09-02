package com.ah.cloud.permissions.elsticsearch.application.checker;

import com.ah.cloud.permissions.elsticsearch.domain.doc.dto.BulkDocRequestDTO;
import com.ah.cloud.permissions.elsticsearch.domain.doc.dto.CommonDocRequest;
import com.ah.cloud.permissions.elsticsearch.domain.doc.dto.CreateDocRequestDTO;
import com.ah.cloud.permissions.elsticsearch.infrastructure.exception.ElasticSearchDocException;
import com.ah.cloud.permissions.enums.common.ElasticSearchErrorCodeEnum;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.formula.functions.T;

import java.util.Collection;
import java.util.Objects;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-09-01 14:42
 **/
public class ElasticSearchDocChecker {

    /**
     * 文档通用参数校验
     *
     * @param commonDocRequest
     */
    public static void checkCommonDocParam(CommonDocRequest commonDocRequest) {

        if (StringUtils.isBlank(commonDocRequest.getIndexName())) {
            throw new ElasticSearchDocException(ElasticSearchErrorCodeEnum.ES_INDEX_NAME_IS_NULL);
        }

        if (StringUtils.isBlank(commonDocRequest.getId())) {
            throw new ElasticSearchDocException(ElasticSearchErrorCodeEnum.ES_INDEX_DOC_ID_IS_NULL);
        }
    }

    /**
     * 批处理文档参数校验
     * @param bulkDocRequestDTO
     */
    public static void checkBulkDocParam(BulkDocRequestDTO bulkDocRequestDTO) {
    }
}
