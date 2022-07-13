package com.ah.cloud.permissions.edi.infrastructure.alarm;

import com.ah.cloud.permissions.edi.domain.config.dto.RetryBizConfigDTO;
import com.ah.cloud.permissions.enums.edi.EdiAlarmTypeEnum;

import java.util.List;

/**
 * @program: permissions-center
 * @description: 告警处理器
 * @author: YuKai Fan
 * @create: 2022-07-11 18:09
 **/
public interface EdiAlarmHandler {

    /**
     * 告警处理
     * @param retryBizConfigDTOList
     */
    void alarm(List<RetryBizConfigDTO>retryBizConfigDTOList);

    /**
     * 获取告警类型
     * @return
     */
    EdiAlarmTypeEnum getEdiAlarmTypeEnum();
}
