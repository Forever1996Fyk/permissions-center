package com.ah.cloud.permissions.biz.domain.quartz.form;

import com.ah.cloud.permissions.biz.infrastructure.annotation.EnumValid;
import com.ah.cloud.permissions.enums.JobStatusEnum;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-05-03 17:40
 **/
@Data
public class QuartzJobChangeStatusForm {

    /**
     * 任务id
     */
    @NotNull(message = "任务id不能为空")
    private Long jobId;

    /**
     * 更改状态
     */
    @EnumValid(message = "状态无效", enumMethod = "isValid", enumClass = JobStatusEnum.class)
    private Integer status;
}
