package com.ah.cloud.permissions.edi.infrastructure.alarm;

import com.ah.cloud.permissions.edi.infrastructure.service.EdiTypeService;
import com.ah.cloud.permissions.enums.edi.EdiAlarmTypeEnum;

import java.util.List;

/**
 * @program: permissions-center
 * @description: edi告警
 * @author: YuKai Fan
 * @create: 2022-07-11 17:16
 **/
public interface RetryAlarmService extends EdiTypeService {

    /**
     * 告警
     * @param ediAlarmTypeEnumList
     */
    void alarmRetry(List<EdiAlarmTypeEnum> ediAlarmTypeEnumList);

    /**
     * 告警处理 一直处于处理中状态的任务, 并重置为初始化
     *
     * 由于某种原因(任务处理时, 服务重试等等), 重试记录的状态可能为会处于一直重试中, 导致任务无法继续执行, 所以需要处理一些长时间状态为"重试中"的记录
     */
    void alarmRetryingReset();
}
