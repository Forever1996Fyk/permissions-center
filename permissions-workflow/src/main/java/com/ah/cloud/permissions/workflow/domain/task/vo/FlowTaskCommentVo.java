package com.ah.cloud.permissions.workflow.domain.task.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: permissions-center
 * @description: 流程任务意见
 * @author: YuKai Fan
 * @create: 2022-06-20 14:57
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FlowTaskCommentVo {

    /**
     * 意见类型
     */
    private String type;

    /**
     * 内容
     */
    private String comment;
}
