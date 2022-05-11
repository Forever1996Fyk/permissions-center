package com.ah.cloud;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @program: permissions-center
 * @description: perm-server 启动类
 * @author: YuKai Fan
 * @create: 2021-12-03 11:22
 **/
@MapperScan(basePackages = {"com.ah.cloud.permissions.biz.infrastructure.repository.mapper"} )
@SpringBootApplication(scanBasePackages = {"com.ah.cloud"})
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
public class PermServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(PermServerApplication.class, args);
    }
}
