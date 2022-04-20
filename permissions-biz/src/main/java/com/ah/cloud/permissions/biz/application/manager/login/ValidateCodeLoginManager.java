package com.ah.cloud.permissions.biz.application.manager.login;

import com.ah.cloud.permissions.biz.application.helper.AuthenticationHelper;
import com.ah.cloud.permissions.biz.domain.login.LoginForm;
import com.ah.cloud.permissions.biz.domain.login.ValidateCodeLoginForm;
import com.ah.cloud.permissions.biz.domain.token.AccessToken;
import com.ah.cloud.permissions.biz.domain.user.LocalUser;
import com.ah.cloud.permissions.biz.infrastructure.security.service.TokenService;
import com.ah.cloud.permissions.biz.infrastructure.security.service.WebSecurityTokenService;
import com.ah.cloud.permissions.biz.infrastructure.security.token.ValidateCodeAuthenticationToken;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-01-09 15:58
 **/
@Component
public class ValidateCodeLoginManager extends AbstractLoginManager<ValidateCodeAuthenticationToken, AccessToken, LocalUser> {
    @Resource
    private AuthenticationHelper authenticationHelper;
    @Resource
    private WebSecurityTokenService webSecurityTokenService;

    @Override
    protected ValidateCodeAuthenticationToken buildAuthentication(LoginForm loginForm) {
        return authenticationHelper.buildValidateCodeAuthenticationToken((ValidateCodeLoginForm) loginForm);
    }

    @Override
    public TokenService<AccessToken, LocalUser> getTokenService() {
        return webSecurityTokenService;
    }

    @Override
    public boolean support(LoginForm loginForm) {
        return ValidateCodeLoginForm.class.isAssignableFrom(loginForm.getClass());
    }
}
