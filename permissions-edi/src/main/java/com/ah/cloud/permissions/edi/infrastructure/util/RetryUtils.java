package com.ah.cloud.permissions.edi.infrastructure.util;

import com.ah.cloud.permissions.biz.infrastructure.util.DateUtils;
import com.ah.cloud.permissions.edi.domain.config.dto.RetryBizConfigCacheDTO;
import com.ah.cloud.permissions.edi.infrastructure.repository.bean.EdiBizRetryRecord;
import com.ah.cloud.permissions.edi.infrastructure.repository.bean.EdiTechBizRetryRecord;
import com.ah.cloud.permissions.enums.edi.BizRetryModelEnum;
import com.ah.cloud.permissions.enums.edi.BizRetryStatusEnum;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-05 17:25
 **/
public class RetryUtils {

    /**
     * 构建分片键
     * @param bizType
     * @return
     */
    public static String getShardingKey(Integer bizType) {
        String dateMonth = DateUtils.localDate2Str(LocalDate.now(), "yyyyMM");
        return dateMonth.concat("_").concat(String.valueOf(bizType));
    }


    /**
     * 构建分片键
     * @param bizType
     * @return
     */
    public static String getShardingKey(Date date, Integer bizType) {
        if (date == null) {
            date = new Date();
        }
        String dateMonth = DateUtils.format(date, DateUtils.pattern5);
        return dateMonth.concat("_").concat(String.valueOf(bizType));
    }

    /**
     * 构建分片键
     * @param bizType
     * @return
     */
    public static String getShardingKey(String date, Integer bizType) {
        String dateMonth = DateUtils.localDate2Str(LocalDate.now(), DateUtils.pattern5);
        if (StringUtils.isNotBlank(date)) {
            dateMonth = DateUtils.localDate2Str(DateUtils.str2LocalDate(date), DateUtils.pattern5);
        }
        return dateMonth.concat("_").concat(String.valueOf(bizType));
    }

    /**
     * 获取下次执行时间
     * @param ediBizRetryRecord
     * @param retryBizConfig
     * @return
     */
    public static Date getNextOpTime(EdiBizRetryRecord ediBizRetryRecord, RetryBizConfigCacheDTO retryBizConfig) {
        Date nextOpTime = null;
        if (hasNextOpTime(ediBizRetryRecord.getRecordStatus(), retryBizConfig.getRetryModel().getType())) {
            if (retryBizConfig.getMaxRetryTimes() == -1 || retryBizConfig.getMaxRetryTimes() > ediBizRetryRecord.getRetryTimes()) {
                Calendar c = Calendar.getInstance();
                c.setTime(ediBizRetryRecord.getLastOpTime());
                c.add(Calendar.MILLISECOND, retryBizConfig.getRetryInterval());
                nextOpTime = c.getTime();
            }
        }
        return nextOpTime;
    }


    /**
     * 获取下次执行时间
     * @param ediTechBizRetryRecord
     * @param retryBizConfig
     * @return
     */
    public static Date getNextOpTime(EdiTechBizRetryRecord ediTechBizRetryRecord, RetryBizConfigCacheDTO retryBizConfig) {
        Date nextOpTime = null;
        if (hasNextOpTime(ediTechBizRetryRecord.getRecordStatus(), retryBizConfig.getRetryModel().getType())) {
            if (retryBizConfig.getMaxRetryTimes() == -1 || retryBizConfig.getMaxRetryTimes() > ediTechBizRetryRecord.getRetryTimes()) {
                Calendar c = Calendar.getInstance();
                c.setTime(ediTechBizRetryRecord.getLastOpTime());
                c.add(Calendar.MILLISECOND, retryBizConfig.getRetryInterval());
                nextOpTime = c.getTime();
            }
        }
        return nextOpTime;
    }

    private static boolean hasNextOpTime(Integer status, Integer retryModel) {
        return (Objects.equals(status, BizRetryStatusEnum.RETRY_FAIL.getType())
                || Objects.equals(status, BizRetryStatusEnum.RETRY_INIT.getType()))
                && Objects.equals(retryModel, BizRetryModelEnum.SYS.getType());
    }
}
