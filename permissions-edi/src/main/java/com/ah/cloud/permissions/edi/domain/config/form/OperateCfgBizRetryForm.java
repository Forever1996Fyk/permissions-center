package com.ah.cloud.permissions.edi.domain.config.form;

import com.ah.cloud.permissions.edi.domain.config.RetryConfigAdapter;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-05 11:43
 **/
@Data
public class OperateCfgBizRetryForm implements RetryConfigAdapter {

    /**
     * 主键
     */
    @NotNull(message = "id不能为空")
    private Long id;

    /**
     * 是否tech
     */
    private boolean isTech;
}
