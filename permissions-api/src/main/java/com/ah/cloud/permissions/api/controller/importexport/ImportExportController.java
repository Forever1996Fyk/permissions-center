package com.ah.cloud.permissions.api.controller.importexport;

import com.ah.cloud.permissions.biz.application.helper.ResourceFileHelper;
import com.ah.cloud.permissions.biz.infrastructure.util.FileUtils;
import com.ah.cloud.permissions.domain.common.PageResult;
import com.ah.cloud.permissions.domain.common.ResponseResult;
import com.ah.cloud.permissions.enums.common.ErrorCodeEnum;
import com.ah.cloud.permissions.task.application.manager.ImportExportTaskManager;
import com.ah.cloud.permissions.task.domain.dto.DataImportDTO;
import com.ah.cloud.permissions.task.domain.enums.ImportExportBizTypeEnum;
import com.ah.cloud.permissions.task.domain.form.DataExportForm;
import com.ah.cloud.permissions.task.domain.form.DataImportForm;
import com.ah.cloud.permissions.task.domain.query.ImportExportTaskQuery;
import com.ah.cloud.permissions.task.domain.vo.ImportExportBizTypeVo;
import com.ah.cloud.permissions.task.domain.vo.ImportExportTaskVo;
import com.ah.cloud.permissions.task.infrastructure.constant.ImportExportTaskConstants;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-15 11:14
 **/
@RestController
@RequestMapping("/importExportTask")
public class ImportExportController {
    @Resource
    private ResourceFileHelper resourceFileHelper;
    @Resource
    private ImportExportTaskManager importExportTaskManager;

    /**
     * 创建导出任务
     * @param form
     * @return
     */
    @PostMapping("/createExportTask")
    public ResponseResult<Void> createExportTask(@RequestBody @Valid DataExportForm form) {
        importExportTaskManager.createExportTask(form);
        return ResponseResult.ok();
    }

    /**
     * 创建导入任务
     * @param request
     * @return
     */
    @PostMapping("/createImportTask")
    public ResponseResult<Void> createImportTask(HttpServletRequest request) throws IOException {
        MultipartFile file = resourceFileHelper.getMultipartFileFromRequest(request);
        Map<String, String[]> map = request.getParameterMap();
        String[] args = map.get(ImportExportTaskConstants.REQUEST_PARAM_BIZ_TYPE_KEY);
        if (args == null || args.length == 0) {
            return ResponseResult.newResponseArgs(ErrorCodeEnum.PARAM_MISS, ImportExportTaskConstants.REQUEST_PARAM_BIZ_TYPE_KEY);
        }
        String bizType = args[0];
        DataImportDTO dto = DataImportDTO.builder()
                .inputStream(file.getInputStream())
                .bizTypeEnum(ImportExportBizTypeEnum.getByCode(bizType))
                .build();
        importExportTaskManager.createImportTask(dto);
        return ResponseResult.ok();
    }

    /**
     * 分页查询任务列表
     * @param query
     * @return
     */
    @GetMapping("/page")
    public ResponseResult<PageResult<ImportExportTaskVo>> page(ImportExportTaskQuery query) {
        return ResponseResult.ok(importExportTaskManager.pageImportExportList(query));
    }

    /**
     * 选择业务类型列表
     * @param taskType
     * @return
     */
    @GetMapping("/selectImportExportBizTypeList")
    public ResponseResult<List<ImportExportBizTypeVo>> selectImportExportBizTypeList(Integer taskType) {
        return ResponseResult.ok(importExportTaskManager.selectImportExportBizTypeList(taskType));
    }

}
