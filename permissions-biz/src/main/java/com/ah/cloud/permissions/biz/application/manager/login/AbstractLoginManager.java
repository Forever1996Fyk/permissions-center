package com.ah.cloud.permissions.biz.application.manager.login;

import com.ah.cloud.permissions.biz.domain.login.LoginForm;
import com.ah.cloud.permissions.biz.domain.token.Token;
import com.ah.cloud.permissions.biz.domain.user.BaseUserDetails;
import com.ah.cloud.permissions.biz.infrastructure.security.service.SecurityUserService;
import com.ah.cloud.permissions.biz.infrastructure.security.service.TokenService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-01-09 15:36
 **/
public abstract class AbstractLoginManager<A extends Authentication, T extends Token, U extends BaseUserDetails<T>> implements LoginManager {

    private final TokenService<T, U> tokenService;
    private final SecurityUserService<U> securityUserService;
    private final AuthenticationManager authenticationManager;

    protected AbstractLoginManager(TokenService<T, U> tokenService, SecurityUserService<U> securityUserService, AuthenticationManager authenticationManager) {
        this.tokenService = tokenService;
        this.securityUserService = securityUserService;
        this.authenticationManager = authenticationManager;
    }


    @Override
    public Token getToken(LoginForm loginForm) {
        A authentication = buildAuthentication(loginForm);
        Authentication authenticate = authenticationManager.authenticate(authentication);
        U userDetails = (U) authenticate.getPrincipal();
        T token = tokenService.createToken(userDetails);
        securityUserService.saveUserDetails(userDetails);
        return token;
    }

    /**
     * 构建认证数据
     *
     * @param loginForm
     * @return
     */
    protected abstract A buildAuthentication(LoginForm loginForm);
}
