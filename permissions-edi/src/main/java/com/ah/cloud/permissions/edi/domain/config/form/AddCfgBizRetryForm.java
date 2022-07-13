package com.ah.cloud.permissions.edi.domain.config.form;

import com.ah.cloud.permissions.biz.infrastructure.annotation.EnumValid;
import com.ah.cloud.permissions.edi.domain.config.RetryConfigAdapter;
import com.ah.cloud.permissions.enums.edi.BizRetryModelEnum;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-05 11:06
 **/
@Data
public class AddCfgBizRetryForm implements RetryConfigAdapter {

    /**
     * 业务类型
     */
    @NotNull(message = "业务类型不能为空")
    private Integer bizType;

    /**
     * 业务名称
     */
    @NotEmpty(message = "业务名称不能为空")
    private String bizName;

    /**
     * 最大重试次数。0表示不重试，-1无限重试
     *
     * 默认10次
     */
    private Integer maxRetryTimes;

    /**
     * 执行间隔，毫秒为单位
     *
     * 默认6000ms
     */
    private Integer retryInterval;

    /**
     * 0系统自动重试 1手动重试
     */
    @EnumValid(enumClass = BizRetryModelEnum.class, enumMethod = "isValid")
    private Integer retryModel;

    /**
     * 备注
     */
    private String remark;

    /**
     * 是否tech
     */
    private boolean isTech;
}
