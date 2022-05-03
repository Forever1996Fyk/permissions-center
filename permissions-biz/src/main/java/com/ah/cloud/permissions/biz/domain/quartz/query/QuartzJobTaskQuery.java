package com.ah.cloud.permissions.biz.domain.quartz.query;

import com.ah.cloud.permissions.domain.common.PageQuery;
import lombok.Data;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-05-03 23:23
 **/
@Data
public class QuartzJobTaskQuery extends PageQuery {

    /**
     * 任务id
     */
    private Long jobId;
}
