package com.ah.cloud.permissions.workflow.domain.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-06-08 16:58
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateModelDTO {

    /**
     * 模型名称
     */
    private String modelName;

    /**
     * 模型key
     */
    private String modelKey;

    /**
     * 模型元信息
     */
    private ModelMetaInfoDTO metaInfo;
}
