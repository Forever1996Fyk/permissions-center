package com.ah.cloud.permissions.server.config;

import com.ah.cloud.permissions.biz.application.helper.QuartzJobHelper;
import com.ah.cloud.permissions.biz.application.manager.SchedulerManager;
import com.ah.cloud.permissions.biz.application.service.QuartzJobService;
import com.ah.cloud.permissions.biz.infrastructure.repository.bean.QuartzJob;
import com.ah.cloud.permissions.enums.common.DeletedEnum;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.quartz.SchedulerException;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-06-01 18:44
 **/
@Component
public class QuartzConfig {
    @Resource
    private QuartzJobHelper quartzJobHelper;
    @Resource
    private QuartzJobService quartzJobService;
    @Resource
    private SchedulerManager schedulerManager;

    /**
     * 项目启动时，初始化定时器 主要是防止手动修改数据库导致未同步到定时任务处理（注：不能手动修改数据库ID和任务组名，否则会导致脏数据）
     */
    @PostConstruct
    public void init() throws SchedulerException {
        schedulerManager.clearAllJob();
        List<QuartzJob> quartzJobList = quartzJobService.list(
                        new QueryWrapper<QuartzJob>().lambda()
                            .eq(QuartzJob::getDeleted, DeletedEnum.NO.value)
        );
        for (QuartzJob quartzJob : quartzJobList) {
            schedulerManager.createScheduleJob(quartzJobHelper.convertScheduleJob(quartzJob));
        }
    }

}
