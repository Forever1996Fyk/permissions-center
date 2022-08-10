package com.ah.cloud.permissions.biz.application.manager;

import com.ah.cloud.permissions.biz.application.checker.QuartzJobChecker;
import com.ah.cloud.permissions.biz.application.helper.QuartzJobHelper;
import com.ah.cloud.permissions.biz.application.service.QuartzJobService;
import com.ah.cloud.permissions.biz.domain.quartz.form.QuartzJobAddForm;
import com.ah.cloud.permissions.biz.domain.quartz.form.QuartzJobChangeStatusForm;
import com.ah.cloud.permissions.biz.domain.quartz.form.QuartzJobUpdateForm;
import com.ah.cloud.permissions.biz.domain.quartz.query.QuartzJobQuery;
import com.ah.cloud.permissions.biz.domain.quartz.vo.QuartzJobVo;
import com.ah.cloud.permissions.biz.infrastructure.exception.BizException;
import com.ah.cloud.permissions.biz.infrastructure.mq.redis.message.QuartzJobChangeMessage;
import com.ah.cloud.permissions.biz.infrastructure.mq.redis.producer.QuartzJobChangeProducer;
import com.ah.cloud.permissions.biz.infrastructure.repository.bean.QuartzJob;
import com.ah.cloud.permissions.domain.common.PageResult;
import com.ah.cloud.permissions.domain.common.ProducerResult;
import com.ah.cloud.permissions.enums.QuartzJobChangeTypeEnum;
import com.ah.cloud.permissions.enums.common.DeletedEnum;
import com.ah.cloud.permissions.enums.common.ErrorCodeEnum;
import com.ah.cloud.permissions.enums.common.RedisErrorCodeEnum;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-04-29 22:03
 **/
@Slf4j
@Component
public class QuartzJobManager {
    @Resource
    private QuartzJobHelper quartzJobHelper;
    @Resource
    private QuartzJobChecker quartzJobChecker;
    @Resource
    private QuartzJobService quartzJobService;
    @Resource
    private QuartzJobChangeProducer quartzJobChangeProducer;

    /**
     * 添加定时任务
     *
     * @param form
     */
    @Transactional(rollbackFor = Exception.class)
    public void addQuartzJob(QuartzJobAddForm form) {
        quartzJobChecker.checkParams(form);
        QuartzJob quartzJob = quartzJobHelper.convertToEntity(form);
        boolean saveResult = quartzJobService.save(quartzJob);
        if (saveResult) {
            ProducerResult<Void> result = quartzJobChangeProducer.sendMessage(
                    QuartzJobChangeMessage.builder()
                            .changeTypeEnum(QuartzJobChangeTypeEnum.ADD)
                            .jobId(quartzJob.getId())
                            .build()
            );
            if (result.isFailed()) {
                throw new BizException(RedisErrorCodeEnum.PUBLISH_MESSAGE_ERROR);
            }
        }
    }

    /**
     * 更新定时任务
     *
     * @param form
     */
    public void updateQuartzJob(QuartzJobUpdateForm form) {
        QuartzJob existQuartzJob = quartzJobService.getOne(
                new QueryWrapper<QuartzJob>().lambda()
                        .eq(QuartzJob::getId, form.getId())
                        .eq(QuartzJob::getDeleted, DeletedEnum.NO.value)
        );
        if (Objects.isNull(existQuartzJob)) {
            throw new BizException(ErrorCodeEnum.QUARTZ_JOB_NOT_EXIST, String.valueOf(form.getId()));
        }
        quartzJobChecker.checkParams(form);
        QuartzJob updateQuartzJob = quartzJobHelper.convertToEntity(form);
        updateQuartzJob.setId(form.getId());
        boolean updateResult = quartzJobService.updateById(updateQuartzJob);
        if (updateResult) {
            ProducerResult<Void> result = quartzJobChangeProducer.sendMessage(
                    QuartzJobChangeMessage.builder()
                            .changeTypeEnum(QuartzJobChangeTypeEnum.UPDATE)
                            .jobId(existQuartzJob.getId())
                            .build()
            );
            if (result.isFailed()) {
                throw new BizException(RedisErrorCodeEnum.PUBLISH_MESSAGE_ERROR);
            }
        }
    }

    /**
     * 根据id删除定时任务
     *
     * @param jodId
     */
    public void deleteQuartzJobById(Long jodId) {
        QuartzJob existQuartzJob = quartzJobService.getOne(
                new QueryWrapper<QuartzJob>().lambda()
                        .eq(QuartzJob::getId, jodId)
                        .eq(QuartzJob::getDeleted, DeletedEnum.NO.value)
        );
        if (Objects.isNull(existQuartzJob)) {
            return;
        }
        QuartzJob deleteQuartzJob = new QuartzJob();
        deleteQuartzJob.setDeleted(jodId);
        deleteQuartzJob.setId(jodId);
        boolean deleteResult = quartzJobService.updateById(deleteQuartzJob);
        if (deleteResult) {
            ProducerResult<Void> result = quartzJobChangeProducer.sendMessage(
                    QuartzJobChangeMessage.builder()
                            .changeTypeEnum(QuartzJobChangeTypeEnum.DELETED)
                            .jobId(existQuartzJob.getId())
                            .build()
            );
            if (result.isFailed()) {
                throw new BizException(RedisErrorCodeEnum.PUBLISH_MESSAGE_ERROR);
            }
        }
    }

    /**
     * 执行一次任务
     *
     * @param jobId
     */
    public void runOnceQuartzJob(Long jobId) {
        QuartzJob quartzJob = quartzJobService.getOne(
                new QueryWrapper<QuartzJob>().lambda()
                        .eq(QuartzJob::getId, jobId)
                        .eq(QuartzJob::getDeleted, DeletedEnum.NO.value)
        );
        if (Objects.isNull(quartzJob)) {
            throw new BizException(ErrorCodeEnum.QUARTZ_JOB_NOT_EXIST, String.valueOf(jobId));
        }

        ProducerResult<Void> result = quartzJobChangeProducer.sendMessage(
                QuartzJobChangeMessage.builder()
                        .changeTypeEnum(QuartzJobChangeTypeEnum.RUN)
                        .jobId(quartzJob.getId())
                        .build()
        );
        if (result.isFailed()) {
            throw new BizException(RedisErrorCodeEnum.PUBLISH_MESSAGE_ERROR);
        }
    }

    /**
     * 变更定时任务状态
     *
     * @param form
     */
    public void changeQuartzJobOfStatus(QuartzJobChangeStatusForm form) {
        QuartzJob quartzJob = quartzJobService.getOne(
                new QueryWrapper<QuartzJob>().lambda()
                        .eq(QuartzJob::getId, form.getJobId())
                        .eq(QuartzJob::getDeleted, DeletedEnum.NO.value)
        );
        if (Objects.isNull(quartzJob)) {
            throw new BizException(ErrorCodeEnum.QUARTZ_JOB_NOT_EXIST, String.valueOf(form.getJobId()));
        }
        QuartzJob updateQuartzJob = new QuartzJob();
        updateQuartzJob.setId(quartzJob.getId());
        updateQuartzJob.setStatus(form.getStatus());
        boolean updateResult = quartzJobService.update(
                updateQuartzJob,
                new UpdateWrapper<QuartzJob>().lambda()
                        .eq(QuartzJob::getId, form.getJobId())
                        .eq(QuartzJob::getVersion, quartzJob.getVersion())
        );
        if (!updateResult) {
            throw new BizException(ErrorCodeEnum.VERSION_ERROR);
        }

        ProducerResult<Void> result = quartzJobChangeProducer.sendMessage(
                QuartzJobChangeMessage.builder()
                        .changeTypeEnum(QuartzJobChangeTypeEnum.CHANGE_STATUS)
                        .jobId(quartzJob.getId())
                        .build()
        );
        if (result.isFailed()) {
            throw new BizException(RedisErrorCodeEnum.PUBLISH_MESSAGE_ERROR);
        }
    }

    /**
     * 根据id查询定时任务
     *
     * @param jodId
     * @return
     */
    public QuartzJobVo findQuartzJobById(Long jodId) {
        QuartzJob quartzJob = quartzJobService.getOne(
                new QueryWrapper<QuartzJob>().lambda()
                        .eq(QuartzJob::getId, jodId)
                        .eq(QuartzJob::getDeleted, DeletedEnum.NO.value)
        );
        if (Objects.isNull(quartzJob)) {
            throw new BizException(ErrorCodeEnum.QUARTZ_JOB_NOT_EXIST, String.valueOf(jodId));
        }
        return quartzJobHelper.convertToVo(quartzJob);
    }

    /**
     * 分页查询定时任务
     *
     * @param query
     * @return
     */
    public PageResult<QuartzJobVo> pageQuartzJobList(QuartzJobQuery query) {
        PageInfo<QuartzJob> pageInfo = PageMethod.startPage(query.getPageNo(), query.getPageSize())
                .doSelectPageInfo(
                        () -> quartzJobService.list(
                                new QueryWrapper<QuartzJob>().lambda()
                                        .like(
                                                StringUtils.isNotBlank(query.getJobName()),
                                                QuartzJob::getJobName,
                                                query.getJobName()
                                        )
                                        .eq(
                                                StringUtils.isNotBlank(query.getJobGroup()),
                                                QuartzJob::getJobGroup,
                                                query.getJobGroup()
                                        )
                                        .eq(
                                                query.getConcurrent() != null,
                                                QuartzJob::getConcurrent,
                                                query.getConcurrent()
                                        )
                                        .eq(
                                                query.getExecutePolicy() != null,
                                                QuartzJob::getExecutePolicy,
                                                query.getExecutePolicy()
                                        )
                                        .eq(
                                                query.getStatus() != null,
                                                QuartzJob::getStatus,
                                                query.getStatus()
                                        )
                        )
                );
        return quartzJobHelper.convertToPageResult(pageInfo);
    }

}
