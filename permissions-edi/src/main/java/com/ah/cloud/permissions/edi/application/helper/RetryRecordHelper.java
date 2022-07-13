package com.ah.cloud.permissions.edi.application.helper;

import com.ah.cloud.permissions.domain.common.PageResult;
import com.ah.cloud.permissions.edi.domain.config.dto.RetryBizConfigCacheDTO;
import com.ah.cloud.permissions.edi.domain.record.vo.RetryBizRecordVo;
import com.ah.cloud.permissions.edi.infrastructure.repository.bean.EdiBizRetryRecord;
import com.ah.cloud.permissions.edi.infrastructure.repository.bean.EdiTechBizRetryRecord;
import com.ah.cloud.permissions.biz.infrastructure.util.SecurityUtils;
import com.ah.cloud.permissions.edi.domain.record.dto.AddRetryBizRecordDTO;
import com.ah.cloud.permissions.edi.domain.record.model.RetryBizRecord;
import com.ah.cloud.permissions.edi.infrastructure.util.RetryUtils;
import com.ah.cloud.permissions.enums.edi.BizRetryModelEnum;
import com.ah.cloud.permissions.enums.edi.BizRetryStatusEnum;
import com.github.pagehelper.PageInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-05 16:44
 **/
@Component
public class RetryRecordHelper {

    /**
     * 构造数据
     * @param dto
     * @return
     */
    public EdiBizRetryRecord buildEdiBizRetryRecord(AddRetryBizRecordDTO dto) {
        String userName = SecurityUtils.getUserNameBySession();
        EdiBizRetryRecord ediBizRetryRecord = Convert.INSTANCE.convertToEntity(dto);
        ediBizRetryRecord.setShardingKey(RetryUtils.getShardingKey(dto.getBizType()));
        ediBizRetryRecord.setCreator(userName);
        ediBizRetryRecord.setModifier(userName);
        return ediBizRetryRecord;
    }

    /**
     * 构造数据
     * @param dto
     * @return
     */
    public EdiTechBizRetryRecord buildTechEdiBizRetryRecord(AddRetryBizRecordDTO dto) {
        String userName = SecurityUtils.getUserNameBySession();
        EdiTechBizRetryRecord ediTechBizRetryRecord = Convert.INSTANCE.convertToTechEntity(dto);
        ediTechBizRetryRecord.setShardingKey(RetryUtils.getShardingKey(dto.getBizType()));
        ediTechBizRetryRecord.setCreator(userName);
        ediTechBizRetryRecord.setModifier(userName);
        return ediTechBizRetryRecord;
    }

    /**
     * 数据转换
     * @param ediTechBizRetryRecord
     * @return
     */
    public RetryBizRecord convertToDTO(EdiTechBizRetryRecord ediTechBizRetryRecord) {
        return Convert.INSTANCE.convertToDTO(ediTechBizRetryRecord);
    }

    /**
     * 数据转换
     * @param ediBizRetryRecord
     * @return
     */
    public RetryBizRecord convertToDTO(EdiBizRetryRecord ediBizRetryRecord) {
        return Convert.INSTANCE.convertToDTO(ediBizRetryRecord);
    }

    /**
     * 数据转换
     * @param ediTechBizRetryRecordList
     * @return
     */
    public List<RetryBizRecord> convertToTechDTOList(List<EdiTechBizRetryRecord> ediTechBizRetryRecordList) {
        return Convert.INSTANCE.convertToTechDTOList(ediTechBizRetryRecordList);
    }

    /**
     * 数据转换
     * @param ediBizRetryRecordList
     * @return
     */
    public List<RetryBizRecord> convertToDTOList(List<EdiBizRetryRecord> ediBizRetryRecordList) {
        return Convert.INSTANCE.convertToDTOList(ediBizRetryRecordList);
    }

    /**
     * 数据转换
     * @param pageInfo
     * @param retryBizConfig
     * @return
     */
    public PageResult<RetryBizRecordVo> convertToTechPageResult(PageInfo<EdiTechBizRetryRecord> pageInfo, RetryBizConfigCacheDTO retryBizConfig) {
        PageResult<RetryBizRecordVo> pageResult = new PageResult<>();
        pageResult.setPageNum(pageInfo.getPageNum());
        pageResult.setPageSize(pageInfo.getPageSize());
        pageResult.setTotal(pageInfo.getTotal());
        pageResult.setRows(Convert.INSTANCE.convertToTechVoList(pageInfo.getList()));
        return pageResult;
    }

    /**
     * 数据转换
     * @param pageInfo
     * @param retryBizConfig
     * @return
     */
    public PageResult<RetryBizRecordVo> convertToPageResult(PageInfo<EdiBizRetryRecord> pageInfo, RetryBizConfigCacheDTO retryBizConfig) {
        PageResult<RetryBizRecordVo> pageResult = new PageResult<>();
        pageResult.setPageNum(pageInfo.getPageNum());
        pageResult.setPageSize(pageInfo.getPageSize());
        pageResult.setTotal(pageInfo.getTotal());
        pageResult.setRows(Convert.INSTANCE.convertToVoList(pageInfo.getList()));
        return pageResult;
    }

    @Mapper(uses = BizRetryStatusEnum.class)
    public interface Convert {
        RetryRecordHelper.Convert INSTANCE = Mappers.getMapper(RetryRecordHelper.Convert.class);

        /**
         * 数据转换
         * @param dto
         * @return
         */
        EdiBizRetryRecord convertToEntity(AddRetryBizRecordDTO dto);

        /**
         * 数据转换
         * @param dto
         * @return
         */
        EdiTechBizRetryRecord convertToTechEntity(AddRetryBizRecordDTO dto);

        /**
         * 数据转换
         * @param ediBizRetryRecord
         * @return
         */
        @Mappings(
                @Mapping(target = "recordStatus", source = "recordStatus")
        )
        RetryBizRecord convertToDTO(EdiBizRetryRecord ediBizRetryRecord);

        /**
         * 数据转换
         * @param ediTechBizRetryRecord
         * @return
         */
        @Mappings(
                @Mapping(target = "recordStatus", source = "recordStatus")
        )
        RetryBizRecord convertToDTO(EdiTechBizRetryRecord ediTechBizRetryRecord);


        /**
         * 数据转换
         * @param ediBizRetryRecordList
         * @return
         */
        List<RetryBizRecord> convertToDTOList(List<EdiBizRetryRecord> ediBizRetryRecordList);

        /**
         * 数据转换
         * @param ediTechBizRetryRecordList
         * @return
         */
        List<RetryBizRecord> convertToTechDTOList(List<EdiTechBizRetryRecord> ediTechBizRetryRecordList);

        /**
         * 数据转换
         * @param ediBizRetryRecord
         * @param retryBizConfig
         * @return
         */
        @Mappings(
                @Mapping(target = "nextOpTime", expression = "java(RetryUtils.getNextOpTime(ediBizRetryRecord, retryBizConfig))")
        )
        RetryBizRecordVo convertToVo(EdiBizRetryRecord ediBizRetryRecord, RetryBizConfigCacheDTO retryBizConfig);

        /**
         * 数据转换
         * @param ediBizRetryRecordList
         * @return
         */
        List<RetryBizRecordVo> convertToVoList(List<EdiBizRetryRecord> ediBizRetryRecordList);

        /**
         * 数据转换
         * @param techBizRetryRecord
         * @param retryBizConfig
         * @return
         */
        @Mappings(
                @Mapping(target = "nextOpTime", expression = "java(RetryUtils.getNextOpTime(techBizRetryRecord, retryBizConfig))")
        )
        RetryBizRecordVo convertToTechVo(EdiTechBizRetryRecord techBizRetryRecord, RetryBizConfigCacheDTO retryBizConfig);

        /**
         * 数据转换
         * @param techBizRetryRecordList
         * @return
         */
        List<RetryBizRecordVo> convertToTechVoList(List<EdiTechBizRetryRecord> techBizRetryRecordList);
    }
}
