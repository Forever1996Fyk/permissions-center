package com.ah.cloud.permissions.biz.application.manager;

import com.ah.cloud.permissions.biz.application.helper.QuartzJobHelper;
import com.ah.cloud.permissions.biz.application.service.QuartzJobTaskService;
import com.ah.cloud.permissions.biz.domain.quartz.query.QuartzJobTaskQuery;
import com.ah.cloud.permissions.biz.domain.quartz.vo.QuartzJobTaskVo;
import com.ah.cloud.permissions.biz.infrastructure.constant.PermissionsConstants;
import com.ah.cloud.permissions.biz.infrastructure.repository.bean.QuartzJobTask;
import com.ah.cloud.permissions.domain.common.PageResult;
import com.ah.cloud.permissions.enums.JobExecuteStatusEnum;
import com.ah.cloud.permissions.enums.common.DeletedEnum;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Objects;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-05-01 11:43
 **/
@Slf4j
@Component
public class QuartzJobTaskManager {
    @Resource
    private QuartzJobHelper quartzJobHelper;
    @Resource
    private QuartzJobTaskService quartzJobTaskService;

    /**
     * 任务调度记录初始化
     * @param jobId
     */
    public QuartzJobTask initQuartzJobTask(Long jobId) {
        QuartzJobTask quartzJobTask = new QuartzJobTask();
        try {
            quartzJobTask.setJobId(jobId);
            quartzJobTask.setCreator(PermissionsConstants.OPERATOR_SYSTEM);
            quartzJobTask.setModifier(PermissionsConstants.OPERATOR_SYSTEM);
            quartzJobTask.setStartTime(new Date());
            quartzJobTask.setStatus(JobExecuteStatusEnum.INIT.getStatus());
            quartzJobTaskService.save(quartzJobTask);
            return quartzJobTask;
        } catch (Exception e) {
            log.error("QuartzJobTaskManager[initQuartzJobTask] init quartz job error, jobId:{}", jobId);
        }
        return quartzJobTask;
    }

    /**
     * 更新任务调度记录
     * @param quartzJobTask
     * @param jobExecuteStatusEnum
     * @param errorMessage
     * @return
     */
    public void updateQuartzJobTask(QuartzJobTask quartzJobTask, JobExecuteStatusEnum jobExecuteStatusEnum, String errorMessage) {
        QuartzJobTask updateQuartzJobTask = new QuartzJobTask();
        updateQuartzJobTask.setId(quartzJobTask.getId());
        updateQuartzJobTask.setStatus(jobExecuteStatusEnum.getStatus());
        if (Objects.equals(jobExecuteStatusEnum, JobExecuteStatusEnum.FAILED)) {
            updateQuartzJobTask.setErrorMessage(errorMessage);
        }

        if (jobExecuteStatusEnum.isEndNode()) {
            updateQuartzJobTask.setEndTime(new Date());
        }
        quartzJobTaskService.updateById(updateQuartzJobTask);
    }

    /**
     * 分页查询任务日志
     * @param query
     * @return
     */
    public PageResult<QuartzJobTaskVo> pageQuartzJobTaskList(QuartzJobTaskQuery query) {
        PageInfo<QuartzJobTask> pageInfo = PageMethod.startPage(query.getPageNo(), query.getPageSize())
                .doSelectPageInfo(
                        () -> quartzJobTaskService.list(
                                new QueryWrapper<QuartzJobTask>().lambda()
                                        .eq(QuartzJobTask::getJobId, query.getJobId())
                                        .eq(QuartzJobTask::getDeleted, DeletedEnum.NO.value)
                        )
                );
        return quartzJobHelper.convertToTaskPageResult(pageInfo);
    }
}
