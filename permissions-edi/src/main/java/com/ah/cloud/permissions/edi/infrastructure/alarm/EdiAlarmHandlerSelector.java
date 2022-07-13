package com.ah.cloud.permissions.edi.infrastructure.alarm;

import com.ah.cloud.permissions.edi.infrastructure.exceprion.EdiException;
import com.ah.cloud.permissions.enums.edi.EdiAlarmTypeEnum;
import com.ah.cloud.permissions.enums.edi.EdiErrorCodeEnum;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-12 13:57
 **/
@Component
public class EdiAlarmHandlerSelector {
    @Resource
    private List<EdiAlarmHandler> list;

    /**
     * 选择处理器
     * @param ediAlarmTypeEnumList
     * @return
     */
    public List<EdiAlarmHandler> select(List<EdiAlarmTypeEnum> ediAlarmTypeEnumList) {
        return list.stream()
                .filter(ediAlarmHandler -> ediAlarmTypeEnumList.contains(ediAlarmHandler.getEdiAlarmTypeEnum()))
                .collect(Collectors.toList());
    }

    /**
     * 选择处理器
     * @param ediAlarmTypeEnum
     * @return
     */
    public EdiAlarmHandler select(EdiAlarmTypeEnum ediAlarmTypeEnum) {
        for (EdiAlarmHandler ediAlarmHandler : list) {
            if (Objects.equals(ediAlarmHandler.getEdiAlarmTypeEnum(), ediAlarmTypeEnum)) {
                return ediAlarmHandler;
            }
        }
        throw new EdiException(EdiErrorCodeEnum.RETRY_ALARM_HANDLER_NOT_EXITED);
    }
}
