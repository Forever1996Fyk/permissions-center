package com.ah.cloud.permissions.biz.domain.login;

import com.ah.cloud.permissions.biz.infrastructure.annotation.ParamDecrypt;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @program: permissions-center
 * @description: 账号密码登录实体
 * @author: YuKai Fan
 * @create: 2021-12-17 17:58
 **/
@Data
public class UsernamePasswordLoginForm implements LoginForm {
    /**
     * 账号
     */
    @NotBlank(message = "账号不能为空")
    private String username;

    /**
     * 密码
     */
    @ParamDecrypt
    @NotBlank(message = "密码不能为空")
    private String password;
}
