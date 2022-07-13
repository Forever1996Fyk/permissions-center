package com.ah.cloud.permissions.edi.infrastructure.alarm.impl;

import com.ah.cloud.permissions.edi.infrastructure.alarm.AbstractRetryAlarmService;
import com.ah.cloud.permissions.enums.edi.EdiTypeEnum;
import org.springframework.stereotype.Component;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-12 14:02
 **/
@Component
public class TechEdiRetryAlarmService extends AbstractRetryAlarmService {

    @Override
    public EdiTypeEnum initType() {
        return EdiTypeEnum.TECH_EDI;
    }
}
