package com.ah.cloud.permissions.server.quartz.retry;

import com.ah.cloud.permissions.biz.infrastructure.quartz.TaskContext;
import com.ah.cloud.permissions.biz.infrastructure.quartz.core.processor.BasicProcessor;
import com.ah.cloud.permissions.edi.infrastructure.alarm.impl.EdiRetryAlarmService;
import com.ah.cloud.permissions.edi.infrastructure.alarm.impl.TechEdiRetryAlarmService;
import com.ah.cloud.permissions.enums.edi.EdiAlarmTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-12 14:11
 **/
@Slf4j
@Component
public class AlarmRetryingResetHandlerJob implements BasicProcessor {
    @Resource
    private EdiRetryAlarmService ediRetryAlarmService;
    @Resource
    private TechEdiRetryAlarmService techEdiRetryAlarmService;

    @Override
    public void process(TaskContext context) {
        ediRetryAlarmService.alarmRetryingReset();
        techEdiRetryAlarmService.alarmRetryingReset();
    }
}
