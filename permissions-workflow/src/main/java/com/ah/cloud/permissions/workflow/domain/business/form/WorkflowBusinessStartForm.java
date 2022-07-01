package com.ah.cloud.permissions.workflow.domain.business.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.Map;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-06-17 09:46
 **/
@Data
public class WorkflowBusinessStartForm {

    /**
     * 流程id
     */
    @NotEmpty(message = "流程id不能为空")
    private Long processId;

    /**
     * 流程变量
     */
    private Map<String, Object> variables;

}
