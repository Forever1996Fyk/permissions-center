package com.ah.cloud.permissions.elsticsearch.application.checker;

import com.ah.cloud.permissions.biz.infrastructure.exception.BizException;
import com.ah.cloud.permissions.biz.infrastructure.util.CollectionUtils;
import com.ah.cloud.permissions.elsticsearch.domain.index.dto.CreateIndexDTO;
import com.ah.cloud.permissions.elsticsearch.domain.index.dto.DeleteIndexDTO;
import com.ah.cloud.permissions.elsticsearch.domain.index.dto.GetIndexRequestDTO;
import com.ah.cloud.permissions.enums.common.ElasticSearchErrorCodeEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-08-25 15:13
 **/
public class ElasticSearchIndexChecker {

    /**
     * 校验创建索引参数
     * @param createIndexDTO
     */
    public static void checkCreateIndexParam(CreateIndexDTO createIndexDTO) {
        if (Objects.isNull(createIndexDTO)) {
            throw new BizException(ElasticSearchErrorCodeEnum.ES_CREATE_INDEX_FAILED_PARAM_IS_NULL);
        }
        if (StringUtils.isBlank(createIndexDTO.getIndexName())) {
            throw new BizException(ElasticSearchErrorCodeEnum.ES_INDEX_NAME_IS_NULL);
        }
    }

    /**
     * 校验获取索引信息参数
     * @param getIndexRequestDTO
     */
    public static void checkGetIndexParam(GetIndexRequestDTO getIndexRequestDTO) {
        if (StringUtils.isBlank(getIndexRequestDTO.getIndexName())) {
            throw new BizException(ElasticSearchErrorCodeEnum.ES_INDEX_NAME_IS_NULL);
        }
    }

    /**
     * 校验删除索引信息参数
     * @param deleteIndexDTO
     */
    public static void checkDeleteIndexParam(DeleteIndexDTO deleteIndexDTO) {
        if (StringUtils.isBlank(deleteIndexDTO.getIndexName())) {
            throw new BizException(ElasticSearchErrorCodeEnum.ES_INDEX_NAME_IS_NULL);
        }
    }

}
