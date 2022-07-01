package com.ah.cloud.permissions.workflow.domain.task.form;

import com.ah.cloud.permissions.biz.infrastructure.annotation.EnumValid;
import com.ah.cloud.permissions.enums.workflow.TaskAssignRuleTypeEnum;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-06-20 21:53
 **/
@Data
public class AssignRuleTaskUpdateForm {

    /**
     * 规则id
     */
    @NotNull(message = "规则id不能为空")
    private Long id;

    /**
     * 规则类型
     */
    @EnumValid(enumClass = TaskAssignRuleTypeEnum.class, enumMethod = "isValid")
    private Integer type;

    /**
     * 规则值
     */
    @NotNull(message = "规则值不能为空")
    private Set<String> options;
}
