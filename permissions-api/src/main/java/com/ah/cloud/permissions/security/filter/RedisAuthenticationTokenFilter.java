package com.ah.cloud.permissions.security.filter;

import com.ah.cloud.permissions.security.application.hepler.AuthenticationHelper;
import com.ah.cloud.permissions.security.application.manager.TokenManager;
import com.ah.cloud.permissions.security.domain.user.LocalUser;
import com.ah.cloud.permissions.security.service.SecurityTokenService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @program: permissions-center
 * @description: 请求验证过滤器
 * @author: YuKai Fan
 * @create: 2021-12-22 15:28
 **/
@Component
public class RedisAuthenticationTokenFilter extends OncePerRequestFilter {
    @Autowired
    private TokenManager tokenManager;
    @Autowired
    private AuthenticationHelper authenticationHelper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        /*
        从 request 中提取到token，获取当前登录用户
         */
        LocalUser localUser = tokenManager.getLocalUserByRequest(request);
        if (ObjectUtils.isNotEmpty(localUser) && ObjectUtils.isEmpty(SecurityContextHolder.getContext().getAuthentication())){
            tokenManager.refreshToken(localUser.getAccessToken());
            UsernamePasswordAuthenticationToken authenticationToken = authenticationHelper.buildUsernamePasswordAuthenticationToken(localUser);
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        filterChain.doFilter(request, response);
    }
}
