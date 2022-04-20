package com.ah.cloud.permissions.biz.application.manager.login;

import com.ah.cloud.permissions.biz.application.helper.AuthenticationHelper;
import com.ah.cloud.permissions.biz.domain.login.LoginForm;
import com.ah.cloud.permissions.biz.domain.login.UsernamePasswordLoginForm;
import com.ah.cloud.permissions.biz.domain.token.AccessToken;
import com.ah.cloud.permissions.biz.domain.user.LocalUser;
import com.ah.cloud.permissions.biz.infrastructure.security.service.TokenService;
import com.ah.cloud.permissions.biz.infrastructure.security.service.WebSecurityTokenService;
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
    @Resource
    private WebSecurityTokenService webSecurityTokenService;



    @Override
    protected UsernamePasswordAuthenticationToken buildAuthentication(LoginForm loginForm) {
        return authenticationHelper.buildUsernamePasswordAuthenticationToken((UsernamePasswordLoginForm) loginForm);
    }

    @Override
    public TokenService<AccessToken, LocalUser> getTokenService() {
        return webSecurityTokenService;
    }

    @Override
    public boolean support(LoginForm loginForm) {
        return UsernamePasswordLoginForm.class.isAssignableFrom(loginForm.getClass());
    }
}
