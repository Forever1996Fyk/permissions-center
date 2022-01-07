package com.ah.cloud.permissions.api.controller;

import com.ah.cloud.permissions.domain.common.ResponseResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: permissions-center
 * @description: 验证码接口
 * @author: YuKai Fan
 * @create: 2022-01-06 21:29
 **/
@RestController
@RequestMapping("/validateCode")
public class ValidateCodeController {

    @PostMapping("/sendSmsCode")
    public ResponseResult sendSmsCode() {
        return ResponseResult.ok();
    }

    @PostMapping("/sendEmailCode")
    public ResponseResult sendEmailCode() {
        return ResponseResult.ok();
    }
}
