package com.ah.cloud.permissions.workflow.domain.model.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-06-09 00:09
 **/
@Getter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class ModelEditorSourceDTO {
    /**
     * 模型名称
     */
    private String modelId;

    /**
     * 模型bpmn的xml字符串
     */
    private String modelBpmnXml;

}
