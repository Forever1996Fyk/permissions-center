package com.ah.cloud.permissions.task.domain.form;

import com.ah.cloud.permissions.biz.infrastructure.annotation.EnumValid;
import com.ah.cloud.permissions.task.domain.enums.ImportExportBizTypeEnum;
import com.ah.cloud.permissions.task.domain.enums.ImportTemplateStatusEnum;
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
public class ImportTemplateChangeStatusForm {

    /**
     * 模板id
     */
    @NotNull(message = "id不能为空")
    private Long id;

    /**
     * 业务类型
     */
    @EnumValid(enumClass = ImportTemplateStatusEnum.class, enumMethod = "isValid")
    private Integer status;
}
