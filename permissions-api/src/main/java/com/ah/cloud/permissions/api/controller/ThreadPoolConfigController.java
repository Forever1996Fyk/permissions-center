package com.ah.cloud.permissions.api.controller;

import com.ah.cloud.permissions.biz.application.manager.ThreadPoolConfigManager;
import com.ah.cloud.permissions.biz.domain.cfg.threadpool.form.CfgThreadPoolAddForm;
import com.ah.cloud.permissions.biz.domain.cfg.threadpool.form.CfgThreadPoolUpdateForm;
import com.ah.cloud.permissions.biz.domain.cfg.threadpool.query.CfgThreadPoolQuery;
import com.ah.cloud.permissions.biz.domain.cfg.threadpool.query.ThreadPoolDataQuery;
import com.ah.cloud.permissions.biz.domain.cfg.threadpool.vo.CfgThreadPoolVo;
import com.ah.cloud.permissions.biz.domain.cfg.threadpool.vo.ThreadPoolDataVo;
import com.ah.cloud.permissions.biz.infrastructure.annotation.ApiMethodLog;
import com.ah.cloud.permissions.domain.common.PageResult;
import com.ah.cloud.permissions.domain.common.ResponseResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @program: permissions-center
 * @description: 线程池配置
 * @author: YuKai Fan
 * @create: 2022-04-26 22:16
 **/
@RestController
@RequestMapping("/threadpool/config")
public class ThreadPoolConfigController {
    @Resource
    private ThreadPoolConfigManager threadPoolConfigManager;

    /**
     * 新增线程池配置
     * @param form
     * @return
     */
    @ApiMethodLog
    @PostMapping("/add")
    public ResponseResult<Void> add(@Valid @RequestBody CfgThreadPoolAddForm form) {
        threadPoolConfigManager.addThreadPoolConfig(form);
        return ResponseResult.ok();
    }

    /**
     * 修改线程池配置
     * @param form
     * @return
     */
    @ApiMethodLog
    @PostMapping("/update")
    public ResponseResult<Void> updateById(@Valid @RequestBody CfgThreadPoolUpdateForm form) {
        threadPoolConfigManager.updateThreadPoolConfig(form);
        return ResponseResult.ok();
    }

    /**
     * 根据id查询线程池配置
     * @param id
     * @return
     */
    @GetMapping("/findById/{id}")
    public ResponseResult<CfgThreadPoolVo> findById(@PathVariable("id") Long id) {
        return ResponseResult.ok(threadPoolConfigManager.findById(id));
    }

    /**
     * 根据id删除线程池配置
     * @param id
     * @return
     */
    @PostMapping("/delete/{id}")
    public ResponseResult<Void> delete(@PathVariable("id") Long id) {
        threadPoolConfigManager.deleteById(id);
        return ResponseResult.ok();
    }

    /**
     * 分页查询线程池配置
     * @return
     */
    @GetMapping("/page")
    public ResponseResult<PageResult<CfgThreadPoolVo>> page(CfgThreadPoolQuery query) {
        return ResponseResult.ok(threadPoolConfigManager.pageCfgThreadPoolList(query));
    }

    /**
     * 分页查询线程池历史监控数据
     * @return
     */
    @GetMapping("/pageHistoryMonitorData")
    public ResponseResult<PageResult<ThreadPoolDataVo>> pageHistoryMonitorData(@Valid ThreadPoolDataQuery query) {
        return ResponseResult.ok(threadPoolConfigManager.pageHistoryMonitorData(query));
    }

    /**
     * 查看线程池实时监控数据
     * @return
     */
    @GetMapping("/findRealTimeMonitorData/{name}")
    public ResponseResult<ThreadPoolDataVo> findRealTimeMonitorData(@PathVariable("name") String name) {
        return ResponseResult.ok(threadPoolConfigManager.findRealTimeMonitorData(name));
    }
}
