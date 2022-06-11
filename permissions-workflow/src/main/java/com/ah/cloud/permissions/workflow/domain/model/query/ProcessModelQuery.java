package com.ah.cloud.permissions.workflow.domain.model.query;

import com.ah.cloud.permissions.domain.common.PageQuery;
import lombok.Data;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-06-08 22:02
 **/
@Data
public class ProcessModelQuery extends PageQuery {

    /**
     * 名称
     */
    private String name;

    /**
     * key
     */
    private String key;
}
