
package com.ah.cloud.permissions.biz.application.strategy.quartz;

import com.ah.cloud.permissions.biz.domain.quartz.dto.ScheduleJobDTO;
import com.ah.cloud.permissions.biz.infrastructure.repository.bean.QuartzJob;
import com.ah.cloud.permissions.enums.JobStatusEnum;
import com.ah.cloud.permissions.enums.QuartzJobChangeTypeEnum;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-05-11 11:50
 **/
@Component
public class ChangeStatusQuartzJobChangeService extends AbstractQuartzJobChangeService {

    @Override
    protected void doHandleChange(QuartzJob quartzJob) {
        ScheduleJobDTO scheduleJobDTO = quartzJobHelper.convertScheduleJob(quartzJob);
        if (Objects.equals(quartzJob.getStatus(), JobStatusEnum.NORMAL.getStatus())) {
            schedulerManager.resumeJob(scheduleJobDTO);
        } else if (Objects.equals(quartzJob.getStatus(), JobStatusEnum.PAUSE.getStatus())) {
            schedulerManager.pauseJob(scheduleJobDTO);
        }
    }

    @Override
    public QuartzJobChangeTypeEnum getQuartzJobChangeTypeEnum() {
        return QuartzJobChangeTypeEnum.CHANGE_STATUS;
    }
}
