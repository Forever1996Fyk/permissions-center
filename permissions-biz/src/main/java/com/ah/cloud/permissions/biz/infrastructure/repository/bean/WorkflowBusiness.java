package com.ah.cloud.permissions.biz.infrastructure.repository.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 工作流业务表
 * </p>
 *
 * @author auto_generation
 * @since 2022-06-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("workflow_business")
public class WorkflowBusiness implements Serializable {

    private static final long serialVersionUID = 1L;

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
     * 业务参数json
     */
    private String businessParamJson;

    /**
     * 业务key
     */
    private String businessKey;


    /**
     * 申请人id
     */
    private Long proposerId;

    /**
     * 申请人
     */
    private String proposer;

    /**
     * 流程状态(启动  撤回  驳回  审批中  审批通过  审批异常)
     */
    private Integer flowStatus;

    /**
     * 处理人
     */
    private String handler;

    /**
     * 当前节点id
     */
    private String taskId;

    /**
     * 当前节点名称
     */
    private String taskName;


    /**
     * 开始时间
     */
    private Date startTime;

    /**
     * 结束时间
     */
    private Date endTime;

    /**
     * 租户id
     */
    private String tenantId;

    /**
     * 行记录创建者
     */
    private String creator;

    /**
     * 行记录最近更新人
     */
    private String modifier;

    /**
     * 行记录创建时间
     */
    private Date createdTime;

    /**
     * 行记录最近修改时间
     */
    private Date modifiedTime;

    /**
     * 行版本号
     */
    @Version
    private Integer version;

    /**
     * 拓展字段
     */
    private String extension;

    /**
     * 是否删除
     */
    private Long deleted;


}
