package com.ah.cloud.permissions.task.domain.form;

import com.ah.cloud.permissions.biz.infrastructure.annotation.EnumValid;
import com.ah.cloud.permissions.task.domain.enums.ImportExportBizTypeEnum;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-13 16:12
 **/
@Data
public class DataImportForm {

    /**
     * 导出业务类型
     */
    @EnumValid(enumClass = ImportExportBizTypeEnum.class, enumMethod = "isImportValid")
    private String bizType;

    /**
     * 文件地址
     */
    @NotEmpty(message = "文件地址不能为空")
    private String fileUrl;

    /**
     * 文件名称
     */
    private String fileName;
}
