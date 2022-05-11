package com.ah.cloud.permissions.biz.application.strategy.quartz;

import com.ah.cloud.permissions.biz.application.helper.QuartzJobHelper;
import com.ah.cloud.permissions.biz.application.helper.ScheduleHelper;
import com.ah.cloud.permissions.biz.application.manager.SchedulerManager;
import com.ah.cloud.permissions.biz.application.service.QuartzJobService;
import com.ah.cloud.permissions.biz.domain.quartz.dto.QuartzJobChangeDTO;
import com.ah.cloud.permissions.biz.infrastructure.exception.BizException;
import com.ah.cloud.permissions.biz.infrastructure.repository.bean.QuartzJob;
import com.ah.cloud.permissions.enums.common.DeletedEnum;
import com.ah.cloud.permissions.enums.common.ErrorCodeEnum;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-05-11 11:33
 **/
@Slf4j
@Component
public abstract class AbstractQuartzJobChangeService implements QuartzJobChangeService {
    @Resource
    protected QuartzJobHelper quartzJobHelper;
    @Resource
    private QuartzJobService quartzJobService;
    @Resource
    protected SchedulerManager schedulerManager;

    @Override
    public void handleChange(QuartzJobChangeDTO dto) {
        QuartzJob quartzJob = quartzJobService.getOne(
                new QueryWrapper<QuartzJob>().lambda()
                        .eq(QuartzJob::getId, dto.getJobId())
                        .eq(QuartzJob::getDeleted, DeletedEnum.NO.value)
        );
        if (Objects.isNull(quartzJob)) {
            throw new BizException(ErrorCodeEnum.QUARTZ_JOB_NOT_EXIST, String.valueOf(dto.getJobId()));
        }
        doHandleChange(quartzJob);
    }

    /**
     * 具体逻辑处理
     *
     * @param quartzJob
     */
    protected abstract void doHandleChange(QuartzJob quartzJob);
}
