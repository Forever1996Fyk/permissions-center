package com.ah.cloud.permissions.task.domain.form;

import com.ah.cloud.permissions.biz.infrastructure.annotation.EnumValid;
import com.ah.cloud.permissions.task.domain.enums.ImportExportBizTypeEnum;
import lombok.Data;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-13 16:12
 **/
@Data
public class DataExportForm {

    /**
     * 导出业务类型
     */
    @EnumValid(enumClass = ImportExportBizTypeEnum.class, enumMethod = "isExportValid")
    private String bizType;

    /**
     * 导出参数
     */
    private String params;
}
