package com.ah.cloud.permissions.workflow.domain.instance.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-06-10 16:20
 **/
@Data
public class StartProcessForm {

    /**
     * 流程部署id
     */
    @NotEmpty(message = "流程部署id不能为空")
    private String deploymentId;

    /**
     * 启动名称
     */
    private String processName;

    /**
     * 业务key
     */
    private String businessKey;
}
