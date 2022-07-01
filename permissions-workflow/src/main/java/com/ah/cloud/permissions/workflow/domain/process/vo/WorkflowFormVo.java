package com.ah.cloud.permissions.workflow.domain.process.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-06-16 17:50
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkflowFormVo {

    /**
     * 流程id
     */
    private Long processId;

    /**
     * 表单内容
     */
    private String content;
}
