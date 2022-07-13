package com.ah.cloud.permissions.edi.application.adapter;

import com.ah.cloud.permissions.edi.domain.config.dto.RetryBizConfigCacheDTO;
import com.ah.cloud.permissions.edi.domain.config.dto.RetryBizConfigDTO;
import com.ah.cloud.permissions.edi.domain.config.form.AddCfgBizRetryForm;
import com.ah.cloud.permissions.edi.domain.config.form.UpdateCfgBizRetryForm;
import com.ah.cloud.permissions.edi.domain.config.query.CfgBizRetryQuery;
import com.ah.cloud.permissions.enums.edi.EdiTypeEnum;

import java.util.List;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-05 11:36
 **/
public interface RetryBizConfigAdapterService {

    /**
     * 添加新的重试配置
     * @param form
     */
    void addBizRetryConfig(AddCfgBizRetryForm form);

    /**
     * 更新重试配置
     *
     * @param form
     */
    void updateBizRetryConfig(UpdateCfgBizRetryForm form);

    /**
     * 启用重试配置
     * @param id
     * @param isTech
     */
    void startBizRetryConfig(Long id, boolean isTech);

    /**
     * 停用重试配置
     * @param id
     * @param isTech
     */
    void stopBizRetryConfig(Long id, boolean isTech);



    /**
     * 查询缓存的重试配置
     * @param bizType
     * @param ediTypeEnum
     * @return
     */
    RetryBizConfigCacheDTO getRetryBizConfigByLocalCache(Integer bizType, EdiTypeEnum ediTypeEnum);

    /**
     * 查询重试配置集合
     *
     * @param query
     * @return
     */
    List<RetryBizConfigDTO> listRetryBizConfig(CfgBizRetryQuery query);
}
