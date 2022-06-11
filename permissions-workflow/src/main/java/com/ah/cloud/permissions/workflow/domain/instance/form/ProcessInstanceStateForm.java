package com.ah.cloud.permissions.workflow.domain.instance.form;

import com.ah.cloud.permissions.biz.infrastructure.annotation.EnumValid;
import com.ah.cloud.permissions.enums.SysApiTypeEnum;
import com.ah.cloud.permissions.enums.workflow.ProcessInstanceStateEnum;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-06-10 16:20
 **/
@Data
public class ProcessInstanceStateForm {

    /**
     * 流程实例id
     */
    @NotEmpty(message = "流程实例id不能为空")
    private String processInstanceId;

    /**
     * 状态
     */
    @EnumValid(enumClass = ProcessInstanceStateEnum.class, enumMethod = "isValid")
    private Integer state;
}
