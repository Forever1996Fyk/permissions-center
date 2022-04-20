package com.ah.cloud.permissions.biz.application.manager.login;

import com.ah.cloud.permissions.biz.domain.login.LoginForm;
import com.ah.cloud.permissions.biz.domain.token.Token;
import com.ah.cloud.permissions.biz.infrastructure.security.service.TokenService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-01-09 15:36
 **/
@Component
public abstract class AbstractLoginManager<A extends Authentication, T extends Token, U extends UserDetails> implements LoginManager {
    @Resource
    private AuthenticationManager authenticationManager;

    @Override
    public Token getToken(LoginForm loginForm) {
        A authentication = buildAuthentication(loginForm);
        Authentication authenticate = authenticationManager.authenticate(authentication);
        U userDetails = (U) authenticate.getPrincipal();
        return getTokenService().createToken(userDetails);
    }

    /**
     * 构建认证数据
     * @param loginForm
     * @return
     */
    protected abstract A buildAuthentication(LoginForm loginForm);

    /**
     * 获取TokenService
     * @return
     */
    public abstract TokenService<T, U> getTokenService();
}
