package com.ah.cloud.permissions.workflow.domain.business.query;

import com.ah.cloud.permissions.biz.infrastructure.annotation.EnumValid;
import com.ah.cloud.permissions.domain.common.PageQuery;
import com.ah.cloud.permissions.enums.workflow.WorkFlowStatusEnum;
import lombok.Data;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-06-17 14:58
 **/
@Data
public class UserSubmitProcessQuery extends PageQuery {

    /**
     * 名称
     */
    private String name;

    /**
     * 开始时间
     */
    private String startTime;

    /**
     * 结束时间
     */
    private String endTime;

    /**
     * 流程状态
     */
    @EnumValid(enumClass = WorkFlowStatusEnum.class, enumMethod = "isValid")
    private Integer flowStatus;
}
