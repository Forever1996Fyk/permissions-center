package com.ah.cloud.permissions.biz.application.helper;

import com.ah.cloud.permissions.biz.domain.login.UsernamePasswordLoginForm;
import com.ah.cloud.permissions.biz.domain.login.ValidateCodeLoginForm;
import com.ah.cloud.permissions.biz.domain.user.LocalUser;
import com.ah.cloud.permissions.biz.infrastructure.security.token.ValidateCodeAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2021-12-17 18:02
 **/
@Component
public class AuthenticationHelper {

    /**
     * 构建密码认证token
     * @param form
     * @return
     */
    public UsernamePasswordAuthenticationToken buildUsernamePasswordAuthenticationToken(UsernamePasswordLoginForm form) {
        return new UsernamePasswordAuthenticationToken(form.getUsername(), form.getPassword());
    }

    /**
     * 构建密码认证token
     * @param localUser
     * @return
     */
    public UsernamePasswordAuthenticationToken buildUsernamePasswordAuthenticationToken(LocalUser localUser) {
        return new UsernamePasswordAuthenticationToken(localUser, null, localUser.getAuthorities());
    }

    /**
     * 构建密码认证token
     * @param form
     * @return
     */
    public ValidateCodeAuthenticationToken buildValidateCodeAuthenticationToken(ValidateCodeLoginForm form) {
        return new ValidateCodeAuthenticationToken(form.getSender(), form.getValidateCode());
    }
}
