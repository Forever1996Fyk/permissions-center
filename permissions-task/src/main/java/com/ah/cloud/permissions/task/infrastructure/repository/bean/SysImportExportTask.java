package com.ah.cloud.permissions.task.infrastructure.repository.bean;

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
 * 导入导出任务表
 * </p>
 *
 * @author auto_generation
 * @since 2022-07-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_import_export_task")
public class SysImportExportTask implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 任务号
     */
    private String taskNo;

    /**
     * 任务状态 1 - 待处理 2 - 处理中 3 - 处理成功 4 - 处理失败 5 - 部分成功
     */
    private Integer status;

    /**
     * 参数
     */
    private String param;

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 文件地址
     */
    private String fileUrl;

    /**
     * 关联文件名称
     */
    private String refFileName;

    /**
     * 关联文件地址
     */
    private String refFileUrl;

    /**
     * 任务类型 1 - 导入， 2 - 导出
     */
    private Integer taskType;

    /**
     * 详细业务类型
     */
    private String bizType;

    /**
     * 错误原因
     */
    private String errorReason;

    /**
     * 任务开始时间
     */
    private Date beginTime;

    /**
     * 任务结束时间
     */
    private Date finishTime;

    /**
     * 环境标示
     */
    private String env;

    /**
     * 操作人
     */
    private String creator;

    /**
     * 操作人id
     */
    private Long creatorId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date modifyTime;

    /**
     * 版本号
     */
    @Version
    private Integer version;

    /**
     * 更新人
     */
    private String modifier;


}
