package com.ah.cloud.permissions.workflow.domain.process.form;

import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-06-16 16:08
 **/
@Data
public class WorkflowProcessDeployForm {

    /**
     * 流程主键
     */
    @NotNull(message = "主键不能为空")
    private Long id;

    /**
     * 部署名称
     */
    @NotEmpty(message = "部署名称不能为空")
    private String deployName;
}
