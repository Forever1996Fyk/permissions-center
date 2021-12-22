package com.ah.cloud.permissions.security.application.hepler;

import com.ah.cloud.permissions.biz.domain.login.UsernamePasswordLoginForm;
import com.ah.cloud.permissions.security.domain.user.LocalUser;
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

    public UsernamePasswordAuthenticationToken buildUsernamePasswordAuthenticationToken(LocalUser localUser) {
        return new UsernamePasswordAuthenticationToken(localUser, null, localUser.getAuthorities());
    }
}
