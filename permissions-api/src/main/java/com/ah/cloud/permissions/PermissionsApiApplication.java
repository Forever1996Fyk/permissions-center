package com.ah.cloud.permissions;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2021-12-03 11:56
 **/
@MapperScan(basePackages = {"com.ah.cloud.permissions.biz.infrastructure.repository.dao"} )
@SpringBootApplication(scanBasePackages = {"com.ah.cloud"})
public class PermissionsApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(PermissionsApiApplication.class, args);
    }
}
