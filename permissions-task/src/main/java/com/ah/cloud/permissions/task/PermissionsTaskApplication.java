package com.ah.cloud.permissions.task;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-05-05 21:24
 **/
@MapperScan(basePackages = {"com.ah.cloud.biz.infrastructure.repository.dao"} )
@SpringBootApplication(scanBasePackages = {"com.ah.cloud"})
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
public class PermissionsTaskApplication {

    public static void main(String[] args) {
        SpringApplication.run(PermissionsTaskApplication.class, args);
    }
}
