package com.ah.cloud.permissions.workflow.domain.model.dto;

import com.ah.cloud.permissions.biz.infrastructure.annotation.EnumValid;
import com.ah.cloud.permissions.enums.workflow.WorkflowTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-06-13 14:09
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeployModelDTO {

    /**
     * 流程模型id
     */
    private String modelId;

    /**
     * 部署名称
     */
    private String deployName;

    /**
     * 流程类型
     */
    private String category;
}
