package com.ah.cloud.permissions.biz.infrastructure.config;

import lombok.Data;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-05-31 20:30
 **/
@Getter
@Component
public class UmengMsgPushConfiguration {
    /**
     * android app key
     */
    @Value("${permission.config.msgcenter.umeng.androidAppKey}")
    private String androidAppKey;

    /**
     * android app secret
     */
    @Value(value = "${permission.config.msgcenter.umeng.androidAppMasterSecret}")
    private String androidAppMasterSecret;

    /**
     * ios app key
     */
    @Value(value = "${permission.config.msgcenter.umeng.iosAppKey}")
    private String iosAppKey;

    /**
     * ios app secret
     */
    @Value(value = "${permission.config.msgcenter.umeng.iosAppMasterSecret}")
    private String iosAppMasterSecret;
}
