package com.ah.cloud.permissions.api.controller;

import com.ah.cloud.permissions.biz.application.manager.SysDictManager;
import com.ah.cloud.permissions.biz.domain.dict.form.SysDictAddForm;
import com.ah.cloud.permissions.biz.domain.dict.form.SysDictUpdateForm;
import com.ah.cloud.permissions.biz.domain.dict.query.SysDictQuery;
import com.ah.cloud.permissions.biz.domain.dict.vo.SysDictVo;
import com.ah.cloud.permissions.domain.common.PageResult;
import com.ah.cloud.permissions.domain.common.ResponseResult;
import com.github.pagehelper.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-19 17:48
 **/
@RestController
@RequestMapping("/dict")
public class SysDictController {
    @Resource
    private SysDictManager sysDictManager;

    /**
     * 添加字典
     * @param form
     * @return
     */
    @PostMapping("/add")
    public ResponseResult<Void> add(@RequestBody @Valid SysDictAddForm form) {
        sysDictManager.addSysDict(form);
        return ResponseResult.ok();
    }

    /**
     * 更新字典
     * @param form
     * @return
     */
    @PostMapping("/update")
    public ResponseResult<Void> update(@RequestBody @Valid SysDictUpdateForm form) {
        sysDictManager.updateSysDict(form);
        return ResponseResult.ok();
    }

    /**
     * 删除字典
     * @param id
     * @return
     */
    @PostMapping("/deleteById/{id}")
    public ResponseResult<Void> deleteById(@PathVariable(value = "id") Long id) {
        sysDictManager.deleteById(id);
        return ResponseResult.ok();
    }

    /**
     * 根据id获取字典
     * @param id
     * @return
     */
    @GetMapping("/findById/{id}")
    public ResponseResult<SysDictVo> findById(@PathVariable(value = "id") Long id) {
        return ResponseResult.ok(sysDictManager.findById(id));
    }

    /**
     * 分页查询字典
     * @param query
     * @return
     */
    @GetMapping("/page")
    public ResponseResult<PageResult<SysDictVo>> page(SysDictQuery query) {
        return ResponseResult.ok(sysDictManager.pageSysDictList(query));
    }
}
