package com.ah.cloud.permissions.biz.application.manager.login;

import com.ah.cloud.permissions.biz.application.helper.AuthenticationHelper;
import com.ah.cloud.permissions.biz.domain.login.LoginForm;
import com.ah.cloud.permissions.biz.domain.login.UsernamePasswordLoginForm;
import com.ah.cloud.permissions.biz.domain.token.AccessToken;
import com.ah.cloud.permissions.biz.domain.user.LocalUser;
import com.ah.cloud.permissions.biz.infrastructure.security.service.impl.WebSecurityUserServiceImpl;
import com.ah.cloud.permissions.biz.infrastructure.security.service.impl.WebTokenServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
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
public class UsernamePasswordLoginManager extends AbstractLoginManager<UsernamePasswordAuthenticationToken, AccessToken, LocalUser> {
    @Resource
    private AuthenticationHelper authenticationHelper;

    @Autowired
    public UsernamePasswordLoginManager(WebSecurityUserServiceImpl webSecurityUserService, AuthenticationManager authenticationManager) {
        super(new WebTokenServiceImpl(), webSecurityUserService, authenticationManager);
    }

    @Override
    protected UsernamePasswordAuthenticationToken buildAuthentication(LoginForm loginForm) {
        return authenticationHelper.buildUsernamePasswordAuthenticationToken((UsernamePasswordLoginForm) loginForm);
    }

    @Override
    public boolean support(LoginForm loginForm) {
        return UsernamePasswordLoginForm.class.isAssignableFrom(loginForm.getClass());
    }
}
