package com.ah.cloud.permissions.api.controller;

import com.ah.cloud.permissions.biz.application.manager.login.HandleManager;
import com.ah.cloud.permissions.biz.domain.login.UsernamePasswordLoginForm;
import com.ah.cloud.permissions.biz.domain.login.ValidateCodeLoginForm;
import com.ah.cloud.permissions.biz.domain.token.AccessToken;
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
    private HandleManager handleManager;

    /**
     * 账号密码登录
     * @param form
     * @return
     */
    @PostMapping("/usernamePasswordLogin")
    public ResponseResult<AccessToken> usernamePasswordLogin(@RequestBody @Valid UsernamePasswordLoginForm form) {
        return ResponseResult.ok(handleManager.getAccessToken(form));
    }

    /**
     * 验证码登录
     * @param form
     * @return
     */
    @PostMapping("/validateCodeLogin")
    public ResponseResult<AccessToken> validateCodeLogin(@RequestBody @Valid ValidateCodeLoginForm form) {
        return ResponseResult.ok(handleManager.getAccessToken(form));
    }
}
