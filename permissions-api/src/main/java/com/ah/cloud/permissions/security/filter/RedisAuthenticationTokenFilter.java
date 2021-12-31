package com.ah.cloud.permissions.security.filter;

import com.ah.cloud.permissions.biz.infrastructure.util.JsonUtils;
import com.ah.cloud.permissions.security.application.hepler.AuthenticationHelper;
import com.ah.cloud.permissions.security.application.manager.TokenManager;
import com.ah.cloud.permissions.security.domain.user.LocalUser;
import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
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
@Slf4j
@Component
public class RedisAuthenticationTokenFilter extends OncePerRequestFilter {
    @Autowired
    private TokenManager tokenManager;
    @Autowired
    private AuthenticationHelper authenticationHelper;
    /**
     * 验证码校验失败处理器
     */
    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        /*
        从 request 中提取到token，获取当前登录用户
         */
        try {
            LocalUser localUser = tokenManager.getLocalUserByRequest(request);
            log.info("RedisAuthenticationTokenFilter[doFilterInternal] ==> 根据token获取当前登录用户信息 ==> localUser:{}", JsonUtils.toJSONString(localUser));
            if (ObjectUtils.isNotEmpty(localUser) && ObjectUtils.isEmpty(SecurityContextHolder.getContext().getAuthentication())){
                tokenManager.refreshToken(localUser.getAccessToken());
                UsernamePasswordAuthenticationToken authenticationToken = authenticationHelper.buildUsernamePasswordAuthenticationToken(localUser);
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        } catch (AuthenticationException e) {
            log.error("RedisAuthenticationTokenFilter[doFilterInternal] ==> 根据token认证用户信息异常 ==> exception:{}", Throwables.getStackTraceAsString(e));
            authenticationFailureHandler.onAuthenticationFailure(request, response, e);
            return;
        }
        filterChain.doFilter(request, response);
    }
}
