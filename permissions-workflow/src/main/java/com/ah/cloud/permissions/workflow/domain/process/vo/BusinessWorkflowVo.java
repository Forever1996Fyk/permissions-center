package com.ah.cloud.permissions.workflow.domain.process.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @program: permissions-center
 * @description: 业务工作流
 * @author: YuKai Fan
 * @create: 2022-06-16 17:14
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BusinessWorkflowVo {
    /**
     * 流程类别
     */
    private String processCategory;

    /**
     * 流程详情
     */
    private List<BusinessWorkflowDetailVo> detailVoList;
}
