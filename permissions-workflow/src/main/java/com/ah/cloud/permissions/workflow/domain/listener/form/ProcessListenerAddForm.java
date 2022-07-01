package com.ah.cloud.permissions.workflow.domain.listener.form;

import com.ah.cloud.permissions.biz.infrastructure.annotation.EnumValid;
import com.ah.cloud.permissions.enums.workflow.FormTypeEnum;
import com.ah.cloud.permissions.enums.workflow.ListenerEventEnum;
import com.ah.cloud.permissions.enums.workflow.ListenerExecuteTypeEnum;
import com.ah.cloud.permissions.enums.workflow.ListenerTypeEnum;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-06-16 10:35
 **/
@Data
public class ProcessListenerAddForm {

    /**
     * 监听器名称
     */
    @NotEmpty(message = "监听器名称不能为空")
    private String listenerName;

    /**
     * 监听器类型
     */
    @EnumValid(enumClass = ListenerTypeEnum.class, enumMethod = "isValid")
    private String listenerType;

    /**
     * 事件
     */
    @EnumValid(enumClass = ListenerEventEnum.class, enumMethod = "isValid")
    private String event;

    /**
     * 执行类型
     */
    @EnumValid(enumClass = ListenerExecuteTypeEnum.class, enumMethod = "isValid")
    private String executeType;

    /**
     * 执行内容
     */
    @NotEmpty(message = "执行内容名称不能为空")
    private String executeContent;

    /**
     * 租户id
     */
    private String tenantId;
}
