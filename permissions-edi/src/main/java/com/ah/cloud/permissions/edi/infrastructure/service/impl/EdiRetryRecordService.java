package com.ah.cloud.permissions.edi.infrastructure.service.impl;

import com.ah.cloud.permissions.edi.infrastructure.service.AbstractEdiRetryRecordService;
import com.ah.cloud.permissions.enums.edi.EdiTypeEnum;
import org.springframework.stereotype.Service;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-06 14:34
 **/
@Service
public class EdiRetryRecordService extends AbstractEdiRetryRecordService {
    private final static String LOG_MARK = "EdiRetryRecordService";

    @Override
    protected String getLogMark() {
        return LOG_MARK;
    }

    @Override
    public EdiTypeEnum initType() {
        return EdiTypeEnum.EDI;
    }
}
