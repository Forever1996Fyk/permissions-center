package com.ah.cloud.permissions.workflow.domain.business.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: permissions-center
 * @description: 用户提交的流程
 * @author: YuKai Fan
 * @create: 2022-06-17 15:03
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserSubmitProcessVo {
    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 业务名称
     */
    private String businessName;

    /**
     * 流程定义key
     */
    private String processDefinitionKey;

    /**
     * 流程定义id
     */
    private String processDefinitionId;

    /**
     * 流程实例id
     */
    private String processInstanceId;

    /**
     * 流程id
     */
    private Long processId;

    /**
     * 流程状态(启动  撤回  驳回  审批中  审批通过  审批异常)
     */
    private Integer flowStatus;

    /**
     * 当前审批的节点
     */
    private String taskName;

    /**
     * 开始时间
     */
    private String startTime;

    /**
     * 结束时间
     */
    private String endTime;
}
