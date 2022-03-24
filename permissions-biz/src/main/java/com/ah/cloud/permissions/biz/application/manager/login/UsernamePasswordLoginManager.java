package com.ah.cloud.permissions.biz.application.manager.login;

import com.ah.cloud.permissions.biz.application.helper.AuthenticationHelper;
import com.ah.cloud.permissions.biz.domain.login.LoginForm;
import com.ah.cloud.permissions.biz.domain.login.UsernamePasswordLoginForm;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-01-09 15:39
 **/
@Component
public class UsernamePasswordLoginManager extends AbstractLoginManager<UsernamePasswordAuthenticationToken>{
    @Resource
    private AuthenticationHelper authenticationHelper;

    @Override
    protected UsernamePasswordAuthenticationToken buildAuthentication(LoginForm loginForm) {
        return authenticationHelper.buildUsernamePasswordAuthenticationToken((UsernamePasswordLoginForm) loginForm);
    }

    @Override
    public boolean support(LoginForm loginForm) {
        return UsernamePasswordLoginForm.class.isAssignableFrom(loginForm.getClass());
    }
}
