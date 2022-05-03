package com.ah.cloud.permissions.biz.application.checker;

import com.ah.cloud.permissions.biz.domain.quartz.form.QuartzJobAddForm;
import com.ah.cloud.permissions.biz.domain.quartz.form.QuartzJobUpdateForm;
import com.ah.cloud.permissions.biz.infrastructure.exception.BizException;
import com.ah.cloud.permissions.biz.infrastructure.util.CronUtils;
import com.ah.cloud.permissions.enums.common.ErrorCodeEnum;
import org.springframework.stereotype.Component;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-04-30 10:55
 **/
@Component
public class QuartzJobChecker {

    /**
     * 校验参数
     * @param form
     */
    public void checkParams(QuartzJobAddForm form) {
        if (!CronUtils.isValid(form.getCronExpression())) {
            throw new BizException(ErrorCodeEnum.QUARTZ_CRON_EXPRESSION_ERROR, form.getCronExpression());
        }
    }

    /**
     * 校验参数
     * @param form
     */
    public void checkParams(QuartzJobUpdateForm form) {
        if (!CronUtils.isValid(form.getCronExpression())) {
            throw new BizException(ErrorCodeEnum.QUARTZ_CRON_EXPRESSION_ERROR, form.getCronExpression());
        }
    }
}
