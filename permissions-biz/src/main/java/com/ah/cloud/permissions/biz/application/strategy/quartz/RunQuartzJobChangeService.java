
package com.ah.cloud.permissions.biz.application.strategy.quartz;

import com.ah.cloud.permissions.biz.domain.quartz.dto.QuartzJobChangeDTO;
import com.ah.cloud.permissions.biz.infrastructure.repository.bean.QuartzJob;
import com.ah.cloud.permissions.enums.QuartzJobChangeTypeEnum;
import org.springframework.stereotype.Component;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-05-11 11:50
 **/
@Component
public class RunQuartzJobChangeService extends AbstractQuartzJobChangeService {

    @Override
    protected void doHandleChange(QuartzJob quartzJob) {
        schedulerManager.triggerJob(quartzJobHelper.convertScheduleJob(quartzJob));
    }

    @Override
    public QuartzJobChangeTypeEnum getQuartzJobChangeTypeEnum() {
        return QuartzJobChangeTypeEnum.RUN;
    }
}
