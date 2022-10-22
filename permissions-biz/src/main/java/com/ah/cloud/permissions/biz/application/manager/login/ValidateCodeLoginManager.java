package com.ah.cloud.permissions.biz.application.manager.login;

import com.ah.cloud.permissions.biz.application.helper.AuthenticationHelper;
import com.ah.cloud.permissions.biz.domain.login.LoginForm;
import com.ah.cloud.permissions.biz.domain.login.ValidateCodeLoginForm;
import com.ah.cloud.permissions.biz.domain.token.AccessToken;
import com.ah.cloud.permissions.biz.domain.user.LocalUser;
import com.ah.cloud.permissions.biz.infrastructure.security.service.impl.WebSecurityUserServiceImpl;
import com.ah.cloud.permissions.biz.infrastructure.security.service.impl.WebTokenServiceImpl;
import com.ah.cloud.permissions.biz.infrastructure.security.token.ValidateCodeAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
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

    public ValidateCodeLoginManager(WebSecurityUserServiceImpl webSecurityUserService, AuthenticationManager authenticationManager) {
        super(new WebTokenServiceImpl(), webSecurityUserService, authenticationManager);
    }

    @Override
    protected ValidateCodeAuthenticationToken buildAuthentication(LoginForm loginForm) {
        return authenticationHelper.buildValidateCodeAuthenticationToken((ValidateCodeLoginForm) loginForm);
    }

    @Override
    public boolean support(LoginForm loginForm) {
        return ValidateCodeLoginForm.class.isAssignableFrom(loginForm.getClass());
    }
}
