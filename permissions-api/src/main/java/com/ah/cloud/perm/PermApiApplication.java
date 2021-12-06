package com.ah.cloud.perm;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2021-12-03 11:56
 **/
@MapperScan(basePackages = {"com.ah.cloud.perm.biz.infrastructure.repository.dao"} )
@SpringBootApplication(scanBasePackages = {"com.ah.cloud"})
public class PermApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(PermApiApplication.class, args);
    }
}
