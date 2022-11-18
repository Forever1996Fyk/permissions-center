package com.ah.cloud.permissions.biz.infrastructure.third.aliyun.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022/11/17 14:51
 **/
@Data
@Component
@ConfigurationProperties(prefix = "aliyun.sms")
public class AliyunSmsProperties {

    /**
     * AccessKey ID
     */
    private String accessKey;

    /**
     * AccessKey Secret
     */
    private String accessKeySecret;

    /**
     * 访问域名
     */
    private String endpoint;
}
