package com.ah.cloud.permissions.workflow.domain.instance.query;

import com.ah.cloud.permissions.domain.common.PageQuery;
import lombok.Data;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-06-10 17:59
 **/
@Data
public class InstanceQuery extends PageQuery {

    /**
     * 流程实例状态
     */
    private Integer state;

    /**
     * 实例名称
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
}
