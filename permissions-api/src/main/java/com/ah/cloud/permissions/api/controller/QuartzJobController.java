package com.ah.cloud.permissions.api.controller;

import com.ah.cloud.permissions.biz.application.manager.QuartzJobManager;
import com.ah.cloud.permissions.biz.application.manager.QuartzJobTaskManager;
import com.ah.cloud.permissions.biz.domain.quartz.form.QuartzJobAddForm;
import com.ah.cloud.permissions.biz.domain.quartz.form.QuartzJobChangeStatusForm;
import com.ah.cloud.permissions.biz.domain.quartz.form.QuartzJobUpdateForm;
import com.ah.cloud.permissions.biz.domain.quartz.query.QuartzJobQuery;
import com.ah.cloud.permissions.biz.domain.quartz.query.QuartzJobTaskQuery;
import com.ah.cloud.permissions.biz.domain.quartz.vo.QuartzJobTaskVo;
import com.ah.cloud.permissions.biz.domain.quartz.vo.QuartzJobVo;
import com.ah.cloud.permissions.biz.infrastructure.annotation.ApiMethodLog;
import com.ah.cloud.permissions.domain.common.PageResult;
import com.ah.cloud.permissions.domain.common.ResponseResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-05-03 18:15
 **/
@RestController
@RequestMapping("/quartz")
public class QuartzJobController {
    @Resource
    private QuartzJobManager quartzJobManager;
    @Resource
    private QuartzJobTaskManager quartzJobTaskManager;

    /**
     * 新增quartz job
     * @param form
     * @return
     */
    @ApiMethodLog
    @PostMapping("/add")
    public ResponseResult<Void> add(@Valid @RequestBody QuartzJobAddForm form) {
        quartzJobManager.addQuartzJob(form);
        return ResponseResult.ok();
    }

    /**
     * 更新quart job
     *
     * @param form
     * @return
     */
    @PostMapping("/update")
    public ResponseResult<Void> update(@Valid @RequestBody QuartzJobUpdateForm form) {
        quartzJobManager.updateQuartzJob(form);
        return ResponseResult.ok();
    }

    /**
     * 根据id删除定时任务
     * @param id
     * @return
     */
    @PostMapping("/delete/{id}")
    public ResponseResult<Void> delete(@PathVariable("id") Long id) {
        quartzJobManager.deleteQuartzJobById(id);
        return ResponseResult.ok();
    }

    /**
     * 根据id获取任务信息
     *
     * @param id
     * @return
     */
    @GetMapping("/findById/{id}")
    public ResponseResult<QuartzJobVo> findById(@PathVariable("id") Long id) {
        return ResponseResult.ok(quartzJobManager.findQuartzJobById(id));
    }

    /**
     * 根据id执行一次任务
     *
     * @param id
     * @return
     */
    @PostMapping("/runOnce/{id}")
    public ResponseResult<Void> runOnce(@PathVariable("id") Long id) {
        quartzJobManager.runOnceQuartzJob(id);
        return ResponseResult.ok();
    }

    /**
     * 变更任务状态
     * @param form
     * @return
     */
    @ApiMethodLog
    @PostMapping("/changeQuartzJobOfStatus")
    public ResponseResult<Void> changeQuartzJobOfStatus(@Valid @RequestBody QuartzJobChangeStatusForm form) {
        quartzJobManager.changeQuartzJobOfStatus(form);
        return ResponseResult.ok();
    }

    /**
     * 分页查询定时任务
     *
     * @param query
     * @return
     */
    @GetMapping("/page")
    public ResponseResult<PageResult<QuartzJobVo>> page(QuartzJobQuery query) {
        return ResponseResult.ok(quartzJobManager.pageQuartzJobList(query));
    }

    /**
     * 分页查询定时任务记录
     *
     * @param query
     * @return
     */
    @GetMapping("/task/page")
    public ResponseResult<PageResult<QuartzJobTaskVo>> page(QuartzJobTaskQuery query) {
        return ResponseResult.ok(quartzJobTaskManager.pageQuartzJobTaskList(query));
    }

}
