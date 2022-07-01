package com.ah.cloud.permissions.workflow.domain.task.query;

import com.ah.cloud.permissions.domain.common.PageQuery;
import lombok.Data;

import java.util.Set;

/**
 * @program: permissions-center
 * @description: 任务查询
 * @author: YuKai Fan
 * @create: 2022-06-17 15:36
 **/
@Data
public class MyTaskQuery extends PageQuery {

    /**
     * 任务发起人id
     */
    private Set<Long> taskSponsorIdSet;

    /**
     * 是否组任务
     */
    private boolean isGroup;


    /**
     * 流程名称
     */
    private String name;
}
