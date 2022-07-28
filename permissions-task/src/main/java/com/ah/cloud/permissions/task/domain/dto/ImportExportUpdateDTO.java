package com.ah.cloud.permissions.task.domain.dto;

import com.ah.cloud.permissions.task.domain.enums.ImportExportTaskStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-13 18:08
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImportExportUpdateDTO {

    /**
     * 任务id
     */
    private Long taskId;

    /**
     * 版本
     */
    private Integer version;

    /**
     * 更新状态
     */
    private ImportExportTaskStatusEnum updateStatus;

    /**
     * 文件名称
     */
    private String refFileName;
    /**
     * 文件路径
     */
    private String refFileUrl;

    /**
     * 文件名
     */
    private String fileName;

    /**
     * 文件地址
     */
    private String fileUrl;

    /**
     * 错误原因
     */
    private String errorReason;
    /**
     * 开始时间
     */
    private Date beginTime;
    /**
     * 完成时间
     */
    private Date finishTime;

    /**
     * 更新人
     */
    private String modifier;

    /**
     * 重试次数
     */
    private Integer retryTimes;
}
