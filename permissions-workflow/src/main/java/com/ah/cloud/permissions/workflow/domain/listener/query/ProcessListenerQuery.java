package com.ah.cloud.permissions.workflow.domain.listener.query;

import com.ah.cloud.permissions.domain.common.PageQuery;
import lombok.Data;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-06-16 11:05
 **/
@Data
public class ProcessListenerQuery extends PageQuery {

    /**
     * 监听器名称
     */
    private String name;

}
