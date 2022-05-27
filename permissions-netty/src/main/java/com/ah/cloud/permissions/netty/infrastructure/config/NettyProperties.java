package com.ah.cloud.permissions.netty.infrastructure.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-05-16 22:52
 **/
@Data
@Component
@ConfigurationProperties(value = "netty")
public class NettyProperties {

    /**
     * 当前端口
     */
    private Integer port;
}
