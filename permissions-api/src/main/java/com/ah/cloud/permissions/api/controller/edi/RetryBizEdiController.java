package com.ah.cloud.permissions.api.controller.edi;

import com.ah.cloud.permissions.domain.common.PageResult;
import com.ah.cloud.permissions.domain.common.ResponseResult;
import com.ah.cloud.permissions.edi.application.manager.RetryBizRecordManager;
import com.ah.cloud.permissions.edi.domain.record.form.CloseRetryRecordForm;
import com.ah.cloud.permissions.edi.domain.record.form.DoRetryRecordForm;
import com.ah.cloud.permissions.edi.domain.record.query.RetryBizRecordScanQuery;
import com.ah.cloud.permissions.edi.domain.record.vo.RetryBizRecordVo;
import com.ah.cloud.permissions.edi.domain.record.vo.RetryBizTypeVo;
import com.ah.cloud.permissions.enums.edi.EdiTypeEnum;
import liquibase.pro.packaged.V;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-04 18:09
 **/
@RestController
@RequestMapping("/edi-record")
public class RetryBizEdiController {
    @Resource
    private RetryBizRecordManager retryBizRecordManager;

    /**
     * 分页查询重试记录
     * @param query
     * @return
     */
    @GetMapping("/page")
    public ResponseResult<PageResult<RetryBizRecordVo>> page(RetryBizRecordScanQuery query) {
        return ResponseResult.ok(retryBizRecordManager.pageRetryBizRecordList(query));
    }

    /**
     * 重试单条记录
     * @param form
     * @return
     */
    @PostMapping("/doRetryRecords")
    public ResponseResult<Void> doRetryRecords(@RequestBody @Valid DoRetryRecordForm form) {
        retryBizRecordManager.doRetryRecords(form);
        return ResponseResult.ok();
    }

    /**
     * 关闭单条记录
     * @param form
     * @return
     */
    @PostMapping("/closeRetryRecord")
    public ResponseResult<Void> closeRetryRecord(@RequestBody @Valid CloseRetryRecordForm form) {
        retryBizRecordManager.closeRetryRecord(form);
        return ResponseResult.ok();
    }

    /**
     * 选择业务类型
     * @param ediType
     * @return
     */
    @GetMapping("/selectRetryBizTypeList/{ediType}")
    public ResponseResult<List<RetryBizTypeVo>> selectRetryBizTypeList(@PathVariable(value = "ediType") Integer ediType) {
        return ResponseResult.ok(retryBizRecordManager.selectRetryBizTypeList(EdiTypeEnum.getByType(ediType)));
    }
}
