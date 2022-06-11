package com.ah.cloud.permissions.workflow.domain.model.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-06-09 00:09
 **/
@Data
public class ModelEditorSourceForm {
    /**
     * 模型名称
     */
    @NotEmpty(message = "模型id")
    private String modelId;

    /**
     * 模型bpmn的xml字符串
     */
    @NotEmpty(message = "bpmn文件不能为空")
    private String modelBpmnXml;

}
