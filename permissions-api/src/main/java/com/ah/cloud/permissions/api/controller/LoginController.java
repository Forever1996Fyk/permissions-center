package com.ah.cloud.permissions.api.controller;

import com.ah.cloud.permissions.api.assembler.ValidateCodeAssembler;
import com.ah.cloud.permissions.biz.application.manager.ValidateCodeManager;
import com.ah.cloud.permissions.biz.application.manager.login.LoginProvider;
import com.ah.cloud.permissions.biz.domain.code.ValidateResult;
import com.ah.cloud.permissions.biz.domain.login.UsernamePasswordLoginForm;
import com.ah.cloud.permissions.biz.domain.login.ValidateCodeLoginForm;
import com.ah.cloud.permissions.biz.domain.token.AccessToken;
import com.ah.cloud.permissions.biz.domain.token.Token;
import com.ah.cloud.permissions.biz.infrastructure.annotation.EnableDecrypt;
import com.ah.cloud.permissions.biz.infrastructure.annotation.ParamsDecrypt;
import com.ah.cloud.permissions.domain.common.ResponseResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @program: permissions-center
 * @description: 登录controller
 * @author: YuKai Fan
 * @create: 2021-12-17 17:56
 **/
@RestController
@RequestMapping("/login")
public class LoginController {
    @Resource
    private LoginProvider loginProvider;
    @Resource
    private ValidateCodeAssembler assembler;
    @Resource
    private ValidateCodeManager validateCodeManager;

    /**
     * 账号密码登录
     * @param form
     * @return
     */
    @EnableDecrypt
    @PostMapping("/usernamePasswordLogin")
    public ResponseResult<Token> usernamePasswordLogin(@RequestBody @Valid @ParamsDecrypt(scope = ParamsDecrypt.DecryptScope.ALL) UsernamePasswordLoginForm form) {
        return ResponseResult.ok(loginProvider.getAccessToken(form));
    }

    /**
     * 验证码登录
     * @param form
     * @return
     */
    @PostMapping("/validateCodeLogin")
    public ResponseResult<Token> validateCodeLogin(@RequestBody @Valid ValidateCodeLoginForm form) {
        ValidateResult<Token> result = validateCodeManager.validateCode(
                assembler.convert(form.getSender())
                , form.getValidateCode()
                , () -> loginProvider.getAccessToken(form));
        return ResponseResult.ok(result.getData());
    }
}
