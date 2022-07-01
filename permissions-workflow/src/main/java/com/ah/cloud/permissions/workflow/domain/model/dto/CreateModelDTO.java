package com.ah.cloud.permissions.workflow.domain.model.dto;

import com.ah.cloud.permissions.biz.infrastructure.annotation.EnumValid;
import com.ah.cloud.permissions.enums.workflow.BusinessCategoryEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

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
     * 模型描述
     */
    private String modelDesc;

    /**
     * 流程类别
     */
    private BusinessCategoryEnum processCategory;
}
