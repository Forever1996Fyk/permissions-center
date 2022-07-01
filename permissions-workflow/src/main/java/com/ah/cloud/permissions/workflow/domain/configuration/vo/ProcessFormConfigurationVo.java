package com.ah.cloud.permissions.workflow.domain.configuration.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-06-15 17:21
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProcessFormConfigurationVo {
    /**
     * 主键id
     */
    private Long id;

    /**
     * 流程id
     */
    private Long workflowProcessId;

    /**
     * 流程定义id
     */
    private String processDefinitionId;

    /**
     * 任务定义key
     */
    private String taskKey;

    /**
     * 表单编码
     */
    private String formCode;

    /**
     * 业务标题表达式
     */
    private String businessTitleExpression;

    /**
     * 租户id
     */
    private String tenantId;
}
