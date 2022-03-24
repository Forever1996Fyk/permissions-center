package com.ah.cloud.permissions.biz.application.manager.login;

import com.ah.cloud.permissions.biz.application.helper.AuthenticationHelper;
import com.ah.cloud.permissions.biz.domain.login.LoginForm;
import com.ah.cloud.permissions.biz.domain.login.ValidateCodeLoginForm;
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
public class ValidateCodeLoginManager extends AbstractLoginManager<ValidateCodeAuthenticationToken> {
    @Resource
    private AuthenticationHelper authenticationHelper;

    @Override
    protected ValidateCodeAuthenticationToken buildAuthentication(LoginForm loginForm) {
        return authenticationHelper.buildValidateCodeAuthenticationToken((ValidateCodeLoginForm) loginForm);
    }

    @Override
    public boolean support(LoginForm loginForm) {
        return ValidateCodeLoginForm.class.isAssignableFrom(loginForm.getClass());
    }
}
