package com.ah.cloud.permissions.server.quartz.retry;

import com.ah.cloud.permissions.biz.infrastructure.quartz.TaskContext;
import com.ah.cloud.permissions.biz.infrastructure.quartz.core.processor.BasicProcessor;
import com.ah.cloud.permissions.biz.infrastructure.util.DateUtils;
import com.ah.cloud.permissions.edi.domain.common.EdiResult;
import com.ah.cloud.permissions.edi.infrastructure.service.RetryRecordService;
import com.ah.cloud.permissions.edi.infrastructure.service.impl.TechEdiRetryRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @program: permissions-center
 * @description: 处理上月数据
 * @author: YuKai Fan
 * @create: 2022-07-11 16:06
 **/
@Slf4j
@Component
public class ScanTechRetryJobLastMonthHandlerJob implements BasicProcessor {
    @Resource
    private TechEdiRetryRecordService techEdiRetryRecordService;

    @Override
    public void process(TaskContext context) {
        Date date = DateUtils.getLastMonthDate(new Date());
        String dateMonth = DateUtils.format(date, DateUtils.pattern5);
        log.info("ScanRetryJobHandlerJob start handle last month data, dateMonth is {}, jobId is {}, taskId is {}", dateMonth, context.getJobId(), context.getTaskId());
        EdiResult<Void> result = techEdiRetryRecordService.scanRetry(dateMonth, null, null, 0, 1);
        log.info("ScanRetryJobHandlerJob end handle last month data, result is {}", result);
    }
}
