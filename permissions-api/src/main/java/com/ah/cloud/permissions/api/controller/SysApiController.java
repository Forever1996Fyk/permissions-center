package com.ah.cloud.permissions.api.controller;

import com.ah.cloud.permissions.biz.application.manager.SysApiManager;
import com.ah.cloud.permissions.biz.domain.api.form.SysApiAddForm;
import com.ah.cloud.permissions.biz.domain.api.form.SysApiUpdateForm;
import com.ah.cloud.permissions.biz.domain.api.query.SysApiQuery;
import com.ah.cloud.permissions.biz.domain.api.vo.SysApiVo;
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
 * @create: 2022-03-26 19:32
 **/
@RestController
@RequestMapping("/api")
public class SysApiController {
    @Resource
    private SysApiManager sysApiManager;

    /**
     * 新增系统api
     *
     * @param form
     * @return
     */
    @ApiMethodLog
    @PostMapping("/add")
    public ResponseResult<Void> add(@Valid @RequestBody SysApiAddForm form) {
        sysApiManager.addSysApi(form);
        return ResponseResult.ok();
    }

    /**
     * 更新系统api
     *
     * @param form
     * @return
     */
    @ApiMethodLog
    @PostMapping("/update")
    public ResponseResult<Void> update(@Valid @RequestBody SysApiUpdateForm form) {
        sysApiManager.updateSysApi(form);
        return ResponseResult.ok();
    }

    /**
     * 根据id删除系统api
     * @param id
     * @return
     */
    @ApiMethodLog
    @PostMapping("/delete/{id}")
    public ResponseResult<Void> delete(@PathVariable(value = "id") Long id) {
        sysApiManager.deleteSysApiById(id);
        return ResponseResult.ok();
    }

    /**
     * 根据id查询接口信息
     * @param id
     * @return
     */
    @GetMapping("/findById/{id}")
    public ResponseResult<SysApiVo> findById(@PathVariable(value = "id") Long id) {
        return ResponseResult.ok(sysApiManager.findById(id));
    }

    /**
     * 分页查询系统api集合
     * @param query
     * @return
     */
    @GetMapping("/page")
    public ResponseResult<PageResult<SysApiVo>> page(SysApiQuery query) {
        return ResponseResult.ok(sysApiManager.pageSysApiList(query));
    }
}
