package com.ah.cloud.permissions.biz.infrastructure.security.filter;

import com.ah.cloud.permissions.biz.application.helper.AuthenticationHelper;
import com.ah.cloud.permissions.biz.application.manager.AccessManager;
import com.ah.cloud.permissions.biz.application.manager.TokenManager;
import com.ah.cloud.permissions.biz.application.manager.UserAuthManager;
import com.ah.cloud.permissions.biz.domain.user.LocalUser;
import com.ah.cloud.permissions.biz.infrastructure.exception.SecurityErrorException;
import com.ah.cloud.permissions.biz.infrastructure.exception.UserAccountErrorException;
import com.ah.cloud.permissions.biz.infrastructure.security.token.InMemoryAuthenticationToken;
import com.ah.cloud.permissions.biz.infrastructure.util.JsonUtils;
import com.ah.cloud.permissions.enums.common.ErrorCodeEnum;
import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * @program: permissions-center
 * @description: 请求验证过滤器
 * @author: YuKai Fan
 * @create: 2021-12-22 15:28
 **/
@Slf4j
@Component
public class RedisAuthenticationTokenFilter extends OncePerRequestFilter {
    @Resource
    private TokenManager tokenManager;
    @Resource
    private AccessManager accessManager;
    @Resource
    private AuthenticationHelper authenticationHelper;
    /**
     * 验证码校验失败处理器
     */
//    @Resource
//    private AuthenticationFailureHandler authenticationFailureHandler;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        /*
        从 request 中提取到token，获取当前登录用户
         */
        try {
            String token = tokenManager.getToken(request);
            LocalUser localUser = tokenManager.getLocalUserByToken(token);
            log.info("RedisAuthenticationTokenFilter[doFilterInternal] 根据token获取当前登录用户信息 localUser:{}", JsonUtils.toJSONString(localUser));
            if (ObjectUtils.isNotEmpty(localUser)) {
                // 重新刷新token过期时间
                tokenManager.refreshToken(token);
                // 判断当前登录人信息是否可用
                if (!localUser.isEnabled()) {
                    throw new SecurityErrorException(ErrorCodeEnum.AUTHENTICATION_ACCOUNT_DISABLED);
                }
                // 判断当前上下文是否存储认证信息
                if (Objects.isNull(SecurityContextHolder.getContext().getAuthentication()) || accessManager.isUserAuthChanged(localUser)) {
                    InMemoryAuthenticationToken authenticationToken = authenticationHelper.buildInMemoryAuthenticationToken(localUser);
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    /*
                    设置到上下文 方便获取当前登录人信息 可自定义策略SecurityContextHolderStrategy
                    通过spring.security.strategy 设置对应的策略类全限定名(类路径)即可
                     */
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    accessManager.resetUserAuthChangedMark(localUser, false);
                }
            }
        }  catch (AuthenticationException e) {
            log.error("RedisAuthenticationTokenFilter[doFilterInternal] 根据token认证用户信息异常 exception:{}", Throwables.getStackTraceAsString(e));
//            authenticationFailureHandler.onAuthenticationFailure(request, response, e);
            return;
        }
        filterChain.doFilter(request, response);
    }
}
