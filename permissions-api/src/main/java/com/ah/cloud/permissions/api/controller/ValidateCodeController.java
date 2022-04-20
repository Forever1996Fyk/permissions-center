package com.ah.cloud.permissions.api.controller;

import com.ah.cloud.permissions.api.assembler.ValidateCodeAssembler;
import com.ah.cloud.permissions.biz.application.manager.ValidateCodeManager;
import com.ah.cloud.permissions.biz.domain.code.SendResult;
import com.ah.cloud.permissions.biz.domain.code.form.LoginCodeForm;
import com.ah.cloud.permissions.biz.domain.code.form.LoginEmailCodeForm;
import com.ah.cloud.permissions.biz.domain.code.form.LoginSmsCodeForm;
import com.ah.cloud.permissions.domain.common.ResponseResult;
import org.checkerframework.checker.units.qual.A;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @program: permissions-center
 * @description: 验证码接口
 * @author: YuKai Fan
 * @create: 2022-01-06 21:29
 **/
@RestController
@RequestMapping("/validateCode")
public class ValidateCodeController {
    @Resource
    private ValidateCodeAssembler assembler;
    @Resource
    private ValidateCodeManager validateCodeManager;

    /**
     * 发送验证码
     * @param form
     * @return
     */
    @PostMapping("/sendCode")
    public ResponseResult<Void> sendCode(@RequestBody @Valid LoginCodeForm form) {
        validateCodeManager.sendCode(assembler.convert(form.getSender()));
        return ResponseResult.ok();
    }

    /**
     * 短信验证码
     * @param form
     * @return
     */
    @PostMapping("/sendSmsCode")
    public ResponseResult<Void> sendSmsCode(@RequestBody @Valid LoginSmsCodeForm form) {
        validateCodeManager.sendCode(assembler.convert(form));
        return ResponseResult.ok();
    }

    /**
     * 邮箱验证码
     * @param form
     * @return
     */
    @PostMapping("/sendEmailCode")
    public ResponseResult<Void> sendEmailCode(@RequestBody @Valid LoginEmailCodeForm form) {
        validateCodeManager.sendCode(assembler.convert(form));
        return ResponseResult.ok();
    }
}
