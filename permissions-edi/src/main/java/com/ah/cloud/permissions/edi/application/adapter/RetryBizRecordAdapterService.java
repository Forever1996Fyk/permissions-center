package com.ah.cloud.permissions.edi.application.adapter;

import com.ah.cloud.permissions.domain.common.PageResult;
import com.ah.cloud.permissions.edi.domain.record.dto.AddRetryBizRecordDTO;
import com.ah.cloud.permissions.edi.domain.record.model.RetryBizRecord;
import com.ah.cloud.permissions.edi.domain.record.dto.RetryBizRecordResultDTO;
import com.ah.cloud.permissions.edi.domain.record.query.RetryBizRecordCountQuery;
import com.ah.cloud.permissions.edi.domain.record.query.RetryBizRecordQuery;
import com.ah.cloud.permissions.edi.domain.record.query.RetryBizRecordScanQuery;
import com.ah.cloud.permissions.edi.domain.record.vo.RetryBizRecordVo;
import com.ah.cloud.permissions.enums.edi.BizRetryStatusEnum;
import com.ah.cloud.permissions.enums.edi.EdiTypeEnum;

import java.util.Date;
import java.util.List;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-05 15:59
 **/
public interface RetryBizRecordAdapterService {

    /**
     * 添加edi记录
     * @param addDTO
     * @param ediTypeEnum
     * @return
     */
    RetryBizRecordResultDTO addRetryRecord(AddRetryBizRecordDTO addDTO, EdiTypeEnum ediTypeEnum);

    /**
     * 获取重试记录
     *
     * @param id
     * @param bizType
     * @param ediTypeEnum
     * @return
     */
    RetryBizRecord getRetryBizRecordById(Long id, Integer bizType, EdiTypeEnum ediTypeEnum);

    /**
     * 更新状态
     * @param retryBizRecord
     * @param updateStatusEnum
     * @param ediTypeEnum
     */
    void updateStatus(RetryBizRecord retryBizRecord, BizRetryStatusEnum updateStatusEnum, EdiTypeEnum ediTypeEnum);

    /**
     * 更新状态和备注信息
     * @param retryBizRecord
     * @param updateStatusEnum
     * @param ediTypeEnum
     * @param remark
     */
    void updateStatusAndRemark(RetryBizRecord retryBizRecord, BizRetryStatusEnum updateStatusEnum, EdiTypeEnum ediTypeEnum, String remark);

    /**
     * 获取重试记录最小id
     * @param env
     * @param startTime
     * @param endTime
     * @param ediTypeEnum
     * @return
     */
    Long getRetryMinId(String env, Date startTime, Date endTime, EdiTypeEnum ediTypeEnum);

    /**
     * 获取重试记录最大id
     * @param env
     * @param startTime
     * @param endTime
     * @param ediTypeEnum
     * @return
     */
    Long getRetryMaxId(String env, Date startTime, Date endTime, EdiTypeEnum ediTypeEnum);

    /**
     * 统计重试记录数
     * @param query
     * @return
     */
    int count(RetryBizRecordCountQuery query);

    /**
     * 查询业务重试记录列表
     * @param query
     * @return
     */
    List<RetryBizRecord> listRetryRecord(RetryBizRecordQuery query);

    /**
     * 查询业务重试记录列表
     * @param retryBizRecordScanQuery
     * @return
     */
    List<RetryBizRecord> listScanRetryRecord(RetryBizRecordScanQuery retryBizRecordScanQuery);

    /**
     * 分页查询重试记录
     *
     * @param query
     * @return
     */
    PageResult<RetryBizRecordVo> pageRetryBizRecordList(RetryBizRecordScanQuery query);

}
