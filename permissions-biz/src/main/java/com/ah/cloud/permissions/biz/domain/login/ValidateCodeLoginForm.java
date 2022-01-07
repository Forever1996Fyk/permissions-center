package com.ah.cloud.permissions.biz.domain.login;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @program: permissions-center
 * @description: 验证码登录
 * @author: YuKai Fan
 * @create: 2022-01-05 22:15
 **/
@Data
public class ValidateCodeLoginForm {

    /**
     * 发送账号
     */
    @NotEmpty(message = "发送账号不能为空")
    private String sender;

    /**
     * 验证码
     */
    @NotEmpty(message = "验证码不能为空")
    private String validateCode;
}
