package com.ah.cloud.permissions.edi.infrastructure.alarm.impl;

import com.ah.cloud.permissions.biz.infrastructure.util.DateUtils;
import com.ah.cloud.permissions.edi.domain.config.dto.RetryBizConfigDTO;
import com.ah.cloud.permissions.edi.domain.record.query.RetryBizRecordCountQuery;
import com.ah.cloud.permissions.edi.infrastructure.alarm.AbstractEdiAlarmHandler;
import com.ah.cloud.permissions.enums.edi.BizRetryStatusEnum;
import com.ah.cloud.permissions.enums.edi.EdiAlarmTypeEnum;
import org.springframework.stereotype.Component;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-12 11:15
 **/
@Component
public class EdiRetryingResetAlarmHandler extends AbstractEdiAlarmHandler {
    private final static String LOG_MARK = "EdiRetryingResetAlarmHandler";

    private static final Integer MINUTES = 30;

    @Override
    protected String getLogMark() {
        return LOG_MARK;
    }

    @Override
    protected int maxSize() {
        return 2048;
    }

    @Override
    protected RetryBizRecordCountQuery buildEdiAlarmQuery(RetryBizConfigDTO retryBizConfigDTO) {
        return RetryBizRecordCountQuery.builder()
                .bizType(retryBizConfigDTO.getBizType())
                .bizRetryStatusEnum(BizRetryStatusEnum.RETRY_ING)
                .modifyTimeEnd(DateUtils.getDateBeforeMin(MINUTES))
                .build();
    }

    @Override
    public EdiAlarmTypeEnum getEdiAlarmTypeEnum() {
        return EdiAlarmTypeEnum.EDI_RETRY_ING_RESET;
    }
}
