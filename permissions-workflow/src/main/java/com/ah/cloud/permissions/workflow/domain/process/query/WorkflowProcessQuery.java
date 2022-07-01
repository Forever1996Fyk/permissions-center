package com.ah.cloud.permissions.workflow.domain.process.query;

import com.ah.cloud.permissions.domain.common.PageQuery;
import lombok.Data;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-06-16 15:43
 **/
@Data
public class WorkflowProcessQuery extends PageQuery {

    /**
     * 流程名称
     */
    private String name;


    /**
     * 流程key
     */
    private String key;

}
