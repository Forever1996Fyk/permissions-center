package com.ah.cloud.permissions.workflow.domain.process.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-06-16 17:21
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BusinessWorkflowDetailVo {
    /**
     * 流程图标
     */
    private String processIcon;

    /**
     * 流程名称
     */
    private String processName;

    /**
     * 流程id
     */
    private Long processId;

    /**
     * 流程类别
     */
    private String processCategory;
}
