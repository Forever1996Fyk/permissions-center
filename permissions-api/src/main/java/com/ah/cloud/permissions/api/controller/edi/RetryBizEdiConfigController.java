package com.ah.cloud.permissions.api.controller.edi;

import com.ah.cloud.permissions.domain.common.PageResult;
import com.ah.cloud.permissions.domain.common.ResponseResult;
import com.ah.cloud.permissions.edi.application.manager.RetryBizConfigManager;
import com.ah.cloud.permissions.edi.domain.config.form.AddCfgBizRetryForm;
import com.ah.cloud.permissions.edi.domain.config.form.UpdateCfgBizRetryForm;
import com.ah.cloud.permissions.edi.domain.config.query.CfgBizRetryPageQuery;
import com.ah.cloud.permissions.edi.domain.config.vo.CfgBizRetryVo;
import com.ah.cloud.permissions.enums.edi.EdiTypeEnum;
import liquibase.pro.packaged.V;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @program: permissions-center
 * @description: EDI配置controller
 * @author: YuKai Fan
 * @create: 2022-07-04 18:01
 **/
@RestController
@RequestMapping("/edi-conf")
public class RetryBizEdiConfigController {
    @Resource
    private RetryBizConfigManager retryBizConfigManager;

    /**
     * 新增重新业务类型
     * @param form
     * @return
     */
    @PostMapping("/add")
    public ResponseResult<Void> add(@RequestBody @Valid AddCfgBizRetryForm form) {
        retryBizConfigManager.addRetryBizConfig(form);
        return ResponseResult.ok();
    }

    /**
     * 更新重试业务类型(不允许更新类型字段)
     * @param form
     * @return
     */
    @PostMapping("/update")
    public ResponseResult<Void> update(@RequestBody @Valid UpdateCfgBizRetryForm form) {
        retryBizConfigManager.updateRetryBizConfig(form);
        return ResponseResult.ok();
    }

    /**
     * 根据id查询业务类型信息
     * @param id
     * @param ediType
     * @return
     */
    @GetMapping("/findById")
    public ResponseResult<CfgBizRetryVo> findById(Long id, Integer ediType) {
        EdiTypeEnum ediTypeEnum = EdiTypeEnum.getByType(ediType);
        return ResponseResult.ok(retryBizConfigManager.findById(id, ediTypeEnum.isTech()));
    }

    /**
     * 分页查询重试业务类型列表
     * @param query
     * @return
     */
    @GetMapping("/page")
    public ResponseResult<PageResult<CfgBizRetryVo>> page(CfgBizRetryPageQuery query) {
        return ResponseResult.ok(retryBizConfigManager.pageCfgBizRetryList(query));
    }

    /**
     * 停用重试业务
     *
     * @param id
     * @param ediType
     * @return
     */
    @GetMapping("/stopBizRetryConfig")
    public ResponseResult<Void> stopBizRetryConfig(Long id, Integer ediType) {
        EdiTypeEnum ediTypeEnum = EdiTypeEnum.getByType(ediType);
        retryBizConfigManager.stopBizRetryConfig(id, ediTypeEnum.isTech());
        return ResponseResult.ok();
    }

    /**
     * 启动重试业务
     *
     * @param id
     * @param ediType
     * @return
     */
    @GetMapping("/startBizRetryConfig")
    public ResponseResult<Void> startBizRetryConfig(Long id, Integer ediType) {
        EdiTypeEnum ediTypeEnum = EdiTypeEnum.getByType(ediType);
        retryBizConfigManager.startBizRetryConfig(id, ediTypeEnum.isTech());
        return ResponseResult.ok();
    }

}
