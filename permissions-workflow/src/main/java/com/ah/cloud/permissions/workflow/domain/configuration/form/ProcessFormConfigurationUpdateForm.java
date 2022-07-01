package com.ah.cloud.permissions.workflow.domain.configuration.form;

import com.ah.cloud.permissions.biz.infrastructure.annotation.EnumValid;
import com.ah.cloud.permissions.enums.workflow.FormProcessTypeEnum;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-06-15 17:03
 **/
@Data
public class ProcessFormConfigurationUpdateForm {

    /**
     * 配置id
     */
    @NotNull(message = "配置id不能为空")
    private Long id;

    /**
     * 任务key
     */
    @NotEmpty(message = "任务key不能为空")
    private String taskKey;

    /**
     * 业务标题表达式
     */
    private String businessTitleExpression;

    /**
     * 租户id
     */
    private String tenantId;
}
