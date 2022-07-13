package com.ah.cloud.permissions.edi.infrastructure.service;

import com.ah.cloud.permissions.edi.domain.common.EdiResult;
import com.ah.cloud.permissions.edi.domain.record.dto.AddRetryBizRecordDTO;
import com.ah.cloud.permissions.edi.domain.record.dto.RetryBizRecordResultDTO;

import java.util.Date;
import java.util.List;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-05 16:34
 **/
public interface RetryRecordService {

    /**
     * 添加重试记录
     *
     * @param addDTO
     * @return
     */
    EdiResult<RetryBizRecordResultDTO> addRetryRecord(AddRetryBizRecordDTO addDTO);

    /**
     * 添加重试记录，并是否自动执行
     *
     * @param addDTO
     * @param needAutoBusiness
     * @return
     */
    EdiResult<RetryBizRecordResultDTO> addRetryRecord(AddRetryBizRecordDTO addDTO, boolean needAutoBusiness);

    /**
     * 重试edi
     * @param idList
     * @param bizType
     * @param startTime
     * @param force
     * @return
     */
    EdiResult<Void> retry(List<Long> idList, Integer bizType, String startTime, boolean force);

    /**
     * 关闭edi
     * @param id
     * @param bizType
     * @param startTime
     * @param remark
     * @return
     */
    EdiResult<Void> closeRecord(Long id, Integer bizType, String startTime, String remark);

    /**
     * 按照月份扫描EDI重试记录执行重试操作
     *
     * @param dateMonth 月份
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @param index     当前机器index
     * @param total     集群总机器数
     * @return
     */
    EdiResult<Void> scanRetry(String dateMonth, Date startTime, Date endTime, int index, int total);
}
