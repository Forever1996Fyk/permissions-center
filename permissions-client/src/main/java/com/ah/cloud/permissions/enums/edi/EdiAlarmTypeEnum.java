package com.ah.cloud.permissions.enums.edi;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-11 17:34
 **/
public enum EdiAlarmTypeEnum {

    /**
     * edi失败重试次数达到上限
     */
    EDI_ERROR(1_1, EdiTypeEnum.EDI, "失败重试次数达到上限"),

    /**
     * edi状态一直为重试中
     */
    EDI_RETRY_ING(1_2, EdiTypeEnum.EDI, "状态一直为重试中"),

    /**
     * edi状态一直为初始中
     */
    EDI_RETRY_INIT(1_3, EdiTypeEnum.EDI, "状态一直为初始状态中"),

    /**
     * 重试中告警状态重置
     */
    EDI_RETRY_ING_RESET(1_4, EdiTypeEnum.EDI, "重试中告警状态重置"),

    /**
     * tech edi失败重试次数达到上限
     */
    TECH_EDI_ERROR(2_1, EdiTypeEnum.TECH_EDI, "失败重试次数达到上限"),

    /**
     * tech edi状态一直为重试中
     */

    TECH_EDI_RETRY_ING(2_2, EdiTypeEnum.TECH_EDI, "状态一直为重试中"),

    /**
     * tech edi状态一直为初始中
     */
    TECH_EDI_RETRY_INIT(2_3, EdiTypeEnum.TECH_EDI, "状态一直为初始状态中"),

    /**
     * 重试中告警状态重置
     */
    TECH_EDI_RETRY_ING_RESET(2_4, EdiTypeEnum.TECH_EDI, "重试中告警状态重置");
    ;

    private final Integer type;

    private final EdiTypeEnum ediTypeEnum;

    private final String desc;

    EdiAlarmTypeEnum(Integer type, EdiTypeEnum ediTypeEnum, String desc) {
        this.type = type;
        this.ediTypeEnum = ediTypeEnum;
        this.desc = desc;
    }

    /**
     * 需要告警的类型
     */
    public final static List<EdiAlarmTypeEnum> NEED_ALARM_TYPE_LIST = new ArrayList<EdiAlarmTypeEnum>(){
        {
            add(EDI_ERROR);
            add(EDI_RETRY_ING);
            add(EDI_RETRY_INIT);
            add(TECH_EDI_ERROR);
            add(TECH_EDI_RETRY_ING);
            add(TECH_EDI_RETRY_INIT);
        }
    };

    public Integer getType() {
        return type;
    }

    public EdiTypeEnum getEdiTypeEnum() {
        return ediTypeEnum;
    }

    public String getDesc() {
        return desc;
    }
}
