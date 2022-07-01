package com.ah.cloud.permissions.domain.dto;

/**
 * @program: permissions-center
 * @description: 通用的下拉选择 接口
 * @author: YuKai Fan
 * @create: 2022-07-01 14:23
 **/
public interface SelectEntity {

    /**
     * 选择实体编码
     * @return
     */
    String getCode();

    /**
     * 选择实体名称
     * @return
     */
    String getName();
}
