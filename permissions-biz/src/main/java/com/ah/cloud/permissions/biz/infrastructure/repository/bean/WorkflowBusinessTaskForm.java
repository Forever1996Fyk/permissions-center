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
 * 工作流业务表单任务关联表
 * </p>
 *
 * @author auto_generation
 * @since 2022-06-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("workflow_business_task_form")
public class WorkflowBusinessTaskForm implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 流程业务id
     */
    private Long workflowBusinessId;

    /**
     * 流程实例id
     */
    private String processInstanceId;

    /**
     * 任务key
     */
    private String taskKey;

    /**
     * 表单名称
     */
    private String name;

    /**
     * 表单编码
     */
    private String code;

    /**
     * 表单配置
     */
    private String config;

    /**
     * 表单类型
     */
    private Integer formType;

    /**
     * 表单内容
     */
    private String content;

    /**
     * 流程参数
     */
    private String variables;

    /**
     * 业务标题表达式
     */
    private String businessTitleExpression;

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
