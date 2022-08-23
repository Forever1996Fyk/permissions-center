package com.ah.cloud.permissions.api.controller;

import com.ah.cloud.permissions.biz.application.manager.SysDeptManager;
import com.ah.cloud.permissions.biz.domain.dept.form.DeptAddForm;
import com.ah.cloud.permissions.biz.domain.dept.form.DeptUpdateForm;
import com.ah.cloud.permissions.biz.domain.dept.query.SysDeptQuery;
import com.ah.cloud.permissions.biz.domain.dept.query.SysDeptTreeSelectQuery;
import com.ah.cloud.permissions.biz.domain.dept.vo.SysDeptTreeSelectVo;
import com.ah.cloud.permissions.biz.domain.dept.vo.SysDeptTreeVo;
import com.ah.cloud.permissions.biz.domain.dept.vo.SysDeptVo;
import com.ah.cloud.permissions.biz.domain.menu.query.SysMenuTreeSelectQuery;
import com.ah.cloud.permissions.biz.domain.menu.tree.SysMenuTreeSelectVo;
import com.ah.cloud.permissions.domain.common.ResponseResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-19 09:48
 **/
@RestController
@RequestMapping("/dept")
public class SysDeptController {
    @Resource
    private SysDeptManager sysDeptManager;

    /**
     * 添加部门
     * @param form
     * @return
     */
    @PostMapping("/add")
    public ResponseResult<Void> add(@RequestBody @Valid DeptAddForm form) {
        sysDeptManager.addDept(form);
        return ResponseResult.ok();
    }

    /**
     * 更新部门
     * @param form
     * @return
     */
    @PostMapping("/update")
    public ResponseResult<Void> update(@RequestBody @Valid DeptUpdateForm form) {
        sysDeptManager.updateSysDept(form);
        return ResponseResult.ok();
    }

    /**
     * 删除部门
     * @param id
     * @return
     */
    @PostMapping("/deleteById/{id}")
    public ResponseResult<Void> deleteById(@PathVariable(value = "id") Long id) {
        sysDeptManager.deleteSysDept(id);
        return ResponseResult.ok();
    }

    /**
     * 查询部门
     * @param id
     * @return
     */
    @GetMapping("/findById/{id}")
    public ResponseResult<SysDeptVo> findById(@PathVariable(value = "id") Long id) {
        return ResponseResult.ok(sysDeptManager.findById(id));
    }

    /**
     * 查询部门树
     * @param query
     * @return
     */
    @GetMapping("/listSysDeptTree")
    public ResponseResult<List<SysDeptTreeVo>> listSysDeptTree(SysDeptQuery query) {
        return ResponseResult.ok(sysDeptManager.listSysDeptTree(query));
    }

    /**
     * 获取部门树形选择结构列表
     *
     * @param query
     * @return
     */
    @GetMapping("/listDeptSelectTree")
    public ResponseResult<List<SysDeptTreeSelectVo>> listDeptSelectTree(SysDeptTreeSelectQuery query) {
        return ResponseResult.ok(sysDeptManager.listDeptSelectTree(query));
    }

}
