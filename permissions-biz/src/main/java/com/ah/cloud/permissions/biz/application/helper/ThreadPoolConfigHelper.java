package com.ah.cloud.permissions.biz.application.helper;

import com.ah.cloud.permissions.biz.domain.api.vo.SysApiVo;
import com.ah.cloud.permissions.biz.domain.cfg.threadpool.dto.CfgThreadPoolDTO;
import com.ah.cloud.permissions.biz.domain.cfg.threadpool.form.CfgThreadPoolAddForm;
import com.ah.cloud.permissions.biz.domain.cfg.threadpool.form.CfgThreadPoolUpdateForm;
import com.ah.cloud.permissions.biz.domain.cfg.threadpool.vo.CfgThreadPoolVo;
import com.ah.cloud.permissions.biz.domain.cfg.threadpool.vo.ThreadPoolDataVo;
import com.ah.cloud.permissions.biz.domain.user.form.SysUserAddForm;
import com.ah.cloud.permissions.biz.domain.user.vo.SysUserVo;
import com.ah.cloud.permissions.biz.infrastructure.constant.PermissionsConstants;
import com.ah.cloud.permissions.biz.infrastructure.repository.bean.CfgThreadPool;
import com.ah.cloud.permissions.biz.infrastructure.repository.bean.SysUser;
import com.ah.cloud.permissions.biz.infrastructure.repository.bean.ThreadPoolData;
import com.ah.cloud.permissions.biz.infrastructure.util.SecurityUtils;
import com.ah.cloud.permissions.domain.common.PageResult;
import com.github.pagehelper.PageInfo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-04-26 22:09
 **/
@Component
public class ThreadPoolConfigHelper {

    /**
     * 数据转换
     * @param cfgThreadPoolList
     * @return
     */
    public List<CfgThreadPoolDTO> convertToDTOList(List<CfgThreadPool> cfgThreadPoolList) {
        return Convert.INSTANCE.convertList(cfgThreadPoolList);
    }

    /**
     * 数据转换
     * @param cfgThreadPool
     * @return
     */
    public CfgThreadPoolDTO convertToDTO(CfgThreadPool cfgThreadPool) {
        return Convert.INSTANCE.convert(cfgThreadPool);
    }

    /**
     * 数据转换
     * @param form
     * @return
     */
    public CfgThreadPool convertToEntity(CfgThreadPoolAddForm form) {
        CfgThreadPool cfgThreadPool = Convert.INSTANCE.convert(form);
        cfgThreadPool.setCreator(SecurityUtils.getUserNameBySession());
        cfgThreadPool.setModifier(SecurityUtils.getUserNameBySession());
        return cfgThreadPool;
    }

    /**
     * 数据转换
     * @param form
     * @return
     */
    public CfgThreadPool convertToEntity(CfgThreadPoolUpdateForm form) {
        CfgThreadPool cfgThreadPool = Convert.INSTANCE.convert(form);
        cfgThreadPool.setModifier(SecurityUtils.getUserNameBySession());
        return cfgThreadPool;
    }

    /**
     * 数据转换
     * @param cfgThreadPool
     * @return
     */
    public CfgThreadPoolVo convertToVo(CfgThreadPool cfgThreadPool) {
        return Convert.INSTANCE.convertToVo(cfgThreadPool);
    }

    /**
     * 数据转换
     * @param pageInfo
     * @return
     */
    public PageResult<CfgThreadPoolVo> convertToPageResult(PageInfo<CfgThreadPool> pageInfo) {
        PageResult<CfgThreadPoolVo> result = new PageResult<>();
        result.setTotal(pageInfo.getTotal());
        result.setPageNum(pageInfo.getPageNum());
        result.setRows(ThreadPoolConfigHelper.Convert.INSTANCE.convertToVo(pageInfo.getList()));
        result.setPageSize(pageInfo.getSize());
        result.setPages(pageInfo.getPages());
        return result;
    }

    /**
     * 数据转换
     * @param pageInfo
     * @return
     */
    public PageResult<ThreadPoolDataVo> convertToThreadDataPageResult(PageInfo<ThreadPoolData> pageInfo) {
        PageResult<ThreadPoolDataVo> result = new PageResult<>();
        result.setTotal(pageInfo.getTotal());
        result.setPageNum(pageInfo.getPageNum());
        result.setRows(ThreadPoolConfigHelper.Convert.INSTANCE.convertToThreadPoolDataVo(pageInfo.getList()));
        result.setPageSize(pageInfo.getSize());
        result.setPages(pageInfo.getPages());
        return result;
    }

    /**
     * 数据转换
     * @param threadPoolDataVo
     * @return
     */
    public ThreadPoolData convertToEntity(ThreadPoolDataVo threadPoolDataVo) {
        ThreadPoolData threadPoolData = Convert.INSTANCE.convert(threadPoolDataVo);
        threadPoolData.setCreator(PermissionsConstants.OPERATOR_SYSTEM);
        threadPoolData.setModifier(PermissionsConstants.OPERATOR_SYSTEM);
        return null;
    }

    @Mapper
    public interface Convert {
        ThreadPoolConfigHelper.Convert INSTANCE = Mappers.getMapper(ThreadPoolConfigHelper.Convert.class);

        /**
         * 数据转换
         * @param cfgThreadPool
         * @return
         */
        CfgThreadPoolDTO convert(CfgThreadPool cfgThreadPool);

        /**
         * 数据转换
         * @param cfgThreadPoolList
         * @return
         */
        List<CfgThreadPoolDTO> convertList(List<CfgThreadPool> cfgThreadPoolList);

        /**
         * 数据转换
         * @param form
         * @return
         */
        CfgThreadPool convert(CfgThreadPoolAddForm form);

        /**
         * 数据转换
         * @param form
         * @return
         */
        CfgThreadPool convert(CfgThreadPoolUpdateForm form);


        /**
         * 数据转换
         * @param threadPoolDataVo
         * @return
         */
        ThreadPoolData convert(ThreadPoolDataVo threadPoolDataVo);

        /**
         * 数据转换
         * @param cfgThreadPool
         * @return
         */
        CfgThreadPoolVo convertToVo(CfgThreadPool cfgThreadPool);


        /**
         * 数据转换
         * @param cfgThreadPoolList
         * @return
         */
        List<CfgThreadPoolVo> convertToVo(List<CfgThreadPool> cfgThreadPoolList);

        /**
         * 数据转换
         * @param threadPoolDataList
         * @return
         */
        List<ThreadPoolDataVo> convertToThreadPoolDataVo(List<ThreadPoolData> threadPoolDataList);
    }
}
