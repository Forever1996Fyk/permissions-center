package com.ah.cloud.permissions.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @program: permissions-center
 * @description: Security配置类
 * @author: YuKai Fan
 * @create: 2021-12-17 15:22
 **/
@Configuration
/*
查看其注解源码，主要是引用WebSecurityConfiguration.class 和 加入了@EnableGlobalAuthentication 注解 ，这里就不介绍了，我们只要明白添加 @EnableWebSecurity 注解将开启 Security 功能。
 */
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 禁用session
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                // 禁用 cors和csrf
                .cors().and()
                .csrf().disable()
                // 调整为让 Spring Security 不创建和使用 session, 因为前后端分离项目, 使用token验证方式, 不需要session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                // 可认证访问接口
                .anyRequest()
                // 自定义接口权限认证
                .access("@accessManager.access(request, authentication)")
                .and()
                .headers().frameOptions().disable();
    }
}
