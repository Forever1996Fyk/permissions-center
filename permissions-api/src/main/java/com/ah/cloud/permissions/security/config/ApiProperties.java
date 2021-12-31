package com.ah.cloud.permissions.security.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2021-12-24 14:27
 **/
@Data
@Component
@Configuration
@ConfigurationProperties(prefix = "security.api")
public class ApiProperties {

    /**
     * 认证放行
     */
    private Set<String> permitAll;
}
