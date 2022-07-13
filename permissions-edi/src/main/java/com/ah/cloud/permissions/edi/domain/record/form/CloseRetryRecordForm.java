package com.ah.cloud.permissions.edi.domain.record.form;

import com.ah.cloud.permissions.biz.infrastructure.annotation.EnumValid;
import com.ah.cloud.permissions.enums.edi.EdiTypeEnum;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-08 18:35
 **/
@Data
public class CloseRetryRecordForm {

    /**
     * 重试id
     */
    @NotNull(message = "重试id不能为空")
    private Long id;

    /**
     * 业务类型
     */
    @NotNull(message = "业务类型不能为空")
    private Integer bizType;

    /**
     * 开始时间
     */
    @NotEmpty(message = "开始时间不能为空")
    private String startTime;

    /**
     * edi类型
     */
    @EnumValid(enumClass = EdiTypeEnum.class, enumMethod = "isValid")
    private Integer ediType;

    /**
     * 备注
     */
    private String remark;
}
