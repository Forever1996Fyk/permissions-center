package com.ah.cloud.permissions.workflow.domain.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-06-13 15:56
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SelectProcessVo {

    /**
     * 流程定义id
     */
    private String processDefinitionId;

    /**
     * 流程定义key
     */
    private String key;

    /**
     * 流程定义名称
     */
    private String name;
}
