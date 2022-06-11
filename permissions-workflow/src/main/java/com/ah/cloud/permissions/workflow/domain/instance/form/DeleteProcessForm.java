package com.ah.cloud.permissions.workflow.domain.instance.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-06-10 17:43
 **/
@Data
public class DeleteProcessForm {

    /**
     * 流程部署id
     */
    @NotEmpty(message = "流程部署id不能为空")
    private String processInstanceId;

    /**
     * 流程实例原因
     */
    @NotEmpty(message = "删除流程原因不能为空")
    private String reason;
}
