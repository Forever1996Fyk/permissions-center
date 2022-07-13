package com.ah.cloud.permissions.edi.application.manager;

import com.ah.cloud.permissions.domain.common.PageResult;
import com.ah.cloud.permissions.edi.application.adapter.RetryBizRecordAdapterService;
import com.ah.cloud.permissions.edi.domain.common.EdiResult;
import com.ah.cloud.permissions.edi.domain.record.dto.RetryBizTypeDTO;
import com.ah.cloud.permissions.edi.domain.record.form.CloseRetryRecordForm;
import com.ah.cloud.permissions.edi.domain.record.form.DoRetryRecordForm;
import com.ah.cloud.permissions.edi.domain.record.query.RetryBizRecordScanQuery;
import com.ah.cloud.permissions.edi.domain.record.vo.RetryBizRecordVo;
import com.ah.cloud.permissions.edi.domain.record.vo.RetryBizTypeVo;
import com.ah.cloud.permissions.edi.infrastructure.exceprion.EdiException;
import com.ah.cloud.permissions.edi.infrastructure.factory.RetryHandleFactory;
import com.ah.cloud.permissions.edi.infrastructure.service.impl.EdiRetryRecordService;
import com.ah.cloud.permissions.edi.infrastructure.service.impl.TechEdiRetryRecordService;
import com.ah.cloud.permissions.enums.edi.EdiTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-07 15:30
 **/
@Slf4j
@Component
public class RetryBizRecordManager {
    @Resource
    private EdiRetryRecordService ediRetryRecordService;
    @Resource
    private TechEdiRetryRecordService techEdiRetryRecordService;
    @Resource
    private RetryBizRecordAdapterService retryBizRecordAdapterService;

    /**
     * 选择业务类型列表
     * @param ediTypeEnum
     * @return
     */
    public List<RetryBizTypeVo> selectRetryBizTypeList(EdiTypeEnum ediTypeEnum) {
        List<RetryBizTypeDTO> retryBizTypeDTOList = RetryHandleFactory.listRetryBizType(ediTypeEnum);
        return retryBizTypeDTOList.stream()
                .map(retryBizTypeDTO -> RetryBizTypeVo.builder().type(retryBizTypeDTO.getType()).name(retryBizTypeDTO.getDesc()).build())
                .collect(Collectors.toList());
    }

    /**
     * 分页查询重试记录
     * @param query
     * @return
     */
    public PageResult<RetryBizRecordVo> pageRetryBizRecordList(RetryBizRecordScanQuery query) {
        return retryBizRecordAdapterService.pageRetryBizRecordList(query);
    }

    /**
     * 批量重试
     *
     * @param form
     */
    public void doRetryRecords(DoRetryRecordForm form) {
        EdiTypeEnum ediTypeEnum = EdiTypeEnum.getByType(form.getEdiType());
        EdiResult<Void> result;
        if (ediTypeEnum.isTech()) {
            result = techEdiRetryRecordService.retry(form.getIdList(), form.getBizType(), form.getStartTime(), Boolean.TRUE);
        } else {
            result = ediRetryRecordService.retry(form.getIdList(), form.getBizType(), form.getStartTime(), Boolean.TRUE);
        }
        if (result.isFailed()) {
            throw new EdiException(result.getEdiErrorCodeEnum(), result.getErrorMsg());
        }
    }

    /**
     * 关闭重试
     * @param form
     */
    public void closeRetryRecord(CloseRetryRecordForm form) {
        EdiTypeEnum ediTypeEnum = EdiTypeEnum.getByType(form.getEdiType());
        EdiResult<Void> result;
        if (ediTypeEnum.isTech()) {
            result = techEdiRetryRecordService.closeRecord(form.getId(), form.getBizType(), form.getStartTime(), form.getRemark());
        } else {
            result = ediRetryRecordService.closeRecord(form.getId(), form.getBizType(), form.getStartTime(), form.getRemark());
        }
        if (result.isFailed()) {
            throw new EdiException(result.getEdiErrorCodeEnum(), result.getErrorMsg());
        }
    }
}
