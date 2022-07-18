package com.ah.cloud.permissions.task.domain.dto;

import com.ah.cloud.permissions.task.domain.enums.ImportExportBizTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.InputStream;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-14 15:53
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DataImportDTO {

    /**
     * 业务类型
     */
    private ImportExportBizTypeEnum bizTypeEnum;

    /**
     * 文件流
     */
    private InputStream inputStream;
}
