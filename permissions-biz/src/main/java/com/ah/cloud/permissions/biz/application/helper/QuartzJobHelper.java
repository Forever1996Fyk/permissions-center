package com.ah.cloud.permissions.biz.application.helper;

import com.ah.cloud.permissions.biz.domain.api.vo.SysApiVo;
import com.ah.cloud.permissions.biz.domain.quartz.dto.ScheduleJobDTO;
import com.ah.cloud.permissions.biz.domain.quartz.form.QuartzJobAddForm;
import com.ah.cloud.permissions.biz.domain.quartz.form.QuartzJobUpdateForm;
import com.ah.cloud.permissions.biz.domain.quartz.vo.QuartzJobTaskVo;
import com.ah.cloud.permissions.biz.domain.quartz.vo.QuartzJobVo;
import com.ah.cloud.permissions.biz.infrastructure.repository.bean.QuartzJob;
import com.ah.cloud.permissions.biz.infrastructure.repository.bean.QuartzJobTask;
import com.ah.cloud.permissions.biz.infrastructure.util.SecurityUtils;
import com.ah.cloud.permissions.domain.common.PageResult;
import com.ah.cloud.permissions.enums.ExecutePolicyEnum;
import com.ah.cloud.permissions.enums.JobStatusEnum;
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
 * @create: 2022-04-30 10:52
 **/
@Component
public class QuartzJobHelper {

    /**
     * 数据转换
     * @param form
     * @return
     */
    public QuartzJob convertToEntity(QuartzJobAddForm form) {
        QuartzJob quartzJob = Convert.INSTANCE.convertToEntity(form);
        quartzJob.setStatus(JobStatusEnum.PAUSE.getStatus());
        quartzJob.setCreator(SecurityUtils.getUserNameBySession());
        quartzJob.setModifier(SecurityUtils.getUserNameBySession());
        return quartzJob;
    }

    /**
     * 数据转换
     * @param form
     * @return
     */
    public QuartzJob convertToEntity(QuartzJobUpdateForm form) {
        QuartzJob quartzJob = Convert.INSTANCE.convertToEntity(form);
        quartzJob.setModifier(SecurityUtils.getUserNameBySession());
        return quartzJob;
    }

    /**
     * 数据转换
     * @param quartzJob
     * @return
     */
    public ScheduleJobDTO convertScheduleJob(QuartzJob quartzJob) {
        return Convert.INSTANCE.convert(quartzJob);
    }

    /**
     * 数据转换
     * @param pageInfo
     * @return
     */
    public PageResult<QuartzJobVo> convertToPageResult(PageInfo<QuartzJob> pageInfo) {
        PageResult<QuartzJobVo> result = new PageResult<>();
        result.setTotal(pageInfo.getTotal());
        result.setPageNum(pageInfo.getPageNum());
        result.setRows(Convert.INSTANCE.convertToVO(pageInfo.getList()));
        result.setPageSize(pageInfo.getSize());
        result.setPages(pageInfo.getPages());
        return result;
    }

    /**
     * 数据转换
     * @param quartzJob
     * @return
     */
    public QuartzJobVo convertToVo(QuartzJob quartzJob) {
        return Convert.INSTANCE.convertToVO(quartzJob);
    }

    public PageResult<QuartzJobTaskVo> convertToTaskPageResult(PageInfo<QuartzJobTask> pageInfo) {
        PageResult<QuartzJobTaskVo> result = new PageResult<>();
        result.setTotal(pageInfo.getTotal());
        result.setPageNum(pageInfo.getPageNum());
        result.setRows(Convert.INSTANCE.convertToTaskVO(pageInfo.getList()));
        result.setPageSize(pageInfo.getSize());
        result.setPages(pageInfo.getPages());
        return result;
    }


    @Mapper(uses = ExecutePolicyEnum.class)
    public interface Convert {
        QuartzJobHelper.Convert INSTANCE = Mappers.getMapper(QuartzJobHelper.Convert.class);

        /**
         * 数据转换
         * @param form
         * @return
         */
        QuartzJob convertToEntity(QuartzJobAddForm form);

        /**
         * 数据转换
         * @param form
         * @return
         */
        QuartzJob convertToEntity(QuartzJobUpdateForm form);

        /**
         * 数据转换
         * @param quartzJob
         * @return
         */
        @Mappings({
                @Mapping(target = "executePolicyEnum", source = "executePolicy"),
                @Mapping(target = "jobId", source = "id"),
        })
        ScheduleJobDTO convert(QuartzJob quartzJob);

        /**
         * 数据转换
         * @param quartzJob
         * @return
         */
        QuartzJobVo convertToVO(QuartzJob quartzJob);

        /**
         * 数据转换
         * @param quartzJobList
         * @return
         */
        List<QuartzJobVo> convertToVO(List<QuartzJob> quartzJobList);

        /**
         * 数据转换
         * @param quartzJobTask
         * @return
         */
        QuartzJobTaskVo convertToTaskVO(QuartzJobTask quartzJobTask);

        /**
         * 数据转换
         * @param quartzJobTaskList
         * @return
         */
        List<QuartzJobTaskVo> convertToTaskVO(List<QuartzJobTask> quartzJobTaskList);
    }
}
