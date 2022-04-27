package com.ah.cloud.permissions.biz.domain.cfg.threadpool.query;

import com.ah.cloud.permissions.domain.common.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-04-27 16:01
 **/
@Data
@EqualsAndHashCode(callSuper = true)
public class CfgThreadPoolQuery extends PageQuery {

    /**
     * 线程池名称
     */
    private String name;
}
