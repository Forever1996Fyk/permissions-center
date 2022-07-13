package com.ah.cloud.permissions.edi.infrastructure.service.impl;

import cn.hutool.core.date.StopWatch;
import com.ah.cloud.permissions.biz.infrastructure.util.CollectionUtils;
import com.ah.cloud.permissions.edi.application.adapter.RetryBizRecordAdapterService;
import com.ah.cloud.permissions.edi.application.manager.RetryResourceManager;
import com.ah.cloud.permissions.edi.domain.record.model.RetryBizRecord;
import com.ah.cloud.permissions.edi.domain.record.query.RetryBizRecordScanQuery;
import com.ah.cloud.permissions.edi.domain.record.table.RetryBizRecordTable;
import com.ah.cloud.permissions.edi.infrastructure.constant.EdiConstants;
import com.ah.cloud.permissions.edi.infrastructure.service.RetryService;
import com.ah.cloud.permissions.edi.infrastructure.service.ScanRetryService;
import com.ah.cloud.permissions.enums.edi.EdiTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.api.hint.HintManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-07 11:04
 **/
@Slf4j
@Service
public class ScanRetryServiceImpl implements ScanRetryService {
    @Resource
    private RetryService retryService;
    @Resource
    private RetryResourceManager retryResourceManager;
    @Resource
    private RetryBizRecordAdapterService retryBizRecordAdapterService;

    @Value("${spring.profiles.active}")
    private String env;

    @Override
    public void scanRetry(String dateMonth, Date startTime, Date endTime, int index, int total, EdiTypeEnum ediTypeEnum) {
        log.info("ScanRetryServiceImpl[scanRetry] scan start, startTime is {}, endTime is {}, dateMonth is {}, index is {}, total is {}, ediType is {}", startTime, endTime
                , dateMonth
                , index
                , total
                , ediTypeEnum);
        StopWatch stopWatch = new StopWatch(String.format("ScanRetry[%s_%s]耗时统计", dateMonth, index));
        stopWatch.start();
        List<RetryBizRecordTable> retryBizRecordTableList = retryResourceManager.buildRetryRecordActualTableSliceList(dateMonth, index, total, ediTypeEnum);
        retryBizRecordTableList.forEach(retryBizRecordTable -> this.doScanRetry(retryBizRecordTable, startTime, endTime, ediTypeEnum));
    }

    private void doScanRetry(RetryBizRecordTable retryBizRecordTable, Date startTime, Date endTime, EdiTypeEnum ediTypeEnum) {
        log.info("Do scan retry retryBizRecordTable is {}", retryBizRecordTable);
        StopWatch stopWatch = new StopWatch(String.format("DoScanRetry[%s_%s]耗时统计", retryBizRecordTable.getDataSource(), retryBizRecordTable.getActualTable()));
        stopWatch.start();
        try (HintManager hintManager = HintManager.getInstance()) {
            hintManager.addDatabaseShardingValue(retryBizRecordTable.getLogicTable(), retryBizRecordTable.getDataSource());
            hintManager.addTableShardingValue(retryBizRecordTable.getLogicTable(), retryBizRecordTable.getActualTable());
            Long minId = retryBizRecordAdapterService.getRetryMinId(env, startTime, endTime, ediTypeEnum);
            if (minId == null || Objects.equals(minId, EdiConstants.ZERO)) {
                log.info("Do scan retry [{}_{}] record is empty", retryBizRecordTable.getDataSource(), retryBizRecordTable.getActualTable());
                return;
            }
            minId--;
            Long maxId = retryBizRecordAdapterService.getRetryMaxId(env, startTime, endTime, ediTypeEnum);
            while (true) {
                RetryBizRecordScanQuery retryBizRecordScanQuery = buildRetryBizRecordQuery(startTime, endTime, ediTypeEnum, minId, maxId);
                List<RetryBizRecord> retryBizRecordList = retryBizRecordAdapterService.listScanRetryRecord(retryBizRecordScanQuery);
                if (CollectionUtils.isEmpty(retryBizRecordList)) {
                    break;
                }
                minId = retryBizRecordList.get(retryBizRecordList.size() - EdiConstants.ONE_INT).getId();
                retryService.retry(retryBizRecordList, ediTypeEnum, false);
            }
        } finally {
            stopWatch.stop();
            log.info("Do scan retry [{}_{}] end, cost {}", retryBizRecordTable.getDataSource(), retryBizRecordTable.getActualTable(), stopWatch.getTotalTimeMillis());
        }
    }

    private RetryBizRecordScanQuery buildRetryBizRecordQuery(Date startTime, Date endTime, EdiTypeEnum ediTypeEnum, Long minId, Long maxId) {
        return RetryBizRecordScanQuery.builder()
                .minId(minId)
                .maxId(maxId)
                .env(env)
                .maxQuerySize(EdiConstants.DEFAULT_SCAN_RECORD_PAGE_SIZE)
                .startTime(startTime)
                .endTime(endTime)
                .ediTypeEnum(ediTypeEnum)
                .build();
    }

    @Override
    public void scanReset(EdiTypeEnum ediTypeEnum) {

    }
}
