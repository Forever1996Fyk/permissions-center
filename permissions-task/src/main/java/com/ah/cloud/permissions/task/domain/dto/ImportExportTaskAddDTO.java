package com.ah.cloud.permissions.task.domain.dto;

import com.ah.cloud.permissions.task.domain.enums.ImportExportBizTypeEnum;
import com.ah.cloud.permissions.task.domain.enums.ImportExportTaskTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-13 16:33
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImportExportTaskAddDTO {

    /**
     * 业务类型
     */
    private ImportExportBizTypeEnum bizTypeEnum;

    /**
     * 任务类型
     */
    private ImportExportTaskTypeEnum taskTypeEnum;

    /**
     * 导出参数
     */
    private String params;

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 文件地址
     */
    private String fileUrl;

    /**
     * 任务创建人
     */
    private String creator;

    /**
     * 任务创建人id
     */
    private Long creatorId;

}
