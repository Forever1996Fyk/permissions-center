package com.ah.cloud.permissions.config;

import com.ah.cloud.permissions.biz.infrastructure.security.config.AbstractWebSecurityConfigurerAdapter;
import com.ah.cloud.permissions.biz.infrastructure.security.filter.RedisAuthenticationTokenFilter;
import com.ah.cloud.permissions.biz.infrastructure.security.handler.AccessDeniedHandlerImpl;
import com.ah.cloud.permissions.biz.infrastructure.security.handler.AuthenticationEntryPointImpl;
import com.ah.cloud.permissions.biz.infrastructure.security.handler.impl.WebLogoutSuccessHandlerImpl;
import com.ah.cloud.permissions.biz.infrastructure.security.provider.ValidateCodeAuthenticationProvider;
import com.ah.cloud.permissions.biz.infrastructure.security.service.AuthenticationTokenFilterService;
import com.ah.cloud.permissions.biz.infrastructure.security.service.impl.WebAuthenticationTokenFilterServiceImpl;
import com.ah.cloud.permissions.filter.SysModeFilter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationEntryPointFailureHandler;
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
@NoArgsConstructor
public class WebSecurityConfiguration extends AbstractWebSecurityConfigurerAdapter {

    /**
     * 系统mode 验证过滤器
     */
    @Lazy
    @Resource
    private SysModeFilter sysModeFilter;

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

    private AuthenticationEntryPoint authenticationEntryPoint;

    private AuthenticationTokenFilterService authenticationTokenFilterService;

    /**
     * 注入需要的处理器
     * @param accessDeniedHandler
     * @param unauthorizedHandler
     * @param logoutSuccessHandler
     */
    @Autowired
    public WebSecurityConfiguration(AccessDeniedHandlerImpl accessDeniedHandler, AuthenticationEntryPointImpl unauthorizedHandler, WebLogoutSuccessHandlerImpl logoutSuccessHandler, WebAuthenticationTokenFilterServiceImpl authenticationTokenFilterService) {
        super(accessDeniedHandler, logoutSuccessHandler, unauthorizedHandler);
        this.authenticationEntryPoint = unauthorizedHandler;
        this.authenticationTokenFilterService = authenticationTokenFilterService;
    }

    /**
     * 添加自定义过滤器
     * @param httpSecurity
     */
    @Override
    protected void addFilter(HttpSecurity httpSecurity) {
        AuthenticationEntryPointFailureHandler failureHandler = new AuthenticationEntryPointFailureHandler(this.authenticationEntryPoint);
        RedisAuthenticationTokenFilter redisAuthenticationTokenFilter = new RedisAuthenticationTokenFilter(failureHandler, this.authenticationTokenFilterService);
        httpSecurity.addFilterBefore(redisAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
        httpSecurity.addFilterBefore(sysModeFilter, RedisAuthenticationTokenFilter.class);
    }

    /**
     * 配置认证提供者 AuthenticationProvider
     *
     *  重写此类的目的是为了 添加自定义的AuthenticationProvider, 从而添加到对应的集合中, 在注入到ProviderManager里, 完成自定义功能
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
        auth.authenticationProvider(validateCodeAuthenticationProvider());
    }

    /**
     * 这段可以看源码, super.authenticationManager()其实是调用我们上面重写的 configure(AuthenticationManagerBuilder auth)
     * 所以最终代码会走到 this.authenticationManager = this.localConfigureAuthenticationBldr.build();
     * 在深入源码看，会走到 AuthenticationManagerBuilder.performBuild()方法，而此方法构造出来的对象其实是 AuthenticationManager的一个实现类  ProviderManager
     * 而 ProviderManager 是真正根据 Authentication 获取到对应的 AuthenticationProvider 的实现类
     * AuthenticationProvider 则是处理调用UserDetailsService的入口
     *
     * @Bean 这里就可以不用@Bean注解注入，
     * 因为父类AbstractWebSecurityConfigurerAdapter实现了super.authenticationManagerBean()的注入
     * @return
     * @throws Exception
     */
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

    /**
     * 重新注入DaoAuthenticationProvider
     *
     * 设置setHideUserNotFoundExceptions为false, 可以抛出UsernameNotFoundException
     * @return
     */
    private DaoAuthenticationProvider daoAuthenticationProvider() {
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
    private ValidateCodeAuthenticationProvider validateCodeAuthenticationProvider() {
        ValidateCodeAuthenticationProvider validateCodeAuthenticationProvider = new ValidateCodeAuthenticationProvider();
        validateCodeAuthenticationProvider.setUserDetailsService(validateCodeUserDetailsService);
        return validateCodeAuthenticationProvider;
    }
}
