package com.ah.cloud.permissions.biz.infrastructure.security.filter;

import com.ah.cloud.permissions.biz.infrastructure.constant.PermissionsConstants;
import com.ah.cloud.permissions.biz.infrastructure.security.service.AuthenticationTokenFilterService;
import com.ah.cloud.permissions.enums.RequestSourceEnum;
import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 * @program: permissions-center
 * @description: 请求验证过滤器
 * @author: YuKai Fan
 * @create: 2021-12-22 15:28
 **/
@Slf4j
public class RedisAuthenticationTokenFilter extends OncePerRequestFilter {
    /**
     * 校验失败处理器
     */
    private final AuthenticationFailureHandler authenticationFailureHandler;

    private final AuthenticationTokenFilterService authenticationTokenFilterService;

    public RedisAuthenticationTokenFilter(AuthenticationFailureHandler authenticationFailureHandler, AuthenticationTokenFilterService authenticationTokenFilterService) {
        this.authenticationFailureHandler = authenticationFailureHandler;
        this.authenticationTokenFilterService = authenticationTokenFilterService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        /*
        从 request 中提取到token，获取当前登录用户
         */
        try {
            String requestSource = request.getHeader(PermissionsConstants.REQUEST_SOURCE_HEAD);
            if (StringUtils.isBlank(requestSource)) {
                return;
            }
            if (Objects.isNull(this.authenticationTokenFilterService)) {
                return;
            }
            this.authenticationTokenFilterService.process(request, response);
        }  catch (AuthenticationException e) {
            log.error("RedisAuthenticationTokenFilter[doFilterInternal] 根据token认证用户信息异常 exception:{}", Throwables.getStackTraceAsString(e));
            this.authenticationFailureHandler.onAuthenticationFailure(request, response, e);
            return;
        }
        filterChain.doFilter(request, response);
    }
}
