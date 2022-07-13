package com.ah.cloud.permissions.edi.infrastructure.service;

import com.ah.cloud.permissions.enums.edi.EdiTypeEnum;

import java.util.Date;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-07 10:50
 **/
public interface ScanRetryService {

    /**
     * 扫描重试记录
     * @param dateMonth
     * @param createTimeStart
     * @param createTimeEnd
     * @param index
     * @param total
     * @param ediTypeEnum
     */
    void scanRetry(String dateMonth, Date createTimeStart, Date createTimeEnd, int index, int total, EdiTypeEnum ediTypeEnum);

    /**
     * 扫描重置
     * @param ediTypeEnum
     */
    void scanReset(EdiTypeEnum ediTypeEnum);
}
