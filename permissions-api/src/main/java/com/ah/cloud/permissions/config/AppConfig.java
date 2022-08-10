package com.ah.cloud.permissions.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-28 17:25
 **/
@Configuration
public class AppConfig implements WebMvcConfigurer {


    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 拦截所有的请求
        registry.addMapping("/**")
                //可跨域的域名，可以为 *
                .allowedOrigins("http://localhost:15000")
                .allowCredentials(true)
                //允许跨域的方法，可以单独配置
                .allowedMethods("*")
                //允许跨域的请求头，可以单独配置
                .allowedHeaders("*");
    }
}
