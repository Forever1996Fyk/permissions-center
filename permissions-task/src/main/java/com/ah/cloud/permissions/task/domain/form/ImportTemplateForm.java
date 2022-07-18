package com.ah.cloud.permissions.task.domain.form;

import com.ah.cloud.permissions.biz.infrastructure.annotation.EnumValid;
import com.ah.cloud.permissions.task.domain.enums.ImportExportBizTypeEnum;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-14 18:03
 **/
@Data
public class ImportTemplateForm {

    /**
     * 模板名称
     */
    @NotEmpty(message = "模板名称不能为空")
    private String templateName;

    /**
     * 模板id
     */
    @NotEmpty(message = "模板url不能为空")
    private String templateUrl;

    /**
     * 业务类型
     */
    @EnumValid(enumClass = ImportExportBizTypeEnum.class, enumMethod = "isImportValid")
    private String bizType;
}
