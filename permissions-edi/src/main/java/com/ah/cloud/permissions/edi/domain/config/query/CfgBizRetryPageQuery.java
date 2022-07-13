package com.ah.cloud.permissions.edi.domain.config.query;

import com.ah.cloud.permissions.domain.common.PageQuery;
import com.ah.cloud.permissions.edi.domain.config.RetryConfigAdapter;
import lombok.Data;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-05 12:04
 **/
@Data
public class CfgBizRetryPageQuery extends PageQuery implements RetryConfigAdapter {

    /**
     * 业务名称
     */
    private String bizName;

    /**
     * 业务类型
     */
    private Integer bizType;

    /**
     * 是否tech
     */
    private boolean isTech;
}
