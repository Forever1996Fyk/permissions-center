package com.ah.cloud.permissions.api.controller;

import com.ah.cloud.permissions.biz.application.manager.SysDictDetailManager;
import com.ah.cloud.permissions.biz.domain.dict.form.SysDictDetailAddForm;
import com.ah.cloud.permissions.biz.domain.dict.form.SysDictDetailUpdateForm;
import com.ah.cloud.permissions.biz.domain.dict.query.SysDictDetailQuery;
import com.ah.cloud.permissions.biz.domain.dict.vo.SelectDictLabelVo;
import com.ah.cloud.permissions.biz.domain.dict.vo.SysDictDetailVo;
import com.ah.cloud.permissions.domain.common.PageResult;
import com.ah.cloud.permissions.domain.common.ResponseResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-19 17:48
 **/
@RestController
@RequestMapping("/dictDetail")
public class SysDictDetailController {
    @Resource
    private SysDictDetailManager sysDictDetailManager;

    /**
     * 添加字典
     * @param form
     * @return
     */
    @PostMapping("/add")
    public ResponseResult<Void> add(@RequestBody @Valid SysDictDetailAddForm form) {
        sysDictDetailManager.addSysDictDetail(form);
        return ResponseResult.ok();
    }

    /**
     * 更新字典
     * @param form
     * @return
     */
    @PostMapping("/update")
    public ResponseResult<Void> update(@RequestBody @Valid SysDictDetailUpdateForm form) {
        sysDictDetailManager.updateSysDictDetail(form);
        return ResponseResult.ok();
    }

    /**
     * 删除字典
     * @param id
     * @return
     */
    @PostMapping("/deleteById/{id}")
    public ResponseResult<Void> deleteById(@PathVariable(value = "id") Long id) {
        sysDictDetailManager.deleteById(id);
        return ResponseResult.ok();
    }

    /**
     * 根据id获取字典
     * @param id
     * @return
     */
    @GetMapping("/findById/{id}")
    public ResponseResult<SysDictDetailVo> findById(@PathVariable(value = "id") Long id) {
        return ResponseResult.ok(sysDictDetailManager.findById(id));
    }

    /**
     * 分页查询字典
     * @param query
     * @return
     */
    @GetMapping("/page")
    public ResponseResult<PageResult<SysDictDetailVo>> page(SysDictDetailQuery query) {
        return ResponseResult.ok(sysDictDetailManager.pageSysDictDetailList(query));
    }

    /**
     * 根据字典编码选择标签列表
     * @param dictCode
     * @return
     */
    @GetMapping("/selectDictLabelList/{dictCode}")
    public ResponseResult<List<SelectDictLabelVo>> selectDictLabelList(@PathVariable(value = "dictCode") String dictCode) {
        return ResponseResult.ok(sysDictDetailManager.selectDictLabelList(dictCode));
    }
}
