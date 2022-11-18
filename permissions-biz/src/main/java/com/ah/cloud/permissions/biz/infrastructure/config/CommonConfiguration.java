package com.ah.cloud.permissions.biz.infrastructure.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2021-12-06 14:39
 **/
@Data
@Component
@ConfigurationProperties(prefix = "permission.common")
public class CommonConfiguration {

    /**
     * 默认男生头像地址
     */
    private String maleAvatarUrl;

    /**
     * 默认女生头像地址
     */
    private String femaleAvatarUrl;
}
