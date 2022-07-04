package com.ah.cloud.permissions.biz.infrastructure.config;

import com.ah.cloud.permissions.biz.application.strategy.selector.AlgorithmEncryptorSelector;
import com.ah.cloud.permissions.biz.infrastructure.jasypt.PermissionEncryptor;
import com.ah.cloud.permissions.biz.infrastructure.jasypt.PermissionPropertyDetector;
import com.ulisesbocchio.jasyptspringboot.annotation.ConditionalOnMissingBean;
import com.ulisesbocchio.jasyptspringboot.properties.JasyptEncryptorConfigurationProperties;
import org.jasypt.encryption.StringEncryptor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-04 10:56
 **/
@Configuration
@EnableConfigurationProperties(JasyptEncryptorConfigurationProperties.class)
public class JasyptConfiguration {

    /**
     * 加载自定义加密校验器
     * @return
     */
    @Bean(name = "encryptablePropertyDetector")
    @ConditionalOnMissingBean
    public PermissionPropertyDetector encryptablePropertyDetector() {
        return new PermissionPropertyDetector();
    }

    /**
     * 注入加密解密器
     * @param properties
     * @return
     */
    @Bean(name = "jasyptStringEncryptor")
    @Primary
    public StringEncryptor stringEncryptor(JasyptEncryptorConfigurationProperties properties, AlgorithmEncryptorSelector algorithmEncryptorSelector) {
        return new PermissionEncryptor(algorithmEncryptorSelector, properties);
    }


//    /**
//     * 注入资源期
//     * @return
//     */
//    @Bean
//    public EncryptablePropertyResolver encryptablePropertyResolver() {
//        return new PermissionEncryptablePropertyResolver(encryptablePropertyDetector());
//    }

}
