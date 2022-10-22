package com.ah.cloud.permissions.biz.infrastructure.security.config;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import java.util.Objects;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022/10/10 21:45
 **/
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
public abstract class AbstractWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

    /**
     * 无权限处理类
     */
    private AccessDeniedHandler accessDeniedHandler;

    /**
     * 退出处理类
     */
    private LogoutSuccessHandler logoutSuccessHandler;

    /**
     * 认证失败处理类
     */
    private AuthenticationEntryPoint unauthorizedHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 禁用session
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                // 禁用 cors和csrf
                .cors().and()
                .csrf().disable()
                // 认证失败处理类
                // 调整为让 Spring Security 不创建和使用 session, 因为前后端分离项目, 使用token验证方式, 不需要session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                // 可认证访问接口
                .anyRequest()
                // 自定义接口权限认证
                .access("@accessManager.access(request, authentication)")
                .and()
                .headers().frameOptions().disable();

        if (Objects.nonNull(unauthorizedHandler)) {
            http.exceptionHandling().authenticationEntryPoint(unauthorizedHandler);
        }

        if (Objects.nonNull(unauthorizedHandler)) {
            http.exceptionHandling().accessDeniedHandler(accessDeniedHandler);
        }

        if (Objects.nonNull(logoutSuccessHandler)) {
            http.logout().logoutUrl(getLogoutUrl()).logoutSuccessHandler(logoutSuccessHandler);
        }
        addFilter(http);
    }

    /**
     * 这个是Spring Security 专门用于注入AuthenticationManager Bean的入口
     *
     * @return
     * @throws Exception
     */
    @Bean(name = "authenticationManager")
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * 添加额外的过滤器
     * @param httpSecurity
     */
    protected void addFilter(HttpSecurity httpSecurity) {}

    /**
     * 退出登录url
     * @return
     */
    protected String getLogoutUrl() {
        return "/logout";
    }
}
