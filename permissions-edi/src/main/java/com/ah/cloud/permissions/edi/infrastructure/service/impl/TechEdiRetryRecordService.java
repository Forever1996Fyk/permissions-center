package com.ah.cloud.permissions.edi.infrastructure.service.impl;

import com.ah.cloud.permissions.edi.infrastructure.service.AbstractTechEdiRetryRecordService;
import com.ah.cloud.permissions.enums.edi.EdiTypeEnum;
import org.springframework.stereotype.Service;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-06 14:34
 **/
@Service
public class TechEdiRetryRecordService extends AbstractTechEdiRetryRecordService {
    private final static String LOG_MARK = "TechEdiRetryRecordService";

    @Override
    protected String getLogMark() {
        return LOG_MARK;
    }

    @Override
    public EdiTypeEnum initType() {
        return EdiTypeEnum.TECH_EDI;
    }
}
