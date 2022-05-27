package com.ah.cloud.permissions;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-05-11 21:55
 **/
@MapperScan(basePackages = {"com.ah.cloud.permissions.biz.infrastructure.repository.mapper"} )
@SpringBootApplication(scanBasePackages = {"com.ah.cloud"})
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
public class PermissionsNettyApplication {

    public static void main(String[] args) {
        SpringApplication.run(PermissionsNettyApplication.class, args);
    }
}
