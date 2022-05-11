package com.ah.cloud.permissions.biz.infrastructure.config;

import com.ah.cloud.permissions.biz.infrastructure.security.filter.RedisAuthenticationTokenFilter;
import com.ah.cloud.permissions.biz.infrastructure.security.handler.AccessDeniedHandlerImpl;
import com.ah.cloud.permissions.biz.infrastructure.security.handler.AuthenticationEntryPointImpl;
import com.ah.cloud.permissions.biz.infrastructure.security.handler.LogoutSuccessHandlerImpl;
import com.ah.cloud.permissions.biz.infrastructure.security.provider.ValidateCodeAuthenticationProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationEntryPointFailureHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;

/**
 * @program: permissions-center
 * @description: Security配置类
 * 查看其注解源码，主要是引用WebSecurityConfiguration.class 和 加入了@EnableGlobalAuthentication 注解 ，这里就不介绍了，我们只要明白添加 @EnableWebSecurity 注解将开启 Security 功能。
 * @author: YuKai Fan
 * @create: 2021-12-17 15:22
 **/
@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    /**
     * 退出处理类
     */
    @Resource
    private LogoutSuccessHandlerImpl logoutSuccessHandler;

    /**
     * 认证失败处理类
     */
    @Resource
    private AuthenticationEntryPointImpl unauthorizedHandler;

    /**
     * 无权限处理类
     */
    @Resource
    private AccessDeniedHandlerImpl accessDeniedHandler;

    /**
     * 默认的账号密码登录校验
     */
    @Resource(name="validateCodeUserDetailsServiceImpl")
    private UserDetailsService validateCodeUserDetailsService;

    /**
     * 默认的账号密码登录校验
     */
    @Resource(name="userDetailsServiceImpl")
    private UserDetailsService usernamePasswordUserDetailsService;

    /**
     * token 验证过滤器
     */
    @Resource
    private RedisAuthenticationTokenFilter authenticationTokenFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 禁用session
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                // 禁用 cors和csrf
                .cors().and()
                .csrf().disable()
                // 认证失败处理类
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                .exceptionHandling().accessDeniedHandler(accessDeniedHandler).and()
                // 调整为让 Spring Security 不创建和使用 session, 因为前后端分离项目, 使用token验证方式, 不需要session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                // 可认证访问接口
                .anyRequest()
                // 自定义接口权限认证
                .access("@accessManager.access(request, authentication)")
                .and()
                .headers().frameOptions().disable();

        http.logout().logoutUrl("/logout").logoutSuccessHandler(logoutSuccessHandler);

        // 添加Redis filter
        http.addFilterBefore(authenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
    }

    /**
     * 密码校验，从数据库中校验密码
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
        auth.authenticationProvider(validateCodeAuthenticationProvider());
    }

    /**
     *
     * @return
     * @throws Exception
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    /**
     * 使用 BCryptPasswordEncoder 进行密码加密
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new AuthenticationEntryPointFailureHandler(unauthorizedHandler);
    }

    /**
     * 重新注入DaoAuthenticationProvider
     *
     * 设置setHideUserNotFoundExceptions为false, 可以抛出UsernameNotFoundException
     * @return
     */
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setHideUserNotFoundExceptions(false);
        daoAuthenticationProvider.setUserDetailsService(usernamePasswordUserDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }


    /**
     * 重新注入DaoAuthenticationProvider
     *
     * 设置setHideUserNotFoundExceptions为false, 可以抛出UsernameNotFoundException
     * @return
     */
    @Bean
    public ValidateCodeAuthenticationProvider validateCodeAuthenticationProvider() {
        ValidateCodeAuthenticationProvider validateCodeAuthenticationProvider = new ValidateCodeAuthenticationProvider();
        validateCodeAuthenticationProvider.setUserDetailsService(validateCodeUserDetailsService);
        return validateCodeAuthenticationProvider;
    }
}
