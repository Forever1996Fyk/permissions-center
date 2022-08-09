package com.ah.cloud.permissions.edi.application.adapter;

import com.ah.cloud.permissions.biz.infrastructure.util.CollectionUtils;
import com.ah.cloud.permissions.biz.infrastructure.util.JsonUtils;
import com.ah.cloud.permissions.biz.infrastructure.util.SecurityUtils;
import com.ah.cloud.permissions.domain.common.PageResult;
import com.ah.cloud.permissions.edi.application.checker.RetryRecordChecker;
import com.ah.cloud.permissions.edi.application.helper.RetryRecordHelper;
import com.ah.cloud.permissions.edi.application.service.EdiBizRetryRecordService;
import com.ah.cloud.permissions.edi.application.service.EdiTechBizRetryRecordService;
import com.ah.cloud.permissions.edi.domain.config.dto.RetryBizConfigCacheDTO;
import com.ah.cloud.permissions.edi.domain.record.dto.AddRetryBizRecordDTO;
import com.ah.cloud.permissions.edi.domain.record.dto.RetryBizRecordResultDTO;
import com.ah.cloud.permissions.edi.domain.record.model.RetryBizRecord;
import com.ah.cloud.permissions.edi.domain.record.query.RetryBizRecordCountQuery;
import com.ah.cloud.permissions.edi.domain.record.query.RetryBizRecordQuery;
import com.ah.cloud.permissions.edi.domain.record.query.RetryBizRecordScanQuery;
import com.ah.cloud.permissions.edi.domain.record.vo.RetryBizRecordVo;
import com.ah.cloud.permissions.edi.infrastructure.exceprion.EdiException;
import com.ah.cloud.permissions.edi.infrastructure.repository.bean.EdiBizRetryRecord;
import com.ah.cloud.permissions.edi.infrastructure.repository.bean.EdiTechBizRetryRecord;
import com.ah.cloud.permissions.edi.infrastructure.repository.mapper.ext.EdiBizRetryRecordExtMapper;
import com.ah.cloud.permissions.edi.infrastructure.repository.mapper.ext.TechEdiBizRetryRecordExtMapper;
import com.ah.cloud.permissions.edi.infrastructure.util.RetryUtils;
import com.ah.cloud.permissions.enums.common.DeletedEnum;
import com.ah.cloud.permissions.enums.edi.BizRetryStatusEnum;
import com.ah.cloud.permissions.enums.edi.EdiErrorCodeEnum;
import com.ah.cloud.permissions.enums.edi.EdiTypeEnum;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-05 16:43
 **/
@Slf4j
@Service
public class RetryBizRecordAdapterServiceImpl implements RetryBizRecordAdapterService {
    @Value("${spring.profiles.active}")
    private String env;
    @Resource
    private RetryRecordHelper retryRecordHelper;
    @Resource
    private RetryRecordChecker retryRecordChecker;
    @Resource
    private EdiBizRetryRecordService ediBizRetryRecordService;
    @Resource
    private EdiBizRetryRecordExtMapper ediBizRetryRecordExtMapper;
    @Resource
    private RetryBizConfigAdapterService retryBizConfigAdapterService;
    @Resource
    private EdiTechBizRetryRecordService ediTechBizRetryRecordService;
    @Resource
    private TechEdiBizRetryRecordExtMapper techEdiBizRetryRecordExtMapper;

    @Override
    public RetryBizRecordResultDTO addRetryRecord(AddRetryBizRecordDTO addDTO, EdiTypeEnum ediTypeEnum) {
        log.info("RetryBizRecordAdapterServiceImpl[addRetryRecord] add retry recordId, params is {}, ediType is {}", JsonUtils.toJSONString(addDTO), ediTypeEnum);
        try {
            retryRecordChecker.check(addDTO);
            ImmutablePair<Long, String> pair = addAndGetRecord(addDTO, ediTypeEnum);
            return createRetryRecord(pair.getLeft(), pair.getRight(), addDTO.getBizType(), ediTypeEnum);
        } catch (EdiException ediException) {
            log.error("RetryBizRecordAdapterServiceImpl[addRetryRecord] add retry recordId failed with ediException, reason is {}, params is {}, ediType is {}"
                    , Throwables.getStackTraceAsString(ediException)
                    , JsonUtils.toJSONString(addDTO)
                    , ediTypeEnum);
            throw ediException;
        } catch (Exception e) {
            log.error("RetryBizRecordAdapterServiceImpl[addRetryRecord] add retry recordId failed with exception, reason is {}, params is {}, ediType is {}"
                    , Throwables.getStackTraceAsString(e)
                    , JsonUtils.toJSONString(addDTO)
                    , ediTypeEnum);
            throw e;
        }
    }

    @Override
    public RetryBizRecord getRetryBizRecordById(Long id, Integer bizType, EdiTypeEnum ediTypeEnum) {
        if (ediTypeEnum.isTech()) {
            EdiTechBizRetryRecord ediTechBizRetryRecord = ediTechBizRetryRecordService.getOne(
                    new QueryWrapper<EdiTechBizRetryRecord>().lambda()
                            .eq(EdiTechBizRetryRecord::getId, id)
                            .eq(EdiTechBizRetryRecord::getShardingKey, RetryUtils.getShardingKey(bizType))
                            .eq(EdiTechBizRetryRecord::getEnv, env)
                            .eq(EdiTechBizRetryRecord::getDeleted, DeletedEnum.NO.value)
            );
            return retryRecordHelper.convertToDTO(ediTechBizRetryRecord);
        } else {
            EdiBizRetryRecord ediBizRetryRecord = ediBizRetryRecordService.getOne(
                    new QueryWrapper<EdiBizRetryRecord>().lambda()
                            .eq(EdiBizRetryRecord::getId, id)
                            .eq(EdiBizRetryRecord::getShardingKey, RetryUtils.getShardingKey(bizType))
                            .eq(EdiBizRetryRecord::getEnv, env)
                            .eq(EdiBizRetryRecord::getDeleted, DeletedEnum.NO.value)
            );
            return retryRecordHelper.convertToDTO(ediBizRetryRecord);
        }
    }

    @Override
    public void updateStatus(RetryBizRecord retryBizRecord, BizRetryStatusEnum updateStatusEnum, EdiTypeEnum ediTypeEnum) {
        boolean result;
        if (ediTypeEnum.isTech()) {
            EdiTechBizRetryRecord updateEdiTechBizRetryRecord = new EdiTechBizRetryRecord();
            updateEdiTechBizRetryRecord.setRecordStatus(updateStatusEnum.getType());
            updateEdiTechBizRetryRecord.setVersion(retryBizRecord.getVersion());
            result = ediTechBizRetryRecordService.update(
                    updateEdiTechBizRetryRecord,
                    new UpdateWrapper<EdiTechBizRetryRecord>().lambda()
                            .eq(EdiTechBizRetryRecord::getId, retryBizRecord.getId())
                            .eq(EdiTechBizRetryRecord::getVersion, retryBizRecord.getVersion())
                            .eq(EdiTechBizRetryRecord::getShardingKey, retryBizRecord.getShardingKey())
            );
        } else {
            EdiBizRetryRecord updateEdiBizRetryRecord = new EdiBizRetryRecord();
            updateEdiBizRetryRecord.setRecordStatus(updateStatusEnum.getType());
            updateEdiBizRetryRecord.setVersion(retryBizRecord.getVersion());
            result = ediBizRetryRecordService.update(
                    updateEdiBizRetryRecord,
                    new UpdateWrapper<EdiBizRetryRecord>().lambda()
                            .eq(EdiBizRetryRecord::getId, retryBizRecord.getId())
                            .eq(EdiBizRetryRecord::getVersion, retryBizRecord.getVersion())
                            .eq(EdiBizRetryRecord::getShardingKey, retryBizRecord.getShardingKey())
            );
        }
        if (!result) {
            throw new EdiException(EdiErrorCodeEnum.RETRY_BIZ_RECORD_UPDATE_FAIL);
        }
        retryBizRecord.setVersion(retryBizRecord.getVersion() + 1);
    }

    @Override
    public void updateStatusAndRemark(RetryBizRecord retryBizRecord, BizRetryStatusEnum updateStatusEnum, EdiTypeEnum ediTypeEnum, String remark) {
        boolean result;
        if (ediTypeEnum.isTech()) {
            EdiTechBizRetryRecord updateEdiTechBizRetryRecord = new EdiTechBizRetryRecord();
            updateEdiTechBizRetryRecord.setRecordStatus(updateStatusEnum.getType());
            updateEdiTechBizRetryRecord.setVersion(retryBizRecord.getVersion() + 1);
            updateEdiTechBizRetryRecord.setRemark(remark);
            updateEdiTechBizRetryRecord.setModifier(SecurityUtils.getUserNameBySession());
            result = ediTechBizRetryRecordService.update(
                    updateEdiTechBizRetryRecord,
                    new UpdateWrapper<EdiTechBizRetryRecord>().lambda()
                            .eq(EdiTechBizRetryRecord::getId, retryBizRecord.getId())
                            .eq(EdiTechBizRetryRecord::getShardingKey, retryBizRecord.getShardingKey())
                            .eq(EdiTechBizRetryRecord::getVersion, retryBizRecord.getVersion())
                            .eq(EdiTechBizRetryRecord::getRecordStatus, retryBizRecord.getRecordStatus())
            );
            retryBizRecord.setVersion(updateEdiTechBizRetryRecord.getVersion());
        } else {
            EdiBizRetryRecord updateEdiBizRetryRecord = new EdiBizRetryRecord();
            updateEdiBizRetryRecord.setRecordStatus(updateStatusEnum.getType());
            updateEdiBizRetryRecord.setVersion(retryBizRecord.getVersion() + 1);
            updateEdiBizRetryRecord.setRemark(remark);
            updateEdiBizRetryRecord.setModifier(SecurityUtils.getUserNameBySession());
            result = ediBizRetryRecordService.update(
                    updateEdiBizRetryRecord,
                    new UpdateWrapper<EdiBizRetryRecord>().lambda()
                            .eq(EdiBizRetryRecord::getId, retryBizRecord.getId())
                            .eq(EdiBizRetryRecord::getShardingKey, retryBizRecord.getShardingKey())
                            .eq(EdiBizRetryRecord::getVersion, retryBizRecord.getVersion())
                            .eq(EdiBizRetryRecord::getRecordStatus, retryBizRecord.getRecordStatus())
            );
            retryBizRecord.setVersion(retryBizRecord.getVersion());
        }
        if (!result) {
            throw new EdiException(EdiErrorCodeEnum.RETRY_BIZ_RECORD_UPDATE_FAIL);
        }
    }

    @Override
    public Long getRetryMinId(String env, Date startTime, Date endTime, EdiTypeEnum ediTypeEnum) {
        if (ediTypeEnum.isTech()) {
            return techEdiBizRetryRecordExtMapper.getRetryMinId(env, startTime, endTime);
        } else {
            return ediBizRetryRecordExtMapper.getRetryMinId(env, startTime, endTime);
        }
    }

    @Override
    public Long getRetryMaxId(String env, Date startTime, Date endTime, EdiTypeEnum ediTypeEnum) {
        if (ediTypeEnum.isTech()) {
            return techEdiBizRetryRecordExtMapper.getRetryMaxId(env, startTime, endTime);
        } else {
            return ediBizRetryRecordExtMapper.getRetryMaxId(env, startTime, endTime);
        }
    }

    @Override
    public List<RetryBizRecord> listRetryRecord(RetryBizRecordQuery query) {
        if (Objects.isNull(query.getEdiTypeEnum()) || query.getBizType() == null || CollectionUtils.isEmpty(query.getIdList())) {
            return null;
        }
        if (query.getEdiTypeEnum().isTech()) {
            List<EdiTechBizRetryRecord> list = ediTechBizRetryRecordService.list(
                    new QueryWrapper<EdiTechBizRetryRecord>().lambda()
                            .in(EdiTechBizRetryRecord::getId, query.getIdList())
                            .eq(EdiTechBizRetryRecord::getEnv, env)
                            .ge(EdiTechBizRetryRecord::getCreateTime, query.getStartTime())
                            .eq(EdiTechBizRetryRecord::getShardingKey, RetryUtils.getShardingKey(query.getBizType()))
            );
            return retryRecordHelper.convertToTechDTOList(list);
        } else {
            List<EdiBizRetryRecord> list = ediBizRetryRecordService.list(
                    new QueryWrapper<EdiBizRetryRecord>().lambda()
                            .in(EdiBizRetryRecord::getId, query.getIdList())
                            .eq(EdiBizRetryRecord::getEnv, env)
                            .ge(EdiBizRetryRecord::getCreateTime, query.getStartTime())
                            .eq(EdiBizRetryRecord::getShardingKey, RetryUtils.getShardingKey(query.getBizType()))
            );
            return retryRecordHelper.convertToDTOList(list);
        }
    }

    @Override
    public List<RetryBizRecord> listScanRetryRecord(RetryBizRecordScanQuery query) {
        if (Objects.isNull(query.getEdiTypeEnum()) || StringUtils.isBlank(query.getEnv())) {
            return null;
        }
        if (query.getEdiTypeEnum().isTech()) {
            return techEdiBizRetryRecordExtMapper.listRetryBizRecord(query);
        } else {
            return ediBizRetryRecordExtMapper.listRetryBizRecord(query);
        }
    }

    @Override
    public PageResult<RetryBizRecordVo> pageRetryBizRecordList(RetryBizRecordScanQuery query) {
        String shardingKey = RetryUtils.getShardingKey(query.getStartTime(), query.getBizType());
        RetryBizConfigCacheDTO retryBizConfig = null;
        if (query.getBizType() != null) {
            retryBizConfig = retryBizConfigAdapterService.getRetryBizConfigByLocalCache(query.getBizType(), query.getEdiTypeEnum());
        }
        if (Objects.isNull(query.getEdiTypeEnum())) {
            PageInfo<EdiTechBizRetryRecord> pageInfo = PageMethod.startPage(query.getPageNo(), query.getPageSize())
                    .doSelectPageInfo(
                            () -> ediTechBizRetryRecordService.list(
                                    new QueryWrapper<EdiTechBizRetryRecord>().lambda()
                                            .eq(query.getBizType() != null, EdiTechBizRetryRecord::getBizType, query.getBizType())
                                            .eq(StringUtils.isNotBlank(query.getBizNo()), EdiTechBizRetryRecord::getBizNo, query.getBizNo())
                                            .ge(query.getStartTime() != null, EdiTechBizRetryRecord::getCreateTime, query.getStartTime())
                                            .le(query.getEndTime() != null, EdiTechBizRetryRecord::getCreateTime, query.getEndTime())
                                            .eq(query.getRecordStatus() != null, EdiTechBizRetryRecord::getRecordStatus, query.getRecordStatus())
                                            .eq(EdiTechBizRetryRecord::getShardingKey, shardingKey)
                                            .eq(EdiTechBizRetryRecord::getEnv, env)
                                            .eq(EdiTechBizRetryRecord::getDeleted, DeletedEnum.NO.value)
                            )
                    );
            return retryRecordHelper.convertToTechPageResult(pageInfo, retryBizConfig);
        } else {
            PageInfo<EdiBizRetryRecord> pageInfo = PageMethod.startPage(query.getPageNo(), query.getPageSize())
                    .doSelectPageInfo(
                            () -> ediBizRetryRecordService.list(
                                    new QueryWrapper<EdiBizRetryRecord>().lambda()
                                            .eq(query.getBizType() != null, EdiBizRetryRecord::getBizType, query.getBizType())
                                            .eq(StringUtils.isNotBlank(query.getBizNo()), EdiBizRetryRecord::getBizNo, query.getBizNo())
                                            .ge(query.getStartTime() != null, EdiBizRetryRecord::getCreateTime, query.getStartTime())
                                            .le(query.getEndTime() != null, EdiBizRetryRecord::getCreateTime, query.getEndTime())
                                            .eq(query.getRecordStatus() != null, EdiBizRetryRecord::getRecordStatus, query.getRecordStatus())
                                            .eq(EdiBizRetryRecord::getShardingKey, shardingKey)
                                            .eq(EdiBizRetryRecord::getEnv, env)
                                            .eq(EdiBizRetryRecord::getDeleted, DeletedEnum.NO.value)
                            )
                    );
            return retryRecordHelper.convertToPageResult(pageInfo, retryBizConfig);
        }
    }

    @Override
    public int count(RetryBizRecordCountQuery query) {
        if (query.getEdiTypeEnum().isTech()) {
            return ediTechBizRetryRecordService.count(
                    new QueryWrapper<EdiTechBizRetryRecord>().lambda()
                            .orderByDesc(EdiTechBizRetryRecord::getId)
                            .eq(query.getId() != null, EdiTechBizRetryRecord::getId, query.getId())
                            .eq(query.getBizType() != null, EdiTechBizRetryRecord::getBizType, query.getBizType())
                            .eq(query.getBizRetryStatusEnum() != null ,EdiTechBizRetryRecord::getRecordStatus, query.getBizRetryStatusEnum().getType())
                            .eq(StringUtils.isNotBlank(query.getBizNo()), EdiTechBizRetryRecord::getBizNo, query.getBizNo())
                            .ge(StringUtils.isNotBlank(query.getCreateTimeStart()), EdiTechBizRetryRecord::getCreateTime, query.getCreateTimeStart())
                            .le(StringUtils.isNotBlank(query.getCreateTimeEnd()), EdiTechBizRetryRecord::getCreateTime, query.getCreateTimeEnd())
                            .le(StringUtils.isNotBlank(query.getModifyTimeEnd()), EdiTechBizRetryRecord::getModifyTime, query.getModifyTimeEnd())
                            .eq(EdiTechBizRetryRecord::getShardingKey, RetryUtils.getShardingKey(query.getCreateTimeStart(), query.getBizType()))
                            .eq(EdiTechBizRetryRecord::getDeleted, DeletedEnum.NO.value)
                            .eq(EdiTechBizRetryRecord::getEnv, env)
            );
        } else {
            return ediBizRetryRecordService.count(
                    new QueryWrapper<EdiBizRetryRecord>().lambda()
                            .orderByDesc(EdiBizRetryRecord::getId)
                            .eq(query.getId() != null, EdiBizRetryRecord::getId, query.getId())
                            .eq(query.getBizType() != null, EdiBizRetryRecord::getBizType, query.getBizType())
                            .eq(query.getBizRetryStatusEnum() != null ,EdiBizRetryRecord::getRecordStatus, query.getBizRetryStatusEnum().getType())
                            .eq(StringUtils.isNotBlank(query.getBizNo()), EdiBizRetryRecord::getBizNo, query.getBizNo())
                            .ge(StringUtils.isNotBlank(query.getCreateTimeStart()), EdiBizRetryRecord::getCreateTime, query.getCreateTimeStart())
                            .le(StringUtils.isNotBlank(query.getCreateTimeEnd()), EdiBizRetryRecord::getCreateTime, query.getCreateTimeEnd())
                            .le(StringUtils.isNotBlank(query.getModifyTimeEnd()), EdiBizRetryRecord::getModifyTime, query.getModifyTimeEnd())
                            .eq(EdiBizRetryRecord::getShardingKey, RetryUtils.getShardingKey(query.getCreateTimeStart(), query.getBizType()))
                            .eq(EdiBizRetryRecord::getDeleted, DeletedEnum.NO.value)
                            .eq(EdiBizRetryRecord::getEnv, env)
            );
        }
    }

    private ImmutablePair<Long, String> addAndGetRecord(AddRetryBizRecordDTO addDTO, EdiTypeEnum ediTypeEnum) {
        boolean result;
        Long id;
        String shardingKey;
        if (ediTypeEnum.isTech()) {
            EdiTechBizRetryRecord ediTechBizRetryRecord = retryRecordHelper.buildTechEdiBizRetryRecord(addDTO);
            result = ediTechBizRetryRecordService.save(ediTechBizRetryRecord);
            id = ediTechBizRetryRecord.getId();
            shardingKey = ediTechBizRetryRecord.getShardingKey();
        } else {
            EdiBizRetryRecord ediBizRetryRecord = retryRecordHelper.buildEdiBizRetryRecord(addDTO);
            result = ediBizRetryRecordService.save(ediBizRetryRecord);
            id = ediBizRetryRecord.getId();
            shardingKey = ediBizRetryRecord.getShardingKey();
        }

        if (!result) {
            throw new EdiException(EdiErrorCodeEnum.RETRY_BIZ_RECORD_ADD_FAIL, ediTypeEnum.getDesc());
        }
        return ImmutablePair.of(id, shardingKey);
    }


    private RetryBizRecordResultDTO createRetryRecord(Long id, String shardingKey, Integer bizType, EdiTypeEnum ediTypeEnum) {
        RetryBizConfigCacheDTO retryBizConfig = retryBizConfigAdapterService.getRetryBizConfigByLocalCache(bizType, ediTypeEnum);
        if (Objects.isNull(retryBizConfig)) {
            throw new EdiException(EdiErrorCodeEnum.RETRY_BIZ_CONFIG_IS_NULL);
        }
        return RetryBizRecordResultDTO.builder()
                .id(id)
                .shardingKey(shardingKey)
                .retryBizConfig(retryBizConfig)
                .build();
    }

}
