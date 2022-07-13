package com.ah.cloud.permissions.edi.infrastructure.alarm;

import com.ah.cloud.permissions.biz.infrastructure.util.CollectionUtils;
import com.ah.cloud.permissions.edi.application.adapter.RetryBizConfigAdapterService;
import com.ah.cloud.permissions.edi.domain.config.dto.RetryBizConfigDTO;
import com.ah.cloud.permissions.edi.domain.config.query.CfgBizRetryQuery;
import com.ah.cloud.permissions.enums.edi.EdiAlarmTypeEnum;
import com.ah.cloud.permissions.enums.edi.EdiTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-11 17:42
 **/
@Slf4j
@Component
public abstract class AbstractRetryAlarmService implements RetryAlarmService {
    @Resource
    private EdiAlarmHandlerSelector ediAlarmHandlerSelector;
    @Resource
    private RetryBizConfigAdapterService retryBizConfigAdapterService;

    @Override
    public void alarmRetry(List<EdiAlarmTypeEnum> ediAlarmTypeEnumList) {
        EdiTypeEnum ediTypeEnum = initType();
        CfgBizRetryQuery query = CfgBizRetryQuery.builder().isTech(ediTypeEnum.isTech()).build();
        List<RetryBizConfigDTO> retryBizConfigList = retryBizConfigAdapterService.listRetryBizConfig(query);
        if (CollectionUtils.isEmpty(retryBizConfigList)) {
            log.warn("no find need alarm edi config");
            return;
        }
        List<EdiAlarmTypeEnum> alarmTypeEnumList = ediAlarmTypeEnumList.stream()
                .filter(ediAlarmTypeEnum -> Objects.equals(ediAlarmTypeEnum.getEdiTypeEnum(), ediTypeEnum))
                .collect(Collectors.toList());

        for (EdiAlarmTypeEnum ediAlarmTypeEnum : alarmTypeEnumList) {
            log.info("开始执行{} 告警, 告警类型:{}", ediTypeEnum.getDesc(), ediAlarmTypeEnum.getDesc());
            EdiAlarmHandler ediAlarmHandler = ediAlarmHandlerSelector.select(ediAlarmTypeEnum);
            ediAlarmHandler.alarm(retryBizConfigList);
            log.info("{}告警执行完成, 告警类型:{}", ediTypeEnum.getDesc(), ediAlarmTypeEnum.getDesc());
        }
    }

    @Override
    public void alarmRetryingReset() {
        EdiTypeEnum ediTypeEnum = initType();
        CfgBizRetryQuery query = CfgBizRetryQuery.builder().isTech(ediTypeEnum.isTech()).build();
        List<RetryBizConfigDTO> retryBizConfigList = retryBizConfigAdapterService.listRetryBizConfig(query);
        if (CollectionUtils.isEmpty(retryBizConfigList)) {
            log.warn("no find need alarm edi config");
            return;
        }
        EdiAlarmTypeEnum ediAlarmTypeEnum = ediTypeEnum.isTech() ? EdiAlarmTypeEnum.TECH_EDI_RETRY_ING_RESET : EdiAlarmTypeEnum.EDI_RETRY_ING_RESET;
        log.info("开始执行{} 告警, 告警类型:{}", ediTypeEnum.getDesc(), ediAlarmTypeEnum.getDesc());
        EdiAlarmHandler ediAlarmHandler = ediAlarmHandlerSelector.select(ediAlarmTypeEnum);
        ediAlarmHandler.alarm(retryBizConfigList);
        log.info("{}告警执行完成, 告警类型:{}", ediTypeEnum.getDesc(), ediAlarmTypeEnum.getDesc());
    }
}
