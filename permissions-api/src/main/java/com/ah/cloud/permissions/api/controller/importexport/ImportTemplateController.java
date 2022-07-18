package com.ah.cloud.permissions.api.controller.importexport;

import com.ah.cloud.permissions.domain.common.PageResult;
import com.ah.cloud.permissions.domain.common.ResponseResult;
import com.ah.cloud.permissions.task.application.manager.ImportTemplateManager;
import com.ah.cloud.permissions.task.domain.form.ImportTemplateForm;
import com.ah.cloud.permissions.task.domain.form.ImportTemplateUpdateForm;
import com.ah.cloud.permissions.task.domain.query.ImportTemplateQuery;
import com.ah.cloud.permissions.task.domain.vo.ImportTemplateDownloadVo;
import com.ah.cloud.permissions.task.domain.vo.ImportTemplateVo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-15 11:54
 **/
@RestController
@RequestMapping("/importTemplate")
public class ImportTemplateController {
    @Resource
    private ImportTemplateManager importTemplateManager;

    /**
     * 创建新导入模板
     * @param form
     * @return
     */
    @PostMapping("/add")
    public ResponseResult<Void> add(@RequestBody @Valid ImportTemplateForm form) {
        importTemplateManager.createImportTemplate(form);
        return ResponseResult.ok();
    }

    /**
     * 更新导入模板
     * @param form
     * @return
     */
    @PostMapping("/update")
    public ResponseResult<Void> update(@RequestBody @Valid ImportTemplateUpdateForm form) {
        importTemplateManager.updateImportTemplate(form);
        return ResponseResult.ok();
    }

    /**
     * 根据id删除模板
     * @param id
     * @return
     */
    @PostMapping("/deleteById/{id}")
    public ResponseResult<Void> deleteById(@PathVariable(value = "id") Long id) {
        importTemplateManager.deleteImportTemplate(id);
        return ResponseResult.ok();
    }

    /**
     * 根据id获取模板信息
     * @param id
     * @return
     */
    @GetMapping("/findById/{id}")
    public ResponseResult<ImportTemplateVo> findById(@PathVariable(value = "id") Long id) {
        return ResponseResult.ok(importTemplateManager.findById(id));
    }

    /**
     * 分页获取模板信息
     * @param query
     * @return
     */
    @GetMapping("/page")
    public ResponseResult<PageResult<ImportTemplateVo>> page(ImportTemplateQuery query) {
        return ResponseResult.ok(importTemplateManager.pageImportTemplateVoList(query));
    }

    /**
     * 根据业务类型获取下载模板信息
     * @param bizType
     * @return
     */
    @GetMapping("/findDownloadTemplate/{bizType}")
    public ResponseResult<ImportTemplateDownloadVo> findDownloadTemplate(@PathVariable(value = "bizType") String bizType) {
        return ResponseResult.ok(importTemplateManager.findImportTemplateDownloadInfoByBizType(bizType));
    }
}
