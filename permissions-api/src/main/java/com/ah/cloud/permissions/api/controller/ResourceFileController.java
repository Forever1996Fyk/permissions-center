package com.ah.cloud.permissions.api.controller;

import com.ah.cloud.permissions.biz.application.manager.ResourceFileManager;
import com.ah.cloud.permissions.biz.domain.resource.form.ResourceFileForm;
import com.ah.cloud.permissions.biz.domain.resource.query.ResourceFileQuery;
import com.ah.cloud.permissions.biz.domain.resource.vo.ResourceFileDetailVo;
import com.ah.cloud.permissions.biz.domain.resource.vo.ResourceFileVo;
import com.ah.cloud.permissions.biz.infrastructure.constant.PermissionsConstants;
import com.ah.cloud.permissions.domain.common.PageResult;
import com.ah.cloud.permissions.domain.common.ResponseResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * @program: permissions-center
 * @description: 资源文件
 * @author: YuKai Fan
 * @create: 2022-05-07 15:34
 **/
@RestController
@RequestMapping("/resource")
public class ResourceFileController {
    @Resource
    private ResourceFileManager resourceFileManager;

    /**
     * 文件上传
     * @return 文件资源id
     */
    @PostMapping("/upload")
    public ResponseResult<Long> upload(@RequestBody @Valid ResourceFileForm form, HttpServletRequest request) {
        return ResponseResult.ok(resourceFileManager.upload(request, form));
    }

    /**
     * 文件上传
     * @return 文件地址
     */
    @PostMapping("/uploadForUrl")
    public ResponseResult<String> uploadForUrl(HttpServletRequest request) {
        return ResponseResult.ok(resourceFileManager.uploadForUrl(request));
    }

    /**
     * 文件下载
     * @param resId
     */
    @PostMapping("/download/{resId}")
    public void download(@PathVariable("resId") Long resId, HttpServletResponse response) {
        resourceFileManager.download(response, resId);
    }

    /**
     * 根据id获取资源信息
     *
     * @param id
     * @return
     */
    @GetMapping("/findById/{id}")
    public ResponseResult<ResourceFileDetailVo> findById(@PathVariable("id") Long id) {
        return ResponseResult.ok(resourceFileManager.findById(id));
    }

    /**
     * 根据id删除资源
     * @param id
     * @return
     */
    @PostMapping("/delete/{id}")
    public ResponseResult<Void> delete(@PathVariable("id") Long id) {
        resourceFileManager.deleteById(id);
        return ResponseResult.ok();
    }

    /**
     * 分页查询文件资源
     * @param query
     * @return
     */
    @GetMapping("/page")
    public ResponseResult<PageResult<ResourceFileVo>> page(ResourceFileQuery query) {
        return ResponseResult.ok(resourceFileManager.pageResourceFileList(query));
    }
}
