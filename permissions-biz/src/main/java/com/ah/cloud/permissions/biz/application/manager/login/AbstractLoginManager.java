package com.ah.cloud.permissions.biz.application.manager.login;

import com.ah.cloud.permissions.biz.application.helper.AuthenticationHelper;
import com.ah.cloud.permissions.biz.domain.login.LoginForm;
import com.ah.cloud.permissions.biz.domain.token.AccessToken;
import com.ah.cloud.permissions.biz.domain.user.LocalUser;
import com.ah.cloud.permissions.biz.infrastructure.security.service.SecurityTokenService;
import com.baomidou.mybatisplus.extension.api.R;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-01-09 15:36
 **/
@Component
public abstract class AbstractLoginManager<T extends Authentication> implements LoginManager {
    @Resource
    private SecurityTokenService securityTokenService;
    @Resource
    private AuthenticationManager authenticationManager;

    @Override
    public AccessToken getAccessToken(LoginForm loginForm) {
        T authentication = buildAuthentication(loginForm);
        Authentication authenticate = authenticationManager.authenticate(authentication);
        LocalUser localUser = (LocalUser) authenticate.getPrincipal();
        return securityTokenService.createToken(localUser);
    }

    protected abstract T buildAuthentication(LoginForm loginForm);
}
