package com.ah.cloud.permissions.workflow.domain.model.form;

import com.ah.cloud.permissions.workflow.domain.model.dto.ModelMetaInfoDTO;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-06-08 18:01
 **/
@Data
public class ModelAddForm {
    /**
     * 模型名称
     */
    @NotEmpty(message = "模型名称不能为空")
    private String modelName;

    /**
     * 模型key
     */
    @NotEmpty(message = "模型key不能为空")
    private String modelKey;

    /**
     * 模型描述
     */
    private String modelDesc;

    /**
     * 模型版本
     */
    private Integer version;
}
