package com.ah.cloud.permissions.biz.application.manager;

import com.ah.cloud.permissions.biz.domain.login.UsernamePasswordLoginForm;
import com.ah.cloud.permissions.biz.application.helper.AuthenticationHelper;
import com.ah.cloud.permissions.biz.domain.token.AccessToken;
import com.ah.cloud.permissions.biz.domain.user.LocalUser;
import com.ah.cloud.permissions.biz.infrastructure.security.service.SecurityTokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

/**
 * @program: permissions-center
 * @description: 登录管理器
 * @author: YuKai Fan
 * @create: 2021-12-17 16:33
 **/
@Slf4j
@Component
public class LoginManager {
    @Autowired
    private SecurityTokenService securityTokenService;
    @Autowired
    private AuthenticationHelper authenticationHelper;
    @Autowired
    private AuthenticationManager authenticationManager;

    /**
     * 登录
     * @param loginForm
     * @return
     */
    public AccessToken getAccessToken(UsernamePasswordLoginForm loginForm) {
        UsernamePasswordAuthenticationToken token = authenticationHelper.buildUsernamePasswordAuthenticationToken(loginForm);
        Authentication authenticate = authenticationManager.authenticate(token);
        LocalUser localUser = (LocalUser) authenticate.getPrincipal();
        return securityTokenService.createToken(localUser);
    }
}
