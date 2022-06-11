package com.ah.cloud.permissions.workflow.domain.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-06-08 17:24
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ModelMetaInfoDTO {


    /**
     * 模型描述
     */
    private String modelDesc;

    /**
     * 模型版本
     */
    private Integer version;
}
