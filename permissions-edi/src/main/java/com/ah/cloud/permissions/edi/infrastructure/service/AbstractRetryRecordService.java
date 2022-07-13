package com.ah.cloud.permissions.edi.infrastructure.service;

import com.ah.cloud.permissions.biz.application.manager.ThreadPoolManager;
import com.ah.cloud.permissions.edi.application.adapter.RetryBizRecordAdapterService;
import com.ah.cloud.permissions.edi.domain.common.EdiResult;
import com.ah.cloud.permissions.edi.domain.record.dto.AddRetryBizRecordDTO;
import com.ah.cloud.permissions.edi.domain.record.dto.RetryBizRecordResultDTO;
import com.ah.cloud.permissions.edi.domain.record.model.RetryBizRecord;
import com.ah.cloud.permissions.edi.domain.record.query.RetryBizRecordQuery;
import com.ah.cloud.permissions.edi.infrastructure.exceprion.EdiException;
import com.ah.cloud.permissions.enums.edi.BizRetryStatusEnum;
import com.ah.cloud.permissions.enums.edi.EdiErrorCodeEnum;
import com.ah.cloud.permissions.enums.edi.EdiTypeEnum;
import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.annotation.Resource;
import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-05 16:40
 **/
@Slf4j
@Component
public abstract class AbstractRetryRecordService implements RetryRecordService, EdiTypeService {
    @Resource
    private RetryService retryService;
    @Resource
    private ScanRetryService scanRetryService;
    @Resource
    private RetryBizRecordAdapterService retryBizRecordAdapterService;

    @Override
    public EdiResult<RetryBizRecordResultDTO> addRetryRecord(AddRetryBizRecordDTO addDTO) {
        try {
            RetryBizRecordResultDTO retryBizRecordResultDTO = retryBizRecordAdapterService.addRetryRecord(addDTO, initType());
            return EdiResult.ofSuccess(retryBizRecordResultDTO);
        } catch (EdiException ediException) {
            return EdiResult.ofFail(ediException.getErrorCode());
        } catch (Exception e) {
            return EdiResult.ofFail(Throwables.getStackTraceAsString(e));
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public EdiResult<RetryBizRecordResultDTO> addRetryRecord(AddRetryBizRecordDTO addDTO, boolean needAutoBusiness) {
        EdiResult<RetryBizRecordResultDTO> ediResult = this.addRetryRecord(addDTO);
        if (ediResult.isSuccess() && needAutoBusiness) {
            RetryBizRecordResultDTO resultData = ediResult.getData();
            // 因为执行retry操作是异步的, 所以存在 上面的添加操作事务还没提交, 查不到数据。所以用下面的这种方式, 等事务提交后再执行 todo 待验证是否可行
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
                @Override
                public void afterCommit() {
                    ThreadPoolManager.retryThreadPool.execute(() -> {
                        try {
                            RetryBizRecord retryBizRecord =  retryBizRecordAdapterService.getRetryBizRecordById(resultData.getId(), addDTO.getBizType(), initType());
                            if (Objects.nonNull(retryBizRecord) && retryBizRecord.getRecordStatus().isOver()) {
                                retryService.retry(retryBizRecord, initType(), false);
                            }
                        } catch (Throwable throwable) {
                            log.error("{}[addRetryRecord] add and handle record failed throw throwable, reason is {}, params is {}"
                                    , getLogMark()
                                    , Throwables.getStackTraceAsString(throwable)
                                    , addDTO.toString());
                        }
                    });
                }
            });
        }
        return ediResult;
    }

    @Override
    public EdiResult<Void> retry(List<Long> idList, Integer bizType, String startTime, boolean force) {
        RetryBizRecordQuery query = RetryBizRecordQuery.builder()
                .idList(idList)
                .ediTypeEnum(initType())
                .bizType(bizType)
                .startTime(startTime)
                .build();
        List<RetryBizRecord> retryBizRecordList = retryBizRecordAdapterService.listRetryRecord(query);
        return retryService.retry(retryBizRecordList, initType(), false);
    }

    @Override
    public EdiResult<Void> closeRecord(Long id, Integer bizType, String startTime, String remark) {
        RetryBizRecord retryBizRecord = retryBizRecordAdapterService.getRetryBizRecordById(id, bizType, initType());
        if (Objects.isNull(retryBizRecord)) {
            throw new EdiException(EdiErrorCodeEnum.RETRY_BIZ_RECORD_IS_NULL);
        }
        retryBizRecordAdapterService.updateStatusAndRemark(retryBizRecord, BizRetryStatusEnum.RETRY_CLOSE, initType(), remark);
        return EdiResult.ofSuccess();
    }

    @Override
    public EdiResult<Void> scanRetry(String dateMonth, Date startTime, Date endTime, int index, int total) {
        scanRetryService.scanRetry(dateMonth, startTime, endTime, index, total, initType());
        return EdiResult.ofSuccess();
    }

    /**
     * 日志标识
     *
     * @return
     */
    protected abstract String getLogMark();
}
