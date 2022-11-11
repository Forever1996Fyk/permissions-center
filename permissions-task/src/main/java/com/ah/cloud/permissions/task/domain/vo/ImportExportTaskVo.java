package com.ah.cloud.permissions.task.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.util.Date;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-14 14:34
 **/
@Getter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class ImportExportTaskVo {

    /**
     * 任务编号
     */
    private String taskNo;

    /**
     * 业务类型
     */
    private String bizType;

    /**
     * 开始时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date beginTime;

    /**
     * 结束时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date finishTime;

    /**
     * 任务状态
     */
    private Integer status;

    /**
     * 任务状态
     */
    private String statusName;

    /**
     * 创建人
     */
    private String creator;

    /**
     * 文件名
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
     * 错误原因
     */
    private String errorReason;
}
