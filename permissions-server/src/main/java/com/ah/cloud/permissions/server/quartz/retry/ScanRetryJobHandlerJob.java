package com.ah.cloud.permissions.server.quartz.retry;

import com.ah.cloud.permissions.biz.infrastructure.quartz.TaskContext;
import com.ah.cloud.permissions.biz.infrastructure.quartz.core.processor.BasicProcessor;
import com.ah.cloud.permissions.biz.infrastructure.util.DateUtils;
import com.ah.cloud.permissions.edi.domain.common.EdiResult;
import com.ah.cloud.permissions.edi.infrastructure.service.RetryRecordService;
import com.ah.cloud.permissions.edi.infrastructure.service.impl.EdiRetryRecordService;
import com.ah.cloud.permissions.edi.infrastructure.service.impl.TechEdiRetryRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-11 16:06
 **/
@Slf4j
@Component
public class ScanRetryJobHandlerJob implements BasicProcessor {
    @Resource
    private EdiRetryRecordService ediRetryRecordService;

    @Override
    public void process(TaskContext context) {
        String dateMonth = DateUtils.format(new Date(), DateUtils.pattern5);
        log.info("ScanRetryJobHandlerJob start handle, dateMonth is {}, jobId is {}, taskId is {}", dateMonth, context.getJobId(), context.getTaskId());
        EdiResult<Void> result = ediRetryRecordService.scanRetry(dateMonth, null, null, 0, 1);
        log.info("ScanRetryJobHandlerJob end handle, result is {}", result);
    }
}
