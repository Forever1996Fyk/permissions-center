package com.ah.cloud.permissions.biz.infrastructure.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022/10/16 10:13
 **/
@Data
@Configuration
@ConfigurationProperties(prefix = "minio")
public class MinioConfiguration {

    /**
     * 上传地址
     */
    private String endpoint;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 访问uri
     */
    private String accessUri;

}
