package com.ah.cloud.permissions.workflow.domain.process.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-06-16 15:44
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkflowProcessVo {

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 流程名称
     */
    private String name;

    /**
     * 流程定义key
     */
    private String processDefinitionKey;

    /**
     * 流程定义id
     */
    private String processDefinitionId;

    /**
     * 流程部署id
     */
    private String processDeployId;

    /**
     * 模型id
     */
    private String modelId;

    /**
     * 表单编码
     */
    private String formCode;

    /**
     * 流程图标
     */
    private String processIcon;

    /**
     * 流程类别
     */
    private String processCategory;

    /**
     * 流程状态
     */
    private Integer status;

    /**
     * 租户id
     */
    private String tenantId;

    /**
     * 行记录创建时间
     */
    private String createdTime;

}
