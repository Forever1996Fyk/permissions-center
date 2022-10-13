package com.ah.cloud.permissions.biz.infrastructure.security.service;

import com.ah.cloud.permissions.biz.domain.token.Token;
import com.ah.cloud.permissions.biz.infrastructure.constant.TokenConstants;
import com.ah.cloud.permissions.biz.infrastructure.util.CollectionUtils;
import org.springframework.security.core.userdetails.UserDetails;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Objects;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022/9/29 16:14
 **/
public abstract class AbstractAuthenticationTokenFilterService<T extends Token, U extends UserDetails> implements AuthenticationTokenFilterService {
    private final TokenService<T, U> tokenService;
    private final SecurityUserService<U> securityUserService;


    protected AbstractAuthenticationTokenFilterService(TokenService<T, U> tokenService, SecurityUserService<U> securityUserService) {
        this.tokenService = tokenService;
        this.securityUserService = securityUserService;
    }

    @Override
    public void process(HttpServletRequest request, HttpServletResponse response) {
        /*
        1. 获取token (如果是jwt的类型，则是jwt生成token)
        2. 根据token获取当前登录人信息
            2.1 如果是jwt token则需要解析jwt, 获取原token数据和请求来源
            2.2 根据请求来源和token从redis中获取当前登录人信息
         */
        Map<String, String> tokenMap = tokenService.getToken(request);
        if (CollectionUtils.isEmpty(tokenMap) || !tokenMap.containsKey(TokenConstants.TOKEN_MAP_KEY)) {
            return;
        }
        String token = tokenMap.get(TokenConstants.TOKEN_MAP_KEY);
        U userDetails = securityUserService.getUserDetails(token);
        if (Objects.nonNull(userDetails)) {
            securityUserService.authenticationUserDetails(userDetails);
        }
    }
}
