package com.ah.cloud.permissions.edi.application.helper;

import com.ah.cloud.permissions.edi.infrastructure.repository.bean.EdiCfgBizRetry;
import com.ah.cloud.permissions.edi.infrastructure.repository.bean.EdiCfgTechBizRetry;
import com.ah.cloud.permissions.biz.infrastructure.util.SecurityUtils;
import com.ah.cloud.permissions.domain.common.PageResult;
import com.ah.cloud.permissions.edi.domain.config.dto.RetryBizConfigCacheDTO;
import com.ah.cloud.permissions.edi.domain.config.dto.RetryBizConfigDTO;
import com.ah.cloud.permissions.edi.domain.config.form.AddCfgBizRetryForm;
import com.ah.cloud.permissions.edi.domain.config.form.UpdateCfgBizRetryForm;
import com.ah.cloud.permissions.edi.domain.config.vo.CfgBizRetryVo;
import com.ah.cloud.permissions.enums.edi.BizRetryModelEnum;
import com.github.pagehelper.PageInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-05 14:14
 **/
@Component
public class RetryBizConfigHelper {

    /**
     * 数据转换
     * @param form
     * @return
     */
    public EdiCfgBizRetry convert(AddCfgBizRetryForm form) {
        EdiCfgBizRetry ediCfgBizRetry = Convert.INSTANCE.convert(form);
        ediCfgBizRetry.setCreator(SecurityUtils.getUserNameBySession());
        ediCfgBizRetry.setModifier(SecurityUtils.getUserNameBySession());
        return ediCfgBizRetry;
    }

    /**
     * 数据转换
     * @param form
     * @return
     */
    public EdiCfgBizRetry convert(UpdateCfgBizRetryForm form) {
        EdiCfgBizRetry ediCfgBizRetry = Convert.INSTANCE.convert(form);
        ediCfgBizRetry.setModifier(SecurityUtils.getUserNameBySession());
        return ediCfgBizRetry;
    }

    /**
     * 数据转换
     * @param form
     * @return
     */
    public EdiCfgTechBizRetry convertToTech(AddCfgBizRetryForm form) {
        EdiCfgTechBizRetry ediCfgTechBizRetry = Convert.INSTANCE.convertToTech(form);
        ediCfgTechBizRetry.setCreator(SecurityUtils.getUserNameBySession());
        ediCfgTechBizRetry.setModifier(SecurityUtils.getUserNameBySession());
        return ediCfgTechBizRetry;
    }

    /**
     * 数据转换
     * @param form
     * @return
     */
    public EdiCfgTechBizRetry convertToTech(UpdateCfgBizRetryForm form) {
        EdiCfgTechBizRetry ediCfgTechBizRetry = Convert.INSTANCE.convertToTech(form);
        ediCfgTechBizRetry.setModifier(SecurityUtils.getUserNameBySession());
        return ediCfgTechBizRetry;
    }

    /**
     * 数据转换
     * @param ediCfgTechBizRetry
     * @return
     */
    public RetryBizConfigCacheDTO convertToDTO(EdiCfgTechBizRetry ediCfgTechBizRetry) {
        return Convert.INSTANCE.convertToDTO(ediCfgTechBizRetry);
    }

    /**
     * 数据转换
     * @param ediCfgBizRetry
     * @return
     */
    public RetryBizConfigCacheDTO convertToDTO(EdiCfgBizRetry ediCfgBizRetry) {
        return Convert.INSTANCE.convertToDTO(ediCfgBizRetry);
    }

    /**
     * 数据转换
     * @param ediCfgTechBizRetryList
     * @return
     */
    public List<RetryBizConfigDTO> convertToTechDTO(List<EdiCfgTechBizRetry> ediCfgTechBizRetryList) {
        return Convert.INSTANCE.convertToTechDTOList(ediCfgTechBizRetryList);
    }

    /**
     * 数据转换
     * @param ediCfgBizRetryList
     * @return
     */
    public List<RetryBizConfigDTO> convertToDTO(List<EdiCfgBizRetry> ediCfgBizRetryList) {
        return Convert.INSTANCE.convertToConfigDTOList(ediCfgBizRetryList);
    }

    /**
     * 数据转换
     * @param ediCfgTechBizRetry
     * @return
     */
    public CfgBizRetryVo convertToVo(EdiCfgTechBizRetry ediCfgTechBizRetry) {
        return Convert.INSTANCE.convertToVo(ediCfgTechBizRetry);
    }

    /**
     * 数据转换
     * @param ediCfgBizRetry
     * @return
     */
    public CfgBizRetryVo convertToVo(EdiCfgBizRetry ediCfgBizRetry) {
        return Convert.INSTANCE.convertToVo(ediCfgBizRetry);
    }

    /**
     * 数据转换
     * @param pageInfo
     * @return
     */
    public PageResult<CfgBizRetryVo> convertToPageResult(PageInfo<EdiCfgBizRetry> pageInfo) {
        PageResult<CfgBizRetryVo> pageResult = new PageResult<>();
        pageResult.setPageSize(pageInfo.getPageSize());
        pageResult.setPageNum(pageResult.getPageNum());
        pageResult.setTotal(pageInfo.getTotal());
        pageResult.setRows(Convert.INSTANCE.convertToVoList(pageInfo.getList()));
        return pageResult;
    }

    /**
     * 数据转换
     * @param pageInfo
     * @return
     */
    public PageResult<CfgBizRetryVo> convertToTechPageResult(PageInfo<EdiCfgTechBizRetry> pageInfo) {
        PageResult<CfgBizRetryVo> pageResult = new PageResult<>();
        pageResult.setPageSize(pageInfo.getPageSize());
        pageResult.setPageNum(pageResult.getPageNum());
        pageResult.setTotal(pageInfo.getTotal());
        pageResult.setRows(Convert.INSTANCE.convertToTechVoList(pageInfo.getList()));
        return pageResult;
    }

    @Mapper(uses = BizRetryModelEnum.class)
    public interface Convert {
        RetryBizConfigHelper.Convert INSTANCE = Mappers.getMapper(RetryBizConfigHelper.Convert.class);

        /**
         * 数据转换
         * @param form
         * @return
         */
        EdiCfgBizRetry convert(AddCfgBizRetryForm form);

        /**
         * 数据转换
         * @param form
         * @return
         */
        EdiCfgBizRetry convert(UpdateCfgBizRetryForm form);

        /**
         * 数据转换
         * @param form
         * @return
         */
        EdiCfgTechBizRetry convertToTech(AddCfgBizRetryForm form);

        /**
         * 数据转换
         * @param form
         * @return
         */
        EdiCfgTechBizRetry convertToTech(UpdateCfgBizRetryForm form);

        /**
         * 数据转换
         * @param ediCfgTechBizRetry
         * @return
         */
        @Mappings({
                @Mapping(target = "retryModel", source = "retryModel")
        })
        RetryBizConfigCacheDTO convertToDTO(EdiCfgTechBizRetry ediCfgTechBizRetry);

        /**
         * 数据转换
         * @param ediCfgBizRetry
         * @return
         */
        @Mappings({
                @Mapping(target = "retryModel", source = "retryModel")
        })
        RetryBizConfigCacheDTO convertToDTO(EdiCfgBizRetry ediCfgBizRetry);

        /**
         * 数据转换
         * @param ediCfgTechBizRetry
         * @return
         */
        @Mappings({
                @Mapping(target = "retryModel", source = "retryModel")
        })
        RetryBizConfigDTO convertToTechDTO(EdiCfgTechBizRetry ediCfgTechBizRetry);

        /**
         * 数据转换
         * @param ediCfgBizRetry
         * @return
         */
        @Mappings({
                @Mapping(target = "retryModel", source = "retryModel")
        })
        RetryBizConfigDTO convertToConfigDTO(EdiCfgBizRetry ediCfgBizRetry);

        /**
         * 数据转换
         * @param ediCfgTechBizRetryList
         * @return
         */
        List<RetryBizConfigDTO> convertToTechDTOList(List<EdiCfgTechBizRetry> ediCfgTechBizRetryList);

        /**
         * 数据转换
         * @param ediCfgTechBizRetryList
         * @return
         */
        List<RetryBizConfigDTO> convertToConfigDTOList(List<EdiCfgBizRetry> ediCfgTechBizRetryList);

        /**
         * 数据转换
         * @param ediCfgTechBizRetry
         * @return
         */
        CfgBizRetryVo convertToVo(EdiCfgTechBizRetry ediCfgTechBizRetry);

        /**
         * 数据转换
         * @param ediCfgBizRetry
         * @return
         */
        CfgBizRetryVo convertToVo(EdiCfgBizRetry ediCfgBizRetry);

        /**
         * 数据转换
         * @param ediCfgTechBizRetryList
         * @return
         */
        List<CfgBizRetryVo> convertToTechVoList(List<EdiCfgTechBizRetry> ediCfgTechBizRetryList);

        /**
         * 数据转换
         * @param ediCfgBizRetryList
         * @return
         */
        List<CfgBizRetryVo> convertToVoList(List<EdiCfgBizRetry> ediCfgBizRetryList);
    }
}
